package com.champlain.ateliermecaniquews.reviewssubdomain.presentationlayer;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
@AllArgsConstructor
public class ReviewRequestModel {
    private String customerId;
    private String appointmentId;
    private String comment;
    private Double rating;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime reviewDate;
}
