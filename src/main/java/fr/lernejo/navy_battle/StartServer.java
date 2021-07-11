package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StartServer {
    final HttpServer myserver;

    public StartServer(int port) throws IOException {
        //instanciation de serveur
        this.myserver = HttpServer.create(new InetSocketAddress(port), 0);
        ExecutorService exec = Executors.newFixedThreadPool(1);

        //PING
        this.myserver.createContext("/ping",new Ping());
        this.myserver.createContext("/api/game/start", new Start(port));
        this.myserver.createContext("/ap/game/fire", new Fire());

        //server.createContext("/ping",new Ping() ) with Ping a class type HttpsHandler
        this.myserver.setExecutor(exec);
        // Start the server.
        this.myserver.start();

        //System.out.println("Loggggg .............");
    }

    public final void Stop_server()
    {
        this.myserver.stop(0);
    }
}
