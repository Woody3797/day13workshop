package ibf2022.ssf.day13workshop;

import java.util.List;

import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ibf2022.ssf.day13workshop.util.IOUtil.*;

@SpringBootApplication
public class Day13workshopApplication {

	private static final Logger logger = LoggerFactory.getLogger(Day13workshopApplication.class);

	public static void main(String[] args) {
		// SpringApplication.run(Day13workshopApplication.class, args);
		SpringApplication app = new SpringApplication(Day13workshopApplication.class);
		DefaultApplicationArguments appArgs = new DefaultApplicationArguments(args);
		List<String> opsVal = appArgs.getOptionValues("dataDir");

		if (opsVal != null) {
			logger.info("" + (String) opsVal.get(0));
			createDir((String) opsVal.get(0));
		} else {
			System.exit(1);
		}

		app.run(args);
	}

}
