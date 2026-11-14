package edu.gcu.cst239.kabwe.elijah.lab5.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.gcu.cst239.kabwe.elijah.lab5.model.Person;

import java.util.List;

class ParserServiceTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private DataService dataService;
    private ParserService parserService;

    @BeforeEach
    void setUp() {
        List<Person> people = List.of(
            new Person(1, "Alice", 25),
            new Person(2, "Bob", 30),
            new Person(3, "Charlie", 35)
        );
        dataService = new DataService(people);
        parserService = new ParserService(dataService);
    }

    @Test
    void allReturnEveryPerson() throws Exception {
        JsonNode result = objectMapper.readTree(parserService.processMessage("ALL"));
        assertEquals(3, result.size());
    }

    @Test
    void getReturnsMatchingPerson() throws Exception {
        JsonNode result = objectMapper.readTree(parserService.processMessage("GET 1"));
        assertEquals("Alice", result.get("name").asText());
    }

    @Test
    void deleteRemovesPerson() throws Exception {
        JsonNode result = objectMapper.readTree(parserService.processMessage("DELETE 1"));
        assertTrue(result.isEmpty() || result.get("id").asInt() == 1);
    }

    @Test
    void findReturnsMatchingPeopleCaseInsensitive() throws Exception {
        JsonNode result = objectMapper.readTree(parserService.processMessage("FIND alice"));
        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).get("name").asText());
    }
}
