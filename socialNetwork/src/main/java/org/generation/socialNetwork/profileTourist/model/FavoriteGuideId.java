package org.generation.socialNetwork.profileTourist.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FavoriteGuideId implements Serializable {

    @Column(name = "tourist_id")
    private Long touristId;

    @Column(name = "guide_id")
    private Long guideId;

    public FavoriteGuideId() {}

    public FavoriteGuideId(Long touristId, Long guideId) {
        this.touristId = touristId;
        this.guideId = guideId;
    }

    public Long getTouristId() { return touristId; }
    public Long getGuideId() { return guideId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FavoriteGuideId that)) return false;
        return Objects.equals(touristId, that.touristId) &&
                Objects.equals(guideId, that.guideId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(touristId, guideId);
    }
}

