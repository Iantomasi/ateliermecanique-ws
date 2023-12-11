package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.CustomerAccount;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.CustomerAccountRepository;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datamapperlayer.CustomerAccountRequestMapper;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datamapperlayer.CustomerAccountResponseMapper;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountRequestModel;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountResponseModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerAccountServiceImpl implements CustomerAccountService{

    final private CustomerAccountRepository customerAccountRepository;
    final private CustomerAccountResponseMapper customerAccountResponseMapper;
    final private CustomerAccountRequestMapper customerAccountRequestMapper;


    @Override
    public List<CustomerAccountResponseModel> getAllCustomerAccounts() {
        return customerAccountResponseMapper.entityToResponseModelList(customerAccountRepository.findAll());
    }

    @Override
    public CustomerAccountResponseModel getCustomerAccountById(String customerId) {
        return customerAccountResponseMapper.entityToResponseModel(customerAccountRepository.findCustomerAccountByCustomerAccountIdentifier_CustomerId(customerId));
    }

    @Override
    public CustomerAccountResponseModel updateCustomerById(String customerId, CustomerAccountRequestModel customerAccountRequestModel) {
        CustomerAccount account = customerAccountRepository.findCustomerAccountByCustomerAccountIdentifier_CustomerId(customerId);
        if (account != null) {
            // Check and update fields only if they are not null in the request model
            if (customerAccountRequestModel.getFirstName() != null) {
                account.setFirstName(customerAccountRequestModel.getFirstName());
            }
            if (customerAccountRequestModel.getLastName() != null) {
                account.setLastName(customerAccountRequestModel.getLastName());
            }
            if (customerAccountRequestModel.getEmail() != null) {
                account.setEmail(customerAccountRequestModel.getEmail());
            }
            if (customerAccountRequestModel.getPhoneNumber() != null) {
                account.setPhoneNumber(customerAccountRequestModel.getPhoneNumber());
            }

            // Save the updated account entity
            account = customerAccountRepository.save(account);

            // Map and return the response model
            return customerAccountResponseMapper.entityToResponseModel(account);
        } else {
            // Handle the case when the account is not found
            return null; // or throw an exception, log, etc. based on your logic
        }
    }


}
