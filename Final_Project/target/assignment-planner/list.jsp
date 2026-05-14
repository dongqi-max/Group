<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.List, edu.cs.Assignment" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Assignment Checklist</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-LtrjvnR4/Jgk4h6v8IHbU5eEbFf5S6xN1vVU5VU5GId4EBXPRVx5IgaNMMf0nQ2c" crossorigin="anonymous" />
    <style>
        .due-soon { background-color: #fff3cd; }
        .overdue { background-color: #f8d7da; }
    </style>
</head>
<body>
<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h1>Assignment Checklist</h1>
            <p class="text-muted">Keep your tasks organized, see due reminders, and earn rewards.</p>
        </div>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/assignments?action=new">Add Assignment</a>
    </div>

    <div class="row mb-4">
        <div class="col-md-4">
            <div class="card shadow-sm">
                <div class="card-body">
                    <h5 class="card-title">Total Points</h5>
                    <p class="card-text display-4"><%= request.getAttribute("totalPoints") != null ? request.getAttribute("totalPoints") : 0 %></p>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card shadow-sm">
                <div class="card-body">
                    <h5 class="card-title">Redeemable Points</h5>
                    <p class="card-text display-4"><%= request.getAttribute("redeemablePoints") != null ? request.getAttribute("redeemablePoints") : 0 %></p>
                    <% int redeemablePoints = request.getAttribute("redeemablePoints") != null ? (Integer) request.getAttribute("redeemablePoints") : 0; %>
                    <% int rewardThreshold = request.getAttribute("rewardThreshold") != null ? (Integer) request.getAttribute("rewardThreshold") : 20; %>
                    <p class="mb-0"><small><%= redeemablePoints >= rewardThreshold ? "Ready to redeem!" : "Need " + (rewardThreshold - redeemablePoints) + " more points." %></small></p>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card shadow-sm">
                <div class="card-body">
                    <h5 class="card-title">Reminders</h5>
                    <p class="card-text display-4"><%= request.getAttribute("dueSoonCount") != null ? request.getAttribute("dueSoonCount") : 0 %></p>
                    <p class="mb-0"><small>Due soon</small></p>
                </div>
            </div>
        </div>
    </div>

    <div class="mb-3">
        <span class="mr-2">Sort by:</span>
        <a class="btn btn-outline-secondary btn-sm" href="<%= request.getContextPath() %>/assignments?action=list&sort=due">Due Date</a>
        <a class="btn btn-outline-secondary btn-sm" href="<%= request.getContextPath() %>/assignments?action=list&sort=priority">Priority</a>
        <% if (redeemablePoints >= rewardThreshold) { %>
            <a class="btn btn-success btn-sm ml-3" href="<%= request.getContextPath() %>/assignments?action=redeem">Redeem Rewards</a>
        <% } %>
    </div>

    <%
        List<Assignment> assignmentList = (List<Assignment>) request.getAttribute("assignmentList");
        List<Assignment> incomplete = new java.util.ArrayList<>();
        List<Assignment> complete = new java.util.ArrayList<>();
        if (assignmentList != null) {
            for (Assignment assignment : assignmentList) {
                if (assignment.isCompleted()) {
                    complete.add(assignment);
                } else {
                    incomplete.add(assignment);
                }
            }
        }
    %>

    <div class="card mb-4">
        <div class="card-header">
            <h4 class="mb-0">Incomplete Assignments</h4>
        </div>
        <div class="card-body p-0">
            <% if (incomplete.isEmpty()) { %>
                <div class="p-4 text-center text-muted">No incomplete assignments yet.</div>
            <% } else { %>
                <div class="table-responsive">
                    <table class="table mb-0">
                        <thead class="thead-light">
                        <tr>
                            <th>Course</th>
                            <th>Title</th>
                            <th>Priority</th>
                            <th>Due Date</th>
                            <th>Estimate</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <% for (Assignment assignment : incomplete) {
                            String rowClass = assignment.isOverdue() ? "overdue" : assignment.isDueSoon() ? "due-soon" : "";
                        %>
                            <tr class="<%= rowClass %>">
                                <td><%= assignment.getCourse() %></td>
                                <td><%= assignment.getTitle() %></td>
                                <td><%= assignment.getPriority() %></td>
                                <td><%= assignment.getDueDate() %></td>
                                <td><%= assignment.getEstimateHours() %> hrs</td>
                                <td><%= assignment.getDueStatus() %></td>
                                <td>
                                    <a class="btn btn-sm btn-outline-primary" href="<%= request.getContextPath() %>/assignments?action=detail&id=<%= assignment.getId() %>">Details</a>
                                    <a class="btn btn-sm btn-success" href="<%= request.getContextPath() %>/assignments?action=complete&id=<%= assignment.getId() %>">Complete</a>
                                    <a class="btn btn-sm btn-outline-secondary" href="<%= request.getContextPath() %>/assignments?action=edit&id=<%= assignment.getId() %>">Edit</a>
                                    <a class="btn btn-sm btn-outline-danger" href="<%= request.getContextPath() %>/assignments?action=delete&id=<%= assignment.getId() %>" onclick="return confirm('Delete this assignment?');">Delete</a>
                                </td>
                            </tr>
                        <% } %>
                        </tbody>
                    </table>
                </div>
            <% } %>
        </div>
    </div>

    <div class="card">
        <div class="card-header">
            <h4 class="mb-0">Completed Assignments</h4>
        </div>
        <div class="card-body p-0">
            <% if (complete.isEmpty()) { %>
                <div class="p-4 text-center text-muted">No completed assignments yet.</div>
            <% } else { %>
                <div class="table-responsive">
                    <table class="table mb-0">
                        <thead class="thead-light">
                        <tr>
                            <th>Course</th>
                            <th>Title</th>
                            <th>Priority</th>
                            <th>Due Date</th>
                            <th>Points</th>
                            <th>Notes</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <% for (Assignment assignment : complete) { %>
                            <tr>
                                <td><%= assignment.getCourse() %></td>
                                <td><%= assignment.getTitle() %></td>
                                <td><%= assignment.getPriority() %></td>
                                <td><%= assignment.getDueDate() %></td>
                                <td><%= assignment.getPoints() %></td>
                                <td><%= assignment.getNotes() != null ? assignment.getNotes() : "" %></td>
                                <td>
                                    <a class="btn btn-sm btn-outline-primary" href="<%= request.getContextPath() %>/assignments?action=detail&id=<%= assignment.getId() %>">Details</a>
                                    <a class="btn btn-sm btn-outline-secondary" href="<%= request.getContextPath() %>/assignments?action=edit&id=<%= assignment.getId() %>">Edit</a>
                                    <a class="btn btn-sm btn-outline-danger" href="<%= request.getContextPath() %>/assignments?action=delete&id=<%= assignment.getId() %>" onclick="return confirm('Delete this assignment?');">Delete</a>
                                </td>
                            </tr>
                        <% } %>
                        </tbody>
                    </table>
                </div>
            <% } %>
        </div>
    </div>
</div>
</body>
</html>
