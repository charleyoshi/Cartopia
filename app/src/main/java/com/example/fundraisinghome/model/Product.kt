package com.example.fundraisinghome.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Product(
    @StringRes val nameRes: Int,
    @StringRes val descriptionRes: Int,
    @DrawableRes val imageRes: Int,
    val price: Double
)
