package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datamapperlayer;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.CustomerAccount;
import com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.CustomerAccountoAuthRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerAccountoAuthRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customerAccountIdentifier", ignore = true)
    CustomerAccount requestModelToEntity(CustomerAccountoAuthRequestModel customerAccountoAuthRequestModel);

}
