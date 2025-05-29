package com.rob.driving.controllers.adapters.error;

import com.rob.driving.controllers.adapters.response.WrapperResponseData;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // Aplica esta lógica a todos los controladores y todos los tipos de respuesta
        return true;
    }

    @Override
    public Object beforeBodyWrite(
            Object body,                                      // El cuerpo de la respuesta original del controlador
            MethodParameter returnType,                       // Información sobre el método que devuelve la respuesta
            MediaType selectedContentType,                    // El tipo de contenido (por ejemplo, application/json)
            Class<? extends HttpMessageConverter<?>> selectedConverterType, // El convertidor que se usará para serializar la respuesta,
                                                                            // en este caso, el convertidor de JSON MappingJackson2HttpMessageConverter
            ServerHttpRequest request,                        // La petición HTTP entrante
            ServerHttpResponse response                       // La respuesta HTTP saliente
    ) {
        // Si la respuesta ya está envuelta, no hacemos nada
        if (body instanceof WrapperResponseData<?>) {
            return body;
        }

        // Si la respuesta es una cadena o un array de bytes, no la tocamos para evitar errores de serialización
        if (body instanceof String || body instanceof byte[]) {
            return body;
        }

        // Envolvemos el cuerpo original dentro de "data"
        return new WrapperResponseData<>(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        System.out.println("⚠️ EXCEPTION TYPE: " + ex.getClass().getName());
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getStatusCode().value(), ex.getReason());
        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = ex.getMostSpecificCause().getMessage();

        if (message != null && message.contains("organizations_name_key")) {
            message = "Organization name already exists";
        } else {
            message = "Data integrity violation";
        }

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "The specified ID does not exist"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}

