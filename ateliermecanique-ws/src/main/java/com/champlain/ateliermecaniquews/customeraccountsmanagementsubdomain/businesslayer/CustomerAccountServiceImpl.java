package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.CustomerAccountRepository;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datamapperlayer.CustomerAccountRequestMapper;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datamapperlayer.CustomerAccountResponseMapper;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerAccountServiceImpl implements CustomerAccountService{

    private CustomerAccountRepository customerAccountRepository;
    private CustomerAccountResponseMapper customerAccountResponseMapper;
    private CustomerAccountRequestMapper customerAccountRequestMapper;

    public CustomerAccountServiceImpl(CustomerAccountRepository customerAccountRepository, CustomerAccountResponseMapper customerAccountResponseMapper, CustomerAccountRequestMapper customerAccountRequestMapper) {
        this.customerAccountRepository = customerAccountRepository;
        this.customerAccountResponseMapper = customerAccountResponseMapper;
        this.customerAccountRequestMapper = customerAccountRequestMapper;
    }

    @Override
    public List<CustomerAccountResponseModel> getAllCustomerAccounts() {
        return customerAccountResponseMapper.entityToResponseModelList(customerAccountRepository.findAll());
    }
}
