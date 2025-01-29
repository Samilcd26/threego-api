package com.main.GuideAPI.controller


import com.main.GuideAPI.business.services.OrganizerServices
import com.main.GuideAPI.data.models.OrganizerModel
import com.main.GuideAPI.data.models.helperModels.filterHelper.FilterModel
import com.main.GuideAPI.data.models.helperModels.generalHelper.Address
import com.main.GuideAPI.data.models.helperModels.organizerHelper.Event
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/organizer")
class OrganizerController(
    val organizerServices: OrganizerServices) {


    

    @PostMapping("/allDataByFilter")
    fun getAllOrganizerByCity(@RequestBody filterModel: FilterModel ):List<Event>?{
        return organizerServices.getAllOrganizerByFilter(filterModel)
    }
    @GetMapping("/getDataById")
    fun getAllOrganizerById(@RequestParam organizerId:Long):OrganizerModel{
        return organizerServices.getAllOrganizerById(organizerId)
    }

    @PostMapping("/create")
    fun createNewOrganizer(@RequestBody organizer:OrganizerModel):ResponseEntity<OrganizerModel>{
       return ResponseEntity.ok(organizerServices.createNewOrganizer(organizer))
    }
    @PostMapping("/addAddressToEvent")
    fun addAddressToEvent(@RequestParam eventId: Long, @RequestBody address: Address):ResponseEntity<Event>{
        return ResponseEntity.ok(organizerServices.addAddressToEvent(eventId,address))
    }


    @PostMapping("/addAddress")
    fun addAddress(@RequestParam organizerId: Long, @RequestBody address: Address):ResponseEntity<OrganizerModel>{
        return ResponseEntity.ok(organizerServices.addNewAddressForOrganizer(organizerId,address))
    }
   

    @GetMapping("/search")
    fun searchOrganizer(@RequestParam organizerName:String):ResponseEntity<List<OrganizerModel>>{
        return ResponseEntity.ok(organizerServices.searchOrganizer(organizerName))
    }

    @GetMapping("/getOrganizerByEmail")
    fun getOrganizerByEmail(@RequestParam email:String):ResponseEntity<OrganizerModel>{
        return ResponseEntity.ok(organizerServices.getOrganizerByEmail(email))
    }
/*
    @PostMapping("/addAddress")
    fun addNewAddress(@RequestParam organizerId:Long,@RequestBody address: Address):ResponseEntity<List<Address>>{
        return ResponseEntity.ok(organizerServices.addNewAddress(organizerId,address))
    }
    @GetMapping("/removeAddress")
    fun removeAddress(@RequestParam organizerId:Long,@RequestParam addressId:Long):ResponseEntity<Address?>{
        //address tablosunda kalanları temizlemenin yolunu bul
        return ResponseEntity.ok(organizerServices.removeAddress(organizerId,addressId))
    }
    */

    @PostMapping("/addEvent")
    fun addNewEvent(@RequestParam organizerId:Long,@RequestBody event: Event):ResponseEntity<Event>{
        return ResponseEntity.ok(organizerServices.addNewEvent(organizerId, event))
    }
    @DeleteMapping("/removeEvent")
    fun removeEvent(@RequestParam organizerId:Long,@RequestParam eventId: Long):ResponseEntity<Event>{
        return ResponseEntity.ok(organizerServices.removeEvent(organizerId,eventId))
    }

    @PostMapping("/editEvent")
    fun editEvent(@RequestParam organizerId:Long,@RequestParam eventId: Long,@RequestBody event: Event):ResponseEntity<Event>{
        return ResponseEntity.ok(organizerServices.removeEvent(organizerId,eventId))
    }

    @PostMapping("/addTag")
    fun addNewTag(@RequestParam organizerId:Long,@RequestParam tag: String):ResponseEntity<String>{
        return ResponseEntity.ok(organizerServices.addNewTagToEvent(organizerId,tag))
    }
    @DeleteMapping("/removeTag")
    fun removeTag(@RequestParam organizerId:Long,@RequestParam addressId:Long):ResponseEntity<String>{
        return ResponseEntity.ok(organizerServices.removeTagToEvent(organizerId,addressId))
    }



}