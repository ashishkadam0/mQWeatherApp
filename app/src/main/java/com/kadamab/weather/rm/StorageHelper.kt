package com.kadamab.weather.rm

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
/**

 * Created by KADAMAB on 13 April 2021

 */
class StorageHelper(context: Context?, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(
        context, DATABASE_NAME,
        factory, DATABASE_VERSION
    ) {
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_PRODUCTS_TABLE = ("CREATE TABLE " +
                TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME
                + " TEXT UNIQUE" + ")")
        db.execSQL(CREATE_PRODUCTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addCity(city: String) {
        val cursor = this.readableDatabase.rawQuery(
            "SELECT * FROM $TABLE_NAME WHERE $COLUMN_NAME = '$city'",
            null
        )
        if (cursor.moveToFirst()) {
            Log.d("ERROR", "Duplicate")
        } else {
            val values = ContentValues()
            values.put(COLUMN_NAME, city)
            val db = this.writableDatabase
            db.insert(TABLE_NAME, null, values)
            db.close()
        }
    }

    fun getAllCities(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    fun getAllCitiesWith(city: String): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME  WHERE $COLUMN_NAME = ", null)
    }

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "mobiquity.db"
        val TABLE_NAME = "recent"
        val COLUMN_ID = "_id"
        val COLUMN_NAME = "name"
    }
}