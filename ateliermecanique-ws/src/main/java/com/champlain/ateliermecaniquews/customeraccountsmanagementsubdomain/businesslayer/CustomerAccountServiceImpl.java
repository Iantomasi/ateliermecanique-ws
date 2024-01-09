package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.CustomerAccount;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.CustomerAccountRepository;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datamapperlayer.CustomerAccountRequestMapper;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datamapperlayer.CustomerAccountResponseMapper;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datamapperlayer.CustomerAccountoAuthRequestMapper;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountRequestModel;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountResponseModel;
import com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.Payload.Request.CustomerAccountoAuthRequestModel;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.businesslayer.VehicleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CustomerAccountServiceImpl implements CustomerAccountService{

    final private CustomerAccountRepository customerAccountRepository;
    final private VehicleService vehicleService;
    final private CustomerAccountResponseMapper customerAccountResponseMapper;
    final private CustomerAccountRequestMapper customerAccountRequestMapper;
    final private CustomerAccountoAuthRequestMapper customerAccountoAuthRequestMapper;

    @Override
    public List<CustomerAccountResponseModel> getAllCustomerAccounts() {
        return customerAccountResponseMapper.entityToResponseModelList(customerAccountRepository.findAll());
    }

    @Override
    public CustomerAccountResponseModel getCustomerAccountByCustomerId(String customerId) {
        return customerAccountResponseMapper.entityToResponseModel(customerAccountRepository.findCustomerAccountByCustomerAccountIdentifier_CustomerId(customerId));
    }

    @Override
    public CustomerAccountResponseModel updateCustomerAccountByCustomerId(String customerId, CustomerAccountRequestModel customerAccountRequestModel) {
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
            //throw exception
            return null;
        }
    }

    @Override
    public void deleteCustomerAccountByCustomerId(String customerId) {
        CustomerAccount account = customerAccountRepository.findCustomerAccountByCustomerAccountIdentifier_CustomerId(customerId);

        if (account != null){
            customerAccountRepository.delete(account);
            vehicleService.deleteAllVehiclesByCustomerId(customerId);
        }
        else{
            //throw exception
        }
    }

    @Override
    public CustomerAccountResponseModel createCustomerAccountForoAuth(CustomerAccountoAuthRequestModel customerAccountoAuthRequestModel) {
       CustomerAccount account = customerAccountRepository.save(customerAccountoAuthRequestMapper.requestModelToEntity(customerAccountoAuthRequestModel));
        if(account==null){
            //throw exception
        }
        return customerAccountResponseMapper.entityToResponseModel(account);
    }

    @Override
    public CustomerAccountResponseModel updateCustomerToken(String customerId, String token) {
        CustomerAccount customerAccount = customerAccountRepository.findCustomerAccountByCustomerAccountIdentifier_CustomerId(customerId);

        customerAccount.setToken(token);

        return customerAccountResponseMapper.entityToResponseModel(customerAccountRepository.save(customerAccount));
    }


}
