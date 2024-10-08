package org.example.techtask.exceptions.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ErrorMessage {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private List<ValidationError> subErrors;

    private ErrorMessage() {
        timestamp = LocalDateTime.now();
    }

    ErrorMessage(HttpStatus status) {
        this();
        this.status = status;
    }
}
