//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.sql.SQLException;
import java.util.Random;

class MatchController {
    Team team1;
    Team team2;
    int oversPerInnings = 1;
    private DatabaseManager dbManager;
    public MatchController(Team team1, Team team2, DatabaseManager dbManager) {
        this.team1 = team1;
        this.team2 = team2;
        this.dbManager = dbManager;
    }

    public void simulateMatch() throws SQLException {
        System.out.println("\nMatch: " + this.team1.name + " vs. " + this.team2.name);
        int matchId = this.dbManager.insertMatch(this);
        this.simulateInnings(this.team1, this.team2, 1, matchId, this.dbManager);
        this.simulateInnings(this.team2, this.team1, 2, matchId, this.dbManager);
        //update the match winner in db here
        if (this.team1.score > this.team2.score) {
            this.dbManager.updateMatchWinner(matchId, team1.id);
        } else if (this.team2.score > this.team1.score) {
            this.dbManager.updateMatchWinner(matchId, team2.id);
        }

        this.printResult();
    }

    private void simulateInnings(Team battingTeam, Team bowlingTeam, int inningsNumber, int matchId, DatabaseManager dbManager) throws SQLException {
        System.out.println("\n--- " + battingTeam.name + " Innings ---");

        for(int over = 0; over < this.oversPerInnings; ++over) {
            System.out.print("Over " + (over + 1) + ": ");
            Scoreboard scoreboard = new Scoreboard(battingTeam,bowlingTeam);
            for(int ball = 0; ball < 6; ++ball) {

                String outcome = this.randomOutcome(scoreboard.batsman);
                scoreboard.update(outcome);
                System.out.println(outcome + " ");
                if (outcome.equals("W")) {
                    ++battingTeam.wickets;
                    // Update wicketsTaken for the current bowler
                    scoreboard.bowler.addWicket();
                    if (battingTeam.wickets == 10) {
                        break;
                    }
                } else {
                    battingTeam.score += Integer.parseInt(outcome);
                    // Update runs for the current batsman
                    scoreboard.batsman.addRuns(Integer.parseInt(outcome));
                    scoreboard.batsman.incrementBallsFaced();
                }

                try {
                    dbManager.updatePlayerPerformance(matchId, scoreboard.batsman);
                    dbManager.updatePlayerPerformance(matchId, scoreboard.bowler); // Update bowler's performance as well
                    dbManager.insertScoreboardEntry(scoreboard, inningsNumber, matchId);
                } catch (SQLException e) {
                    // Handle database errors
                    e.printStackTrace();
                }
            }
            System.out.println();
        }

        System.out.println(battingTeam.name + ": " + battingTeam.score + "/" + battingTeam.wickets);
    }

    private String randomOutcome(Player batsman) {
        Random random = new Random();
        if (batsman.type == PlayerType.BATSMAN) {
            // Higher probability of runs for batsmen
            String[] outcomes = {"0", "1", "1", "2", "2", "3", "4", "6", "W"}; // Increased chance of 1s and 2s
            return outcomes[random.nextInt(outcomes.length)];
        } else {
            // Default outcome distribution for bowlers
            String[] outcomes = {"0", "1", "2", "3", "4", "5", "6", "W"};
            return outcomes[random.nextInt(outcomes.length)];
        }
    }

    private void printResult() {
        if (this.team1.score > this.team2.score) {
            System.out.println(this.team1.name + " wins by " + (this.team1.score - this.team2.score) + " runs!");
        } else if (this.team2.score > this.team1.score) {
            System.out.println(this.team2.name + " wins by " + (this.team2.score - this.team1.score) + " runs!");
        } else {
            System.out.println("It's a tie!");
        }

    }
}
