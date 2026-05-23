package edu.cs;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/assignments")
public class AssignmentServlet extends HttpServlet {

    private AssignmentDAO dao = new AssignmentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        String action = request.getParameter("action");

        try {

            if (action == null || action.equals("list")) {

                String sortBy = request.getParameter("sortBy");

                if (sortBy == null || sortBy.isEmpty()) {
                    sortBy = "dueDate";
                }

                dao.applyOverduePenalty(userId);

                java.util.List<Assignment> list = dao.selectAllAssignments(sortBy, userId);
                // assign schedules across the list to avoid clashes
                Assignment.assignSchedules(list);

                request.setAttribute(
                    "assignmentList",
                    list
                );

                request.setAttribute(
                        "totalPoints",
                        dao.selectTotalPoints(userId)
                );

                request.setAttribute(
                        "redeemablePoints",
                        dao.selectRedeemablePoints(userId)
                );

                request.getRequestDispatcher("/list.jsp")
                        .forward(request, response);
            }

            else if (action.equals("new")) {

                request.getRequestDispatcher("/assignment-form.jsp")
                        .forward(request, response);
            }

            else if (action.equals("detail")) {

                int id = Integer.parseInt(request.getParameter("id"));

                Assignment assignment =
                        dao.selectAssignment(id, userId);

                request.setAttribute("assignment", assignment);

                request.getRequestDispatcher("/assignment-detail.jsp")
                        .forward(request, response);
            }

            else if (action.equals("edit")) {

                int id = Integer.parseInt(request.getParameter("id"));

                Assignment assignment =
                        dao.selectAssignment(id, userId);

                request.setAttribute("assignment", assignment);

                request.getRequestDispatcher("/assignment-form.jsp")
                        .forward(request, response);
            }

            else if (action.equals("delete")) {

                int id = Integer.parseInt(request.getParameter("id"));

                dao.deleteAssignment(id, userId);

                response.sendRedirect(
                        request.getContextPath()
                                + "/assignments?action=list"
                );
            }

            else if (action.equals("complete")) {

                int id = Integer.parseInt(request.getParameter("id"));

                dao.updateCompletion(id, true, userId);

                response.sendRedirect(
                        request.getContextPath()
                                + "/assignments?action=list"
                );
            }

            else if (action.equals("undo")) {

                int id = Integer.parseInt(request.getParameter("id"));

                dao.updateCompletion(id, false, userId);

                response.sendRedirect(
                        request.getContextPath()
                                + "/assignments?action=list"
                );
            }

            else if (action.equals("redeem")) {

                dao.redeemRewards(userId);

                response.sendRedirect(
                        request.getContextPath()
                                + "/assignments?action=list"
                );
            }

            else if (action.equals("generateWeeklyReport")) {

                generateWeeklyReport(response, userId);
            }

            else {

                response.sendRedirect(
                        request.getContextPath()
                                + "/assignments?action=list"
                );
            }

        } catch (SQLException e) {

            throw new ServletException(e);
        }
    }

    private void generateWeeklyReport(HttpServletResponse response, int userId)
            throws SQLException, IOException {

        dao.applyOverduePenalty(userId);
        java.util.List<Assignment> assignments =
                dao.selectAllAssignments("dueDate", userId);

        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.minusDays(6);

        int completed = 0;
        int overdue = 0;
        int incomplete = 0;
        int total = 0;

        for (Assignment assignment : assignments) {

            if (assignment.getDueDate() == null ||
                    assignment.getDueDate().isEmpty()) {
                continue;
            }

            LocalDate dueDate;

            try {
                dueDate = LocalDate.parse(assignment.getDueDate());
            } catch (Exception e) {
                continue;
            }

            if (dueDate.isBefore(weekStart) || dueDate.isAfter(today)) {
                continue;
            }

            total++;

            if (assignment.isCompleted()) {
                completed++;
            } else {
                incomplete++;

                if (dueDate.isBefore(today)) {
                    overdue++;
                }
            }
        }

        double productivity = total == 0
                ? 0.0
                : (completed * 100.0) / total;

        String report = String.format(
                "Weekly Assignment Report%n"
                        + "Generated: %s%n%n"
                        + "Date range: %s to %s%n"
                        + "Completed assignments: %d%n"
                        + "Overdue assignments: %d%n"
                        + "Incomplete assignments: %d%n"
                        + "Overall productivity: %.2f%%%n",
                today,
                weekStart,
                today,
                completed,
                overdue,
                incomplete,
                productivity
        );

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=\"weekly-report.txt\""
        );
        response.getWriter().write(report);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {

            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String course = request.getParameter("course");
        String title = request.getParameter("title");
        String dueDate = request.getParameter("dueDate");
        String estimateHoursText =
                request.getParameter("estimateHours");
        String priority = request.getParameter("priority");
        String notes = request.getParameter("notes");
        String status = request.getParameter("status");

        if (priority == null || priority.isEmpty()) {
            priority = "Low";
        }

        if (status == null || status.isEmpty()) {
            status = "Not Started";
        }

        int estimateHours = 1;

        if (estimateHoursText != null &&
                !estimateHoursText.isEmpty()) {

            estimateHours =
                    Integer.parseInt(estimateHoursText);
        }

        boolean completed =
                status.equals("Completed");

        int points = completed ? 10 : 0;

        Assignment assignment = new Assignment(
                course,
                title,
                dueDate,
                estimateHours,
                priority,
                notes,
                status,
                completed,
                points
        );

        assignment.setUserId(userId);

        try {

            if (id == null || id.isEmpty()) {

                dao.insertAssignment(assignment);

            } else {

                assignment.setId(
                        Integer.parseInt(id)
                );

                assignment.setUserId(userId);

                dao.updateAssignment(assignment);
            }

            response.sendRedirect(
                    request.getContextPath()
                            + "/assignments?action=list"
            );

        } catch (SQLException e) {

            throw new ServletException(e);
        }
    }
}
