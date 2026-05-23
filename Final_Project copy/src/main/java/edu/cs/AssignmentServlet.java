package edu.cs;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/assignments")
public class AssignmentServlet extends HttpServlet {

    private AssignmentDAO dao = new AssignmentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if (action == null || action.equals("list")) {
                String sortBy = request.getParameter("sortBy");

                if (sortBy == null || sortBy.isEmpty()) {
                    sortBy = "dueDate";
                }

                dao.applyOverduePenalty();

                request.setAttribute("assignmentList", dao.selectAllAssignments(sortBy));
                request.setAttribute("totalPoints", dao.selectTotalPoints());
                request.setAttribute("redeemablePoints", dao.selectRedeemablePoints());

                request.getRequestDispatcher("/list.jsp").forward(request, response);
            }

            else if (action.equals("new")) {
                request.getRequestDispatcher("/assignment-form.jsp").forward(request, response);
            }

            else if (action.equals("detail")) {
                int id = Integer.parseInt(request.getParameter("id"));
                Assignment assignment = dao.selectAssignment(id);
                request.setAttribute("assignment", assignment);
                request.getRequestDispatcher("/assignment-detail.jsp").forward(request, response);
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
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String course = request.getParameter("course");
        String title = request.getParameter("title");
        String dueDate = request.getParameter("dueDate");
        String estimateHoursText = request.getParameter("estimateHours");
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

        if (estimateHoursText != null && !estimateHoursText.isEmpty()) {
            estimateHours = Integer.parseInt(estimateHoursText);
        }

        boolean completed = status.equals("Completed");
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

        try {
            if (id == null || id.isEmpty()) {
                dao.insertAssignment(assignment);
            } else {
                assignment.setId(Integer.parseInt(id));
                dao.updateAssignment(assignment);
            }

            response.sendRedirect(request.getContextPath() + "/assignments?action=list");

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}