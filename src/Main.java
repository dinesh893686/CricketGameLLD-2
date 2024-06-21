public class Main {
    public static void main(String[] args) {
        // Create Teams
        Team india = new Team("India");
        Team australia = new Team("Australia");

        // Add Indian Players
        // Batsmen
        india.addPlayer(new Player("Rohit Sharma", PlayerType.BATSMAN));
        india.addPlayer(new Player("Shubman Gill", PlayerType.BATSMAN));
//        india.addPlayer(new Player("Virat Kohli", PlayerType.BATSMAN));
//        india.addPlayer(new Player("KL Rahul", PlayerType.BATSMAN));
//        india.addPlayer(new Player("Shreyas Iyer", PlayerType.BATSMAN));
//        india.addPlayer(new Player("Suryakumar Yadav", PlayerType.BATSMAN));
//        india.addPlayer(new Player("Hardik Pandya", PlayerType.BATSMAN)); // All-rounder
//        india.addPlayer(new Player("Ravindra Jadeja", PlayerType.BATSMAN)); // All-rounder

        // Bowlers
        india.addPlayer(new Player("Jasprit Bumrah", PlayerType.BOWLER));
        india.addPlayer(new Player("Mohammed Shami", PlayerType.BOWLER));
//        india.addPlayer(new Player("Mohammed Siraj", PlayerType.BOWLER));
//        india.addPlayer(new Player("Kuldeep Yadav", PlayerType.BOWLER));
//        india.addPlayer(new Player("Yuzvendra Chahal", PlayerType.BOWLER));
//        india.addPlayer(new Player("Ravichandran Ashwin", PlayerType.BOWLER)); // All-rounder
//        india.addPlayer(new Player("Axar Patel", PlayerType.BOWLER)); // All-rounder

        // Add Australian Players
        // Batsmen
        australia.addPlayer(new Player("David Warner", PlayerType.BATSMAN));
        australia.addPlayer(new Player("Usman Khawaja", PlayerType.BATSMAN));
//        australia.addPlayer(new Player("Steve Smith", PlayerType.BATSMAN));
//        australia.addPlayer(new Player("Marnus Labuschagne", PlayerType.BATSMAN));
//        australia.addPlayer(new Player("Travis Head", PlayerType.BATSMAN));
//        australia.addPlayer(new Player("Cameron Green", PlayerType.BATSMAN)); // All-rounder
//        australia.addPlayer(new Player("Alex Carey", PlayerType.BATSMAN)); // Wicketkeeper-Batsman

        // Bowlers
        australia.addPlayer(new Player("Pat Cummins", PlayerType.BOWLER));
//        australia.addPlayer(new Player("Mitchell Starc", PlayerType.BOWLER));
//        australia.addPlayer(new Player("Josh Hazlewood", PlayerType.BOWLER));
//        australia.addPlayer(new Player("Nathan Lyon", PlayerType.BOWLER));
//        australia.addPlayer(new Player("Adam Zampa", PlayerType.BOWLER));
//        australia.addPlayer(new Player("Mitchell Marsh", PlayerType.BOWLER)); // All-rounder

        // Create Match and Controller
        MatchController matchController = new MatchController(india, australia);

        // Simulate the Match
        matchController.simulateMatch();
    }
}