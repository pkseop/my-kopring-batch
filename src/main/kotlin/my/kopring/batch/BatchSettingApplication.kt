package my.kopring.batch

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import kotlin.system.exitProcess

@SpringBootApplication
class BatchSettingApplication

fun main(args: Array<String>) {
	var exitCode = 0

	try {
		exitCode = SpringApplication.exit(runApplication<BatchSettingApplication>(*args))
	} catch (e: Exception) {
		exitCode = 5
	} finally {
		exitProcess(exitCode)
	}
}
