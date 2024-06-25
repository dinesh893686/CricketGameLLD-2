import java.sql.*;

class DatabaseManager {
    private final Connection connection;

    public DatabaseManager(String url, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
    public void insertTeamAndPlayer(Team team, Player player) throws SQLException {
        try {
            connection.setAutoCommit(false); // Start a transaction

            // 1. Insert Team
            insertTeam(team);

            // 2. Assign teamId to Player
            player.teamId = team.id;

            // 3. Insert Player
            insertPlayer(player);

            connection.commit(); // Commit the transaction
        } catch (SQLException e) {
            connection.rollback(); // Rollback in case of error
            throw e;
        } finally {
            connection.setAutoCommit(true); // Reset auto-commit
        }
    }

    // Insert Team
    public void insertTeam(Team team) throws SQLException {
        String sql = "INSERT INTO teams (name) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, team.name);
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    team.id = generatedKeys.getInt(1);
                }
            }
        }
    }

    // Insert Player
    public void insertPlayer(Player player) throws SQLException {
        String sql = "INSERT INTO players (name, type, team_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, player.name);
            stmt.setString(2, player.type.toString());
            stmt.setInt(3, player.teamId);
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    player.id = generatedKeys.getInt(1);
                }
            }
        }
    }


    // Insert Match
    public int insertMatch(MatchController matchController) throws SQLException {
        String sql = "INSERT INTO matches (team1_id, team2_id, winner_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, matchController.team1.id);
            stmt.setInt(2, matchController.team2.id);

            // Set winner_id to null initially (will be updated after the match)
            stmt.setNull(3, java.sql.Types.INTEGER);

            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating match failed, no ID obtained.");
                }
            }
        }
    }

    // Insert PlayerPerformance
    public void insertPlayerPerformance(int matchId, Player player) throws SQLException {
        String sql = "INSERT INTO player_performances (match_id, player_id, runs, balls_faced, wickets) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, matchId);
            stmt.setInt(2, player.id);
            stmt.setInt(3, 0); // Initial runs, balls faced, and wickets are 0
            stmt.setInt(4, 0);
            stmt.setInt(5, 0);
            stmt.executeUpdate();
        }
    }

    // Update Player Performance
    public void updatePlayerPerformance(int matchId, Player player) throws SQLException {
        // SQL query with placeholders for updated values
        String sql = "UPDATE player_performances SET runs = ?, balls_faced = ?, wickets = ? WHERE match_id = ? AND player_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Set the updated values based on player's current performance
            stmt.setInt(1, player.runs); // Set the updated number of runs
            stmt.setInt(2, player.ballsFaced); // Set the updated number of balls faced
            stmt.setInt(3, player.wicketsTaken); // Set the updated number of wickets taken (if applicable)

            // Set the WHERE clause values to match the specific player and match
            stmt.setInt(4, matchId); // Set the match ID
            stmt.setInt(5, player.id); // Set the player ID

            // Execute the update
            stmt.executeUpdate();
        }
    }

    public void insertScoreboardEntry(Scoreboard scoreboard, int inningsNumber, int matchId) throws SQLException {
        String sql = "INSERT INTO scoreboard (match_id, innings_number, overs, balls, total_runs, wickets, batsman_id, current_run) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, matchId);
            stmt.setInt(2, inningsNumber);
            stmt.setInt(3, scoreboard.overs);
            stmt.setInt(4, scoreboard.balls);
            stmt.setInt(5, scoreboard.totalRuns);
            stmt.setInt(6, scoreboard.wickets);
            stmt.setInt(7, scoreboard.batsman.id); // Assuming Player class has an 'id' field
            stmt.setInt(8, scoreboard.currentRun);
            stmt.executeUpdate();
        }
    }

    // Update winner for a match
    public void updateMatchWinner(int matchId, int winnerId) throws SQLException {
        String sql = "UPDATE matches SET winner_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, winnerId);
            stmt.setInt(2, matchId);
            stmt.executeUpdate();
        }
    }
}