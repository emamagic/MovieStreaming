package com.emamagic.moviestreaming.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.emamagic.moviestreaming.db.dao.*
import com.emamagic.moviestreaming.db.entity.*

@Database(
    entities = [
        SliderEntity::class,
        MovieEntity::class,
        GenreEntity::class,
        CastEntity::class,
        SeasonEntity::class
    ], version = 6, exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun sliderDao(): SliderDao
    abstract fun genreDao(): GenreDao
    abstract fun castDao(): CastDao
    abstract fun seasonDao(): SeasonDao
}