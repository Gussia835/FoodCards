package ru.mealcard.service;

import lombok.Getter;
import ru.mealcard.Base;
import ru.mealcard.dto.DataForEnrollDTO;
import ru.mealcard.dto.GenerateRequestDTO;
import ru.mealcard.dto.ResponseDTO;
import ru.mealcard.format.EnrollVisitor;
import ru.mealcard.format.Visitor;
import ru.mealcard.models.TypeProcedure;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class GenerateService extends Base {

    private final FileGeneratorService fileGenerator = FileGeneratorService.getInstance();
    private final RequestValidator validator = RequestValidator.getInstance();

    private final Visitor<DataForEnrollDTO> visitor = new EnrollVisitor();

    @Getter
    private static final GenerateService instance = new GenerateService();

    private GenerateService() {}

    public ResponseDTO process(GenerateRequestDTO requestDTO) {
        validator.validateGenerate(requestDTO);

        DataForEnrollDTO data = convertToDataForEnroll(requestDTO);

        String filename = fileGenerator.generate(
                data,
                visitor,
                requestDTO.getBankCode(),
                requestDTO.getBranchCode(),
                requestDTO.getNameSystem()
        );

        ResponseDTO response = new ResponseDTO();
        response.setStatus("SUCCESS");
        response.setFilename(filename);
        return response;
    }

    private DataForEnrollDTO convertToDataForEnroll(GenerateRequestDTO request) {
        TypeProcedure proc = TypeProcedure.fromCode(request.getProcType());
        ZonedDateTime scheduled = null;
        if (proc == TypeProcedure.IN_TIME && request.getProcessAt() != null) {
            scheduled = ZonedDateTime.parse(request.getProcessAt())
                    .withZoneSameInstant(ZoneId.of(getConfig().getZone()));
        }

        return new DataForEnrollDTO(
                ZonedDateTime.now(ZoneId.of(getConfig().getZone())),
                scheduled,
                proc,
                request.getCards()
        );
    }
}