package org.generation.socialNetwork.profileGuide.repository;

import org.generation.socialNetwork.profileGuide.model.GuideProfileLanguage;
import org.generation.socialNetwork.profileGuide.model.GuideProfileLanguageId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GuideProfileLanguageRepository extends JpaRepository<GuideProfileLanguage, GuideProfileLanguageId> {
}