package com.geras.fishistory.data.dataclasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "fish_table")
data class Fish(
    @PrimaryKey @ColumnInfo(name = "fish_name")
    var name: String,
    var location: String,
    var weight: Double,
    var photo: Int
) : Serializable {
    override fun toString(): String {
        return "$name\n" +
                "$weight kg\n" +
                location
    }
}
