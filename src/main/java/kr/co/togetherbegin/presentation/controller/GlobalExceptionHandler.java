package kr.co.togetherbegin.presentation.controller;

import kr.co.togetherbegin.domain.exception.EntityNotFoundException;
import kr.co.togetherbegin.domain.exception.PastDeadlineException;
import kr.co.togetherbegin.presentation.dto.ErrorMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessageDto> handleEntityNotFoundException(
            EntityNotFoundException ex
    ) {
       ErrorMessageDto errorMessageDto = new ErrorMessageDto(ex.getMessage());

       return new ResponseEntity<>(errorMessageDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PastDeadlineException.class)
    public ResponseEntity<ErrorMessageDto> handlePastDeadlineException(
            PastDeadlineException ex
    ) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(ex.getMessage());

        return new ResponseEntity<>(errorMessageDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
