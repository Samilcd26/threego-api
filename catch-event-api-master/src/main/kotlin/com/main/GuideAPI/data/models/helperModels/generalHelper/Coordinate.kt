package com.main.GuideAPI.data.models.helperModels.generalHelper

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
data class Coordinate(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable=false)
    var id:Long?=null,
    val latitude:Double? = null,
    val longitude:Double? = null
)
