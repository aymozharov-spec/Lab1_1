package by.psu.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class CustomControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ProblemDetail handleApplicationException(ApplicationException e) {
        var problemDetail = ProblemDetail.forStatusAndDetail(e.getHttpStatus(), e.getMessage());
        problemDetail.setTitle(e.getCode());
        return problemDetail;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolation(ConstraintViolationException e) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        var errorFields = e.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        cv -> cv.getPropertyPath().toString(),
                        cv -> (Object) cv.getMessage()
                ));
        problemDetail.setProperty("errorFields", errorFields);
        problemDetail.setTitle("VLD-001");
        return problemDetail;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            org.springframework.http.HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, "Ошибка валидации");
        var errorFields = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fe -> (Object) fe.getDefaultMessage()
                ));
        problemDetail.setProperty("errorFields", errorFields);
        problemDetail.setTitle("VLD-001");
        return ResponseEntity.unprocessableEntity().body(problemDetail);
    }
}