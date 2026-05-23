package edu.cs;

import java.sql.Connection;
<<<<<<< HEAD
=======
import java.sql.Date;
>>>>>>> 23118552fa6ea3b69cd5560f6eb69532c7f0c309
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
<<<<<<< HEAD
import java.time.LocalDate;
=======
>>>>>>> 23118552fa6ea3b69cd5560f6eb69532c7f0c309
import java.util.ArrayList;
import java.util.List;

public class AssignmentDAO {

<<<<<<< HEAD
    private String jdbcURL =
    "jdbc:mysql://kodama.proxy.rlwy.net:11559/railway?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";


    private String jdbcUsername = "root";
    private String jdbcPassword = "UhiiJVpLQXmqdRweZBpcdsoZlzkfSonc";

    public Connection getConnection() throws SQLException {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("MYSQL DRIVER LOADED");

        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }

        return DriverManager.getConnection(
                jdbcURL,
                jdbcUsername,
                jdbcPassword
        );
    }

    public void insertAssignment(Assignment assignment) throws SQLException {
        String sql = "INSERT INTO assignments " +
                "(course, title, due_date, estimate_hours, priority, notes, status, completed, points, reward_redeemed, penalty_applied, user_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, assignment.getCourse());
            ps.setString(2, assignment.getTitle());
            ps.setString(3, assignment.getDueDate());
            ps.setInt(4, assignment.getEstimateHours());
            ps.setString(5, assignment.getPriority());
            ps.setString(6, assignment.getNotes());
            ps.setString(7, assignment.getStatus());
            ps.setBoolean(8, assignment.isCompleted());
            ps.setInt(9, assignment.getPoints());
            ps.setBoolean(10, false);
            ps.setBoolean(11, false);
            ps.setInt(12, assignment.getUserId());

            ps.executeUpdate();
        }
    }

    public List<Assignment> selectAllAssignments(String sortBy, int userId) throws SQLException {
        List<Assignment> list = new ArrayList<>();

        String orderBy = "due_date ASC";

        if ("priority".equals(sortBy)) {
            orderBy = "CASE priority WHEN 'High' THEN 1 WHEN 'Medium' THEN 2 WHEN 'Low' THEN 3 ELSE 4 END";
        }

        String sql = "SELECT * FROM assignments WHERE user_id = ? ORDER BY " + orderBy;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapAssignment(rs));
                }
            }
        }

        Assignment.assignSchedules(list);
        return list;
    }

    public Assignment selectAssignment(int id, int userId) throws SQLException {
        for (Assignment assignment : selectAllAssignments("dueDate", userId)) {
            if (assignment.getId() == id) {
                return assignment;
            }
        }

        return null;
    }

    public void updateAssignment(Assignment assignment) throws SQLException {
        String sql = "UPDATE assignments SET course=?, title=?, due_date=?, estimate_hours=?, priority=?, notes=?, status=?, completed=?, points=? WHERE id=? AND user_id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, assignment.getCourse());
            ps.setString(2, assignment.getTitle());
            ps.setString(3, assignment.getDueDate());
            ps.setInt(4, assignment.getEstimateHours());
            ps.setString(5, assignment.getPriority());
            ps.setString(6, assignment.getNotes());
            ps.setString(7, assignment.getStatus());
            ps.setBoolean(8, assignment.isCompleted());
            ps.setInt(9, assignment.getPoints());
            ps.setInt(10, assignment.getId());
            ps.setInt(11, assignment.getUserId());

            ps.executeUpdate();
        }
    }

    public void deleteAssignment(int id, int userId) throws SQLException {
        String sql = "DELETE FROM assignments WHERE id=? AND user_id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }

    public void updateCompletion(int id, boolean completed, int userId) throws SQLException {
        String status = completed ? "Completed" : "In Progress";
        int points = completed ? 10 : 0;

        String sql = "UPDATE assignments SET completed=?, status=?, points=? WHERE id=? AND user_id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, completed);
            ps.setString(2, status);
            ps.setInt(3, points);
            ps.setInt(4, id);
            ps.setInt(5, userId);

            ps.executeUpdate();
        }
    }

    public int selectTotalPoints(int userId) throws SQLException {
        String sql = "SELECT COALESCE(SUM(points),0) AS total FROM assignments WHERE user_id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        }

        return 0;
    }

    public int selectRedeemablePoints(int userId) throws SQLException {
        String sql = "SELECT COALESCE(SUM(points),0) AS total FROM assignments WHERE reward_redeemed=false AND user_id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        }

        return 0;
    }

    public void redeemRewards(int userId) throws SQLException {
        String sql = "UPDATE assignments SET reward_redeemed=true WHERE completed=true AND user_id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }

    public void applyOverduePenalty(int userId) throws SQLException {
        String sql = "SELECT * FROM assignments WHERE completed=false AND penalty_applied=false AND user_id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String dueDate = rs.getString("due_date");

                    if (dueDate != null && !dueDate.isEmpty()) {
                        LocalDate due = LocalDate.parse(dueDate);

                        if (due.isBefore(LocalDate.now())) {
                            applyPenalty(rs.getInt("id"), userId);
                        }
                    }
                }
            }
        }
    }

    private void applyPenalty(int id, int userId) throws SQLException {
        String sql = "UPDATE assignments SET points = points - 5, penalty_applied=true WHERE id=? AND user_id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }

    private Assignment mapAssignment(ResultSet rs) throws SQLException {
        Assignment assignment = new Assignment(
                rs.getInt("id"),
                rs.getString("course"),
                rs.getString("title"),
                rs.getString("due_date"),
                rs.getInt("estimate_hours"),
                rs.getString("priority"),
                rs.getString("notes"),
                rs.getString("status"),
                rs.getBoolean("completed"),
                rs.getInt("points"),
                rs.getBoolean("reward_redeemed"),
                rs.getBoolean("penalty_applied")
        );

        assignment.setUserId(rs.getInt("user_id"));
        assignment.setSchedule("");
        assignment.setStartDate(null);

        return assignment;
    }
=======
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
>>>>>>> 23118552fa6ea3b69cd5560f6eb69532c7f0c309
}
