package edu.cs;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeeklyReportGeneratorTest {

    @Test
    void generatesExpectedSummaryForLastSevenDays() {
        LocalDate today = LocalDate.of(2026, 5, 22);

        Assignment completed = new Assignment();
        completed.setDueDate("2026-05-22");
        completed.setCompleted(true);

        Assignment overdueIncomplete = new Assignment();
        overdueIncomplete.setDueDate("2026-05-20");
        overdueIncomplete.setCompleted(false);

        Assignment incompleteDueWithinRange = new Assignment();
        incompleteDueWithinRange.setDueDate("2026-05-19");
        incompleteDueWithinRange.setCompleted(false);

        Assignment outsideRange = new Assignment();
        outsideRange.setDueDate("2026-05-10");
        outsideRange.setCompleted(false);

        WeeklyReportSummary summary = WeeklyReportGenerator.generateSummary(
                List.of(completed, overdueIncomplete, incompleteDueWithinRange, outsideRange),
                today
        );

        assertEquals(3, summary.getTotalAssignments());
        assertEquals(1, summary.getCompletedAssignments());
        assertEquals(2, summary.getOverdueAssignments());
        assertEquals(2, summary.getIncompleteAssignments());
        assertEquals(33, summary.getProductivityPercent());
    }
}
