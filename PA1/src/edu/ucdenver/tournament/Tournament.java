package edu.ucdenver.tournament;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Class Tournament consists of lists that keep track of countries,players,teams,matches, and referees
 * this includes methods for adding to each of these lists and acts as the main functionality of the program
 * contains functionality for loading and saving the program to a file as well as retrieving lists to find matches with parameters
 *
 */
public class Tournament {
    private String name;

    private final ArrayList<Country> countryList;
    private final ArrayList<Player> playerList;
    private final ArrayList<Team> teamList;
    private final ArrayList<Match> matchList;
    private final ArrayList<Referee> refereeList;

    private LocalDate startDate;
    private LocalDate endDate;


    public Tournament(String name, LocalDate startDate, LocalDate endDate)  {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;

        this.countryList = new ArrayList<>();
        this.playerList = new ArrayList<>();
        this.teamList = new ArrayList<>();
        this.matchList = new ArrayList<>();
        this.refereeList = new ArrayList<>();
    }

    /**
     * Used to retrieve the country list from tournament
     * @return country list
     */
    public List<Country> getCountryList() {
       return countryList;

    }

    /**
     * Getter for name of tournament
     * @return name of tournament
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the tournament
     * @param name String object name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the start date of the tournament
     * @return
     */
    public LocalDate getStartDate() {
        return this.startDate;
    }

    /**
     * Sets the start date of the tournament
     * @param startDate
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date of the tournament
     * @return
     */
    public LocalDate getEndDate() {
        return this.endDate;
    }

    /**
     * Sets the end date of the tournament
     * @param endDate
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Loads from a specified file and calls on all the methods in tournament that are necessary to load the players, teams, matches, referees
     * @param fileName The file name to load from
     * @throws IllegalArgumentException if the file is not found throws an exception
     * @throws IOException if the file is corrupted or the system could not create an IO stream throws an exception
     */
 //TODO: implement loadFromFile
    public void loadFromFile(String fileName) throws IllegalArgumentException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String tournamentNameString = br.readLine();
        String[] t = null; // array that is used to set the name, startDate, endDate for the tournament
        String startDateString = null;
        String endDateString = null;
        LocalDate startDate = null;
        LocalDate endDate = null;
        t = tournamentNameString.split("    ");
        startDateString = t[1];
        endDateString = t[2];
        startDate = LocalDate.parse(startDateString);
        endDate = LocalDate.parse(endDateString);
        setName(t[0]);
        setStartDate(startDate);
        setEndDate(endDate);

        // string used to count how many countries there are
        String numOfCountries = br.readLine();
        //System.out.println(br.readLine()); // debugging
        // loop that gets all the country names and adds them back to tournament
        for (int i = 0; i < Integer.parseInt(numOfCountries); i++) {
            String country = br.readLine();
            country = country.replace("Country: ", "");
            country = country.replace("|", "");
            addCountry(country); //TODO: uncomment this
        }
        String numberOfTeamsString = br.readLine();
        ArrayList<String> teams = new ArrayList<>();
        String[] players = null;
        ArrayList<String> checkForPlayers = new ArrayList<>();
        int numberOfTeams = Integer.parseInt(numberOfTeamsString);
        for (int i = 0; i < numberOfTeams; i++) {
            String line = br.readLine();
            String name = null, age = null, height = null, weight = null, teamName = null, countryName = null;
            line = line.replace("Country: ", "");


            teamName = line.substring(0, line.indexOf("|"));
            teamName = teamName.replace("|", "");
            countryName = line.substring(line.indexOf("|"), line.indexOf("||"));
            countryName = countryName.replace("|", "");
            addTeam(teamName, countryName); //TODO:uncomment this line

            players = (line.split("Player Name: "));
            int numOfPlayers = players.length;
            if (numOfPlayers > 0) {
                for (int j = 1; j < numOfPlayers; j++) {
                    String player = players[j];
                    name = player.substring(0, player.indexOf("|"));
                    age = player.substring(player.indexOf("|") + 1, player.indexOf("H"));
                    age = age.replace(" Age: ", "");
                    age = age.replace("|", "");
                    age = age.replace(" ", "");
                    height = player.substring(player.indexOf("Height: "), player.indexOf("W"));
                    height = height.replace("Height: ", "");
                    height = height.replace("|", "");
                    height = height.replace(" ", "");
                    if (player.endsWith(" ")) {
                        weight = player.substring(player.indexOf("Weight: "));
                        weight = weight.replace("Weight: ", "");
                        weight = weight.replace(", ", "");
                    }
                    if (player.endsWith("]")) {
                        weight = player.substring(player.indexOf("Weight: "), player.indexOf("]"));
                        weight = weight.replace("Weight: ", "");
                    }
                    int ageInt = Integer.parseInt(age);
                    double heightDouble = Double.parseDouble(height);
                    double weightDouble = Double.parseDouble(weight);
                    addPlayer(teamName, name, ageInt, heightDouble, weightDouble); // TODO: uncomment this line
                    // System.out.println(age);
                }
                //System.out.println(players[1]);
                //  System.out.println(players[2]);
            }


            //System.out.println(countryName);


        }
        String numberOfMatchesString = br.readLine();


