package edu.cs;

<<<<<<< HEAD
import java.io.IOException;
import java.sql.SQLException;

=======
>>>>>>> 23118552fa6ea3b69cd5560f6eb69532c7f0c309
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
<<<<<<< HEAD
import javax.servlet.http.HttpSession;
=======

import java.io.IOException;
import java.sql.SQLException;
>>>>>>> 23118552fa6ea3b69cd5560f6eb69532c7f0c309

@WebServlet("/assignments")
public class AssignmentServlet extends HttpServlet {

    private AssignmentDAO dao = new AssignmentDAO();

    @Override
<<<<<<< HEAD
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

=======
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if (action == null || action.equals("list")) {

                String sortBy = request.getParameter("sortBy");
>>>>>>> 23118552fa6ea3b69cd5560f6eb69532c7f0c309
                if (sortBy == null || sortBy.isEmpty()) {
                    sortBy = "dueDate";
                }

<<<<<<< HEAD
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

            else if (action.equals("weeklyReport")) {

                writeWeeklyReport(response, userId);
            }

            else {

                response.sendRedirect(
                        request.getContextPath()
                                + "/assignments?action=list"
                );
            }

        } catch (SQLException e) {

=======
                dao.applyOverduePenalty();

                request.setAttribute("assignments", dao.selectAllAssignments(sortBy));
                request.setAttribute("totalPoints", dao.selectTotalPoints());
                request.setAttribute("redeemablePoints", dao.selectRedeemablePoints());

                request.getRequestDispatcher("/list.jsp").forward(request, response);
            }

            else if (action.equals("new")) {
                request.getRequestDispatcher("/assignment-form.jsp").forward(request, response);
            }

            else if (action.equals("edit")) {
                int id = Integer.parseInt(request.getParameter("id"));
                Assignment assignment = dao.selectAssignment(id);
                request.setAttribute("assignment", assignment);
                request.getRequestDispatcher("/assignment-form.jsp").forward(request, response);
            }

            else if (action.equals("delete")) {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.deleteAssignment(id);
                response.sendRedirect(request.getContextPath() + "/assignments?action=list");
            }

            else if (action.equals("complete")) {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.updateCompletion(id, true);
                response.sendRedirect(request.getContextPath() + "/assignments?action=list");
            }

            else if (action.equals("undo")) {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.updateCompletion(id, false);
                response.sendRedirect(request.getContextPath() + "/assignments?action=list");
            }

            else if (action.equals("redeem")) {
                dao.redeemRewards();
                response.sendRedirect(request.getContextPath() + "/assignments?action=list");
            }

            else {
                response.sendRedirect(request.getContextPath() + "/assignments?action=list");
            }

        } catch (SQLException e) {
>>>>>>> 23118552fa6ea3b69cd5560f6eb69532c7f0c309
            throw new ServletException(e);
        }
    }

<<<<<<< HEAD
    private void writeWeeklyReport(HttpServletResponse response, int userId)
            throws IOException, SQLException {

        java.util.List<Assignment> assignments = dao.selectAllAssignments("dueDate", userId);
        WeeklyReportSummary summary = WeeklyReportGenerator.generateSummary(assignments);
        String reportContent = WeeklyReportGenerator.buildReportText(summary);

        response.setContentType("text/plain;charset=UTF-8");
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=\"weekly-report.txt\""
        );

        response.getWriter().write(reportContent);
    }

=======
>>>>>>> 23118552fa6ea3b69cd5560f6eb69532c7f0c309
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

<<<<<<< HEAD
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {

            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");

=======
>>>>>>> 23118552fa6ea3b69cd5560f6eb69532c7f0c309
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String course = request.getParameter("course");
        String title = request.getParameter("title");
        String dueDate = request.getParameter("dueDate");
<<<<<<< HEAD
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

=======
        String estimateHoursText = request.getParameter("estimateHours");
        String priority = request.getParameter("priority");
        String notes = request.getParameter("notes");

        if (course == null) course = "";
        if (title == null) title = "";
        if (dueDate == null) dueDate = "";
        if (priority == null) priority = "Low";
        if (notes == null) notes = "";

        int estimateHours = 1;
        if (estimateHoursText != null && !estimateHoursText.isEmpty()) {
            estimateHours = Integer.parseInt(estimateHoursText);
        }

        boolean completed = request.getParameter("completed") != null;
>>>>>>> 23118552fa6ea3b69cd5560f6eb69532c7f0c309
        int points = completed ? 10 : 0;

        Assignment assignment = new Assignment(
                course,
                title,
                dueDate,
                estimateHours,
                priority,
                notes,
<<<<<<< HEAD
                status,
=======
>>>>>>> 23118552fa6ea3b69cd5560f6eb69532c7f0c309
                completed,
                points
        );

<<<<<<< HEAD
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

=======
        try {
            if (id == null || id.isEmpty()) {
                dao.insertAssignment(assignment);
            } else {
                assignment.setId(Integer.parseInt(id));
                dao.updateAssignment(assignment);
            }

            response.sendRedirect(request.getContextPath() + "/assignments?action=list");

        } catch (SQLException e) {
>>>>>>> 23118552fa6ea3b69cd5560f6eb69532c7f0c309
            throw new ServletException(e);
        }
    }
}