package com.champlain.ateliermecaniquews.reviewssubdomain.datalayer;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "reviews")
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private ReviewIdentifier reviewIdentifier;

    @Column(name = "user_id")
    private String customerId;

    @Column(name = "appointment_id")
    private String appointmentId;

    private String comment;

    private Double rating;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime reviewDate;

    private String mechanicReply;

    public Review() {
        this.reviewIdentifier = new ReviewIdentifier();
    }

    public Review(String customerId, String appointmentId, String comment, Double rating, String reviewDate, String mechanicReply) {
        this.reviewIdentifier = new ReviewIdentifier();
        this.customerId = customerId;
        this.appointmentId = appointmentId;
        this.comment = comment;
        this.rating = rating;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.reviewDate = LocalDateTime.parse(reviewDate, formatter);
        this.mechanicReply = mechanicReply;
    }
}
