package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datamapperlayer;

import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.CustomerAccount;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerAccountResponseMapper {

    @Mapping(expression = "java(customerAccount.getCustomerAccountIdentifier().getCustomerAccountId())", target = "customerAccountId")
    CustomerAccountResponseModel entityToResponseModel(CustomerAccount customerAccount);
    List<CustomerAccountResponseModel> entityToResponseModelList(List<CustomerAccount> customerAccounts);


}
