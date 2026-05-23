package edu.cs;

import java.time.LocalDate;

public class WeeklyReportSummary {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final int totalAssignments;
    private final int completedAssignments;
    private final int overdueAssignments;
    private final int incompleteAssignments;
    private final int productivityPercent;

    public WeeklyReportSummary(LocalDate startDate, LocalDate endDate,
                               int totalAssignments, int completedAssignments,
                               int overdueAssignments, int incompleteAssignments,
                               int productivityPercent) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalAssignments = totalAssignments;
        this.completedAssignments = completedAssignments;
        this.overdueAssignments = overdueAssignments;
        this.incompleteAssignments = incompleteAssignments;
        this.productivityPercent = productivityPercent;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getTotalAssignments() {
        return totalAssignments;
    }

    public int getCompletedAssignments() {
        return completedAssignments;
    }

    public int getOverdueAssignments() {
        return overdueAssignments;
    }

    public int getIncompleteAssignments() {
        return incompleteAssignments;
    }

    public int getProductivityPercent() {
        return productivityPercent;
    }
}
