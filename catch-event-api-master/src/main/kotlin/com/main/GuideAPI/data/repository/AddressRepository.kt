package com.main.GuideAPI.data.repository


import com.main.GuideAPI.data.models.helperModels.generalHelper.Address
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@EnableJpaRepositories
@Repository
interface AddressRepository:JpaRepository<Address,Long> {




    @Query("select ad from Address ad where ad.country=:country and  ad.city=:city")
    fun findAddressByCountryAndCity(@Param("country") country: String,@Param("city") city: String):MutableList<Address>;
}