// Scoreboard Class
class Scoreboard {
    int overs;
    int balls;
    Team battingTeam;
    Player batsman;
    int currentRun;
    int totalRuns;
    int wickets;

    public Scoreboard(Team battingTeam) {
        this.overs = 0;
        this.balls = 0;
        this.battingTeam = battingTeam;
        this.batsman = battingTeam.players.get(0); // Assume first player is a batsman
        this.currentRun = 0;
        this.totalRuns = 0;
        this.wickets = 0;
    }

    public void update(String outcome) {
        balls++;
        if (balls == 6) {
            overs++;
            balls = 0;
        }

        if (outcome.equals("W")) {
            wickets++;
            if (wickets == 10) {
                return; // All out
            } else {
                // Find the next batsman (assuming there are more)
                for (Player player : battingTeam.players) {
                    if (player.type == PlayerType.BATSMAN && !player.equals(batsman)) {
                        batsman = player;
                        break;
                    }
                }
            }
        } else {
            currentRun = Integer.parseInt(outcome);
            totalRuns += currentRun;
        }

        printScoreboard();
    }

    public void printScoreboard() {
        System.out.println("\nScoreboard:");
        System.out.println("Overs: " + overs + "." + balls);
        System.out.println("Batting Team: " + battingTeam.name);
        System.out.println("Batsman: " + batsman.name);
        System.out.println("Current Run: " + currentRun);
        System.out.println("Total Runs: " + totalRuns);
        System.out.println("Wickets: " + wickets);
    }
}
