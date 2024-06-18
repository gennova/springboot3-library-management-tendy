package com.amazone.test;

import com.amazon.test.model.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
@SpringBootTest
public class IntegrationTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testBookCreation() {
        Book book = new Book("1234567890", "Clean Code", "Robert C. Martin");

        // Assert properties
        assertEquals("1234567890", book.getIsbn());
        assertEquals("Clean Code", book.getTitle());
        assertEquals("Robert C. Martin", book.getAuthor());
    }

    @Test
    public void testGetProductsSuccess() {
        String url = "http://localhost:8080/api/v1/books";  // Adjust port if needed
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Assert response body content (e.g., using JSONAssert)
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }
}
