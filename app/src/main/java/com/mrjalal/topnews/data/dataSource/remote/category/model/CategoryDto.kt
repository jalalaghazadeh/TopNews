package com.mrjalal.topnews.data.dataSource.remote.category.model

import com.mrjalal.topnews.data.dataSource.local.category.entity.CategoryItemEntity
import com.mrjalal.topnews.domain.repository.util.Dto

data class CategoryDto(
    val categories:List<CategoryItemDto>
) {
    data class CategoryItemDto(
        val id: Int,
        val text: String,
        val iconLink: String,
    ): Dto {
        override fun toEntity() = CategoryItemEntity(
            id, text, iconLink
        )
    }

    companion object {
        val MOCK = CategoryDto(
            categories = listOf(
                CategoryItemDto(
                    id = 0,
                    text = "Apple",
                    iconLink = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ_TdcdU7mn2qOxM6fh7kKDkcaXyMlAA9kQtiN3zhkwQmBGXalcXrhZxLHzE4HKCDN9OJs&usqp=CAU"
                ),
                CategoryItemDto(
                    id = 1,
                    text = "Tesla",
                    iconLink = "https://www.svgrepo.com/show/331599/tesla.svg"
                ),
                CategoryItemDto(
                    id = 2,
                    text = "Microsoft",
                    iconLink = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwf8zES2a2xITHIrxAMUFz-AC2fMQJtppaaZHgevZIu9k0ff9wycBVJJMJ5O85VWancpU&usqp=CAU"
                ),
                CategoryItemDto(
                    id = 3,
                    text = "Google",
                    iconLink = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSVsI35NM2hR4g5qTOKRZaWem4FmoBAnLqVwQ&s"
                ),
            )
        )
    }
}
