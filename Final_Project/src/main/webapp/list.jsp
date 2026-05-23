<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*, edu.cs.Assignment, java.time.LocalDate" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Assignment Checklist</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 30px;
            background-color: #f4f6f8;
        }

        h1, h2 { color: #2c3e50; }

        .card {
            background: white;
            padding: 20px;
            margin-bottom: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        .btn {
            padding: 8px 12px;
            text-decoration: none;
            border-radius: 5px;
            color: white;
            background-color: #3498db;
            margin-right: 5px;
        }

        .btn-small {
            padding: 5px 8px;
            font-size: 13px;
        }

        .btn-red { background-color: #e74c3c; }
        .btn-green { background-color: #27ae60; }
        .btn-gray { background-color: #7f8c8d; }

        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
        }

        th {
            background-color: #2c3e50;
            color: white;
        }

        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }

        .high {
            color: red;
            font-weight: bold;
        }

        .medium {
            color: orange;
            font-weight: bold;
        }

        .low {
            color: green;
            font-weight: bold;
        }

        .overdue {
            color: red;
            font-weight: bold;
        }

        .summary-box {
            display: inline-block;
            width: 22%;
            margin-right: 1%;
            background-color: white;
            padding: 15px;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        .summary-number {
            font-size: 26px;
            font-weight: bold;
            color: #3498db;
        }
    </style>
</head>

<body>

<%
    List<Assignment> assignmentList =
            (List<Assignment>) request.getAttribute("assignmentList");

    if (assignmentList == null) {
        assignmentList = new ArrayList<Assignment>();
    }

    int totalAssignments = assignmentList.size();
    int completedCount = 0;
    int incompleteCount = 0;
    int dueSoonCount = 0;

    LocalDate today = LocalDate.now();

    for (Assignment a : assignmentList) {
        if (a.isCompleted()) {
            completedCount++;
        } else {
            incompleteCount++;
        }

        if (!a.isCompleted()
                && a.getDueDate() != null
                && !a.getDueDate().isEmpty()) {

            LocalDate due = LocalDate.parse(a.getDueDate());

            if (!due.isBefore(today)
                    && !due.isAfter(today.plusDays(2))) {
                dueSoonCount++;
            }
        }
    }
%>

<h1>Assignment Checklist</h1>

<p>
    Welcome,
    <strong><%= session.getAttribute("username") %></strong>
    |
    <a class="btn btn-red btn-small"
       href="<%= request.getContextPath() %>/logout">
        Logout
    </a>
</p>

<div class="card">
    <p>
        Keep your tasks organized,
        see due reminders,
        and track your productivity.
    </p>

    <a class="btn"
       href="<%= request.getContextPath() %>/assignments?action=new">
        Add Assignment
    </a>
</div>

<div class="summary-box">
    <p>Total Assignments</p>
    <div class="summary-number"><%= totalAssignments %></div>
</div>

<div class="summary-box">
    <p>Completed</p>
    <div class="summary-number"><%= completedCount %></div>
</div>

<div class="summary-box">
    <p>Incomplete</p>
    <div class="summary-number"><%= incompleteCount %></div>
</div>

<div class="summary-box">
    <p>Due Soon</p>
    <div class="summary-number"><%= dueSoonCount %></div>
</div>

<div class="card">
    <p>
        <strong>Productivity Score:</strong>
        <%= request.getAttribute("totalPoints") %>
    </p>

    <p>
        Sort by:
        <a href="<%= request.getContextPath() %>/assignments?action=list&sortBy=dueDate">Due Date</a>
        |
        <a href="<%= request.getContextPath() %>/assignments?action=list&sortBy=priority">Priority</a>
    </p>
</div>

<div class="card">
    <h2>Reminder / Due Soon</h2>

    <ul>
    <%
        boolean hasReminder = false;

        for (Assignment a : assignmentList) {
            if (!a.isCompleted()
                    && a.getDueDate() != null
                    && !a.getDueDate().isEmpty()) {

                LocalDate due = LocalDate.parse(a.getDueDate());

                if (!due.isBefore(today)
                        && !due.isAfter(today.plusDays(2))) {

                    hasReminder = true;
    %>
        <li>
            <strong><%= a.getTitle() %></strong>
            is due on
            <%= a.getDueDate() %>
        </li>
    <%
                }
            }
        }

        if (!hasReminder) {
    %>
        <li>No assignments due soon.</li>
    <%
        }
    %>
    </ul>
</div>

<div class="card">
    <h2>Incomplete Assignments</h2>

    <table>
        <tr>
            <th>Course</th>
            <th>Title</th>
            <th>Due Date</th>
            <th>Priority</th>
            <th>Status</th>
            <th>Hours</th>
            <th>Points</th>
            <th>Actions</th>
        </tr>

    <%
        boolean hasIncomplete = false;

        for (Assignment a : assignmentList) {
            if (!a.isCompleted()) {
                hasIncomplete = true;

                String priorityClass = "low";

                if ("High".equals(a.getPriority())) {
                    priorityClass = "high";
                } else if ("Medium".equals(a.getPriority())) {
                    priorityClass = "medium";
                }

                boolean overdue = false;

                if (a.getDueDate() != null
                        && !a.getDueDate().isEmpty()) {
                    LocalDate due = LocalDate.parse(a.getDueDate());
                    overdue = due.isBefore(today);
                }
    %>

        <tr>
            <td><%= a.getCourse() %></td>
            <td><%= a.getTitle() %></td>
            <td>
                <%= a.getDueDate() %>
                <% if (overdue) { %>
                    <span class="overdue">OVERDUE</span>
                <% } %>
            </td>
            <td class="<%= priorityClass %>"><%= a.getPriority() %></td>
            <td><%= a.getStatus() %></td>
            <td><%= a.getEstimateHours() %></td>
            <td><%= a.getPoints() %></td>
            <td>
                <a class="btn btn-small"
                   href="<%= request.getContextPath() %>/assignments?action=detail&id=<%= a.getId() %>">Details</a>

                <a class="btn btn-small"
                   href="<%= request.getContextPath() %>/assignments?action=edit&id=<%= a.getId() %>">Edit</a>

                <a class="btn btn-green btn-small"
                   href="<%= request.getContextPath() %>/assignments?action=complete&id=<%= a.getId() %>">Complete</a>

                <a class="btn btn-red btn-small"
                   href="<%= request.getContextPath() %>/assignments?action=delete&id=<%= a.getId() %>">Delete</a>
            </td>
        </tr>

    <%
            }
        }

        if (!hasIncomplete) {
    %>
        <tr>
            <td colspan="8">No incomplete assignments yet.</td>
        </tr>
    <%
        }
    %>
    </table>
</div>

<div class="card">
    <h2>Completed Assignments</h2>

    <table>
        <tr>
            <th>Course</th>
            <th>Title</th>
            <th>Due Date</th>
            <th>Priority</th>
            <th>Status</th>
            <th>Hours</th>
            <th>Points</th>
            <th>Actions</th>
        </tr>

    <%
        boolean hasCompleted = false;

        for (Assignment a : assignmentList) {
            if (a.isCompleted()) {
                hasCompleted = true;

                String priorityClass = "low";

                if ("High".equals(a.getPriority())) {
                    priorityClass = "high";
                } else if ("Medium".equals(a.getPriority())) {
                    priorityClass = "medium";
                }
    %>

        <tr>
            <td><%= a.getCourse() %></td>
            <td><%= a.getTitle() %></td>
            <td><%= a.getDueDate() %></td>
            <td class="<%= priorityClass %>"><%= a.getPriority() %></td>
            <td><%= a.getStatus() %></td>
            <td><%= a.getEstimateHours() %></td>
            <td><%= a.getPoints() %></td>
            <td>
                <a class="btn btn-small"
                   href="<%= request.getContextPath() %>/assignments?action=detail&id=<%= a.getId() %>">Details</a>

                <a class="btn btn-small"
                   href="<%= request.getContextPath() %>/assignments?action=edit&id=<%= a.getId() %>">Edit</a>

                <a class="btn btn-gray btn-small"
                   href="<%= request.getContextPath() %>/assignments?action=undo&id=<%= a.getId() %>">Undo</a>

                <a class="btn btn-red btn-small"
                   href="<%= request.getContextPath() %>/assignments?action=delete&id=<%= a.getId() %>">Delete</a>
            </td>
        </tr>

    <%
            }
        }

        if (!hasCompleted) {
    %>
        <tr>
            <td colspan="8">No completed assignments yet.</td>
        </tr>
    <%
        }
    %>
    </table>
</div>

</body>
</html>