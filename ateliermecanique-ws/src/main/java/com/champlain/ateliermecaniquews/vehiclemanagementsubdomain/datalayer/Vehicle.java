    package com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer;

    import jakarta.persistence.*;
    import lombok.Data;

    @Entity
    @Table(name = "vehicles")
    @Data
    public class Vehicle {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Embedded
        private VehicleIdentifier vehicleIdentifier;
        @Column(name = "user_id")
        private String userId;
        private String make;
        private String model;
        private String year;
        @Enumerated(EnumType.STRING)
        @Column(name = "transmission_type")
        private TransmissionType transmission_type;
        private String mileage;

        public Vehicle(){ this.vehicleIdentifier = new VehicleIdentifier(); }

        public Vehicle(String userId, String make, String model, String year, TransmissionType transmission_type, String mileage) {
            this.vehicleIdentifier = new VehicleIdentifier();
            this.userId = userId;
            this.make = make;
            this.model = model;
            this.year = year;
            this.transmission_type = transmission_type;
            this.mileage = mileage;
        }


    }
