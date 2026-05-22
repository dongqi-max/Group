<%@ page contentType="text/html;charset=UTF-8" language="java" import="edu.cs.Assignment" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Assignment Details</title>
</head>
<body>

<%
    Assignment assignment = (Assignment) request.getAttribute("assignment");
%>

<h1>Assignment Details</h1>

<% if (assignment != null) { %>

<p><strong>Course:</strong> <%= assignment.getCourse() %></p>
<p><strong>Title:</strong> <%= assignment.getTitle() %></p>
<p><strong>Start Date:</strong> <%= assignment.getStartDate() %></p><p><strong>Schedule:</strong> <%= assignment.getSchedule() %></p><p><strong>Due Date:</strong> <%= assignment.getDueDate() %></p>
<p><strong>Estimated Hours:</strong> <%= assignment.getEstimateHours() %></p>
<p><strong>Priority:</strong> <%= assignment.getPriority() %></p>
<p><strong>Status:</strong> <%= assignment.getStatus() %></p>
<p><strong>Completed:</strong> <%= assignment.isCompleted() ? "Yes" : "No" %></p>
<p><strong>Points:</strong> <%= assignment.getPoints() %></p>
<p><strong>Notes:</strong></p>
<p><%= assignment.getNotes() %></p>

<p>
    <a href="<%= request.getContextPath() %>/assignments?action=edit&id=<%= assignment.getId() %>">Edit</a> |
    <a href="<%= request.getContextPath() %>/assignments?action=list">Back</a>
</p>

<% } else { %>

<p>Assignment not found.</p>
<a href="<%= request.getContextPath() %>/assignments?action=list">Back</a>

<% } %>

</body>
</html>