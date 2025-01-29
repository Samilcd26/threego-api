package com.main.GuideAPI.data.repository

import com.main.GuideAPI.data.models.OrganizerModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.repository.query.Param

import org.springframework.stereotype.Repository
import javax.validation.constraints.Email


@EnableJpaRepositories
@Repository
interface OrganizerRepository :JpaRepository<OrganizerModel,Long>{

    //@Query("select org from OrganizerModel org where org.address.country=:countryName")
    //fun getPublisherByCountry(@Param("organizerName") countryName: String):List<OrganizerModel>;


    @Query("select org from OrganizerModel org where org.title like :organizerName%")
    fun searchOrganizer(@Param("organizerName") organizerName: String):List<OrganizerModel>;

    fun findByEmail(email: String):OrganizerModel
}