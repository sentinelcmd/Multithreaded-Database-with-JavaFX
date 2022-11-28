package edu.ucdenver.app;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.*;


import java.io.IOException;
import java.time.format.DateTimeFormatter;


public class ClientController {

    public ListView listUpcomingMatches;
    public Tab tabUpcomingMatches;
    public DatePicker dateMatchesOnDate;
    public Tab tabTeamMatches;
    public Tab tabMatchesOnDate;
    public ListView listMatchesOn;
    public ListView listTeamMatches;
    public TextField txtMatchesTeamTeam;

    Client client;

    public ClientController() {
        client = new Client();
        client.connect();
        this.listUpcomingMatches = new ListView();
    }


    private String sendCommand(String cmd) {
        String response;
        if (client.isConnected()) {

            try {
                response = client.sendRequest(cmd); // returns server response
            } catch (IOException e) {
                response = "ERR|" + e.getMessage();
            }
        } else {
            response = "ERR|Client is not connected ";

        }
        return response;
    }


    public void listMatchesUpdate(Event event) {
        /*
        "LU|" -> List Upcoming Matches
         */
        String cmd = "LU|";
        Alert alert;
        String response = sendCommand(cmd);
        try {
            String[] respArgs = response.split("\\|");
            switch (respArgs[0]) {
                case "OK":
                    alert = new Alert(Alert.AlertType.CONFIRMATION, "Server Response: " + response, ButtonType.OK);
                    alert.show();
                    break;
                case "ERR":
                    alert = new Alert(Alert.AlertType.ERROR, respArgs[1], ButtonType.OK);
                    alert.show();

            }

            listUpcomingMatches.getItems().clear();
            for (String s : respArgs) {
                if (this.tabUpcomingMatches.isSelected())
                    listUpcomingMatches.getItems().add(s);
            }

        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR, "There are no upcoming matches", ButtonType.OK);
            alert.show();
        }

    }

    public void searchMatchesOn(ActionEvent actionEvent) {
        /*
        "LO|" -> List Matches on Day
         *      "LO|Date"
         */
        String date = dateMatchesOnDate.getValue().format(DateTimeFormatter.ofPattern("yyyy,MM,dd"));
        String cmd = String.format("%s|%s", "LO", date);
        Alert alert;
        String response = sendCommand(cmd);
        String[] respArgs = response.split("\\|");
        switch (respArgs[0]) {
            case "OK":
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Server Response: " + response, ButtonType.OK);
                alert.show();
                break;
            case "ERR":
                alert = new Alert(Alert.AlertType.ERROR, respArgs[1], ButtonType.OK);
                alert.show();

        }
        listMatchesOn.getItems().clear();
        for (String s : respArgs) {
            if (this.tabMatchesOnDate.isSelected())
                listMatchesOn.getItems().add(s);
        }


    }

    public void searchMatchesTeam(ActionEvent actionEvent) {
        /*
        "LT|" -> List Team Matches
         */
        String teamName = txtMatchesTeamTeam.getText();
        String cmd = String.format("%s|%s", "LT", teamName);
        Alert alert;
        String response = sendCommand(cmd);
        String[] respArgs = response.split("\\|");
        switch (respArgs[0]) {
            case "OK":
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Server Response: " + response, ButtonType.OK);
                alert.show();
                break;
            case "ERR":
                alert = new Alert(Alert.AlertType.ERROR, respArgs[1], ButtonType.OK);
                alert.show();

        }
        listTeamMatches.getItems().clear();
        for (String s : respArgs) {
            if (this.tabTeamMatches.isSelected())
                listTeamMatches.getItems().add(s);
        }


    }

    public void exit(ActionEvent actionEvent) {
        /*
         * "E|" -> Terminates the Client Connection
         */
        String cmd = String.format("E|");
        String response = sendCommand(cmd);
        Alert alert;
        String[] respArgs = response.split("\\|");
        switch (respArgs[0]) {
            case "OK":
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Server Response: " + response, ButtonType.OK);
                alert.show();
                break;
            case "ERR":
                alert = new Alert(Alert.AlertType.ERROR, respArgs[1], ButtonType.OK);
                alert.show();
                break;
        }
        Platform.exit();
    }
}


