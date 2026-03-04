package org.generation.socialNetwork.profileGuide.repository;

import org.generation.socialNetwork.profileGuide.model.GuideProfile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GuideProfileRepository extends JpaRepository<GuideProfile, Long> {
}