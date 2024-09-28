package com.ecommerce.beatiful.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import co.touchlab.sqliter.DatabaseConfiguration
import com.ecommerce.beatiful.db.EcommerceDB
import org.koin.dsl.module


actual val driverSQLModule  = module {

        single<SqlDriver> {
            NativeSqliteDriver(
                schema = EcommerceDB.Schema,
                name = "EcommerceDB.db",
               onConfiguration = { config: DatabaseConfiguration ->
                   config.copy(
                       extendedConfig = DatabaseConfiguration.Extended(foreignKeyConstraints = true)
                   )
               }
            )
        }
}