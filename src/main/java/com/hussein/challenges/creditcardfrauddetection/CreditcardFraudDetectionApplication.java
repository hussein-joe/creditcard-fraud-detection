package com.hussein.challenges.creditcardfrauddetection;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

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
