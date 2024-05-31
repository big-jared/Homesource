package org.sodgutt.homesource.db.migration

import Profile
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.sodgutt.homesource.UserDao

class V1_create_users_table : BaseJavaMigration() {
    override fun migrate(context: Context?) {
        transaction{
            SchemaUtils.create(
                UserDao
            )
        }
    }
}