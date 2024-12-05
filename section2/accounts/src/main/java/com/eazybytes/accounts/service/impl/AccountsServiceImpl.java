package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.CustomerAlreadyExistsException;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountsMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountsRepo;
import com.eazybytes.accounts.repository.CustomerRepo;
import com.eazybytes.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {
    @Autowired
    private AccountsRepo accountsRepo;
    @Autowired
    private CustomerRepo customerRepo;
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepo.findByMobileNumber(customer.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already exists with the given mobile number "+customerDto.getMobileNumber());
        }



        Customer savedCustomer = customerRepo.save(customer);
        accountsRepo.save(createNewAccount(savedCustomer));


    }



    private Accounts createNewAccount(Customer customer){
        Accounts newAccount= new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 10000000000L +new Random().nextInt(900000000);
        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);


        return newAccount;
    }
    @Override
    public CustomerDto fetchAccountDetails(String mobileNumber) {
        Customer customer=customerRepo.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Customer","mobileNumber",mobileNumber)
        );
        Accounts accounts = accountsRepo.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()->new ResourceNotFoundException("Account","customerId",customer.getCustomerId().toString())
        );

        CustomerDto customerDto=CustomerMapper.mapToCustomerDto(customer,new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts,new AccountsDto()));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto !=null ){
            Accounts accounts = accountsRepo.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepo.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepo.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepo.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepo.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Customer","mobileNumber",mobileNumber)
        );
        accountsRepo.deleteByCustomerId(customer.getCustomerId());
        customerRepo.deleteById(customer.getCustomerId());
        return true;
    }


}
