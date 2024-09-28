package com.ecommerce.beatiful.di

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.ecommerce.beatiful.db.EcommerceDB
import org.koin.dsl.module


actual val  driverSQLModule = module {

       single<SqlDriver> { AndroidSqliteDriver(
           schema = EcommerceDB.Schema,
           context = get(),
           name = "EcommerceDB.db",
           callback = object : AndroidSqliteDriver.Callback(EcommerceDB.Schema) {
               override fun onOpen(db: SupportSQLiteDatabase) {
                   db.setForeignKeyConstraintsEnabled(true) // enabling foreign key constraints for the Android SQLite driver.
               }
           }
       ) }

}