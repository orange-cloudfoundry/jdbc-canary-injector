package data.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JdbcCanaryInjectorApplication implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(JdbcCanaryInjectorApplication.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(JdbcCanaryInjectorApplication.class, args);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	@Override
	public void run(String... args) throws Exception {
		StringBuilder sb = new StringBuilder();
		for (final String option : args) {
			sb.append(" ").append(option);
		}
		sb = sb.length() == 0 ? sb.append("No Options Specified") : sb;
		LOG.info(String.format("Application launched with following options: %s", sb.toString()));
	}
}
