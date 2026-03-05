package org.generation.socialNetwork.help.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "support_tickets_attachment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class SupportTicketsAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(unique = true, nullable = false)
    private Long id;

    @Column (name = "file_url", nullable = false)
    private String fileUrl;

    @Column (name = "file_name", nullable = false)
    private String fileName;

    @Column (name = "mime_type", nullable = false)
    private String mimeType;

    @Column (name = "file_size", nullable = false)
    private String fileSize;

    @Column (name = "uploaded_at", nullable = false)
    private String uploadedAt;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private SupportTickets ticket;
}
