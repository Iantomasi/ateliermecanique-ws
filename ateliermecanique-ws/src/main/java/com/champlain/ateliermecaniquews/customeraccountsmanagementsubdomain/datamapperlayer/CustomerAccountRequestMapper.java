package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datamapperlayer;

import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.CustomerAccount;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerAccountRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customerAccountIdentifier", ignore = true)
    CustomerAccount requestModelToEntity(CustomerAccountRequestModel customerAccountRequestModel);


}
