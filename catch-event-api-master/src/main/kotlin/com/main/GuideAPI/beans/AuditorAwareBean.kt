package com.main.GuideAPI.beans

import com.main.GuideAPI.audit.AuditorAwareImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware


@Configuration
class AuditorAwareBean {

    @Bean
    public fun auditorAware(): AuditorAware<String>? {
        return AuditorAwareImpl()
    }
}