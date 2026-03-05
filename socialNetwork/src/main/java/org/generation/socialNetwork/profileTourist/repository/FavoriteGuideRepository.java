package org.generation.socialNetwork.profileTourist.repository;

import org.generation.socialNetwork.profileTourist.model.FavoriteGuidesEntity;
import org.generation.socialNetwork.profileTourist.model.FavoriteGuideId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteGuidesRepository extends JpaRepository<FavoriteGuidesEntity, FavoriteGuideId> {

    List<FavoriteGuidesEntity> findByIdTouristId(Long touristId);

    boolean existsByIdTouristIdAndIdGuideId(Long touristId, Long guideId);
}