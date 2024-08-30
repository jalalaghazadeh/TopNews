package com.mrjalal.topnews.domain.repository.util

interface Dto {
    fun toEntity(): NewsEntity
}