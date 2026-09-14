package com.example.universityjpa.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler =
            new GlobalExceptionHandler();

    @Test
    void handleNotFound_deberiaRetornarError404() {
        ResourceNotFoundException exception =
                new ResourceNotFoundException("Student not found");

        ResponseEntity<Map<String, Object>> response =
                handler.handleNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());

        assertEquals(404, response.getBody().get("status"));
        assertEquals("Not Found", response.getBody().get("error"));
        assertEquals("Student not found", response.getBody().get("message"));
        assertNotNull(response.getBody().get("timestamp"));
    }

    @Test
    void handleBadRequest_deberiaRetornarError400() {
        IllegalArgumentException exception =
                new IllegalArgumentException("Invalid argument");

        ResponseEntity<Map<String, Object>> response =
                handler.handleBadRequest(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());

        assertEquals(400, response.getBody().get("status"));
        assertEquals("Bad Request", response.getBody().get("error"));
        assertEquals("Invalid argument", response.getBody().get("message"));
        assertNotNull(response.getBody().get("timestamp"));
    }
}