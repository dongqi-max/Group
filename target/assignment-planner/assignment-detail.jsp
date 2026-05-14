<%@ page contentType="text/html;charset=UTF-8" language="java" import="edu.cs.Assignment" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Assignment Details</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-LtrjvnR4/Jgk4h6v8IHbU5eEbFf5S6xN1vVU5VU5GId4EBXPRVx5IgaNMMf0nQ2c" crossorigin="anonymous" />
</head>
<body>
<div class="container mt-5">
    <div class="card shadow-sm">
        <div class="card-header bg-primary text-white">
            <h3 class="mb-0">Assignment Details</h3>
        </div>
        <div class="card-body">
            <%
                Assignment assignment = (Assignment) request.getAttribute("assignment");
                if (assignment == null) {
            %>
                <p class="text-danger">Assignment not found.</p>
            <% } else { %>
                <dl class="row">
                    <dt class="col-sm-3">Course</dt>
                    <dd class="col-sm-9"><%= assignment.getCourse() %></dd>

                    <dt class="col-sm-3">Title</dt>
                    <dd class="col-sm-9"><%= assignment.getTitle() %></dd>

                    <dt class="col-sm-3">Due Date</dt>
                    <dd class="col-sm-9"><%= assignment.getDueDate() %></dd>

                    <dt class="col-sm-3">Priority</dt>
                    <dd class="col-sm-9"><%= assignment.getPriority() %></dd>

                    <dt class="col-sm-3">Estimated Hours</dt>
                    <dd class="col-sm-9"><%= assignment.getEstimateHours() %></dd>

                    <dt class="col-sm-3">Status</dt>
                    <dd class="col-sm-9"><%= assignment.isCompleted() ? "Completed" : assignment.getDueStatus() %></dd>

                    <dt class="col-sm-3">Points</dt>
                    <dd class="col-sm-9"><%= assignment.getPoints() %></dd>

                    <dt class="col-sm-3">Notes</dt>
                    <dd class="col-sm-9"><pre class="bg-light p-3 rounded"><%= assignment.getNotes() != null ? assignment.getNotes() : "No notes." %></pre></dd>
                </dl>
            <% } %>
            <a class="btn btn-secondary" href="<%= request.getContextPath() %>/assignments?action=list">Back to list</a>
            <% if (assignment != null) { %>
                <a class="btn btn-primary" href="<%= request.getContextPath() %>/assignments?action=edit&id=<%= assignment.getId() %>">Edit</a>
            <% } %>
        </div>
    </div>
</div>
</body>
</html>
