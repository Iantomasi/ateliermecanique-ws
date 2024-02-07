package com.champlain.ateliermecaniquews.reviewssubdomain.datalayer;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "reviews")
@Data
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private ReviewIdentifier reviewIdentifier;

    private String customer_name;

    private String comment;

    private Double rating;

    public Reviews() {
        this.reviewIdentifier = new ReviewIdentifier();
    }

    public Reviews( String customer_name, String comment, Double rating) {
        this.reviewIdentifier = new ReviewIdentifier();
        this.customer_name = customer_name;
        this.comment = comment;
        this.rating = rating;
    }
}
