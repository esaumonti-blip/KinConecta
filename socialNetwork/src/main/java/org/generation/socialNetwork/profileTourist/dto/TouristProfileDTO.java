package org.generation.socialNetwork.profileTourist.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class TouristProfileDto {

    private Long userId;
    private String location;
    private String bio;
    private LocalDate memberSince;
    private String badge;
    private String travelStyle;
    private String tripStyle;
    private String paceAndCompany;
    private String activityLevel;
    private String groupPreference;
    private String dietaryPreferences;
    private String planningLevel;
    private String amenities;
    private String transport;
    private String photoPreference;
    private String accessibility;
    private String additionalNotes;
    private String avatarUrl;
    private String coverUrl;
    private LocalDateTime updatedAt;
    private Set<String> languages;
    private Set<String> interests;

    public TouristProfileDto() {}

    public Long getUserId() { return userId; }
    public String getLocation() { return location; }
    public String getBio() { return bio; }
    public LocalDate getMemberSince() { return memberSince; }
    public String getBadge() { return badge; }
    public String getTravelStyle() { return travelStyle; }
    public String getTripStyle() { return tripStyle; }
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
    public Set<String> getLanguages() { return languages; }
    public Set<String> getInterests() { return interests; }

    public void setUserId(Long userId) { this.userId = userId; }
    public void setLocation(String location) { this.location = location; }
    public void setBio(String bio) { this.bio = bio; }
    public void setMemberSince(LocalDate memberSince) { this.memberSince = memberSince; }
    public void setBadge(String badge) { this.badge = badge; }
    public void setTravelStyle(String travelStyle) { this.travelStyle = travelStyle; }
    public void setTripStyle(String tripStyle) { this.tripStyle = tripStyle; }
    public void setPaceAndCompany(String paceAndCompany) { this.paceAndCompany = paceAndCompany; }
    public void setActivityLevel(String activityLevel) { this.activityLevel = activityLevel; }
    public void setGroupPreference(String groupPreference) { this.groupPreference = groupPreference; }
    public void setDietaryPreferences(String dietaryPreferences) { this.dietaryPreferences = dietaryPreferences; }
    public void setPlanningLevel(String planningLevel) { this.planningLevel = planningLevel; }
    public void setAmenities(String amenities) { this.amenities = amenities; }
    public void setTransport(String transport) { this.transport = transport; }
    public void setPhotoPreference(String photoPreference) { this.photoPreference = photoPreference; }
    public void setAccessibility(String accessibility) { this.accessibility = accessibility; }
    public void setAdditionalNotes(String additionalNotes) { this.additionalNotes = additionalNotes; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public void setCoverUrl(String coverUrl) { this.coverUrl = coverUrl; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setLanguages(Set<String> languages) { this.languages = languages; }
    public void setInterests(Set<String> interests) { this.interests = interests; }
}