package com.co.fundmanagement.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ResponseTest {

    @Test
    public void testResponseBuilder() {
        Integer code = 200;
        String message = "Success";
        Object body = new Object(); // Puedes cambiar esto a un objeto específico si lo prefieres

        Response response = Response.builder()
                .code(code)
                .message(message)
                .body(body)
                .build();

        assertEquals(code, response.getCode());
        assertEquals(message, response.getMessage());
        assertEquals(body, response.getBody());
    }

    @Test
    public void testResponseNoArgsConstructor() {
        Response response = new Response();

        assertNull(response.getCode());
        assertNull(response.getMessage());
        assertNull(response.getBody());
    }

    @Test
    public void testResponseAllArgsConstructor() {

        Integer code = 404;
        String message = "Not Found";
        Object body = null; // En este caso, puedes probar con un objeto real o null

        Response response = new Response(code, message);

        assertEquals(code, response.getCode());
        assertEquals(message, response.getMessage());
        assertNull(response.getBody());
    }

    @Test
    public void testResponseJsonSerialization() throws Exception {
        Response response = Response.builder()
                .code(200)
                .message("Success")
                .body("This is the body")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(response);

        assertTrue(json.contains("\"code\":200"));
        assertTrue(json.contains("\"message\":\"Success\""));
        assertTrue(json.contains("\"body\":\"This is the body\""));
    }
}
