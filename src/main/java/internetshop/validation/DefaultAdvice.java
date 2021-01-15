package internetshop.validation;

import internetshop.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class DefaultAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> messages = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            messages.put(fieldName, errorMessage);
        });


        Response response = new Response();
        response.setCode(1102);
        response.setMessages(messages);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Response> handleServiceExceptions(
            ServiceException ex) {
        Map<String, String> messages = new HashMap<>();
        messages.put("field", ex.getErrors().getMessage());

        Response response = new Response();
        response.setCode(ex.getErrors().getCode());
        response.setMessages(messages);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
