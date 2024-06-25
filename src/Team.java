import java.util.ArrayList;
import java.util.List;


class Team {
    String name;
    List<Player> players = new ArrayList<>();
    int id;
    int score = 0;
    int wickets = 0;

    public Team(String name) {
        this.name = name;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }
}