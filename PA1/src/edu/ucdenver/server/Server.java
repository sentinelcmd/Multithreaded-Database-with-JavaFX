package edu.ucdenver.server;

import edu.ucdenver.tournament.Tournament;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final int port;
    private final int backlog;
    private int connectionCounter;
    private ServerSocket serverSocket;

    private Tournament tournament;


    public Server(int port, int backlog) {
        this.port = port;
        this.backlog = backlog;
        this.connectionCounter = 0;
        this.tournament = new Tournament("Test Tournament", LocalDate.now(), LocalDate.of(2022, 11, 11));
    }

    public Server() {
        this(1820, 5);
    }

    private Socket waitForClientConnection() throws IOException {
        System.out.println("Waiting for a connection...");
        Socket clientConnection = serverSocket.accept();
        this.connectionCounter++;
        System.out.printf("Connection #%d Accepted From %s %n ", this.connectionCounter,
                clientConnection.getInetAddress().getHostName());

        return clientConnection;
    }


    public void runServer() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            this.serverSocket = new ServerSocket(this.port, this.backlog);


            while (true) {
                try {
                    Socket clientConnection = this.waitForClientConnection();

                    ClientWorker cw = new ClientWorker(clientConnection, this.tournament, this.connectionCounter);

                    executorService.execute(cw);


                } catch (IOException ioe) {
                    System.out.println("\n---------------\nServer Terminated");
                    ioe.printStackTrace();

                }
            }

        } catch (IOException ioe) {
            System.out.println("Cannot Open the Server");
            executorService.shutdown();
            ioe.printStackTrace();
        }

    }
}
