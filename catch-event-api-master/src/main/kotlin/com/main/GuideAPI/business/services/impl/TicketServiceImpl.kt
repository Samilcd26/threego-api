package com.main.GuideAPI.business.services.impl

import com.main.GuideAPI.business.services.TicketService
import com.main.GuideAPI.data.models.UserModel
import com.main.GuideAPI.data.models.helperModels.userHelper.TicketModel
import com.main.GuideAPI.data.repository.TicketRepository
import com.main.GuideAPI.data.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TicketServiceImpl(
    @Autowired
   val userRepository: UserRepository,
    @Autowired
val ticketRepository: TicketRepository
): TicketService{
    override fun createNewTicket(userId: Long, ticketModel: TicketModel): TicketModel {
        var currentUser:UserModel=userRepository.findById(userId).orElseThrow()
        var temp:ArrayList<TicketModel> =ArrayList()

        currentUser.ticketList?.map { s->temp.add(s) }
        temp.add(ticketModel)

        currentUser.ticketList=temp
        userRepository.save(currentUser)
        return ticketModel
    }

    override fun deleteTicket(userId: Long, ticketId: String): List<TicketModel> {
        var currentUser:UserModel=userRepository.findById(userId).orElseThrow()
        var temp:ArrayList<TicketModel> =ArrayList()

        currentUser.ticketList?.map { s->temp.add(s) }
        temp.remove(temp.find { a->a.id== ticketId })

        currentUser.ticketList=temp
        userRepository.save(currentUser)
        return temp
    }

    override fun getAllTicketFromUser(ticketIdList:List<String>): List<TicketModel>? {
        ticketRepository.findAllById(ticketIdList)

        return ticketRepository.findAllById(ticketIdList)
    }

}
