package com.emamagic.moviestreaming.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.emamagic.moviestreaming.db.dao.GenreDao
import com.emamagic.moviestreaming.db.dao.MovieDao
import com.emamagic.moviestreaming.db.dao.SliderDao
import com.emamagic.moviestreaming.db.entity.GenreEntity
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.db.entity.SliderEntity

@Database(
    entities = [
        SliderEntity::class,
        MovieEntity::class,
        GenreEntity::class
    ], version = 4, exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun sliderDao(): SliderDao
    abstract fun genreDao(): GenreDao
}