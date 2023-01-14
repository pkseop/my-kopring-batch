package my.kopring.batch.job.example

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ExampleJobConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val exampleTasklet: ExampleTasklet
) {
    @Bean
    fun exampleJob(): Job {
        return jobBuilderFactory.get("exampleJob")
            .start(exampleStep())
            .build()
    }

    @Bean
    fun exampleStep(): Step {
        return stepBuilderFactory.get("exampleStep")
            .tasklet(exampleTasklet)
            .build()
    }
}