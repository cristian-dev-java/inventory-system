package my.projects.inventorysystem.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import my.projects.inventorysystem.dto.CustomConstrainFields;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Component
@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<CustomConstrainFields> messages = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> CustomConstrainFields.builder()
                        .fieldName("El campo: ".concat(e.getField()))
                        .fieldMessage(e.getDefaultMessage()).build())
                .toList();
        return ResponseEntity.internalServerError().body(messages);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {
        try {
            log.info(ex.getConstraintViolations().toString());
            List<CustomConstrainFields> messages = ex.getConstraintViolations()
                    .stream()
                    .map(e -> CustomConstrainFields.builder()
                            .fieldName("El campo: ".concat(splitAndReturnLastElementInArray(e.getPropertyPath().toString())))
                            .fieldMessage(e.getMessage()).build())
                    .toList();
            return ResponseEntity.internalServerError().body(messages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        try {
            log.info("Esta es la excepción : {}", ex);
            CustomConstrainFields message = CustomConstrainFields.builder()
                    .fieldName("El campo: ".concat(ex.getName()))
                    .fieldMessage(ex.getMessage()).build();
            return ResponseEntity.internalServerError().body(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    private String splitAndReturnLastElementInArray(String s) {
        String[] stringArray = s.split(Pattern.quote("."));
        int sizeStringArray = stringArray.length;
        return stringArray[sizeStringArray - 1];
    }

}
