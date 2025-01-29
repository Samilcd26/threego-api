package com.main.GuideAPI.business.services.impl

import com.main.GuideAPI.business.services.OrganizerServices
import com.main.GuideAPI.data.models.OrganizerModel
import com.main.GuideAPI.data.models.helperModels.filterHelper.FilterModel
import com.main.GuideAPI.data.models.helperModels.generalHelper.Address
import com.main.GuideAPI.data.models.helperModels.organizerHelper.Event
import com.main.GuideAPI.data.repository.AddressRepository
import com.main.GuideAPI.data.repository.EventRepository
import com.main.GuideAPI.data.repository.OrganizerRepository
import com.main.GuideAPI.data.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Time
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*


@Service
class OrganizerServicesImpl(
    @Autowired
    val organizerRepository: OrganizerRepository,
    @Autowired
    val eventRepository: EventRepository,
    @Autowired
    val addressRepository: AddressRepository,
    @Autowired
    val userRepository: UserRepository
) : OrganizerServices {


    fun isDateInRange(startDate: LocalDate, endDate: LocalDate, dateToCheck: LocalDateTime): Boolean {

        var startDateTime: LocalDateTime = startDate.atTime(0, 0)
        var endDateTime: LocalDateTime = endDate.atTime(0, 0)
        return dateToCheck in startDateTime..endDateTime
    }

    fun isHourInRange(startHour: Time, endHour: Time, dateToCheck: LocalDateTime): Boolean {
        var time: Time = Time(dateToCheck.hour, dateToCheck.minute, dateToCheck.second)

        return time in startHour..endHour
    }

    override fun getAllOrganizerByFilter(filterModel: FilterModel): List<Event>? {
        try {
            if (filterModel.country != null && filterModel.city != null) {
                var selectedAddress: MutableList<Address> =
                    addressRepository.findAddressByCountryAndCity(filterModel.country!!, filterModel.city!!)
                var selectedEventList: MutableList<Event> = arrayListOf()


                selectedAddress.map { s ->
                    if (s.eventId != null) selectedEventList.add(eventRepository.findById(s.eventId!!).orElseThrow())
                }
                if (filterModel.eventType != null) {
                    var temp = selectedEventList.filter { s -> s.type == filterModel.eventType }
                    selectedEventList = temp.toMutableList()
                }

                if (filterModel.online != null) {
                    var temp = selectedEventList.filter { s -> s.isOnline == filterModel.online }
                    selectedEventList = temp.toMutableList()
                }




                if (filterModel.ticketNeed != null) {
                    var temp = selectedEventList.filter { s -> s.isTicketNeed == filterModel.ticketNeed }
                    selectedEventList = temp.toMutableList()
                }

                if (filterModel.verifyAccount != null) {
//                    //Bu asıl değer
//                    var tempEventList: MutableList<Event> = arrayListOf()
//                    var tempOrganizerId: MutableList<Long> = arrayListOf()
//                    var tempOrganizer: MutableList<OrganizerModel> = arrayListOf()
//                    selectedEventList.map { s ->
//                        tempOrganizer.add(
//                            organizerRepository.findById(s.organizerId!!).orElseThrow()
//                        )
//                    }.toMutableList()
//
//                    tempOrganizer.map { s -> if (s.verify == filterModel.verifyAccount) tempOrganizerId.add(s.id!!) }
//                    tempOrganizer.forEach { it -> if (tempOrganizerId.contains(it.id)) tempEventList = it.event!! }
//                    selectedEventList = tempEventList
                }


                //Select by free or price range
                if (filterModel.free != null) {

                    if (filterModel.free == true) {
                        var temp = selectedEventList.filter { s -> s.price == 0 }
                        selectedEventList = temp.toMutableList()
                    } else {
                        if (filterModel.minPrice != null && filterModel.maxPrice != null) {
                            var temp =
                                selectedEventList.filter { s -> filterModel.minPrice!! <= s.price!! && s.price!! <= filterModel.maxPrice!! }
                            selectedEventList = temp.toMutableList()
                        }

                    }

                }


                //Select by category
                if (filterModel.selectCategory != null && filterModel.selectCategory!!.isNotEmpty()) {
                    var temp = selectedEventList.filter { s -> filterModel.selectCategory!!.contains(s.category) }
                    selectedEventList = temp.toMutableList()
                }



                if (filterModel.onlyFollow != null && filterModel.userId != null && filterModel.onlyFollow == true) {

                    var currentUser = userRepository.findById(filterModel.userId!!).orElseThrow()

                    var temp = selectedEventList.filter { s -> currentUser.followOrganizer!!.contains(s.organizerId!!) }
                    selectedEventList = temp.toMutableList()
                }


                //tarih aralığı olucak
                if (filterModel.startDate != null && filterModel.endDate != null) {
                    var temp: MutableList<Event> = arrayListOf()
                    selectedEventList.map { s ->
                        if (isDateInRange(
                                filterModel.startDate!!,
                                filterModel.endDate!!,
                                s.eventDateTime!!.first()
                            ) && isDateInRange(filterModel.startDate!!, filterModel.endDate!!, s.eventDateTime!!.last())
                        ) {
                            temp.add(s)
                        }
                    }

                    selectedEventList = temp
                }

                // saat aralığı
                if (filterModel.startHour != null && filterModel.endHour != null) {
                    var temp: MutableList<Event> = arrayListOf()
                    selectedEventList.map { s ->
                        if (isHourInRange(
                                filterModel.startHour!!,
                                filterModel.startHour!!,
                                s.eventDateTime!!.first()
                            ) && isHourInRange(
                                filterModel.startHour!!,
                                filterModel.startHour!!,
                                s.eventDateTime!!.last()
                            )
                        ) {
                            temp.add(s)
                        }
                    }
                    selectedEventList = temp
                }

                ///Final
//                var finalOOrganizerList: MutableList<OrganizerModel> = arrayListOf()
//                selectedEventList.map { s ->
//                    var findOrg: OrganizerModel = organizerRepository.findById(s.organizerId!!).orElseThrow()
//                    finalOOrganizerList.add(findOrg)
//                }

                return selectedEventList
            }


            return null
        } catch (e: Exception) {
            println(e)
            return null
        }
    }

    override fun getAllOrganizerById(organizerID: Long): OrganizerModel {
        return organizerRepository.findById(organizerID).orElseThrow()
    }

    override fun getOrganizerByEmail(email: String): OrganizerModel {
        return organizerRepository.findByEmail(email)
    }

    override fun addNewAddressForOrganizer(organizerId: Long, address: Address): OrganizerModel {
        var currentOrganizer:OrganizerModel = organizerRepository.findById(organizerId).orElseThrow()
        currentOrganizer.address?.add(address)
        organizerRepository.save(currentOrganizer)
        return currentOrganizer
    }


    override fun createNewOrganizer(organizer: OrganizerModel): OrganizerModel? {
        try {
            organizerRepository.save(organizer)
            return organizer
        } catch (e: Exception) {
            println(e)
            return null
        }
    }

    override fun updateOrganizerById(id: Long, organizer: OrganizerModel) {
        try {
//            var findOrganizer = organizerRepository.findById(id.toLong());
//
//
//            findOrganizer.get().address=organizer.address
//            findOrganizer.get().brand=organizer.brand
//            findOrganizer.get().category=organizer.category
//            findOrganizer.get().description=organizer.description
//            findOrganizer.get().email=organizer.email
//            findOrganizer.get().image=organizer.image
//            findOrganizer.get().openingHours=organizer.openingHours
//            findOrganizer.get().coordinate=organizer.coordinate
//
//            organizerRepository.save(findOrganizer.get())
        } catch (e: Exception) {
            println(e)
        }
    }

    override fun deleteOrganizerById(id: Long) {
        try {
//            var findItem=organizerRepository.findById(id.toLong())
//            organizerRepository.delete(findItem.get())
        } catch (e: Exception) {
            println(e)
        }
    }

    override fun searchOrganizer(organizerName: String): List<OrganizerModel>? {
        try {
            return organizerRepository.searchOrganizer(organizerName)
        } catch (e: Exception) {
            println(e)
            return organizerRepository.searchOrganizer(organizerName)
        }

    }

    override fun addNewEvent(organizerId: Long, event: Event): Event {

        try {
            event.organizerId = organizerId
            //event.address!!.map { s -> s.eventID!!.id = event.id }
            var currentOrganizer: OrganizerModel = organizerRepository.findById(organizerId).orElseThrow()
            if (!LocalDateTime.now().isBefore(currentOrganizer.eventLimitRefreshDate)) {
                currentOrganizer.eventLimit = 4
                currentOrganizer.eventLimitRefreshDate = currentOrganizer.eventLimitRefreshDate?.plusMonths(1)
            }
            var temp: ArrayList<Event>? = ArrayList<Event>()
            if (currentOrganizer.eventLimit!! >= -6) {


                if (currentOrganizer.event?.isNotEmpty() == true) currentOrganizer.event?.map { s -> temp?.add(s) }

                currentOrganizer.eventLimit = currentOrganizer.eventLimit!! - 1


                temp?.add(event)
                currentOrganizer.event = temp

                var resultOrganizer: OrganizerModel = organizerRepository.save(currentOrganizer)
                var length: Int = resultOrganizer.event!!.count() - 1
                var eventId: Long? = resultOrganizer!!.event!!.get(length).id
                resultOrganizer!!.event!!.get(length).address!!.map { s -> s.eventId = eventId }
                organizerRepository.save(resultOrganizer)

            }
            return event
        } catch (e: Exception) {
            println(e);
            return event
        }
    }

    override fun editEvent(organizerId: Long, eventId: Long, event: Event): Event {

        event.id=eventId;
        eventRepository.save(event);
        return event;
    }

    override fun addAddressToEvent(eventId: Long, address: Address): Event {

        var selectedEvent: Event = eventRepository.findById(eventId).orElseThrow()
        selectedEvent.address!!.add(address)

        eventRepository.save(selectedEvent)
        return selectedEvent
    }

    override fun removeEvent(organizerId: Long, eventId: Long): Event? {
        var currentOrganizer: OrganizerModel = organizerRepository.findById(organizerId).orElseThrow()
        var temp: ArrayList<Event> = ArrayList()
        currentOrganizer.event?.map { s -> temp.add(s) }
        temp.remove(temp.find { s -> s.id == eventId })
        currentOrganizer.event = temp
        organizerRepository.save(currentOrganizer)
        return temp.find { s -> s.id == eventId }
    }


    override fun addNewTagToEvent(eventEd: Long, tag: String): String {
        TODO("Not yet implemented")
    }

    override fun removeTagToEvent(eventEd: Long, tagId: Long): String {
        TODO("Not yet implemented")
    }
    /*
    var currentOrganizer:OrganizerModel=organizerRepository.findById(organizerId).orElseThrow()
    var temp:ArrayList<Address> = ArrayList()
    currentOrganizer.address?.map { s->temp.add(s) }
    temp.remove(temp.find { s->s.id==addressId })
    currentOrganizer.address=temp
    organizerRepository.save(currentOrganizer)
    return temp.find { s->s.id==addressId }

*/

}