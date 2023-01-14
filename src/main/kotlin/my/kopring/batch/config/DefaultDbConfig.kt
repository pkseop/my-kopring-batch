package biz.gripcloud.batch.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
class DefaultDbConfig {

    @Bean
    @Primary
    @ConfigurationProperties("datasource.default")
    fun springBatchDb(): DataSource {
        val builder = DataSourceBuilder.create()
        builder.type(HikariDataSource::class.java)
        return builder.build()
    }

    @Bean
    fun springBatchTxManager(): PlatformTransactionManager {
        return DataSourceTransactionManager(springBatchDb())
    }
}