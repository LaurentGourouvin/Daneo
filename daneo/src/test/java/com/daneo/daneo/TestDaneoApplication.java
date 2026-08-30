package com.daneo.daneo;

import org.springframework.boot.SpringApplication;

public class TestDaneoApplication {

	public static void main(String[] args) {
		SpringApplication.from(DaneoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
