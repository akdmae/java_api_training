package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
            exchange.sendResponseHeaders(404,"404 Not Found".getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write("404 Not Found".getBytes());
            }
        }
        //SEND RESPONSE
        else
        {
            JsonStart requestJson = ParseResponse(exchange);
            if (requestJson == null ||  requestJson.id.equals("\"\"") || requestJson.url.equals("\"\"") || requestJson.message.equals("\"\""))
            {
                exchange.sendResponseHeaders(400, "Bad JSON".getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write( "Bad JSON".getBytes());
                }
            }
            else
            {
                exchange.sendResponseHeaders(202,"{\"id\":\"1\", \"url\":\"http://localhost:\", \"message\":\"Enjoy the Game !!!\"}".getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write("{\"id\":\"1\", \"url\":\"http://localhost:\", \"message\":\"Enjoy the Game !!!\"}".getBytes());
                }
            }
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
