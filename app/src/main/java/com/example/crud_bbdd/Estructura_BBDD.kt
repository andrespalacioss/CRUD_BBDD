package com.example.crud_bbdd

import android.provider.BaseColumns

object Estructura_BBDD{

    object FeedEntry: BaseColumns{
        const val TABLE_NAME = "DatosPersonales"
        const val NOMBRE_COLUMNA1 = "Id"
        const val NOMBRE_COLUMNA2 = "Nombre"
        const val NOMBRE_COLUMNA3 = "Apellido"
    }

    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${FeedEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${FeedEntry.NOMBRE_COLUMNA1} TEXT," +
                "${FeedEntry.NOMBRE_COLUMNA2} TEXT," +
                "${FeedEntry.NOMBRE_COLUMNA3} TEXT)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${FeedEntry.TABLE_NAME}"
}