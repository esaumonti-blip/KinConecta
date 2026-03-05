package org.generation.socialNetwork.help.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupportTicketRequest {

    private Long userId;
    private String roleContext;
    private String fullName;
    private String email;
    private String subject;
    private String category;
    private String message;

}
