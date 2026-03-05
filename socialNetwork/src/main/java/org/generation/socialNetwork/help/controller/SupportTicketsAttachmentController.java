package org.generation.socialNetwork.help.controller;

import lombok.RequiredArgsConstructor;
import org.generation.socialNetwork.help.model.SupportTicketsAttachment;
import org.generation.socialNetwork.help.repository.SupportTicketsAttachmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupportTicketsAttachmentService {

    private final SupportTicketsAttachmentRepository attachmentRepository;

    public List<SupportTicketsAttachment> getAllAttachments(){
        return attachmentRepository.findAll();
    }

    public SupportTicketsAttachment saveAttachment(SupportTicketsAttachment attachment){
        return attachmentRepository.save(attachment);
    }

}