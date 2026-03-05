package org.generation.socialNetwork.tours.dto;

import jakarta.transaction.Status;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TourRequest {
    private Long tourId;
    private Long guideId;
    private Integer categoryId;
    private String title;
    private String description;
    private BigDecimal price;
    private String currency;
    private BigDecimal durationHours;
    private Short maxGroupSize;
    private String meetingPoint;
    private Status status;
    private String coverImage;
    private String imageClass;
    private BigDecimal ratingAvg;
    private Integer bookingsCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
