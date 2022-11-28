package edu.ucdenver.app;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class AdminController {
    public TextField txtAddCountryName;
    public TextArea txtAddCountryOutput;
    public TextField txtAddTeamCountry;
    public TextField txtAddTeamName;
    public TextField txtAddPlayerTeam;
    public TextField txtAddPlayerPlayer;
    public TextField txtAddPlayerAge;
    public TextField txtAddPlayerWeight;
    public TextField txtAddPlayerHeight;
    public TextField txtAddMatchTeamA;
    public TextField txtAddMatchTeamB;
    public DatePicker dateAddMatchDate;
    public TextField txtAddRefereeName;
    public TextField txtAddRefereeCountry;
    public TextField txtAddRefereeMatchName;
    public DatePicker dateAddRefereeMatchDate;
    public TextField txtAddPlayerMatchName;
    public TextField txtAddPlayerMatchTeam;
    public DatePicker dateAddPlayerMatchDate;
    public Tab tabLoadFile;
    public TextField txtFileName;
    public TextField txtSaveFileName;


    Client client;

    public AdminController() {
        client = new Client();
        client.connect();
    }

    private String sendCommand(String cmd) {
        String response;
        if (client.isConnected()) {

            try {
                response = client.sendRequest(cmd);
            } catch (IOException e) {
                response = "ERR|" + e.getMessage();
            }
        } else {
            response = "ERR|Client is not connected ";

        }
        return response;
    }

    public void addCountry(ActionEvent actionEvent) {
        String countryNameText = txtAddCountryName.getText();
        Alert alert;
        /*
        "C|" -> Add Country
        "C|Country Name"
         */
        String cmd = String.format("%s|%s", "C", countryNameText);

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
                break;
        }
        txtAddCountryOutput.clear();
        for (String s : respArgs) {
            txtAddCountryOutput.appendText(s);
            txtAddCountryOutput.appendText(System.getProperty("line.separator"));
        }


    }

    public void addTeam(ActionEvent actionEvent) {
        String teamNameText = txtAddTeamName.getText();
        String teamCountryText = txtAddTeamCountry.getText();
        Alert alert;
        /*
        "T|" -> Add Team
        "T|Team Name| Country Name"
         */
        String cmd = String.format("%s|%s|%s", "T", teamNameText, teamCountryText);
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
                break;
        }
    }

    public void addPlayer(ActionEvent actionEvent) {

        String teamNameText = txtAddPlayerTeam.getText();
        String playerNameText = txtAddPlayerPlayer.getText();
        String playerAge = txtAddPlayerAge.getText();
        String playerHeight = txtAddPlayerHeight.getText();
        String playerWeight = txtAddPlayerWeight.getText();

        Alert alert;
        /*
        "P|" -> Add Player
        "P|TeamName|PlayerName|Age|Height|Weight"
        */
        String cmd = String.format("%s|%s|%s|%s|%s|%s", "P",
                teamNameText, playerNameText, playerAge, playerHeight, playerWeight);
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
                break;
        }

    }

    public void addMatch(ActionEvent actionEvent) {
        String date = dateAddMatchDate.getValue().format(DateTimeFormatter.ofPattern("yyyy,MM,dd"));
        String teamAtxt = txtAddMatchTeamA.getText();
        String teamBtxt = txtAddMatchTeamB.getText();
        Alert alert;
        /*
        "M|" -> Add Match
        "M|Date|TeamA|TeamB
         */
        String cmd = String.format("%s|%s|%s|%s", "M", date, teamAtxt, teamBtxt);
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
                break;
        }
    }
    //

    public void addReferee(ActionEvent actionEvent) {
        String refereeName = txtAddRefereeName.getText();
        String refereeCountry = txtAddRefereeCountry.getText();
        Alert alert;
        /*
        "R|" -> Add Referee
        "R|Name|CountryName"
         */
        String cmd = String.format("%s|%s|%s", "R", refereeName, refereeCountry);
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
                break;
        }
    }

    public void addRefereeToMatch(ActionEvent actionEvent) {
        String date = dateAddRefereeMatchDate.getValue().format(DateTimeFormatter.ofPattern("yyyy,MM,dd"));
        String refereeName = txtAddRefereeMatchName.getText();
        Alert alert;
        /*
        "AR|" -> Add Referee to MATCH
        "AR|Date|Name"
         */
        String cmd = String.format("%s|%s|%s", "AR", date, refereeName);
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
                break;
        }
    }

    public void addPlayerToMatch(ActionEvent actionEvent) {
        String date = dateAddPlayerMatchDate.getValue().format(DateTimeFormatter.ofPattern("yyyy,MM,dd"));
        String teamName = txtAddPlayerMatchTeam.getText();
        String playerName = txtAddPlayerMatchName.getText();
        Alert alert;
        /*
        "AP|" -> Add Player to MATCH
        "AP|Date|Team|Player
         */
        String cmd = String.format("%s|%s|%s|%s", "AP", date, teamName, playerName);
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
                break;
        }
    }

    public void loadFile(ActionEvent actionEvent) {
        String fileName = txtFileName.getText();
        Alert alert;
        String cmd = String.format("%s|%s", "L", fileName);
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
                break;
        }
    }


    public void saveFile(ActionEvent actionEvent) {
        String saveFileName = txtSaveFileName.getText();
        Alert alert;
        String cmd = String.format("%s|%s", "S", saveFileName);
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
                break;
        }
    }

    public void exit(ActionEvent actionEvent) {
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
