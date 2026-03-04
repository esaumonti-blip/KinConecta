package org.generation.socialNetwork.profileGuide.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GuideProfileExpertiseId implements Serializable {

    private Long userId;
    private Integer expertiseId;
}
