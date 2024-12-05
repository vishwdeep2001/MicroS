package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ErrorResponseDto;
import com.eazybytes.accounts.dto.ResponseDto;
import com.eazybytes.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Tag(
        name = "Accounts Controller for Crud APIs",
        description = "CRUD APIs for Accounts"

)
@RestController
@RequestMapping(path="/api",produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor//Constructor  for dependency injection
@Valid
public class AccountsController {
    //@Autowired for dependency injection
    private IAccountsService accountsService;

    @Operation(summary = "Create new account",
    description = "Create new account for customer")
    @ApiResponse(responseCode = "201",
    description = "Account created successfully")
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
    accountsService.createAccount(customerDto);
     return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(new ResponseDto(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201));
    }

    @ApiResponse(responseCode = "200",
            description = "Account details fetched successfully")
    @Operation(summary = "Fetch account details",
description = "Fetch account details for customer")
@GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@Pattern(regexp = "(^|[0-9]{10})",message = "Mobile number should be 10 digits")
                                                           @RequestParam String mobileNumber){

CustomerDto customerDto=accountsService.fetchAccountDetails(mobileNumber);
return  ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }


@ApiResponse(responseCode = "200",
description = "Account details updated successfully")
@Operation(summary = "Update account details",
        description = "Update account details for customer")
    @PutMapping("/update")
    public ResponseEntity<ResponseDto>updateAccountDetails(@Valid @RequestBody CustomerDto customerDto){
        boolean isUpdated = accountsService.updateAccount(customerDto);
        if(isUpdated){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));

        }
        else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                        .body(new ResponseDto(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_UPDATE));
        }
    }


    @ApiResponses({@ApiResponse(responseCode = "500",
            description = "Internal Server Error"),

@ApiResponse( responseCode = "200",
            description = "Account deleted successfully",
content=@Content(
        schema = @Schema(implementation = ErrorResponseDto.class)
    ))})
    @Operation(summary = "Delete account details",
            description = "Delete account details for customer")
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(
            @Pattern(regexp = "(^|[0-9]{10})",message = "Mobile number should be 10 digits")
            @RequestParam String mobileNumber){
        boolean isDeleted=accountsService.deleteAccount(mobileNumber);
        if(isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_DELETE));
        }
    }

}
