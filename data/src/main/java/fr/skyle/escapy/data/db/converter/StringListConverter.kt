package fr.skyle.escapy.data.db.converter

import androidx.room.TypeConverter
import fr.skyle.escapy.data.json

class StringListConverter {

    @TypeConverter
    fun fromList(list: List<String>): String =
        json.encodeToString(list)

    @TypeConverter
    fun toList(data: String): List<String> =
        json.decodeFromString<List<String>>(data)
}