package com.carson_mccombs.fetch_exercise.customItem.models

import kotlinx.serialization.Serializable

@Serializable
data class CustomItem(
    val id: Long,
    val listId: Long,
    val name: String?
) {
    val isValid = !name.isNullOrBlank()
}