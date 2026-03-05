package org.generation.socialNetwork.tours.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


//Lombok
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TripBookings {
    @Id
    @GeneratedValue
    @Column(unique=true, nullable=false)
    private Long tripId;

    @Column(nullable=false, name="start_datetime")
    private Date startDatetime;

    @Column(nullable = false, name="end_datetime")
    private Date endDatetime;

    @Enumerated(EnumType.STRING)
    @Column(name = "trip_status")
    private


}
