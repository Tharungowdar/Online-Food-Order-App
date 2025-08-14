package orderapp.exception;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import orderapp.dto.ResponseStructure;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseStructure<String>> handleNoSuchElementException(NoSuchElementException exception) {
        ResponseStructure<String> apiResponse = new ResponseStructure<>();
        apiResponse.setData(exception.getMessage());
        apiResponse.setMessage("Restaurant Object Not Found.");
        apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
}
