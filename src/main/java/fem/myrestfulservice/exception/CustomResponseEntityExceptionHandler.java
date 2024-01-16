package fem.myrestfulservice.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse
                = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity(exceptionResponse, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> userNotFoundException(UserNotFoundException ex, WebRequest request) {
        ExceptionResponse exceptionResponse
                = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity(exceptionResponse, NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        ArgumentNotValidExceptionResponse test = ArgumentNotValidExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .fieldErrors(createFieldErrorResponse(ex.getBindingResult(), request.getLocale()))
                .message("validate failed")
                .details(request.getDescription(false))
                .build();
        return new ResponseEntity(test, BAD_REQUEST);
    }

    private List<ArgumentNotValidExceptionResponse.FieldErrorResponse> createFieldErrorResponse(BindingResult bindingResult, Locale locale) {
        return bindingResult.getFieldErrors().stream()
                .map(e -> ArgumentNotValidExceptionResponse.FieldErrorResponse.builder()
                        .fieldName(e.getField())
                        .rejectValue(e.getRejectedValue())
                        .message(messageSource.getMessage(e, locale))
                        .build()
                )
                .toList();
    }
}
