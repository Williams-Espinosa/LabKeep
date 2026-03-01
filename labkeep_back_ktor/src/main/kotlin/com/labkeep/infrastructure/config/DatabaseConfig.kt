package com.labkeep.infrastructure.config

import com.labkeep.infrastructure.adapter.persistence.repository.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.cdimascio.dotenv.dotenv
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseConfig {

    fun init() {
        val env = dotenv { ignoreIfMissing = true }

        val config = HikariConfig().apply {
            jdbcUrl         = "jdbc:postgresql://${env["DB_HOST"]}:${env["DB_PORT"]}/${env["DB_NAME"]}"
            username        = env["DB_USER"]
            password        = env["DB_PASS"]
            driverClassName = "org.postgresql.Driver"
            maximumPoolSize = 10
            isAutoCommit    = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        }

        Database.connect(HikariDataSource(config))


        transaction {
            SchemaUtils.createMissingTablesAndColumns(
                UsuarioTable,
                CategoriaTable,
                DispositivoTable,
                PrestamoTable
            )
        }
    }
}
