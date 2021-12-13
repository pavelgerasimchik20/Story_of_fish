package com.geras.fishistory.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "fish_table")
data class Fish(
    @PrimaryKey
    var id: String,
    var name: String,
    var location: String,
    var weight: Double,
    var photoPath: String?
) : Serializable {
    override fun toString(): String {
        return "$name\n" +
                "$weight kg\n" +
                location
    }
}
