package com.kjs;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.github.tomakehurst.wiremock.matching.EqualToPattern;

@WireMockTest(httpPort = 8081)
public class MainTest {

	private final ByteArrayOutputStream systemOutCapture = new ByteArrayOutputStream();

	@BeforeEach
	public void before() {
		System.setOut(new PrintStream(systemOutCapture));
	}

	@Test
	public void verifyNullArgumentsIsReported() {
		Main.main(null);
		assertEquals("Provide a single argument that points to a csv", this.systemOutCapture.toString().trim());
	}

	@Test
	public void verifyZeroArgumentsIsReported() {
		Main.main(new String[] {});
		assertEquals("Provide a single argument that points to a csv", this.systemOutCapture.toString().trim());
	}

	@Test
	public void verifyMoreThanOneArgumentIsReported() {
		Main.main(new String[] { "One", "Two" });
		assertEquals("Provide a single argument that points to a csv", this.systemOutCapture.toString().trim());
	}

	@Test
	public void verifyNonExistantFileIsHandled() {
		String fileParameter = new File("src/test/resources/nonExistantCustomerData.csv").getAbsolutePath();

		Main.main(new String[] { fileParameter });
		assertEquals("Unable to read specified file, does it exist?", this.systemOutCapture.toString().trim());
	}

	@Test
	public void verifyValidFileIsPostedToEndpoint() {
		stubFor(post(urlPathMatching("/.*")).willReturn(
				aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody("Error Content")));

		String fileParameter = new File("src/test/resources/validCustomerData.csv").getAbsolutePath();

		Main.main(new String[] { fileParameter });

		verify(postRequestedFor(urlEqualTo("/")).withHeader("Content-Type", new EqualToPattern("application/json"))
				.withRequestBody(new EqualToPattern(
						"{\"reference\":\"reference\",\"name\":\"name\",\"addressOne\":\"addressOne\",\"addressTwo\":\"addressTwo\",\"town\":\"town\",\"county\":\"county\",\"country\":\"country\",\"postcode\":\"postcode\"}")));
		assertTrue(this.systemOutCapture.toString().trim().isEmpty());
	}

	@Test
	public void verifyErrorResponseIsHandled() {
		stubFor(post(urlPathMatching("/.*")).willReturn(
				aResponse().withStatus(500).withHeader("Content-Type", "application/json").withBody("Error Content")));

		String fileParameter = new File("src/test/resources/validCustomerData.csv").getAbsolutePath();

		Main.main(new String[] { fileParameter });

		assertEquals("Error received from server, check logs and verify data.\nCode [500]\nResponse [Error Content]",
				this.systemOutCapture.toString().trim());
	}
}
