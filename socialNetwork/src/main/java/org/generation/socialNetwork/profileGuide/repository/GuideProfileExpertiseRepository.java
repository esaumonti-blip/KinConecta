package org.generation.socialNetwork.profileGuide.repository;

import org.generation.socialNetwork.profileGuide.model.GuideProfileExpertise;
import org.generation.socialNetwork.profileGuide.model.GuideProfileExpertiseId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GuideProfileExpertiseRepository extends JpaRepository<GuideProfileExpertise, GuideProfileExpertiseId> {
}