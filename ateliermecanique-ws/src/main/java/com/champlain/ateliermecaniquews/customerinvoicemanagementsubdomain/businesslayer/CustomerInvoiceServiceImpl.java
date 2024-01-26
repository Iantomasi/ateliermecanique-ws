package com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.UserRepository;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.datalayer.CustomerInvoiceRepository;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.datamapperlayer.CustomerInvoiceRequestMapper;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.datamapperlayer.CustomerInvoiceResponseMapper;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.presentationlayer.CustomerInvoiceResponseModel;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer.VehicleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CustomerInvoiceServiceImpl implements CustomerInvoiceService{


    private CustomerInvoiceRepository customerInvoiceRepository;
    private UserRepository userRepository;
    private VehicleRepository vehicleRepository;
    private CustomerInvoiceResponseMapper customerInvoiceResponseMapper;

    @Override
    public List<CustomerInvoiceResponseModel> getAllInvoices() {
        return customerInvoiceResponseMapper.entityToResponseModelList(customerInvoiceRepository.findAll());

    }
}
