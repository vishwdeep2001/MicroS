package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(
    name = "Error Response",
        description = "Error Response information"
)
public class ErrorResponseDto {
    @Schema(description = "API path", example = "/accounts")
    private String apiPath;
@Schema(description = "Error code", example = "417")
    private HttpStatus errorCode;
@Schema(description = "Error message", example = "Internal Server Error")
    private String errorMessage;

    private LocalDateTime errorTime;


}
