package org.generation.socialNetwork.profileTourist.controller;

import org.generation.socialNetwork.profileTourist.dto.TouristProfileDto;
import org.generation.socialNetwork.profileTourist.service.TouristProfileService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tourists")
public class TouristProfileController {

    private final TouristProfileService touristProfileService;

    public TouristProfileController(TouristProfileService touristProfileService) {
        this.touristProfileService = touristProfileService;
    }

    // ─── PROFILE ───────────────────────────────────────────────────────────────

    @GetMapping("/{userId}")
    public ResponseEntity<TouristProfileDto> getProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(touristProfileService.getProfile(userId));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<TouristProfileDto> updateProfile(
            @PathVariable Long userId,
            @RequestBody TouristProfileDto dto) {
        return ResponseEntity.ok(touristProfileService.updateProfile(userId, dto));
    }

    // ─── FAVORITE GUIDES ───────────────────────────────────────────────────────

    @GetMapping("/{touristId}/favorites")
    public ResponseEntity<List<Long>> getFavoriteGuides(@PathVariable Long touristId) {
        return ResponseEntity.ok(touristProfileService.getFavoriteGuides(touristId));
    }

    @PostMapping("/{touristId}/favorites/{guideId}")
    public ResponseEntity<Void> addFavoriteGuide(
            @PathVariable Long touristId,
            @PathVariable Long guideId) {
        touristProfileService.addFavoriteGuide(touristId, guideId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{touristId}/favorites/{guideId}")
    public ResponseEntity<Void> removeFavoriteGuide(
            @PathVariable Long touristId,
            @PathVariable Long guideId) {
        touristProfileService.removeFavoriteGuide(touristId, guideId);
        return ResponseEntity.noContent().build();
    }
}