        int numberOfMatches = Integer.parseInt(numberOfMatchesString);
        if (numberOfMatches > 0) {
            for (int i = 0; i < numberOfMatches; i++) {
                String firstLine = br.readLine();
                String secondLine = br.readLine();
                String thirdLine = br.readLine();
                String fourthLine = br.readLine();
                String fifthLine = br.readLine();
                String sixthLine = br.readLine();


                String matchStartDate = null, teamA = null, teamB = null, refereeName = null, refereeCountry = null;


//                String[] referees = null;
                firstLine = firstLine.replace("Match Date: ", "");
                //System.out.println(firstLine);
                matchStartDate = firstLine;
                LocalDate matchDate = LocalDate.parse(matchStartDate);
                // create the match using the matchDate, teamA, TeamB
                String teamAMatch = null, teamBMatch = null;
                teamAMatch = secondLine.replace("TeamA: ", "");
                teamBMatch = fourthLine.replace("TeamB: ", "");
                addMatch(matchDate, teamAMatch, teamBMatch); //TODO: uncomment this


                teamA = secondLine.replace("TeamA: ", "");

                if (!thirdLine.equals("Lineup:[]|")) {
                    String[] lineUpTeamA = thirdLine.split("Player Name: ");
                    int lineUpTeamASize = lineUpTeamA.length;
                    lineUpTeamASize--;
                    if (lineUpTeamASize > 0) {
                        if (lineUpTeamASize == 1) {
                            String aPlayer = lineUpTeamA[1];
                            String playerName = aPlayer.substring(0, aPlayer.indexOf("|"));
                            addPlayerToMatch(matchDate, teamA, playerName); // TODO: uncomment this line
                        } else {
                            for (int j = 1; j < lineUpTeamASize + 1; j++) {
                                String aPlayer = lineUpTeamA[j];
                                String playerName = aPlayer.substring(0, aPlayer.indexOf("|"));
                                addPlayerToMatch(matchDate, teamA, playerName);// TODO: uncomment this line

                            }
                        }
                    }
                }
                teamB = fourthLine.replace("TeamB: ", "");

                if (!fifthLine.equals("Lineup:[]|")) {
                    String[] lineUpTeamB = fifthLine.split("Player Name: ");
                    int lineUpTeamBSize = lineUpTeamB.length;
                    lineUpTeamBSize--;
                    if (lineUpTeamBSize > 0) {
                        if (lineUpTeamBSize == 1) {
                            String aPlayer = lineUpTeamB[1];
                            String playerName = aPlayer.substring(0, aPlayer.indexOf("|"));
                            addPlayerToMatch(matchDate, teamB, playerName); //TODO: uncomment this line
                        } else {
                            for (int j = 1; j < lineUpTeamBSize + 1; j++) {
                                String aPlayer = lineUpTeamB[j];
                                String playerName = aPlayer.substring(0, aPlayer.indexOf("|"));
                                addPlayerToMatch(matchDate, teamB, playerName);// TODO: uncomment this line
                            }
                        }
                    }
                }
                String[] referees = sixthLine.split("Referee Name: ");
                if (!sixthLine.equals("Referees []")) {
                    for (int k = 1; k < referees.length; k++) {
                        String referee = referees[k];
                       // System.out.println(referee);
                        if (referee.endsWith(" ")) {
                            refereeName = referee.substring(0, referee.indexOf("|"));
                            refereeCountry = referee;
                            refereeCountry = refereeCountry.replace(refereeName,"");
                            refereeCountry = refereeCountry.replace("Country: ","");
                            refereeCountry = refereeCountry.replace("|","");
                            refereeCountry = refereeCountry.replace(", ","");
                            addReferee(refereeName,refereeCountry);
                            try {
                                addRefereeToMatch(matchDate,refereeName);
                            } catch (Exception e) {System.err.println(e);}
                        }
                        if (referee.endsWith("]")) {
                            refereeName = referee.substring(0, referee.indexOf("|"));
                            refereeCountry = referee;
                            refereeCountry = refereeCountry.replace(refereeName,"");
                            refereeCountry = refereeCountry.replace("Country: ","");
                            refereeCountry = refereeCountry.replace("|","");
                            refereeCountry = refereeCountry.replace("]","");
                            addReferee(refereeName,refereeCountry);
                            try {
                                addRefereeToMatch(matchDate,refereeName);
                            } catch (Exception e) {System.err.println(e);}
                        }

                        }
                    }
                }
            }
        }

    /**
     * Saves the current state of the program and instance variables of tournament to a file in a format needed for loadFromFile
     * @param fileName The file name to saved too
     * @throws IOException If the system is unable to create a IO stream throws an exception
     */
    public void saveToFile(String fileName) throws IOException  {
        PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
        String tournament = getName() +"    "+getStartDate() +"    "+getEndDate()+"    ";
        pw.println(tournament);

        String numOfCountries = Integer.toString(countryList.size());
        pw.println(numOfCountries);
        if (countryList.size() > 0) {
            for (Country country: countryList) {
                pw.println(country.toString());
            }
        }
        pw.println(teamList.size());
        if (teamList.size() > 0) {
            for (Team team: teamList) {
                pw.println(team.toString());
            }
        }
        pw.println(matchList.size());
        if (matchList.size() > 0) {
            for (Match match: matchList) {
                pw.println(match.toString());
            }
        }
        pw.close();

    }

    /**
     * Adds a country if parameters are valid to the tournaments countryList
     * @param countryName The country name
     * @throws IllegalArgumentException If the country is already added to the list throws an exception
     */
    public void addCountry(String countryName) throws IllegalArgumentException {
        Country country = null;
        if (countryList.isEmpty()) {
            country = new Country(countryName);
            countryList.add(country);
        }
        else {
            for (Country value : countryList) {
                if (value.getCountryName().equals(countryName)) {
                    throw new IllegalArgumentException("The country already exists");
                }
            }
            country = new Country(countryName);
            countryList.add(country);
        }
    }

    /**
     * Adds a team to the tournament list of teams
     * @param teamName The team name
     * @param countryName The country name
     * @throws IllegalArgumentException Throws an exception if the CountryName is not found on the country list, or the TeamName already exists
     */
    public void addTeam (String teamName,String countryName) throws IllegalArgumentException {
        Country country = null;
        Team team = null;
        if (teamList.isEmpty()) {
            for (Country value : countryList) {
                if (value.getCountryName().equals(countryName)) {
                    country = value;
                    Team team1 = new Team(teamName, value);
                    team1.setName(teamName);
                    team1.setCountry(countryName);
                    team = team1;
                    teamList.add(team1);
                    break;
                }
            }
        }
        else {
            for (Team item : teamList) {
                if (item.getName().equals(teamName)) {
                    throw new IllegalArgumentException("A team with that name already exists");
                }
            }
            for (Country value : countryList) {
                if (value.getCountryName().equals(countryName)) {
                    country = value;
                    Team team1 = new Team(teamName, country);
                    team1.setName(teamName);
                    team1.setCountry(countryName);
                    team = team1;
                    teamList.add(team1);
                    break;
                }
            }
            }
        if (country == null) {throw new IllegalArgumentException("A country with that name was not found");}
        }

    /**
     * Adds a player to the list of players in tournament and to the Teams list of players
     * @param teamName The TeamName to add the player to
     * @param playerName The players name
     * @param age The players age
     * @param height The players height
     * @param weight The players weight
     * @throws IllegalArgumentException If the teamName does not exist, player does not exist, or any of the players values are negative throws an IllegalArgumentException
     */
    public void addPlayer(String teamName, String playerName, int age, double height, double weight) throws IllegalArgumentException {
        Team team = null;
        Player player = null;
        for (Team value : teamList) {
            if (value.getName().equals(teamName)) {
                team = value;
            }
        }
        if (team == null) {throw new IllegalArgumentException("A team was not found with that name");}
            if (team.getSquad().isEmpty()) {
                team.addPlayer(playerName,age,height,weight);
                Player player1 = new Player(playerName,age,height,weight);
                playerList.add(player1);
                player = player1;
            }
            else {
                for (int j = 0; j < team.getSquad().size(); j++) {
                    if (team.getSquad().get(j).getName().equals(playerName)) {
                        throw new IllegalArgumentException("A player with that name already exists on the team");
                    }
                }
                team.addPlayer(playerName,age,height,weight);
                Player pLayer1 = new Player(playerName,age,height,weight);
                playerList.add(pLayer1);
                player = pLayer1;
            }


    }

    /**
     * Adds a match to the match list in tournament
     * @param date The date of the match
     * @param teamAName TeamA's name
     * @param teamBName TeamB's name
     * @throws IllegalArgumentException If TeamA, TeamB does not exist or the match date already exists throw an exception
     */
    public void addMatch(LocalDate date, String teamAName, String teamBName) throws IllegalArgumentException {
        //TODO: maybe give the option to select the correct team if it isn't listed or add a new one
        Team teamA = null;
        Team teamB = null;
        // loop to make sure the match does not already exist
        for (Match item : matchList) {
            if (item.getDateTime().equals(date)) {
                throw new IllegalArgumentException("A match with that date was already added");
            }
        }
        // loop to find the country from the teamName for teamA
        for (Team value : teamList) {
            if (value.getName().equals(teamAName)) {
                teamA = value;
            }
        }
        // loop to find the country from the teamName for teamB
        for (Team value : teamList) {
            if (value.getName().equals(teamBName)) {
                teamB = value;
            }
        }
        if (teamA == null) {
            throw new IllegalArgumentException("Team A was not found");
        }
        if (teamB == null) {
            throw new IllegalArgumentException("Team B was not found");
        }
        Match match = new Match(date,teamA,teamB);
        match.setTeamA(teamA);
        match.setTeamB(teamB);
        LineUp teamALineUp = new LineUp(teamA);
        LineUp teamBLineUp = new LineUp(teamB);
        match.setTeamALineUp(teamALineUp);
        match.setTeamBLineUp(teamBLineUp);
        matchList.add(match);
    }

    /**
     * Adds a referee to the referee list in tournament
     * @param name Referees name
     * @param countryName Referees Country of representation name
     * @throws IllegalArgumentException Throws an exception if the referee already exists in the referee list
     */
    public void addReferee (String name, String countryName) throws IllegalArgumentException {
        //TODO: this method
        for (Country country : countryList) {
            if (country.getCountryName().equals(countryName)) {
                if (refereeList.isEmpty()) {
                    Referee referee1 = new Referee(name, country);
                    refereeList.add(referee1);
                } else {
                    Referee referee1 = new Referee(name, country);
                    if (!refereeList.contains(referee1)) {
                        refereeList.add(referee1);
                    } else {
                        throw new IllegalArgumentException("A referee with that name already exists");
                    }
                }
            }
        }
    }

    /**
     * Adds a referee to the specified match
     * @param date Date of the match
     * @param refereeName Referees name
     * @throws IllegalArgumentException Throws an exception if the referee has already been added to the match, if a match with date is not found and if the total referees listed on the match is not equal to four
     */
    public void addRefereeToMatch(LocalDate date, String refereeName) throws IllegalArgumentException {
        // TODO: this method
        Referee referee = null;
        Match match = null;
        // loop to find the match object if not found throw exception
        for (Match value : matchList) {
            if (value.getDateTime().equals(date)) {
                match = value;
            }
        }
        if (match == null) {throw new IllegalArgumentException("A match was not found with that date");}
        // loop to find the referee object and throw exception if they are not on the list or the country is the same as either team
        for (Referee value : refereeList) {
            if (value.getName().equals(refereeName)) {
                referee = value;
                for (int j = 0; j < matchList.size(); j++) {
                    if (match.getTeamA().getCountry().getCountryName().equals(referee.getCountry().getCountryName()) ||
                            match.getTeamB().getCountry().getCountryName().equals(referee.getCountry().getCountryName())) {
                        throw new IllegalArgumentException("The referees country cannot be the same as either teams country");
                    }
                }
            }
        }
        if (referee == null) {throw new IllegalArgumentException("There was no referee found with that name");}
        match.addReferee(referee);




        //if (match.getReferees().size() != 4) {throw new IllegalArgumentException("There must be four referees added to the match");}


    }

    /**
     * Adds a player to the match with the specified date and team
     * @param date Match date
     * @param teamName Team name in match
     * @param playerName Players name
     * @throws IllegalArgumentException Throws exception if the match with specified date is not found, if a player with specified name is not found, or if the team with that name is not found
     */
    public void addPlayerToMatch(LocalDate date, String teamName, String playerName) throws IllegalArgumentException {
        Player player = null;
        Team team = null;
        Match match = null;
        LineUp lineUp = null;
        //get the match object that matches the specified time
        for (Match value : matchList) {
            if (value.getDateTime().equals(date)) {
                match = value;
            }
        }
        // if the match is not found throw an exception
        if (match == null) {throw new IllegalArgumentException("A match with that date was not found");}
        //if the teamName matches teamA of the specified match date get the lineup object for that team
        if (match.getTeamA().getName().equals(teamName)) {
            lineUp = match.getTeamALineUp();

            // search teamA to make sure the player object in that team exists
            for (int i = 0; i < match.getTeamA().getSquad().size(); i++) {
                //add the player to the lineup if they exist on the team
                if (match.getTeamA().getSquad().get(i).getName().equals(playerName)) {
                    player = match.getTeamA().getSquad().get(i);
                    // if the list on lineup is empty then add them to lineup
                    if (match.getTeamALineUp().getPlayers().isEmpty()) {
                        match.getTeamALineUp().addPlayer(player);
                    } else {
                        for (int j = 0; j < match.getTeamALineUp().getPlayers().size(); j++) {
                            // if the player already exists on the lineup throw exception
                            if (match.getTeamALineUp().getPlayers().get(j).getName().equals(playerName)) {
                                throw new IllegalArgumentException("The player already exists on the lineup");
                            }
                        }
                        match.getTeamALineUp().addPlayer(player);
                    }

                }
            }
        }
        if (match.getTeamB().getName().equals(teamName)) {

            //do the same for teamB
            for (int i = 0; i < match.getTeamB().getSquad().size(); i++) {
                if (match.getTeamB().getSquad().get(i).getName().equals(playerName)) {
                    player = match.getTeamB().getSquad().get(i);
                    if (match.getTeamBLineUp().getPlayers().isEmpty()) {
                        match.getTeamBLineUp().addPlayer(player);
                    }

                } else {
                    for (int j = 0; j < match.getTeamBLineUp().getPlayers().size(); j++) {
                        // if the player already exists on the lineup throw exception
                        if (match.getTeamBLineUp().getPlayers().get(j).getName().equals(playerName)) {
                            throw new IllegalArgumentException("The player already exists on the lineup");
                        }
                    }
                    match.getTeamBLineUp().addPlayer(player);
                }
            }
        }

        if (player == null) {throw new IllegalArgumentException("A player with that name was not found on either team");}

    }

    /**
     * Sets the score both teamA and teamB in a specified match
     * @param date Match date
     * @param teamAScore TeamA score in match
     * @param teamBScore TeamB score in match
     * @throws IllegalArgumentException If either of the teams scores is less than zero or the match is not found with the specified date throw an exception
     */
    public void setMatchScore(LocalDate date, int teamAScore, int teamBScore) throws IllegalArgumentException {
        for (Match match : matchList) {
            if (match.getDateTime().equals(date)) {
                if (teamAScore <= 0 || teamBScore <= 0) {
                    throw new IllegalArgumentException("The team score can not be less than 0");
                } else match.setMatchScore(teamAScore, teamBScore);
            }
        }
    }

    /**
     * \ Gets the matches which dates are in the future
     * @return A list of matches that are in the future
     */
    public List<Match> getUpcomingMatches()  {
        ArrayList<Match> matches = new ArrayList<>();
        for (Match match: matchList) {
            if (match.isUpcoming(match.getDateTime())) {
                matches.add(match);
            }
        }
        return matches;
    }

    /**
     * Gets the list of referees in the tournament
     * @return gets the list of referees in the tournament
     */
    public List<Referee> getRefereeList() {
        return refereeList;
    }

    /**
     * Gets the matches on a specified date
     * @param dateTime Date of the match
     * @return List of the matches that occur on the specified date
     */
    public List<Match> getMatchesOn(LocalDate dateTime)  {
        ArrayList<Match> arrayList = new ArrayList<>();
        for (Match match : matchList) {
            if (match.getDateTime().equals(dateTime)) {
                arrayList.add(match);
            }
        }

        return arrayList;
    }

    /**
     * Searches for matches that have a specified team name
     * @param teamName Teams name
     * @return List of matches that have the specified team name
     * @throws IllegalArgumentException if the team name is not found in the tournaments team list throw an exception
     */
    public ArrayList<Match> getMatchesFor(String teamName) throws IllegalArgumentException {
       ArrayList<Match> arrayList = new ArrayList<>();
       Team aTeam = null;
       for (Team team: teamList) {
           if (team.getName().equals(teamName)) {
               aTeam = team;
           }
       }
        for (Match match : matchList) {

            if (match.getTeamA().getName().equals(teamName)) {
                arrayList.add(match);

            }
            if (match.getTeamB().getName().equals(teamName)) {
                arrayList.add(match);

            }
        }
        if (aTeam == null) {throw new IllegalArgumentException("There are no teams with that name");}
        return arrayList;

    }

    /**
     * Retrieve the team list from the tournament
     * @return List of teams in the tournament
     */
    public ArrayList<Team> getTeamList() {
        return teamList;
    }

    /**
     * Searches and creates a list of matches that match the specified date
     * @param dateTime Date of the match
     * @return List of matches that are on the specified date
     */
    public List<LineUp> getMatchLineUps(LocalDate dateTime) {
        ArrayList<LineUp> arrayList = new ArrayList<>();
        for (Match match : matchList) {
            if (match.getDateTime().equals(dateTime)) {
                arrayList.add(match.getTeamALineUp());
                arrayList.add(match.getTeamBLineUp());
            }
        }
        return arrayList;
    }

    /**
     * Retrieve the list of players in the tournament
     * @return List of players in the tournament
     */
    public ArrayList<Player> getPlayersInTournament() {
        return playerList;
    }

    /**
     * String method to return the lists in tournament including the countries, teams, matches, players, referees
     * @return String that includes the tournament name and lists for countries, teams, matches, players, and referees
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(String.format("Tournament: %s | Start: %s | End: %s|", getName(), getStartDate(), getEndDate()));
        output.append("====Country List====|");
        for(Country a : countryList) {output.append(a).append("|");}
        output.append("====Team List====|");
        for(Team b : teamList) {output.append(b).append("|");}
        output.append("====Player List====|");
        for(Player c : playerList) {output.append(c).append("|");}
        output.append("====Referee List====|");
        for(Referee d : refereeList) {output.append(d).append("|");}
        output.append("====Match List====|");
        for(Match e : matchList) {output.append(e).append("|");}
        return output.toString();
    }


}
