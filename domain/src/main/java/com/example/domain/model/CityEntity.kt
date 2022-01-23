package com.example.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.core.Constants.TABLE_CITY

@Entity(
    tableName = TABLE_CITY,
    indices = [Index(value = ["country", "city", "iso2", "iso3"], unique = true)]
)
data class CityEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "country")
    val country: String,

    @ColumnInfo(name = "city")
    val city: String,

    @ColumnInfo(name = "iso2")
    val iso2: String,

    @ColumnInfo(name = "iso3")
    val iso3: String
)