package edu.ucdenver.server;

import edu.ucdenver.tournament.Tournament;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;

public class ClientWorker implements Runnable {
    private final Tournament tournament;
    private final Socket clientConnection;
    private final int id;

    PrintWriter output;
    BufferedReader input;
    private boolean keepRunningClient;


    public ClientWorker(Socket connection, Tournament tournament, int id) {
        this.clientConnection = connection;
        this.tournament = tournament;
        this.id = id;
        this.keepRunningClient = true;

    }

    private void getOutputStream(Socket clientConnection) throws IOException {
        this.output = new PrintWriter(clientConnection.getOutputStream(), true);
    }

    private void getInputStream(Socket clientConnection) throws IOException {
        this.input = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
    }

    private void processClientRequest() throws IOException {

        String clientMessage = this.input.readLine(); //receive client
        displayMsg("Client >>> " + clientMessage);
        /*
         * Processes client requests into tournament.
         *
         * ===============KEY===============
         *      "C|" -> Add Country
         *      "C|Country Name"
         * <p>
         *      "T|" -> Add Team
         *      "T|Team Name| Country Name"
         * <p>
         *      "P|" -> Add Player
         *      "P|TeamName|PlayerName|Age|Height|Weight"
         * <p>
         *      "M|" -> Add Match
         *      "M|Date|TeamA|TeamB
         * <p>
         *      "R|" -> Add Referee
         *      "R|Name|CountryName"
         * <p>
         *      "AR|" -> Add Referee to MATCH
         *      "AR|Date|Name"
         * <p>
         *      "AP|" -> Add Player to MATCH
         *      "AP|Date|Team|Player"
         * <p>
         *      "LU|" -> List Upcoming Matches
         * <p>
         *      "LO|" -> List Matches on Day
         *      "LO|Date"
         * <p>
         *      "LT|" -> List Team Matches
         * <p>
         *      "L|" -> Load File
         *      "L|Fle Name"
         * <p>
         *      "S|" -> Save File
         *      "S|File Name"
         * <p>
         *      "Z|" -> Print Tournament
         * <p>
         *      "E|" -> Terminates the Client Connection
         * =============================================
         */


        String[] arguments = clientMessage.split("\\|"); // splits string delimited |
        String response = null;
        try {
            switch (arguments[0]) { //arguments[0] command
                case "C": // Add Country
                    this.tournament.addCountry(arguments[1]);
                    response = "OK|";
                    break;
                case "T": // Add Team
                    this.tournament.addTeam(arguments[1], arguments[2]);
                    response = "OK|";
                    break;
                case "P": //Add Player
                    this.tournament.addPlayer(arguments[1], arguments[2], Integer.parseInt(arguments[3]), Integer.parseInt(arguments[4]), Integer.parseInt(arguments[5]));
                    response = "OK|";
                    break;
                case "M": //Add Match
                    String[] dateFields = arguments[1].split(",");
                    LocalDate date = LocalDate.of(Integer.parseInt(dateFields[0]), Integer.parseInt(dateFields[1]),
                            Integer.parseInt(dateFields[2]));
                    this.tournament.addMatch(date, arguments[2], arguments[3]);
                    response = "OK|";
                    break;
                case "R": //Add Referee
                    this.tournament.addReferee(arguments[1], arguments[2]);
                    response = "OK|";
                    break;
                case "AR": //Add Referee to MATCH
                    dateFields = arguments[1].split(",");
                    date = LocalDate.of(Integer.parseInt(dateFields[0]), Integer.parseInt(dateFields[1]),
                            Integer.parseInt(dateFields[2]));
                    this.tournament.addRefereeToMatch(date, arguments[2]);
                    response = "OK|";
                    break;
                case "AP": //Add Player to MATCH
                    dateFields = arguments[1].split(",");
                    date = LocalDate.of(Integer.parseInt(dateFields[0]), Integer.parseInt(dateFields[1]),
                            Integer.parseInt(dateFields[2]));
                    this.tournament.addPlayerToMatch(date, arguments[2], arguments[3]);
                    response = "OK|";
                    break;
                case "LU": //List Upcoming Matches
                    response = "OK|" + this.tournament.getUpcomingMatches();
                    break;

                case "LO": // List Matches On Date
                    dateFields = arguments[1].split(",");
                    date = LocalDate.of(Integer.parseInt(dateFields[0]), Integer.parseInt(dateFields[1]),
                            Integer.parseInt(dateFields[2]));
                    response = "OK|" + this.tournament.getMatchesOn(date);
                    break;

                case "LT": // List Matches for Team
                    response = "OK|" + this.tournament.getMatchesFor(arguments[1]);

                    break;
                case "L": //Load File
                    this.tournament.loadFromFile(arguments[1]);

                    response = "OK";
                    break;
                case "S": //Save File
                    this.tournament.saveToFile(arguments[1]);
                    response = "OK|";
                    break;

                case "Z": //Print Tournament
                    response = "OK|" + this.tournament.toString();
                    break;
                case "E": //Terminate
                    this.keepRunningClient = false;
                    response = "OK|Terminating";
                    break;
                default:
                    response = "ERR|Unknown Command";

            }
        } catch (IllegalArgumentException iae) {
            response = "ERR|" + iae.getMessage();
        }
        this.sendMessage(response);

    }

    private void closeClientConnection() {
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
            this.clientConnection.close();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

    }

    private void sendMessage(String message) {
        displayMsg("Server >>> " + message);
        this.output.println(message);
    }

    public void displayMsg(String message) {
        System.out.printf("Client[%d] >>> %s%n", this.id, message);
    }

    @Override
    public void run() {
        displayMsg("Getting Data");
        try {
            getOutputStream(clientConnection);
            getInputStream(clientConnection);

            sendMessage("Connected to WorldCup Database Server created by Noah & Bailey");

            while (this.keepRunningClient)
                processClientRequest();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeClientConnection();

        }
        //Send data here
    }
}
