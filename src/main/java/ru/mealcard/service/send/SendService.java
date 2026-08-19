package ru.mealcard.service.send;

import lombok.Getter;
import ru.mealcard.Base;
import ru.mealcard.exception.InvalidRequestException;
import ru.mealcard.service.dto.ResponseDTO;
import ru.mealcard.service.send.dto.SendRequestDTO;
import ru.mealcard.service.validator.RequestValidator;
import ru.mealcard.utils.sendModels.Sender;
import ru.mealcard.utils.sendModels.SenderFabric;

import java.nio.file.Files;
import java.nio.file.Path;

public class SendService extends Base {

    @Getter
    private static final SendService instance = new SendService();

    private final SenderFabric senderFabric = SenderFabric.getInstance();
    private final RequestValidator validator = RequestValidator.getInstance();

    private SendService() {}

    public ResponseDTO process(SendRequestDTO requestDTO) {
        validator.validateSend(requestDTO);

        Path file = getConfig().getOutputDir().resolve(requestDTO.getFilename());
        if (!Files.exists(file)) {
            throw new InvalidRequestException("File not found: " + requestDTO.getFilename());
        }

        Sender sender = senderFabric.get(requestDTO.getTypeSend());
        sender.send(file, requestDTO.getMeta());

        ResponseDTO response = new ResponseDTO();
        response.setStatus("SUCCESS");
        response.setFilename(requestDTO.getFilename());
        return response;
    }
}