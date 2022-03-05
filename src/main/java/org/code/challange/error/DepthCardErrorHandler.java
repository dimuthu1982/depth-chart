package org.code.challange.error;

import java.util.ArrayList;
import java.util.List;

import org.code.challange.exception.IlligalCardOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DepthCardErrorHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(IlligalCardOperationException.class)
    public final ResponseEntity<ErrorResponse> handleUserNotFoundException(IlligalCardOperationException ex, WebRequest request)
    {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(ex.getMessage(), details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
