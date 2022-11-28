package edu.ucdenver.tournament;


/**
 * Referee contains the getters and setters for the referees name and country
 */
public class Referee {
    private String name;
    private  Country country;

    public Referee(String name, Country country) {
        this.name = name;
        this.country = country;

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry(){
        return this.country;

    }
    @Override
    public String toString() {
        return String.format("Referee Name: %s|%s", getName(),getCountry());
    }
}
