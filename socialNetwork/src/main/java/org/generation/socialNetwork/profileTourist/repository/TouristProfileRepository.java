package org.generation.socialNetwork.profileTourist.repository;

import org.generation.socialNetwork.profileTourist.model.TouristProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TouristProfileRepository extends JpaRepository<TouristProfileEntity, Long> {
}