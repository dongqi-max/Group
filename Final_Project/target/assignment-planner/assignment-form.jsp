<%@ page contentType="text/html;charset=UTF-8" language="java" import="edu.cs.Assignment" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Assignment Form</title>
</head>
<body>

<%
    Assignment assignment = (Assignment) request.getAttribute("assignment");

    String id = assignment != null ? String.valueOf(assignment.getId()) : "";
    String course = assignment != null ? assignment.getCourse() : "";
    String title = assignment != null ? assignment.getTitle() : "";
    String dueDate = assignment != null ? assignment.getDueDate() : "";
    String priority = assignment != null ? assignment.getPriority() : "Low";
    String notes = assignment != null ? assignment.getNotes() : "";
    String status = assignment != null ? assignment.getStatus() : "Not Started";
    int estimateHours = assignment != null ? assignment.getEstimateHours() : 1;
%>

<h1><%= assignment == null ? "Add Assignment" : "Edit Assignment" %></h1>

<form method="post" action="<%= request.getContextPath() %>/assignments">
    <input type="hidden" name="id" value="<%= id %>">

    <p>
        Course:
        <input type="text" name="course" value="<%= course %>" required>
    </p>

    <p>
        Title:
        <input type="text" name="title" value="<%= title %>" required>
    </p>

    <p>
        Due Date:
        <input type="date" name="dueDate" value="<%= dueDate %>" required>
    </p>

    <p>
        Estimated Hours:
        <input type="number" name="estimateHours" min="1" value="<%= estimateHours %>" required>
    </p>

    <p>
        Priority:
        <select name="priority">
            <option value="High" <%= "High".equals(priority) ? "selected" : "" %>>High</option>
            <option value="Medium" <%= "Medium".equals(priority) ? "selected" : "" %>>Medium</option>
            <option value="Low" <%= "Low".equals(priority) ? "selected" : "" %>>Low</option>
        </select>
    </p>

    <p>
        Status:
        <select name="status">
            <option value="Not Started" <%= "Not Started".equals(status) ? "selected" : "" %>>Not Started</option>
            <option value="In Progress" <%= "In Progress".equals(status) ? "selected" : "" %>>In Progress</option>
            <option value="Completed" <%= "Completed".equals(status) ? "selected" : "" %>>Completed</option>
        </select>
    </p>

    <p>
        Notes:<br>
        <textarea name="notes" rows="5" cols="50"><%= notes %></textarea>
    </p>

    <button type="submit">Save Assignment</button>
    <a href="<%= request.getContextPath() %>/assignments?action=list">Cancel</a>
</form>

</body>
</html>