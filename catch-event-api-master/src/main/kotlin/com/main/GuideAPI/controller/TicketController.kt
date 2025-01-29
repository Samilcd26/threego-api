package com.main.GuideAPI.controller

import com.main.GuideAPI.business.services.TicketService
import com.main.GuideAPI.data.models.helperModels.userHelper.TicketModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/ticket")
class TicketController (
    val ticketService: TicketService
){
    @PostMapping("/create")
    fun createNewTicket(@RequestParam userId:Long,@RequestBody ticketModel: TicketModel): ResponseEntity<TicketModel> {
        return ResponseEntity.ok(ticketService.createNewTicket(userId,ticketModel))
    }

    @DeleteMapping("/delete")
    fun deleteTicket(@RequestParam userId:Long,@RequestParam ticketId:String): ResponseEntity<List<TicketModel>> {
        return ResponseEntity.ok(ticketService.deleteTicket(userId,ticketId))
    }

    @GetMapping("/getAllTicket")
    fun getAllTicketByUserID(@RequestParam userId:Long,@RequestBody ticketIdList:List<String>): ResponseEntity<List<TicketModel>> {
        return ResponseEntity.ok(ticketService.getAllTicketFromUser(ticketIdList))
    }
}