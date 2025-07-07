package com.thiago.desafiovotacao.exception;

import com.thiago.desafiovotacao.cpf.dto.ValidadorCpfDto;
import com.thiago.desafiovotacao.cpf.enums.ValidaVoto;
import com.thiago.desafiovotacao.model.dtos.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponseDto> handleApiException(ApiException ex) {
        return buildErrorResponse(ex.getMessage(), ex.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return buildErrorResponse(msg, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleAll(Exception ex) {
        return buildErrorResponse("Erro interno do servidor", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({
            CpfNotFoundException.class,
            jakarta.validation.ConstraintViolationException.class,
            CpfUnableToVoteException.class
    })
    public ResponseEntity<ValidadorCpfDto> handleCpfNotAllowed(Exception ex) {
        ValidadorCpfDto dto = new ValidadorCpfDto(ValidaVoto.UNABLE_TO_VOTE);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(dto);
    }


    private ResponseEntity<ErrorResponseDto> buildErrorResponse(String message, HttpStatus status) {
        ErrorResponseDto body = new ErrorResponseDto(
                LocalDateTime.now().format(FORMATTER),
                status.value(),
                status.getReasonPhrase(),
                message
        );
        return new ResponseEntity<>(body, status);
    }
}
