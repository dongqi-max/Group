<<<<<<< HEAD
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

        h1, h2 {
            color: #2c3e50;
        }

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

        .btn-red {
            background-color: #e74c3c;
        }

        .btn-green {
            background-color: #27ae60;
        }

        .btn-gray {
            background-color: #7f8c8d;
        }

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

<div class="card">
    <p>
        Keep your tasks organized,
        see due reminders,
        and earn rewards.
    </p>

    <a class="btn"
       href="<%= request.getContextPath() %>/assignments?action=new">
        Add Assignment
    </a>
</div>

<div class="card">
    <p>
        Download a weekly report for the past seven days,
        including completed, overdue, incomplete, and productivity totals.
    </p>

    <a class="btn btn-green"
       href="<%= request.getContextPath() %>/assignments?action=weeklyReport">
        Download Weekly Report
    </a>
</div>

<div class="summary-box">
    <p>Total Assignments</p>
    <div class="summary-number">
        <%= totalAssignments %>
    </div>
</div>

<div class="summary-box">
    <p>Completed</p>
    <div class="summary-number">
        <%= completedCount %>
    </div>
</div>

<div class="summary-box">
    <p>Incomplete</p>
    <div class="summary-number">
        <%= incompleteCount %>
    </div>
</div>

<div class="summary-box">
    <p>Due Soon</p>
    <div class="summary-number">
        <%= dueSoonCount %>
    </div>
</div>

