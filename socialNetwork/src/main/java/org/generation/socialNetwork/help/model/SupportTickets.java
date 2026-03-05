package org.generation.socialNetwork.help.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "support_tickets")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class SupportTickets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(unique = true, nullable = false)
    private Long id;

    @Column (name = "role_context", nullable = false)
    private String roleContext;

    @Column (name = "full_name", nullable = false)
    private String fullName;

    @Column (nullable = false)
    private String email;

    @Column (nullable = false)
    private String subject;

    @Column (nullable = false)
    private String category;

    @Column (nullable = false)
    private String message;

    @Column (nullable = false)
    private String status;

    @Column (name = "created_at", nullable = false)
    private String createdAt;

    @Column (name = "updated_at", nullable = false)
    private String updatedAt;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<SupportTicketsAttachment> attachments;

}
