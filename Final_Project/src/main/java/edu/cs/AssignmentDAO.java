package edu.cs;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AssignmentDAO {

    private String jdbcURL =
            "jdbc:mysql://localhost:3306/assignment_planner?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private String jdbcUsername = "root";
    private String jdbcPassword = "sheshou1217";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
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

        return list;
    }

    public Assignment selectAssignment(int id, int userId) throws SQLException {
        String sql = "SELECT * FROM assignments WHERE id = ? AND user_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setInt(2, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapAssignment(rs);
                }
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
        return assignment;
    }
}