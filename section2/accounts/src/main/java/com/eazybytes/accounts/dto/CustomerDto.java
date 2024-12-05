package com.eazybytes.accounts.dto;

import com.eazybytes.accounts.entity.Customer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Singular;

@Data
@Schema(
        name = "Customer",
        description = "Customer details"
)
public class CustomerDto {
    @Schema(
        description = "Customer name",example = "John Doe"
    )
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 5 ,max=20,message = "The length of the customer name should be between 5 and 20")
    private  String name;
    @Schema(
            description = "Customer Email",example = "John@gmail.com"
    )
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;
    @Schema(
            name = "Mobile number",example = "1234567890"

    )
    @Pattern(regexp = "(^|[0-9]{10})",message = "Mobile number should be 10 digits")
    private String mobileNumber;


    private AccountsDto accountsDto;
}
