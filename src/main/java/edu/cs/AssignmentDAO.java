package edu.cs;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AssignmentDAO {

   private String jdbcURL =
        "jdbc:mysql://localhost:3306/assignment_planner?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

private String jdbcUsername = "root";
private String jdbcPassword = "sheshou1217";

    private static final String INSERT_ASSIGNMENT_SQL = "INSERT INTO assignments (course, title, due_date, estimate_hours, priority, notes, completed, points, reward_redeemed, penalty_applied) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SELECT_ASSIGNMENT_BY_ID = "SELECT * FROM assignments WHERE id = ?;";
    private static final String DELETE_ASSIGNMENT_SQL = "DELETE FROM assignments WHERE id = ?;";
    private static final String UPDATE_ASSIGNMENT_SQL = "UPDATE assignments SET course = ?, title = ?, due_date = ?, estimate_hours = ?, priority = ?, notes = ?, completed = ?, points = ? WHERE id = ?;";
    private static final String UPDATE_COMPLETION_SQL = "UPDATE assignments SET completed = ?, points = ? WHERE id = ?;";
    private static final String UPDATE_REWARD_REDEEMED_SQL = "UPDATE assignments SET reward_redeemed = true WHERE completed = true AND reward_redeemed = false;";
    private static final String APPLY_OVERDUE_PENALTY_SQL = "UPDATE assignments SET points = GREATEST(points - 5, 0), penalty_applied = true WHERE completed = false AND due_date < CURDATE() AND penalty_applied = false;";
    private static final String SELECT_TOTAL_POINTS = "SELECT IFNULL(SUM(points), 0) AS total FROM assignments;";
    private static final String SELECT_REDEEMABLE_POINTS = "SELECT IFNULL(SUM(points), 0) AS total FROM assignments WHERE completed = true AND reward_redeemed = false;";

    public AssignmentDAO() {
    }

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    public void insertAssignment(Assignment assignment) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ASSIGNMENT_SQL)) {
            statement.setString(1, assignment.getCourse());
            statement.setString(2, assignment.getTitle());
            statement.setDate(3, assignment.getDueDate() == null || assignment.getDueDate().isEmpty() ? null : Date.valueOf(assignment.getDueDate()));
            statement.setInt(4, assignment.getEstimateHours());
            statement.setString(5, assignment.getPriority());
            statement.setString(6, assignment.getNotes());
            statement.setBoolean(7, assignment.isCompleted());
            statement.setInt(8, assignment.getPoints());
            statement.setBoolean(9, assignment.isRewardRedeemed());
            statement.setBoolean(10, assignment.isPenaltyApplied());
            statement.executeUpdate();
        }
    }

    public Assignment selectAssignment(int id) throws SQLException {
        Assignment assignment = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ASSIGNMENT_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    assignment = mapResultSetToAssignment(resultSet);
                }
            }
        }
        return assignment;
    }

    public List<Assignment> selectAllAssignments(String sortBy) throws SQLException {
        String orderClause;
        if ("priority".equalsIgnoreCase(sortBy)) {
            orderClause = " ORDER BY CASE priority WHEN 'High' THEN 1 WHEN 'Medium' THEN 2 WHEN 'Low' THEN 3 ELSE 4 END ASC, due_date ASC";
        } else {
            orderClause = " ORDER BY due_date ASC, CASE priority WHEN 'High' THEN 1 WHEN 'Medium' THEN 2 WHEN 'Low' THEN 3 ELSE 4 END ASC";
        }

        String query = "SELECT * FROM assignments" + orderClause + ";";
        List<Assignment> assignments = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                assignments.add(mapResultSetToAssignment(resultSet));
            }
        }
        return assignments;
    }

    public boolean deleteAssignment(int id) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ASSIGNMENT_SQL)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    public boolean updateAssignment(Assignment assignment) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ASSIGNMENT_SQL)) {
            statement.setString(1, assignment.getCourse());
            statement.setString(2, assignment.getTitle());
            statement.setDate(3, assignment.getDueDate() == null || assignment.getDueDate().isEmpty() ? null : Date.valueOf(assignment.getDueDate()));
            statement.setInt(4, assignment.getEstimateHours());
            statement.setString(5, assignment.getPriority());
            statement.setString(6, assignment.getNotes());
            statement.setBoolean(7, assignment.isCompleted());
            statement.setInt(8, assignment.getPoints());
            statement.setInt(9, assignment.getId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean updateCompletion(int id, boolean completed) throws SQLException {
        int points = calculatePoints(completed);
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_COMPLETION_SQL)) {
            statement.setBoolean(1, completed);
            statement.setInt(2, points);
            statement.setInt(3, id);
            return statement.executeUpdate() > 0;
        }
    }

    public void redeemRewards() throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_REWARD_REDEEMED_SQL)) {
            statement.executeUpdate();
        }
    }

    public void applyOverduePenalty() throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(APPLY_OVERDUE_PENALTY_SQL)) {
            statement.executeUpdate();
        }
    }

    public int selectTotalPoints() throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_TOTAL_POINTS);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        }
        return 0;
    }

    public int selectRedeemablePoints() throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_REDEEMABLE_POINTS);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        }
        return 0;
    }

    private int calculatePoints(boolean completed) {
        return completed ? 10 : 0;
    }

    private Assignment mapResultSetToAssignment(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String course = resultSet.getString("course");
        String title = resultSet.getString("title");
        Date dueDate = resultSet.getDate("due_date");
        int estimateHours = resultSet.getInt("estimate_hours");
        String priority = resultSet.getString("priority");
        String notes = resultSet.getString("notes");
        boolean completed = resultSet.getBoolean("completed");
        int points = resultSet.getInt("points");
        boolean rewardRedeemed = resultSet.getBoolean("reward_redeemed");
        boolean penaltyApplied = resultSet.getBoolean("penalty_applied");

        return new Assignment(id, course, title, dueDate != null ? dueDate.toString() : "", estimateHours, priority, notes, completed, points, rewardRedeemed, penaltyApplied);
    }
}