<div class="card">

    <p>
        <strong>Total Points:</strong>
        <%= request.getAttribute("totalPoints") %>
    </p>

    <p>
        <strong>Redeemable Points:</strong>
        <%= request.getAttribute("redeemablePoints") %>

        <a class="btn btn-green btn-small"
           href="<%= request.getContextPath() %>/assignments?action=redeem">
            Redeem Rewards
        </a>
    </p>

    <p>
        Sort by:

        <a href="<%= request.getContextPath() %>/assignments?action=list&sortBy=dueDate">
            Due Date
        </a>

        |

        <a href="<%= request.getContextPath() %>/assignments?action=list&sortBy=priority">
            Priority
        </a>
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

                LocalDate due =
                        LocalDate.parse(a.getDueDate());

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
            <th>Task</th>
            <th>Schedule</th>
            <th>Due</th>
            <th>Status</th>
            <th>Hours</th>
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
                }
                else if ("Medium".equals(a.getPriority())) {
                    priorityClass = "medium";
                }

                boolean overdue = false;

                if (a.getDueDate() != null
                        && !a.getDueDate().isEmpty()) {

                    LocalDate due =
                            LocalDate.parse(a.getDueDate());

                    overdue = due.isBefore(today);
                }
    %>

        <tr>
            <td><%= a.getCourse() %></td>
            <td><%= a.getTitle() %></td>
            <td>
                <%
                    String scheduleHtml = "";
                    String sched = a.getSchedule();
                    if (sched != null && !sched.isEmpty()) {
                        String[] parts = sched.split(",\\s*");
                        for (int i = 0; i < parts.length; i++) {
                            String s = parts[i];
                            String[] sp = s.split(" ");
                            String dateStr = sp.length > 0 ? sp[0] : "";
                            String timeRange = sp.length > 1 ? sp[1] : "";
                            String displayDate = dateStr;
                            try {
                                displayDate = java.time.LocalDate.parse(dateStr)
                                        .format(java.time.format.DateTimeFormatter.ofPattern("MMM d"));
                            } catch (Exception ignored) {
                            }
                            String displayTime = "";
                            if (!timeRange.isEmpty()) {
                                String[] times = timeRange.split("-");
                                String start = times[0];
                                String end = times.length > 1 ? times[1] : "";
                                String startHour = start.split(":")[0].replaceAll("^0", "");
                                String endHour = end.split(":")[0].replaceAll("^0", "");
                                String endSuffix = end.replaceAll(".*?(AM|PM)$", "$1");
                                displayTime = startHour + "–" + endHour + " " + endSuffix;
                            }
                            scheduleHtml += displayDate;
                            if (!displayTime.isEmpty()) scheduleHtml += " • " + displayTime;
                            if (i < parts.length - 1) scheduleHtml += "<br/>";
                        }
                    } else if (a.getStartDate() != null && !a.getStartDate().isEmpty()) {
                        try {
                            scheduleHtml = java.time.LocalDate.parse(a.getStartDate())
                                    .format(java.time.format.DateTimeFormatter.ofPattern("MMM d"));
                        } catch (Exception ignored) {
                            scheduleHtml = a.getStartDate();
                        }
                    }
                %>
                <%= scheduleHtml %>
            </td>
            <td>
                <%= a.getDueDate() %>
                <% if (overdue) { %>
                    <span class="overdue"> OVERDUE </span>
                <% } %>
            </td>
            <td>
                <% String priorityIcon = "🟢"; if ("High".equals(a.getPriority())) { priorityIcon = "🔴"; } else if ("Medium".equals(a.getPriority())) { priorityIcon = "🟠"; } %>
                <%= priorityIcon %> <%= a.getPriority() %>
            </td>
            <td><%= a.getEstimateHours() %>h</td>
            <td>
                <a class="btn btn-small"
                   href="<%= request.getContextPath() %>/assignments?action=detail&id=<%= a.getId() %>">
                    Details
                </a>

                <a class="btn btn-small"
                   href="<%= request.getContextPath() %>/assignments?action=edit&id=<%= a.getId() %>">
                    Edit
                </a>

                <a class="btn btn-green btn-small"
                   href="<%= request.getContextPath() %>/assignments?action=complete&id=<%= a.getId() %>">
                    Complete
                </a>

                <a class="btn btn-red btn-small"
                   href="<%= request.getContextPath() %>/assignments?action=delete&id=<%= a.getId() %>">
                    Delete
                </a>
            </td>
        </tr>

    <%
            }
        }

        if (!hasIncomplete) {
    %>

        <tr>
            <td colspan="7">
                No incomplete assignments yet.
            </td>
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
            <th>Task</th>
            <th>Schedule</th>
            <th>Due</th>
            <th>Status</th>
            <th>Hours</th>
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
                }
                else if ("Medium".equals(a.getPriority())) {
                    priorityClass = "medium";
                }
    %>

        <tr>
            <td><%= a.getCourse() %></td>
            <td><%= a.getTitle() %></td>
            <td>
                <%
                    String scheduleHtml = "";
                    String sched = a.getSchedule();
                    if (sched != null && !sched.isEmpty()) {
                        String[] parts = sched.split(",\\s*");
                        for (int i = 0; i < parts.length; i++) {
                            String s = parts[i];
                            String[] sp = s.split(" ");
                            String dateStr = sp.length > 0 ? sp[0] : "";
                            String timeRange = sp.length > 1 ? sp[1] : "";
                            String displayDate = dateStr;
                            try {
                                displayDate = java.time.LocalDate.parse(dateStr)
                                        .format(java.time.format.DateTimeFormatter.ofPattern("MMM d"));
                            } catch (Exception ignored) {
                            }
                            String displayTime = "";
                            if (!timeRange.isEmpty()) {
                                String[] times = timeRange.split("-");
                                String start = times[0];
                                String end = times.length > 1 ? times[1] : "";
                                String startHour = start.split(":")[0].replaceAll("^0", "");
                                String endHour = end.split(":")[0].replaceAll("^0", "");
                                String endSuffix = end.replaceAll(".*?(AM|PM)$", "$1");
                                displayTime = startHour + "–" + endHour + " " + endSuffix;
                            }
                            scheduleHtml += displayDate;
                            if (!displayTime.isEmpty()) scheduleHtml += " • " + displayTime;
                            if (i < parts.length - 1) scheduleHtml += "<br/>";
                        }
                    } else if (a.getStartDate() != null && !a.getStartDate().isEmpty()) {
                        try {
                            scheduleHtml = java.time.LocalDate.parse(a.getStartDate())
                                    .format(java.time.format.DateTimeFormatter.ofPattern("MMM d"));
                        } catch (Exception ignored) {
                            scheduleHtml = a.getStartDate();
                        }
                    }
                %>
                <%= scheduleHtml %>
            </td>
            <td><%= a.getDueDate() %></td>
            <td>
                <% String priorityIcon = "🟢"; if ("High".equals(a.getPriority())) { priorityIcon = "🔴"; } else if ("Medium".equals(a.getPriority())) { priorityIcon = "🟠"; } %>
                <%= priorityIcon %> <%= a.getPriority() %>
            </td>
            <td><%= a.getEstimateHours() %>h</td>
            <td>
                <a class="btn btn-small"
                   href="<%= request.getContextPath() %>/assignments?action=detail&id=<%= a.getId() %>">
                    Details
                </a>

                <a class="btn btn-small"
                   href="<%= request.getContextPath() %>/assignments?action=edit&id=<%= a.getId() %>">
                    Edit
                </a>

                <a class="btn btn-gray btn-small"
                   href="<%= request.getContextPath() %>/assignments?action=undo&id=<%= a.getId() %>">
                    Undo
                </a>

                <a class="btn btn-red btn-small"
                   href="<%= request.getContextPath() %>/assignments?action=delete&id=<%= a.getId() %>">
                    Delete
                </a>
            </td>
        </tr>

    <%
            }
        }

        if (!hasCompleted) {
    %>

        <tr>
            <td colspan="7">
                No completed assignments yet.
            </td>
        </tr>

    <%
        }
    %>

    </table>
</div>

</body>
</html>
=======
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
>>>>>>> 23118552fa6ea3b69cd5560f6eb69532c7f0c309
