package com.kjs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjs.customer.CustomerDto;

public class Main {

	public static void main(String[] args) {
		if (args == null || args.length != 1) {
			System.out.println("Provide a single argument that points to a csv");
			return;
		}

		try {
			BufferedReader reader = new BufferedReader(new FileReader(args[0]));

			HttpClient client = HttpClient.newHttpClient();

			String line = reader.readLine();
			while (line != null) {
				String[] data = line.split(",");
				if (data.length != 8) {
					System.out.println("Invalid data provided : " + line);
					reader.close();
					return;
				}

				HttpResponse<String> response = send(client, createCustomer(data));

				if (response.statusCode() != 200) {
					System.out.println("Error received from server, check logs and verify data.\nCode ["
							+ response.statusCode() + "]\nResponse [" + response.body() + "]");
					reader.close();
					return;
				}

				line = reader.readLine();
			}

			reader.close();
		} catch (IOException e) {
			System.out.println("Unable to read specified file, does it exist?");
		} catch (URISyntaxException e) {
			System.out.println("Unable to communicate with server, is it running?");
		} catch (InterruptedException e) {
			System.out.println("Unable to communicate with server, is it running?");
		}
	}

	private static HttpResponse<String> send(HttpClient client, CustomerDto customer)
			throws URISyntaxException, IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder().uri(new URI("http://localhost:8081/"))
				.setHeader("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writer().writeValueAsString(customer)))
				.build();
		return client.send(request, BodyHandlers.ofString());
	}

	private static CustomerDto createCustomer(String[] data) {
		return new CustomerDto().setReference(data[0]).setName(data[1]).setAddressOne(data[2]).setAddressTwo(data[3])
				.setTown(data[4]).setCounty(data[5]).setCountry(data[6]).setPostcode(data[7]);
	}
}
