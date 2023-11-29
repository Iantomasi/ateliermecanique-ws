package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datamapperlayer;

import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.CustomerAccount;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountRequestModel;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-29T00:00:26-0500",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.4.jar, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class CustomerAccountRequestMapperImpl implements CustomerAccountRequestMapper {

    @Override
    public CustomerAccount requestModelToEntity(CustomerAccountRequestModel customerAccountRequestModel) {
        if ( customerAccountRequestModel == null ) {
            return null;
        }

        String customerFirstName = null;
        String customerLastName = null;
        String customerEmail = null;
        String customerPhoneNumber = null;

        customerFirstName = customerAccountRequestModel.getCustomerFirstName();
        customerLastName = customerAccountRequestModel.getCustomerLastName();
        customerEmail = customerAccountRequestModel.getCustomerEmail();
        customerPhoneNumber = customerAccountRequestModel.getCustomerPhoneNumber();

        String customerPassword = null;

        CustomerAccount customerAccount = new CustomerAccount( customerFirstName, customerLastName, customerEmail, customerPhoneNumber, customerPassword );

        return customerAccount;
    }
}
