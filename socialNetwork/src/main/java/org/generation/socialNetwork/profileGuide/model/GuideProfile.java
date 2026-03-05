package org.generation.socialNetwork.profileGuide.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.generation.socialNetwork.users.model.User;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "guide_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class GuideProfile {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "summary")
    private String summary;

    @Column(name = "story")
    private String story;

    @Column(name = "status_text")
    private String statusText;

    @Column(name = "hourly_rate")
    private BigDecimal hourlyRate;

    @Column(name = "currency")
    private String currency;

    @Column(name = "rating_avg")
    private BigDecimal ratingAvg;

    @Column(name = "reviews_count")
    private Integer reviewsCount;

    @Column(name = "location_label")
    private String locationLabel;

    @Column(name = "experience_level")
    private String experienceLevel;

    @Column(name = "style")
    private String style;

    @Column(name = "group_size")
    private String groupSize;

    @Column(name = "tour_intensity")
    private String tourIntensity;

    @Column(name = "transport_offered")
    private String transportOffered;

    @Column(name = "photo_style")
    private String photoStyle;

    @Column(name = "additional_notes")
    private String additionalNotes;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "cover_url")
    private String coverUrl;

    @Column(name = "post_text")
    private String postText;

    @Column(name = "post_image_url")
    private String postImageUrl;

    @Column(name = "post_caption")
    private String postCaption;

    @Column(name = "post_published_at")
    private LocalDateTime postPublishedAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;
}
