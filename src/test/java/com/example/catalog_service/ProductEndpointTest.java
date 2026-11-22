package com.example.catalog_service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import tools.jackson.databind.json.JsonMapper;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductEndpointTest {

    @LocalServerPort
    private int port;

    @Test
    void returnsProductsWithIdsAndPrices() throws Exception {
        var request = HttpRequest.newBuilder(
                URI.create("http://localhost:" + port + "/api/products")).GET().build();

        try (var client = HttpClient.newHttpClient()) {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assertThat(response.statusCode()).isEqualTo(200);
            assertThat(response.headers().firstValue("Content-Type").orElse(""))
                    .contains("application/json");

            var products = JsonMapper.builder().build().readTree(response.body());
            assertThat(products.isArray()).isTrue();
            assertThat(products.size()).isEqualTo(3);
            assertThat(products.get(0).path("id").asLong()).isEqualTo(1);
            assertThat(products.get(0).path("name").asText()).isEqualTo("Java Notebook");
            assertThat(products.get(0).path("priceInPaise").asInt()).isEqualTo(19_900);
        }
    }
}
