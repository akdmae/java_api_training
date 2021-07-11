package fr.lernejo.navy_battle;

import java.io.IOException;

public class Launcher {
    //LAUNCH PROGRAM
    public static void main(String[] args) throws InterruptedException, IOException {
        if (args.length == 0)
        {
            System.err.println("No Port");
            System.exit(-1);
        }
        else if (args.length >  1)
        {
            new SecondServer().Server(Integer.parseInt(args[0]),args[1]);
        }
        new StartServer(Integer.parseInt(args[0]));

    }
}
