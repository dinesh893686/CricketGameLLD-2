//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.Random;

class MatchController {
    Team team1;
    Team team2;
    int oversPerInnings = 5;

    public MatchController(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;
    }

    public void simulateMatch() {
        System.out.println("\nMatch: " + this.team1.name + " vs. " + this.team2.name);
        this.simulateInnings(this.team1, this.team2);
        this.simulateInnings(this.team2, this.team1);
        this.printResult();
    }

    private void simulateInnings(Team battingTeam, Team bowlingTeam) {
        System.out.println("\n--- " + battingTeam.name + " Innings ---");

        for(int over = 0; over < this.oversPerInnings; ++over) {
            System.out.print("Over " + (over + 1) + ": ");

            for(int ball = 0; ball < 6; ++ball) {
                Scoreboard scoreboard = new Scoreboard(battingTeam);
                String outcome = this.randomOutcome(scoreboard.batsman);
                scoreboard.update(outcome);
                System.out.print(outcome + " ");
                if (outcome.equals("W")) {
                    ++battingTeam.wickets;
                    if (battingTeam.wickets == 10) {
                        break;
                    }
                } else {
                    battingTeam.score += Integer.parseInt(outcome);
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
