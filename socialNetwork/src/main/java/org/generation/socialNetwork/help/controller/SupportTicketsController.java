package org.generation.socialNetwork.help.controller;


import lombok.RequiredArgsConstructor;
import org.generation.socialNetwork.help.model.SupportTickets;
import org.generation.socialNetwork.help.service.SupportTicketsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/support-tickets")
@RequiredArgsConstructor
public class SupportTicketsController {

    private final SupportTicketsService supportTicketsService;

    @GetMapping
    public List<SupportTickets> getTickets(){
        return supportTicketsService.getAllTickets();
    }

    @GetMapping("/{id}")
    public SupportTickets getTicketById(@PathVariable Long id){
        return supportTicketsService.getTicketsById(id);
    }

    @PostMapping
    public SupportTickets createTicket(@RequestBody SupportTickets ticket){
        return supportTicketsService.createTicket(ticket);
    }

}