package my.kopring.batch.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(basePackages = [ "my.kopring.batch.example.repository" ],
    transactionManagerRef = "exampleTransactionManager",
    entityManagerFactoryRef = "exampleEntityManager"
)
class ExampleDbConfig {
    @Autowired
    private lateinit var env: Environment

    @Bean
    fun exampleEntityManager(): LocalContainerEntityManagerFactoryBean {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = exampleDataSource()
        em.setPackagesToScan("my.kopring.batch.example.entity")

        val vendorAdapter = HibernateJpaVendorAdapter()
        em.jpaVendorAdapter = vendorAdapter

        //Hibernate 설정
        val properties = mutableMapOf<String, String?>()
        properties["hibernate.hbm2ddl.auto"] = env.getProperty("jpa.example.hibernate.ddl-auto")
        properties["hibernate.generate-ddl"] = env.getProperty("jpa.example.hibernate.generate-ddl")
        properties["hibernate.physical_naming_strategy"] = env.getProperty("jpa.example.hibernate.naming.physical-strategy")
        properties["hibernate.show_sql"] = env.getProperty("jpa.example.properties.hibernate.show_sql")
        properties["hibernate.format_sql"] = env.getProperty("jpa.example.properties.hibernate.format_sql")
        em.setJpaPropertyMap(properties)

        return em
    }

    @Bean(name = ["exampleDataSource"])
    @ConfigurationProperties(prefix = "datasource.example")
    fun exampleDataSource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @Bean
    fun exampleTransactionManager(): PlatformTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = exampleEntityManager().`object`
        return transactionManager
    }

    @Bean(name = ["exampleJdbcTemplate"])
    fun jdbcTemplate(@Qualifier("exampleDataSource") exampleDataSource: DataSource): JdbcTemplate {
        return JdbcTemplate(exampleDataSource)
    }
}