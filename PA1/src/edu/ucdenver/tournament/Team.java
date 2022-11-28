package edu.ucdenver.tournament;
import java.util.ArrayList;

/**
 * Team has a teams name, country, list of players, as well as addPlayer to add a player to the team
 */
public class Team  {
    private String name;
    private Country country;
    public ArrayList<Player> players;
    private final int teamSize = 35;

    public Team(String name, Country country) {
        this.name = name;
        this.country = country;
        this.players = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = name;
    }
    public void setCountry(String country) {this.country = new Country(country);}
    public Country getCountry() {
        return this.country;
    }
    public ArrayList<Player> getSquad() throws NullPointerException {
       return players;
        }

    public void addPlayer(String name, int age, double height, double weight) throws IllegalArgumentException {

        if (players.size() == teamSize) {throw new IllegalArgumentException("The team is already at maximum capacity");}
        if (players.isEmpty() && age > 0 && height > 0 && weight > 0) {
            Player player = new Player(name,age,height,weight);
            players.add(player);
        }
        else if (age > 0 && height > 0 && weight > 0) {
            for (int i =0; i < players.size(); i++){
                if (players.get(i).getName().equals(name)) {
                    throw new IllegalArgumentException("The players has already been added to the list");
                }
            }
            Player player = new Player(name,age,height,weight);
            players.add(player);
        }
        if (age < 0 || height < 0 || weight < 0 ){throw new IllegalArgumentException("The age/height/weight cannot be negative");}
    }
    @Override
    public String toString() {
        return String.format("%s|%s|Players %s", getName(),getCountry(),getSquad());
    }
}
