package com.hussein.challenges.creditcardfrauddetection;

import com.hussein.challenges.creditcardfrauddetection.reader.ConsoleDevice;
import com.hussein.challenges.creditcardfrauddetection.reader.ConsoleParameterReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);
		ConsoleParameterReader reader = new ConsoleParameterReader(new ConsoleDevice());
		reader.read();
	}
}
