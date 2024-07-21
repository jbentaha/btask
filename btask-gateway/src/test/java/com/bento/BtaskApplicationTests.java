package com.bento;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.SpringVersion;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BtaskApplicationTests {

	@Test
	void contextLoads() {
		assertEquals("6.0.2", SpringVersion.getVersion());
	}

}
