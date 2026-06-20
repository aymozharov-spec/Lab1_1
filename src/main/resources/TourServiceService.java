package by.psu.service;

import by.psu.dto.request.ExcursionRequest;
import by.psu.dto.response.ExcursionResponse;
import by.psu.mapper.ExcursionMapper;
import by.psu.model.Excursion;
import by.psu.repository.ExcursionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TourServiceService {
    private final ExcursionRepository excursionRepository;
    private final ExcursionMapper excursionMapper;

    public ExcursionResponse createExcursion(ExcursionRequest request) {
        var excursion = excursionMapper.toEntity(request);
        var savedExcursion = excursionRepository.save(excursion);
        return excursionMapper.toResponse(savedExcursion);
    }

    public ExcursionResponse getExcursionById(Integer id) {
        var excursion = excursionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Excursion not found with id: " + id));
        return excursionMapper.toResponse(excursion);
    }

    public Page<ExcursionResponse> getExcursionPage(Pageable pageable) {
        return excursionRepository.findAll(pageable).map(excursionMapper::toResponse);
    }

    @Transactional
    public ExcursionResponse updateExcursion(Integer id, ExcursionRequest request) {
        Excursion existingExcursion = excursionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Excursion not found with id: " + id));

        // MapStruct обновляет existingExcursion из request
        excursionMapper.updateEntity(existingExcursion, request);

        Excursion updatedExcursion = excursionRepository.save(existingExcursion);
        return excursionMapper.toResponse(updatedExcursion);
    }

    @Transactional
    public void deleteExcursion(Integer id) {
        if (!excursionRepository.existsById(id)) {
            throw new EntityNotFoundException("Excursion not found with id: " + id);
        }
        excursionRepository.deleteById(id);
    }
}