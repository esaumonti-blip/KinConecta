package org.generation.socialNetwork.profileTourist.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapsId;
// import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.generation.socialNetwork.matching.model.LanguageEntity;
import org.generation.socialNetwork.matching.model.InterestEntity;
import org.generation.socialNetwork.matching.model.UserEntity;


@Entity
@Table(name = "tourist_profiles")
public class TouristProfileEntity {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "location")
    private String location;

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @Column(name = "member_since")
    private LocalDate memberSince;

    @Column(name = "badge")
    private String badge;

    @Column(name = "travel_style")
    private String travelStyle;

    @Column(name = "trip_style")
    private String tripStyle;

    @Column(name = "pace_and_company")
    private String paceAndCompany;

    // Enum in schema, EnumType.STRING for enum in java
    @Column(name = "activity_level")
    private String activityLevel;

    @Column(name = "group_preference")
    private String groupPreference;

    @Column(name = "dietary_preferences")
    private String dietaryPreferences;

    // another enum, ref line 52
    @Column(name = "planning_level")
    private String planningLevel;

    @Column(name = "amenities")
    private String amenities;

    @Column(name = "transport")
    private String transport;

    @Column(name = "photo_preference")
    private String photoPreference;

    @Column(name = "accessibility")
    private String accessibility;

    @Column(name = "additional_notes")
    private String additionalNotes;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "cover_url")
    private String coverUrl;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tourist_profile_languages",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "language_code")
    )
    private Set<LanguageEntity> languages = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tourist_profile_interests",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id")
    )
    private Set<InterestEntity> interests = new HashSet<>();

    // @OneToMany(mappedBy = "tourist", fetch = FetchType.LAZY)
    // private Set<FavoriteGuideEntity> favoriteGuides = new HashSet<>();


    public Long getUserId() { return userId; }
    public UserEntity getUser() { return user; }
    public String getLocation() { return location; }
    public String getBio() { return bio; }
    public LocalDate getMemberSince() { return memberSince; }
    public String getBadge() { return badge; }
    public String getTravelStyle() { return travelStyle; }
    public String getTripType() { return tripStyle; }
    public String getPaceAndCompany() { return paceAndCompany; }
    public String getActivityLevel() { return activityLevel; }
    public String getGroupPreference() { return groupPreference; }
    public String getDietaryPreferences() { return dietaryPreferences; }
    public String getPlanningLevel() { return planningLevel; }
    public String getAmenities() { return amenities; }
    public String getTransport() { return transport; }
    public String getPhotoPreference() { return photoPreference; }
    public String getAccessibility() { return accessibility; }
    public String getAdditionalNotes() { return additionalNotes; }
    public String getAvatarUrl() { return avatarUrl; }
    public String getCoverUrl() { return coverUrl; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public Set<LanguageEntity> getLanguages() { return languages; }
    public Set<InterestEntity> getInterests() { return interests; }
    //public Set<FavoriteGuideEntity> getFavoriteGuides() { return favoriteGuides; }

}

