package edu.ucdenver.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private final int serverPort;
    private final String serverIP;
    private boolean isConnected;
    private Socket serverConnection;
    private PrintWriter output;
    private BufferedReader input;

    public Client(String ip, int port) {
        this.serverPort = port;
        this.serverIP = ip;
        this.isConnected = false;
    }

    public Client() {
        this("127.0.0.1", 1820);
    }

    public boolean isConnected() {
        return this.isConnected;
    }

    private PrintWriter getOutputStream() throws IOException {
        return new PrintWriter(this.serverConnection.getOutputStream(), true);
    }

    private BufferedReader getInputStream() throws IOException {
        return new BufferedReader(new InputStreamReader(this.serverConnection.getInputStream()));
    }

    public void connect() {
        displayMsg("Attempting connection to server");

        try {
            this.serverConnection = new Socket(this.serverIP, this.serverPort);
            this.isConnected = true;
            this.output = this.getOutputStream();
            this.input = this.getInputStream();
            getServerInitialResponse();
        } catch (IOException e) {
            this.input = null;
            this.output = null;
            this.serverConnection = null;
            this.isConnected = false;
        }
    }

    public void disconnect() {
        displayMsg("\n Terminating Client Connection to Server \n");
        try {
            this.input.close();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        try {
            this.output.close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            this.serverConnection.close();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void getServerInitialResponse() throws IOException {
        String srvResponse = this.input.readLine();
        displayMsg("Server >>> " + srvResponse);
    }

    public String sendRequest(String request) throws IOException {
        this.output.println(request);
        displayMsg("Client >>> " + request);
        String srvResponse = this.input.readLine();
        displayMsg("Server >>> " + srvResponse);
        return srvResponse;
    }

    public void displayMsg(String message) {
        System.out.println(message);
    }
}
