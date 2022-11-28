Project Creators:
Bailey Seyller
Noah Wood

First run ServerApplication and then Client or Admin applications.

Our project currently includes two semi functioning JavaFX applications.


Tournament works as intended and laid out in the UML
Exception handling for functions works as intended

loadFromFile: load uses a txt file and loads progress into tournament in both client and admin javafx applications
saveToFile: creates a txt file that saves all progress the user makes in the javafx admin application
tournament: adds a new tournament with a specified name, start date , and end date
addCountry: has exception handling for duplicate values
addPlayer: has exception handling for duplicate values or invalid negative numbers for age,weight,height
addReferee: works as intended, exception handling for duplicate values or countries that do not exist
addMatch: exception handling for duplicate matches or invalid teams
addPlayerToMatch: exception handling for duplicate values and for player that was not already on the team or match not found
addRefereeToMatch: works as intended throws exception if referee does not already exist or referee's country is the same as either team in the matches country
getUpcomingMatches: works as intended

Bugs: some of our exception handling is not fully working as intended in the ui for example one could enter numbers in certain parameters where not intended printing of matches could be cleaned up a bit and is not working completely as intended
server response is confirmed each time list tabs are selected
loadFile and saveFile breaks after adding things
ui could use some improvement

 
