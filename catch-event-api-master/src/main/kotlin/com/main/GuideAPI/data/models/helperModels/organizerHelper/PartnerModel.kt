package com.main.GuideAPI.data.models.helperModels.organizerHelper

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import javax.persistence.*

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
data class PartnerModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long?=null,
    var imageUrl:String?=null,
    var name:String?=null,
    @Column(length = 600)
    var about:String?=null,
    var aboutLink:String?=null,
    var category: EventCategory?=null,

)
