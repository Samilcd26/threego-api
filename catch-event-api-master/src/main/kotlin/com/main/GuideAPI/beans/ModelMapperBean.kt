package com.main.GuideAPI.beans

import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ModelMapperBean {
@Bean
fun modelMapper():ModelMapper{
    return  ModelMapper();
}

}