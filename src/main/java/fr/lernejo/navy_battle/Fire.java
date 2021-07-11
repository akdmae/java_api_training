package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class Fire implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        byte[] response = "Bonjour, Bienvenue dans le navy_battle FIRE.".getBytes();
        exchange.sendResponseHeaders(200, response.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response);
        }
    }
}
