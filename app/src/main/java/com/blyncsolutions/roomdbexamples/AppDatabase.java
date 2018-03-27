package com.blyncsolutions.roomdbexamples;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by laptopzone on 17-01-2018.
 */

@Database(entities = {WorldPopulation.class}, version=1 )
public abstract class AppDatabase extends RoomDatabase {

    public abstract StudentsDAO studentsDao();

    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }
}
