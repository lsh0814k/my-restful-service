package fem.myrestfulservice.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ArgumentNotValidExceptionResponse {
    private LocalDateTime timestamp;
    private String message;
    private String details;
    private List<FieldErrorResponse> fieldErrors;

    @AllArgsConstructor @NoArgsConstructor
    @Getter
    @Builder
    public static class FieldErrorResponse {
        private String fieldName;
        private Object rejectValue;
        private String message;
    }
}
