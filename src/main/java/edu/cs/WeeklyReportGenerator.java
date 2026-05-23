package edu.cs;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class WeeklyReportGenerator {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    public static WeeklyReportSummary generateSummary(List<Assignment> assignments) {
        return generateSummary(assignments, LocalDate.now());
    }

    public static WeeklyReportSummary generateSummary(List<Assignment> assignments, LocalDate today) {
        LocalDate startDate = today.minusDays(6);

        int totalAssignments = 0;
        int completedAssignments = 0;
        int overdueAssignments = 0;
        int incompleteAssignments = 0;

        for (Assignment assignment : assignments) {
            if (assignment == null || assignment.getDueDate() == null || assignment.getDueDate().isEmpty()) {
                continue;
            }

            LocalDate dueDate = LocalDate.parse(assignment.getDueDate(), DATE_FORMAT);

            if (dueDate.isBefore(startDate) || dueDate.isAfter(today)) {
                continue;
            }

            totalAssignments++;

            if (assignment.isCompleted()) {
                completedAssignments++;
            } else {
                incompleteAssignments++;

                if (dueDate.isBefore(today)) {
                    overdueAssignments++;
                }
            }
        }

        int productivityPercent = totalAssignments == 0
                ? 0
                : (int) Math.round((completedAssignments * 100.0) / totalAssignments);

        return new WeeklyReportSummary(
                startDate,
                today,
                totalAssignments,
                completedAssignments,
                overdueAssignments,
                incompleteAssignments,
                productivityPercent
        );
    }

    public static String buildReportText(WeeklyReportSummary summary) {
        StringBuilder report = new StringBuilder();
        report.append("Weekly Assignment Report\n");
        report.append("Generated for: ")
                .append(summary.getStartDate())
                .append(" to ")
                .append(summary.getEndDate())
                .append("\n\n");
        report.append("Total assignments in range: ")
                .append(summary.getTotalAssignments())
                .append("\n");
        report.append("Completed assignments: ")
                .append(summary.getCompletedAssignments())
                .append("\n");
        report.append("Overdue assignments: ")
                .append(summary.getOverdueAssignments())
                .append("\n");
        report.append("Incomplete assignments: ")
                .append(summary.getIncompleteAssignments())
                .append("\n");
        report.append("Overall productivity: ")
                .append(summary.getProductivityPercent())
                .append("%\n");
        return report.toString();
    }
}
