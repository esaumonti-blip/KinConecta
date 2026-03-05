package org.generation.socialNetwork.profileTourist.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "favorite_guides")
public class FavoriteGuidesEntity {

    @EmbeddedId
    private FavoriteGuideId id;

    @MapsId("touristId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tourist_id")
    private TouristProfileEntity tourist;

    @Column(name = "guide_id", insertable = false, updatable = false)
    private Long guideId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public FavoriteGuideId getId() { return id; }
    public TouristProfileEntity getTourist() { return tourist; }
    public Long getGuideId() { return guideId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}


