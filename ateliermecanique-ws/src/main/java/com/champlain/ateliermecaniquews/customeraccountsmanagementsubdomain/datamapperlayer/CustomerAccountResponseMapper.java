package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datamapperlayer;

import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.User;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerAccountResponseMapper {

    @Mapping(expression = "java(customerAccount.getUserIdentifier().getUserId())", target = "id")
    CustomerAccountResponseModel entityToResponseModel(User customerAccount);
    List<CustomerAccountResponseModel> entityToResponseModelList(List<User> customerAccounts);


}
