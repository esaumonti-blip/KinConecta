package org.generation.socialNetwork.profileTourist.service;

import org.generation.socialNetwork.profileTourist.dto.TouristProfileDto;
import org.generation.socialNetwork.profileTourist.model.TouristProfileEntity;
import org.generation.socialNetwork.profileTourist.model.FavoriteGuidesEntity;
import org.generation.socialNetwork.profileTourist.model.FavoriteGuideId;
import org.generation.socialNetwork.profileTourist.repository.TouristProfileRepository;
import org.generation.socialNetwork.profileTourist.repository.FavoriteGuidesRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TouristProfileService {

    private final TouristProfileRepository touristProfileRepository;
    private final FavoriteGuidesRepository favoriteGuidesRepository;

    public TouristProfileService(TouristProfileRepository touristProfileRepository,
                                 FavoriteGuidesRepository favoriteGuidesRepository) {
        this.touristProfileRepository = touristProfileRepository;
        this.favoriteGuidesRepository = favoriteGuidesRepository;
    }

    // ─── GET ───────────────────────────────────────────────────────────────────

    public TouristProfileDto getProfile(Long userId) {
        TouristProfileEntity entity = touristProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Tourist profile not found for userId: " + userId));
        return mapToDto(entity);
    }

    // ─── UPDATE ────────────────────────────────────────────────────────────────

    @Transactional
    public TouristProfileDto updateProfile(Long userId, TouristProfileDto dto) {
        TouristProfileEntity entity = touristProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Tourist profile not found for userId: " + userId));

        applyDtoToEntity(dto, entity);
        entity.setUpdatedAt(LocalDateTime.now());

        touristProfileRepository.save(entity);
        return mapToDto(entity);
    }

    // ─── FAVORITE GUIDES ───────────────────────────────────────────────────────

    @Transactional
    public void addFavoriteGuide(Long touristId, Long guideId) {
        FavoriteGuideId favoriteGuideId = new FavoriteGuideId(touristId, guideId);

        if (favoriteGuidesRepository.existsById(favoriteGuideId)) {
            throw new RuntimeException("Guide " + guideId + " is already in favorites");
        }

        TouristProfileEntity tourist = touristProfileRepository.findById(touristId)
                .orElseThrow(() -> new RuntimeException("Tourist profile not found for userId: " + touristId));

        FavoriteGuidesEntity favorite = new FavoriteGuidesEntity();
        favorite.setId(favoriteGuideId);
        favorite.setTourist(tourist);
        favorite.setCreatedAt(LocalDateTime.now());

        favoriteGuidesRepository.save(favorite);
    }

    @Transactional
    public void removeFavoriteGuide(Long touristId, Long guideId) {
        FavoriteGuideId favoriteGuideId = new FavoriteGuideId(touristId, guideId);

        if (!favoriteGuidesRepository.existsById(favoriteGuideId)) {
            throw new RuntimeException("Guide " + guideId + " is not in favorites");
        }

        favoriteGuidesRepository.deleteById(favoriteGuideId);
    }

    public List<Long> getFavoriteGuides(Long touristId) {
        return favoriteGuidesRepository.findByIdTouristId(touristId)
                .stream()
                .map(FavoriteGuidesEntity::getGuideId)
                .collect(Collectors.toList());
    }

    // ─── MAPPER ────────────────────────────────────────────────────────────────

    private TouristProfileDto mapToDto(TouristProfileEntity entity) {
        TouristProfileDto dto = new TouristProfileDto();

        dto.setUserId(entity.getUserId());
        dto.setLocation(entity.getLocation());
        dto.setBio(entity.getBio());
        dto.setMemberSince(entity.getMemberSince());
        dto.setBadge(entity.getBadge());
        dto.setTravelStyle(entity.getTravelStyle());
        dto.setTripStyle(entity.getTripType());
        dto.setPaceAndCompany(entity.getPaceAndCompany());
        dto.setActivityLevel(entity.getActivityLevel());
        dto.setGroupPreference(entity.getGroupPreference());
        dto.setDietaryPreferences(entity.getDietaryPreferences());
        dto.setPlanningLevel(entity.getPlanningLevel());
        dto.setAmenities(entity.getAmenities());
        dto.setTransport(entity.getTransport());
        dto.setPhotoPreference(entity.getPhotoPreference());
        dto.setAccessibility(entity.getAccessibility());
        dto.setAdditionalNotes(entity.getAdditionalNotes());
        dto.setAvatarUrl(entity.getAvatarUrl());
        dto.setCoverUrl(entity.getCoverUrl());
        dto.setUpdatedAt(entity.getUpdatedAt());

        dto.setLanguages(
                entity.getLanguages().stream()
                        .map(lang -> lang.getLanguageCode()) // adjust getter to match your LanguageEntity
                        .collect(Collectors.toSet())
        );

        dto.setInterests(
                entity.getInterests().stream()
                        .map(interest -> interest.getName()) // adjust getter to match your InterestEntity
                        .collect(Collectors.toSet())
        );

        return dto;
    }

    private void applyDtoToEntity(TouristProfileDto dto, TouristProfileEntity entity) {
        if (dto.getLocation() != null)           entity.setLocation(dto.getLocation());
        if (dto.getBio() != null)                entity.setBio(dto.getBio());
        if (dto.getBadge() != null)              entity.setBadge(dto.getBadge());
        if (dto.getTravelStyle() != null)        entity.setTravelStyle(dto.getTravelStyle());
        if (dto.getTripStyle() != null)          entity.setTripStyle(dto.getTripStyle());
        if (dto.getPaceAndCompany() != null)     entity.setPaceAndCompany(dto.getPaceAndCompany());
        if (dto.getActivityLevel() != null)      entity.setActivityLevel(dto.getActivityLevel());
        if (dto.getGroupPreference() != null)    entity.setGroupPreference(dto.getGroupPreference());
        if (dto.getDietaryPreferences() != null) entity.setDietaryPreferences(dto.getDietaryPreferences());
        if (dto.getPlanningLevel() != null)      entity.setPlanningLevel(dto.getPlanningLevel());
        if (dto.getAmenities() != null)          entity.setAmenities(dto.getAmenities());
        if (dto.getTransport() != null)          entity.setTransport(dto.getTransport());
        if (dto.getPhotoPreference() != null)    entity.setPhotoPreference(dto.getPhotoPreference());
        if (dto.getAccessibility() != null)      entity.setAccessibility(dto.getAccessibility());
        if (dto.getAdditionalNotes() != null)    entity.setAdditionalNotes(dto.getAdditionalNotes());
        if (dto.getAvatarUrl() != null)          entity.setAvatarUrl(dto.getAvatarUrl());
        if (dto.getCoverUrl() != null)           entity.setCoverUrl(dto.getCoverUrl());
    }
}