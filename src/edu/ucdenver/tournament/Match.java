package edu.ucdenver.tournament;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Match contains TeamA and TeamB objects as well as their LineUps a list of referees and getters and setters for match date
 * as well as setting a teams score, and adding a referee to the match
 */
public class Match {
    private final LocalDate dateTime;
    private int scoreTeamA, scoreTeamB;
    private Team teamA, teamB;
    private LineUp teamALineUp, teamBLineUp;
    private ArrayList<Referee> referees;


    public Match(LocalDate dateTime, Team teamA, Team teamB) {
        this.dateTime = dateTime;
        this.referees = new ArrayList<>();


        this.teamA = teamA;
        this.teamB = teamB;
    }

    //Getters
    public LocalDate getDateTime() {
        return this.dateTime;
    }

    public int getScoreTeamA() {
        return this.scoreTeamA;
    }

    public int getScoreTeamB() {
        return this.scoreTeamB;
    }

    public Team getTeamA() {
        return this.teamA;
    }
    public void setTeamALineUp(LineUp lineUp) {
        this.teamALineUp = lineUp;
    }
    public void setTeamBLineUp (LineUp lineUp) {
        this.teamBLineUp = lineUp;
    }
    public void setTeamA (Team teamA) {
        this.teamA = teamA;
    }
    public void setTeamB (Team teamB) {
        this.teamB = teamB;
    }
    public LineUp getTeamALineUp() {
        return this.teamALineUp;
    }
    public LineUp getTeamBLineUp() {
        return this.teamBLineUp;
    }

    public Team getTeamB() {
        return this.teamB;
    }

    public List<Referee> getReferees() throws NullPointerException {
        return referees;

    }

    //Setters
    public void setMatchScore(int teamAScore, int teamBScore) throws IllegalArgumentException {
        if (scoreTeamA < 0 || scoreTeamB < 0) {
            throw new IllegalArgumentException("The score can not be less than 0");
        } else {
            scoreTeamA = teamAScore;
            scoreTeamB = teamBScore;
        }
    }

    public boolean isUpcoming(LocalDate date) {
        if ((date.isAfter(LocalDate.now()) )) {
            return true;
        } else return false;
    }

    public void addPlayer(Player player, Team team) throws IllegalArgumentException {
        Player player1 = null;
        Team team1 = null;

        if (team.equals(getTeamA())) {
            for (int i = 0; i < teamA.getSquad().size(); i++) {
                if (teamA.getSquad().get(i).equals(player)) {
                    teamALineUp.addPlayer(player);
                    player1 = player;
                    team1 = teamA;
                }
            }
        }
        else if (team.equals(getTeamB())) {
            for (int i = 0; i < teamB.getSquad().size(); i++) {
                if (teamB.getSquad().get(i).equals(player)) {
                    teamBLineUp.addPlayer(player);
                    player1 = player;
                    team1 = teamB;
                }
            }
        }
        if (player1 == null) {throw new IllegalArgumentException("The player was not found to be added");}
        if (team1 == null) {throw new IllegalArgumentException("The team does not exist");}
    }
    public void addReferee(Referee referee) throws IllegalArgumentException {
        Referee referee1 = null;
        if (referees.isEmpty()) {
            referees.add(referee);
            referee1 = referee;
        } else {
            for (int i = 0; i < referees.size(); i++) {
                if (!referees.get(i).equals(referee)) {
                    referees.add(referee);
                    referee1 = referee;
                }
            }
        }
        if (referees.size() != 4) {
            throw new IllegalArgumentException("There must be four referees for the match to take place");
        }
        if (referee1 == null) {
            throw new IllegalArgumentException("The referee already exists in the list");
        }
    }

    @Override
    public String toString() {
        return String.format("Match Date: %s|TeamA: %s|Lineup: %s||TeamB: %s|Lineup: %s||Referees %s", getDateTime(),getTeamA().getName(),getTeamALineUp(),getTeamB().getName(),getTeamBLineUp(),getReferees());
    }


}
