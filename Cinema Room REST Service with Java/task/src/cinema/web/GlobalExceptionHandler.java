package cinema.web;

import cinema.exception.AlreadyPurchasedException;
import cinema.exception.OutOfBoundsException;
import cinema.dto.ErrorResponse;
import cinema.exception.WrongPasswordException;
import cinema.exception.WrongTokenException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({OutOfBoundsException.class, AlreadyPurchasedException.class, WrongTokenException.class})
    public ResponseEntity<ErrorResponse> handleBadRequest(RuntimeException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ErrorResponse> handleWrongPassword(RuntimeException e) {
        return ResponseEntity.status(401).body(new ErrorResponse(e.getMessage()));
    }
}
