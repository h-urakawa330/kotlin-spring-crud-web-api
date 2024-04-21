package com.example.kotlinspringcrudwebapi

/**
 * Customer model
 *
 * @property id カスタマーID
 * @property firstName 名前
 * @property lastName 名字
 */
data class Customer(
    val id: Long,
    val firstName: String,
    val lastName: String,
)