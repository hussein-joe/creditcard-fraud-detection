package com.hussein.challenges.creditcardfrauddetection;

import com.hussein.challenges.creditcardfrauddetection.listeners.FraudCreditCardsContainer;
import com.hussein.challenges.creditcardfrauddetection.reader.ConsoleParameterReader;
import com.hussein.challenges.creditcardfrauddetection.reader.file.TransactionFileReaderAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CreditcardFraudDetectionApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder()
				.sources(CreditcardFraudDetectionApplication.class)
				.main(CreditcardFraudDetectionApplication.class)
				.bannerMode(Banner.Mode.OFF)
				.build()
				.run(args);
	}
}
