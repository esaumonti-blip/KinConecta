package org.generation.socialNetwork.help.service;

import lombok.RequiredArgsConstructor;
import org.generation.socialNetwork.help.model.SupportTickets;
import org.generation.socialNetwork.help.repository.SupportTicketsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class SupportTicketsService {
    private final SupportTicketsRepository supportTicketsRepository;

    public List<SupportTickets> getAllTickets(){
        return supportTicketsRepository.findAll();
    }

    public SupportTickets createTicket(SupportTickets ticket){
        return supportTicketsRepository.save(ticket);
    }

    public SupportTickets getTicketsById(Long id){
        return supportTicketsRepository.findById(id).orElse(null);
    }

}
