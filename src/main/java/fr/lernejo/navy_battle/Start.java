package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class Start implements HttpHandler {

    protected final StringBuilder URL = new StringBuilder();

    public Start(int port) {
        this.URL.append("http://localhost:").append(port);
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //NOT FOUND PAGE
        if (!exchange.getRequestMethod().equals("POST"))
        {
            NoFoundR(exchange);
        }
        //SEND RESPONSE
        else
        {
            JsonStart requestJson = ParseResponse(exchange);
            if (requestJson == null ||  requestJson.id.equals("\"\"") || requestJson.url.equals("\"\"") || requestJson.message.equals("\"\""))
            {
                SendR(exchange,400,"Bad JSON");
            }
            else
            {
                SendR(exchange,202,"{\n\t\"id\":\"" + UUID.randomUUID() + "\",\n\t\"url\":\"" + this.URL + "\",\n\t\"message\":\"May the best code win\"\n}");
            }
        }
    }

    private void SendR(HttpExchange exchange, int rCode, String response) throws IOException {
        exchange.sendResponseHeaders(rCode, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private void NoFoundR(HttpExchange exchange) throws IOException {  exchange.sendResponseHeaders(404, "NULL".getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write("NULL".getBytes());
        }
    }

    private JsonStart ParseResponse(HttpExchange exchange) throws IOException {
        ObjectMapper m = new ObjectMapper();
        JsonStart requestJson = null;
        String res = StreamtoString(exchange.getRequestBody());
        if ( !res.isBlank())
        {
            try {
                requestJson = m.readValue(res,JsonStart.class);
            }
            catch (IllegalArgumentException e)
            {
                exchange.sendResponseHeaders(400,"Bad request".length());
            }
        }
        return requestJson;
    }

    private String StreamtoString(InputStream requestBody) throws IOException {
        StringBuilder res = new StringBuilder();
        int i;
        while ((i = requestBody.read()) > 0)
        {
            res.append(i);
        }
        return String.valueOf(res);
    }

}
