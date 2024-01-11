package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datamapperlayer;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.User;
import com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.Payload.Request.CustomerAccountoAuthRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerAccountoAuthRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userIdentifier", ignore = true)
    User requestModelToEntity(CustomerAccountoAuthRequestModel customerAccountoAuthRequestModel);

}
