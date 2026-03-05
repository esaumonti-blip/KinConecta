package org.generation.socialNetwork.help.service;


import lombok.RequiredArgsConstructor;
import org.generation.socialNetwork.help.model.SupportTicketsAttachment;
import org.generation.socialNetwork.help.repository.SupportTicketsAttachmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service



public class SupportTicketsAttachmentService {
    private final SupportTicketsAttachmentRepository attachmentRepository;

    public SupportTicketsAttachment saveAttachment(SupportTicketsAttachment attachment){
        return attachmentRepository.save(attachment);
    }
    public interface SupportTicketsAttachmentRepository
            extends JpaRepository<SupportTicketsAttachment, Long> {
    }
}

