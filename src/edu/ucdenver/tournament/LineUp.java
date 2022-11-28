package edu.ucdenver.tournament;
import java.util.ArrayList;
import java.util.List;

/**
 * LineUp contains a list of players that are on a teams line up for a specific match, as well as getters and setters for team and adding a player to a lineup
 */
public class LineUp {
    private final Team team;
    private ArrayList<Player> players;
    private final int lineUpSize = 11;

    public LineUp(Team team) {
        this.team = team;
        this.players = new ArrayList<>();
    }

    public Team getTeam(){
        return this.team;
    }
    public void addPlayer(Player player) throws IllegalArgumentException {
        Player player1 = null;
        Team team1 = null;
        if (players.size() == lineUpSize) {
            throw new IllegalArgumentException("The line up is currently at maximum capacity, therefore the player could not be added");
        }
        else if (players.isEmpty()) {
            for (int  i =0; i < team.getSquad().size(); i++) {
                if (team.getSquad().get(i).equals(player)) {
                    players.add(player);
                    player1 = player;
                }
            }
        }
        else {
            for (int j = 0; j < players.size(); j++) {
                if (players.get(j).equals(player)) {
                    throw new IllegalArgumentException("The player has already been added to the line up");
                }
            }
                for (int i = 0; i <team.getSquad().size(); i++) {
                    if (team.getSquad().get(i).equals(player)) {
                        players.add(player);
                        player1 = player;
                    }
                }
            }
        if (player1 == null) {throw new IllegalArgumentException("The player was not found listed in the team squad");}
    }

    public List<Player> getPlayers() {
        return this.players;
    }
    @Override
    public String toString() {
        String s = null;
        s = String.format("%s",getPlayers());
        return s;
    }

}
