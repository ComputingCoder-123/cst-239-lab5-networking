package edu.gcu.cst239.kabwe.elijah.lab5.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.gcu.cst239.kabwe.elijah.lab5.model.Person;

public class ParserService {
    private final DataService dataService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ParserService(DataService dataService) {
        this.dataService = dataService;
    }

    public String processMessage(String message) {
        if (message == null || message.isBlank()) {
            return error("Command is required");
        }
        String[] parts = message.trim().split("\\s+");
        String command = parts[0].toUpperCase();
        try {
            return switch (command) {
                case "ALL" -> json(dataService.getPeople());
                case "GET" -> processGet(parts);
                case "ADD" -> processAdd(parts);
                case "DELETE" -> processDelete(parts);
                case "FIND" -> json(dataService.findByName(parts.length > 1 ? parts[1] : ""));
                default -> error("Unknown command: " + command);
            };
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    private String processGet(String[] parts) {
        requireLength(parts, 2, "Usage: GET id");
        Person person = dataService.getPerson(Integer.parseInt(parts[1]));
        return json(person);
    }

    private String processAdd(String[] parts) {
        requireLength(parts, 4, "Usage: ADD id name age");
        Person person = new Person(Integer.parseInt(parts[1]), parts[2], Integer.parseInt(parts[3]));
        boolean added = dataService.addPerson(person);
        return json(added ? person : null);
    }

    private String processDelete(String[] parts) {
        requireLength(parts, 2, "Usage: DELETE id");
        Person person = dataService.deletePerson(Integer.parseInt(parts[1]));
        return json(person);
    }

    private void requireLength(String[] parts, int expected, String usage) {
        if (parts.length < expected) {
            throw new IllegalArgumentException(usage);
        }
    }

    private String json(Object data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            return error("JSON serialization error");
        }
    }

    private String error(String message) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("error", message);
        return node.toString();
    }
}
