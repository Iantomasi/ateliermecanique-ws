package com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer;

import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer.TransmissionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Data
public class Appointment {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Embedded
        private AppointmentIdentifier appointmentIdentifier;

        @Column(name = "customer_id")
        private String customerId;

        @Column(name = "vehicle_id")
        private String vehicleId;


        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime appointmentDate;

        private String services;
        private String comments;

        @Enumerated(EnumType.STRING)
        @Column(name = "status")
        private Status status;

        public Appointment(){ this.appointmentIdentifier = new AppointmentIdentifier(); }

        public Appointment(String customerId, String vehicleId, String appointmentDate, String services, String comments, Status status) {
                this.appointmentIdentifier = new AppointmentIdentifier();
                this.customerId = customerId;
                this.vehicleId = vehicleId;
                this.appointmentDate = LocalDateTime.parse(appointmentDate);
                this.services = services;
                this.comments = comments;
                this.status = status;
        }
}
