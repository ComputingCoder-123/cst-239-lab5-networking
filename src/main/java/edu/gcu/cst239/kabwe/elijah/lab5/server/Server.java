package edu.gcu.cst239.kabwe.elijah.lab5.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import edu.gcu.cst239.kabwe.elijah.lab5.service.DataService;
import edu.gcu.cst239.kabwe.elijah.lab5.service.ParserService;

public class Server {
    private final ParserService parserService;

    public Server(ParserService parserService) {
        this.parserService = parserService;
    }

    public void start(int port) {
        System.out.println("Server listening on port " + port);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                handleClient(clientSocket);
            }
        } catch (IOException ex) {
            System.err.println("Server error: " + ex.getMessage());
        }
    }

    private void handleClient(Socket socket) {
        System.out.println("Client connected: " + socket.getRemoteSocketAddress());
        try (socket;
             BufferedReader input = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

            String message;
            while ((message = input.readLine()) != null) {
                if (message.equals(".")) {
                    output.println("Goodbye");
                    break;
                }
                output.println(parserService.processMessage(message));
            }
        } catch (IOException ex) {
            System.err.println("Client error: " + ex.getMessage());
        }
        System.out.println("Client disconnected");
    }

    public static void main(String[] args) {
        DataService dataService = DataService.createWithSampleData();
        ParserService parserService = new ParserService(dataService);
        new Server(parserService).start(5050);
    }
}
