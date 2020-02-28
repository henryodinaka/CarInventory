package com.init.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

@Data
@Slf4j
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class Response implements Serializable {

    private String statusCode;
    private String statusMessage;
    private Boolean isAccountText;
    private Object data;

    public Response(Integer statusCode, String statusMessage, Object data) {
        this.statusCode = (statusCode == 202 || statusCode == 200 ? "00" : String.valueOf(statusCode));
        this.statusMessage = statusMessage;
        this.data = data;
    }

    public Response(String statusCode, String statusMessage, Object data) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.data = data;
    }

    public static ResponseEntity setUpResponse(Integer httpCode, String statusMessage, Object obj) {
        Response responseMessage = new Response(httpCode, statusMessage, obj);
        return ResponseEntity.status(httpCode).body(responseMessage);
    }

    public static ResponseEntity setUpResponse(Integer httpCode, String statusMessage) {
        Response responseMessage = new Response(httpCode, statusMessage, null);
        return ResponseEntity.status(httpCode).body(responseMessage);
    }

}