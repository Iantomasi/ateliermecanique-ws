package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.ERole;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.User;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.UserRepository;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datamapperlayer.CustomerAccountResponseMapper;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountRequestModel;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountResponseModel;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.businesslayer.VehicleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class CustomerAccountServiceImpl implements CustomerAccountService{

    final private UserRepository userRepository;
    final private VehicleService vehicleService;
    final private CustomerAccountResponseMapper customerAccountResponseMapper;


    @Override
    public List<CustomerAccountResponseModel> getAllCustomerAccounts() {
        List<User> usersWithRoles = userRepository.findAll();

        return customerAccountResponseMapper.entityToResponseModelList(
                usersWithRoles
                        .stream()
                        .filter(user -> user.getRoles().stream()
                                .anyMatch(role -> role.getName().equals(ERole.ROLE_CUSTOMER)))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public CustomerAccountResponseModel getCustomerAccountByCustomerId(String userId) {
        return customerAccountResponseMapper.entityToResponseModel(userRepository.findUserByUserIdentifier_UserId(userId));
    }

    @Override
    public CustomerAccountResponseModel updateCustomerAccountByCustomerId(String userId, CustomerAccountRequestModel customerAccountRequestModel) {
        User account = userRepository.findUserByUserIdentifier_UserId(userId);
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
            account = userRepository.save(account);

            // Map and return the response model
            return customerAccountResponseMapper.entityToResponseModel(account);
        } else {
            //throw exception
            return null;
        }
    }

    @Override
    public void deleteCustomerAccountByCustomerId(String userId) {
        User account = userRepository.findUserByUserIdentifier_UserId(userId);

        if (account != null){
            userRepository.delete(account);
            vehicleService.deleteAllVehiclesByCustomerId(userId);
        }
        else{
            //throw exception
        }
    }


}
