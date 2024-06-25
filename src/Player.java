class Player {
    int id;
    String name;
    PlayerType type;
    int teamId;

    // New attributes
    int runs;
    int ballsFaced;
    int wicketsTaken; // Only relevant for bowlers

    public Player(String name, PlayerType type) {
        this.name = name;
        this.type = type;

        // Initialize attributes
        this.runs = 0;
        this.ballsFaced = 0;
        this.wicketsTaken = 0; // Bowlers start with 0 wickets
    }

    // Update the number of runs
    public void addRuns(int run){
        runs = runs+run;
    }

    public void incrementBallsFaced(){
        ballsFaced++;
    }

    // Update the number of wickets (for bowlers only)
    public void addWicket(){
        if(type == PlayerType.BOWLER){
            wicketsTaken++;
        }
    }
}
