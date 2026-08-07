package ru.mealcard.service;

import lombok.Getter;
import ru.mealcard.Base;
import ru.mealcard.dto.RequestDTO;
import ru.mealcard.dto.ResponseDTO;
import ru.mealcard.format.EnrollVisitor;
import ru.mealcard.models.FileContent;
import ru.mealcard.models.TypeProcedure;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class GenerateService extends Base {
    private final RequestConverterService converter = RequestConverterService.getInstance();
    private final FilenameService filenameService = FilenameService.getInstance();
    private final FileService fileService = FileService.getInstance();
    private final ShedulerService shedulerService = ShedulerService.getInstance();

    private final EnrollVisitor enrollVisitor = new EnrollVisitor();

    @Getter private static final GenerateService instance = new GenerateService();

    private GenerateService() {}

    public ResponseDTO process(RequestDTO request) {
        FileContent file = converter.convert(request);
        String content = file.render(enrollVisitor);
        String filename = filenameService.generate();
        fileService.save(filename, content);
        sheduleFile(file);

        ResponseDTO response = new ResponseDTO();
        response.setStatus("SUCCESS");
        response.setFilename(filename);
        response.setContent(content);

        return response;
    }

    private void sheduleFile(FileContent file) {
        Runnable task = () -> {
            info("Processing file at {}", LocalDateTime.now(ZoneId.of(getConfig().getZone())));
        };

            if (file.getHeader().getProcType() == TypeProcedure.IN_TIME) {
                shedulerService.shedule(file.getHeader().getSheduledTime(), task);
            } else {
                task.run();
            }

    }
}
