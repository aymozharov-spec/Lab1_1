package by.psu.mapper;

import by.psu.dto.request.ExcursionRequest;
import by.psu.dto.response.ExcursionResponse;
import by.psu.model.Excursion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.ERROR;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = ERROR)
public interface ExcursionMapper {

    @Mapping(target = "id", ignore = true)
    Excursion toEntity(ExcursionRequest request);

    ExcursionResponse toResponse(Excursion excursion);

    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget Excursion excursion, ExcursionRequest request);
}