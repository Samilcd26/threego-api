package com.main.GuideAPI.business.services

import com.main.GuideAPI.data.models.helperModels.userHelper.TicketModel

interface TicketService {

    fun createNewTicket(userId:Long,ticketModel: TicketModel):TicketModel
    fun deleteTicket(userId:Long,ticketI: String):List<TicketModel>
    fun getAllTicketFromUser(ticketIdList:List<String>):List<TicketModel>?
}