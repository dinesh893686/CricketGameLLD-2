public class Main {
    public static void main(String[] args) {
        Team india = new Team("India");
        Team australia = new Team("Australia");

        MatchController matchController = new MatchController(india, australia);
        matchController.simulateMatch();
    }
}