package com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.datalayer;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.AppointmentIdentifier;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "invoices")
@Data
public class CustomerInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private CustomerInvoiceIdentifier customerInvoiceIdentifier;

    @Column(name = "user_id")
    private String customerId;

    @Column(name = "appointment_id")
    private String appointmentId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime invoiceDate;

    private String mechanicNotes;
    private Double sumOfServices;

    CustomerInvoice() {
        this.customerInvoiceIdentifier = new CustomerInvoiceIdentifier();
    }

    public CustomerInvoice(String customerId, String appointmentId, LocalDateTime invoiceDate, String mechanicNotes, Double sumOfServices) {
        this.customerInvoiceIdentifier = new CustomerInvoiceIdentifier();
        this.customerId = customerId;
        this.appointmentId = appointmentId;
        this.invoiceDate = invoiceDate;
        this.mechanicNotes = mechanicNotes;
        this.sumOfServices = sumOfServices;
    }

}
