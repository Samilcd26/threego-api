package com.main.GuideAPI.business.services

import com.main.GuideAPI.data.models.OrganizerModel
import com.main.GuideAPI.data.models.helperModels.filterHelper.FilterModel
import com.main.GuideAPI.data.models.helperModels.generalHelper.Address
import com.main.GuideAPI.data.models.helperModels.organizerHelper.Event


interface OrganizerServices {
    fun getAllOrganizerByFilter(filterModel: FilterModel):List<Event>?
    fun getAllOrganizerById(organizerID:Long):OrganizerModel
    fun getOrganizerByEmail(email:String):OrganizerModel

    fun addNewAddressForOrganizer(organizerId: Long,address: Address):OrganizerModel
    fun createNewOrganizer(organizer:OrganizerModel):OrganizerModel?
    fun updateOrganizerById(id:Long,organizer: OrganizerModel)
    fun deleteOrganizerById(id: Long)

    fun searchOrganizer(organizerName:String):List<OrganizerModel>?


    fun removeEvent(organizerId:Long,eventId: Long):Event?
    fun addNewEvent(organizerId:Long,event: Event):Event

    fun editEvent(organizerId:Long, eventId:Long,event: Event):Event
    fun addAddressToEvent(eventId:Long, address: Address):Event


    fun addNewTagToEvent(eventEd:Long,tag:String):String
    fun removeTagToEvent(eventEd:Long,tagId:Long):String



}