package fem.myrestfulservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExceptionResponse {
    private LocalDateTime timestamp;
    private String message;
    private String details;
}
