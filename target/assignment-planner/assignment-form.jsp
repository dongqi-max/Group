<%@ page contentType="text/html;charset=UTF-8" language="java" import="edu.cs.Assignment" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title><%= request.getAttribute("assignment") != null ? "Edit Assignment" : "Add Assignment" %></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-LtrjvnR4/Jgk4h6v8IHbU5eEbFf5S6xN1vVU5VU5GId4EBXPRVx5IgaNMMf0nQ2c" crossorigin="anonymous" />
</head>
<body>
<div class="container mt-5">
    <div class="card shadow-sm">
        <div class="card-header bg-primary text-white">
            <h3 class="mb-0"><%= request.getAttribute("assignment") != null ? "Edit Assignment" : "Add Assignment" %></h3>
        </div>
        <div class="card-body">
            <%
                Assignment assignment = (Assignment) request.getAttribute("assignment");
                String formAction = request.getContextPath() + "/assignments";
                String course = assignment != null ? assignment.getCourse() : "";
                String title = assignment != null ? assignment.getTitle() : "";
                String dueDate = assignment != null ? assignment.getDueDate() : "";
                String priority = assignment != null ? assignment.getPriority() : "Low";
                String notes = assignment != null ? assignment.getNotes() : "";
                int estimateHours = assignment != null ? assignment.getEstimateHours() : 1;
                boolean completed = assignment != null && assignment.isCompleted();
                String idValue = assignment != null ? String.valueOf(assignment.getId()) : "";
            %>
            <form method="post" action="<%= formAction %>">
                <input type="hidden" name="id" value="<%= idValue %>" />

                <div class="form-group">
                    <label for="course">Course</label>
                    <input type="text" class="form-control" id="course" name="course" value="<%= course %>" required />
                </div>

                <div class="form-group">
                    <label for="title">Assignment Title</label>
                    <input type="text" class="form-control" id="title" name="title" value="<%= title %>" required />
                </div>

                <div class="form-row">
                    <div class="form-group col-md-4">
                        <label for="dueDate">Due Date</label>
                        <input type="date" class="form-control" id="dueDate" name="dueDate" value="<%= dueDate %>" />
                    </div>
                    <div class="form-group col-md-4">
                        <label for="priority">Priority</label>
                        <select class="form-control" id="priority" name="priority">
                            <option value="High" <%= "High".equals(priority) ? "selected" : "" %>>High</option>
                            <option value="Medium" <%= "Medium".equals(priority) ? "selected" : "" %>>Medium</option>
                            <option value="Low" <%= "Low".equals(priority) ? "selected" : "" %>>Low</option>
                        </select>
                    </div>
                    <div class="form-group col-md-4">
                        <label for="estimateHours">Estimated Hours</label>
                        <input type="number" class="form-control" id="estimateHours" name="estimateHours" min="1" value="<%= estimateHours %>" required />
                    </div>
                </div>

                <div class="form-group">
                    <label for="notes">Notes / Description</label>
                    <textarea class="form-control" id="notes" name="notes" rows="5"><%= notes %></textarea>
                </div>

                <div class="form-group form-check">
                    <input type="checkbox" class="form-check-input" id="completed" name="completed" <%= completed ? "checked" : "" %> />
                    <label class="form-check-label" for="completed">Mark as completed</label>
                </div>

                <button type="submit" class="btn btn-primary"><%= request.getAttribute("assignment") != null ? "Update Assignment" : "Save Assignment" %></button>
                <a class="btn btn-secondary" href="<%= request.getContextPath() %>/assignments?action=list">Cancel</a>
            </form>
        </div>
    </div>
</div>
</body>
</html>
