package edu.cs;

<<<<<<< HEAD
=======
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

>>>>>>> 23118552fa6ea3b69cd5560f6eb69532c7f0c309
public class Assignment {
    private int id;
    private String course;
    private String title;
    private String dueDate;
<<<<<<< HEAD
    private String startDate;
    private String schedule;
    private int estimateHours;
    private String priority;
    private String notes;
    private String status;
=======
    private int estimateHours;
    private String priority;
    private String notes;
>>>>>>> 23118552fa6ea3b69cd5560f6eb69532c7f0c309
    private boolean completed;
    private int points;
    private boolean rewardRedeemed;
    private boolean penaltyApplied;
<<<<<<< HEAD
    private int userId;
=======
>>>>>>> 23118552fa6ea3b69cd5560f6eb69532c7f0c309

    public Assignment() {
        this.priority = "Low";
        this.notes = "";
<<<<<<< HEAD
        this.status = "Not Started";
    }

    public Assignment(String course, String title, String dueDate,
                      int estimateHours, String priority, String notes,
                      String status, boolean completed, int points) {
=======
    }

    public Assignment(String course, String title, String dueDate, int estimateHours, String priority, String notes, boolean completed, int points) {
>>>>>>> 23118552fa6ea3b69cd5560f6eb69532c7f0c309
        this.course = course;
        this.title = title;
        this.dueDate = dueDate;
        this.estimateHours = estimateHours;
<<<<<<< HEAD
        this.priority = priority;
        this.notes = notes;
        this.status = status;
        this.completed = completed;
        this.points = points;
    }

    public Assignment(int id, String course, String title, String dueDate,
                      int estimateHours, String priority, String notes,
                      String status, boolean completed, int points,
                      boolean rewardRedeemed, boolean penaltyApplied) {
=======
        this.priority = priority != null ? priority : "Low";
        this.notes = notes != null ? notes : "";
        this.completed = completed;
        this.points = points;
        this.rewardRedeemed = false;
        this.penaltyApplied = false;
    }

    public Assignment(int id, String course, String title, String dueDate, int estimateHours, String priority, String notes, boolean completed, int points, boolean rewardRedeemed, boolean penaltyApplied) {
>>>>>>> 23118552fa6ea3b69cd5560f6eb69532c7f0c309
        this.id = id;
        this.course = course;
        this.title = title;
        this.dueDate = dueDate;
        this.estimateHours = estimateHours;
<<<<<<< HEAD
        this.priority = priority;
        this.notes = notes;
        this.status = status;
=======
        this.priority = priority != null ? priority : "Low";
        this.notes = notes != null ? notes : "";
>>>>>>> 23118552fa6ea3b69cd5560f6eb69532c7f0c309
        this.completed = completed;
        this.points = points;
        this.rewardRedeemed = rewardRedeemed;
        this.penaltyApplied = penaltyApplied;
    }

<<<<<<< HEAD
    public int getId() { 
        return id; 
    }

    public void setId(int id) { 
        this.id = id; 
    }

    public String getCourse() { 
        return course; 
    }

    public void setCourse(String course) { 
        this.course = course; 
    }

    public String getTitle() { 
        return title; 
    }

    public void setTitle(String title) { 
        this.title = title; 
    }

    public String getDueDate() { 
        return dueDate; 
    }

    public void setDueDate(String dueDate) { 
        this.dueDate = dueDate; 
    }

    public int getEstimateHours() { 
        return estimateHours; 
    }

    public void setEstimateHours(int estimateHours) { 
        this.estimateHours = estimateHours; 
    }

    public String getPriority() { 
        return priority; 
    }

    public void setPriority(String priority) { 
        this.priority = priority; 
    }

    public String getNotes() { 
        return notes; 
    }

    public void setNotes(String notes) { 
        this.notes = notes; 
    }

    public String getStatus() { 
        return status; 
    }

    public void setStatus(String status) { 
        this.status = status; 
    }

    public boolean isCompleted() { 
        return completed; 
    }

    public void setCompleted(boolean completed) { 
        this.completed = completed; 
    }

    public int getPoints() { 
        return points; 
    }

    public void setPoints(int points) { 
        this.points = points; 
    }

    public boolean isRewardRedeemed() { 
        return rewardRedeemed; 
    }

    public void setRewardRedeemed(boolean rewardRedeemed) { 
        this.rewardRedeemed = rewardRedeemed; 
    }

    public boolean isPenaltyApplied() { 
        return penaltyApplied; 
    }

    public void setPenaltyApplied(boolean penaltyApplied) { 
        this.penaltyApplied = penaltyApplied; 
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public static void assignSchedules(java.util.List<Assignment> assignments) {
        if (assignments == null || assignments.isEmpty()) {
            return;
        }

        assignments.sort((a, b) -> {
            int compareDue = compareNullSafe(a.getDueDate(), b.getDueDate());
            if (compareDue != 0) {
                return compareDue;
            }
            int comparePriority = priorityValue(a.getPriority()) - priorityValue(b.getPriority());
            if (comparePriority != 0) {
                return comparePriority;
            }
            return Integer.compare(a.getEstimateHours(), b.getEstimateHours());
        });

        java.time.LocalDate today = java.time.LocalDate.now();
        java.util.Map<java.time.LocalDate, java.util.List<TimeRange>> booked = new java.util.HashMap<>();

        for (Assignment assignment : assignments) {
            String dueDate = assignment.getDueDate();
            int estimateHours = assignment.getEstimateHours();

            if (dueDate == null || dueDate.isEmpty() || estimateHours <= 0) {
                assignment.setSchedule("");
                assignment.setStartDate(null);
                continue;
            }

            java.time.LocalDate due = java.time.LocalDate.parse(dueDate);
            java.time.LocalDate earliest = today.isBefore(due) ? today : due;
            int remaining = Math.max(1, estimateHours);
            java.util.List<String> sessions = new java.util.ArrayList<>();

            java.time.LocalDate day = due;
            while (remaining > 0 && !day.isBefore(earliest)) {
                java.util.List<TimeRange> freeRanges = calculateFreeRanges(day, booked);
                for (int i = freeRanges.size() - 1; i >= 0 && remaining > 0; i--) {
                    TimeRange range = freeRanges.get(i);
                    int sessionHours = Math.min(remaining, Math.min(maxSessionHours(assignment.getPriority()), range.durationHours()));
                    if (sessionHours <= 0) {
                        continue;
                    }
                    java.time.LocalTime sessionEnd = range.end;
                    java.time.LocalTime sessionStart = sessionEnd.minusHours(sessionHours);
                    TimeRange scheduled = new TimeRange(sessionStart, sessionEnd);
                    sessions.add(0, formatSession(day, scheduled));
                    bookRange(day, booked, scheduled);
                    remaining -= sessionHours;
                }
                day = day.minusDays(1);
            }

            if (remaining > 0) {
                day = due.plusDays(1);
                while (remaining > 0) {
                    java.util.List<TimeRange> freeRanges = calculateFreeRanges(day, booked);
                    for (int i = freeRanges.size() - 1; i >= 0 && remaining > 0; i--) {
                        TimeRange range = freeRanges.get(i);
                        int sessionHours = Math.min(remaining, Math.min(maxSessionHours(assignment.getPriority()), range.durationHours()));
                        if (sessionHours <= 0) {
                            continue;
                        }
                        java.time.LocalTime sessionStart = range.start;
                        java.time.LocalTime sessionEnd = sessionStart.plusHours(sessionHours);
                        TimeRange scheduled = new TimeRange(sessionStart, sessionEnd);
                        sessions.add(formatSession(day, scheduled));
                        bookRange(day, booked, scheduled);
                        remaining -= sessionHours;
                    }
                    day = day.plusDays(1);
                }
            }

            String schedule = String.join(", ", sessions);
            assignment.setSchedule(schedule);
            assignment.setStartDate(extractStartDate(schedule));
        }
    }

    private static int compareNullSafe(String a, String b) {
        if (a == null && b == null) {
            return 0;
        }
        if (a == null) {
            return 1;
        }
        if (b == null) {
            return -1;
        }
        return a.compareTo(b);
    }

    private static int priorityValue(String priority) {
        if ("High".equalsIgnoreCase(priority)) {
            return 1;
        }
        if ("Medium".equalsIgnoreCase(priority)) {
            return 2;
        }
        return 3;
    }

    private static int maxSessionHours(String priority) {
        if ("High".equalsIgnoreCase(priority)) {
            return 3;
        }
        if ("Medium".equalsIgnoreCase(priority)) {
            return 2;
        }
        return 1;
    }

    private static java.util.List<TimeRange> calculateFreeRanges(java.time.LocalDate day,
                                                                 java.util.Map<java.time.LocalDate, java.util.List<TimeRange>> booked) {
        java.util.List<TimeRange> available = new java.util.ArrayList<>();
        available.add(new TimeRange(java.time.LocalTime.of(9, 0), java.time.LocalTime.of(12, 0)));
        available.add(new TimeRange(java.time.LocalTime.of(13, 0), java.time.LocalTime.of(17, 0)));

        java.util.List<TimeRange> reserved = booked.getOrDefault(day, new java.util.ArrayList<>());
        reserved.sort((a, b) -> a.start.compareTo(b.start));

        java.util.List<TimeRange> free = new java.util.ArrayList<>();
        for (TimeRange slot : available) {
            java.time.LocalTime slotStart = slot.start;
            for (TimeRange reservedRange : reserved) {
                if (!reservedRange.overlaps(slotStart, slot.end)) {
                    continue;
                }
                if (reservedRange.start.isAfter(slotStart)) {
                    free.add(new TimeRange(slotStart, reservedRange.start));
                }
                slotStart = reservedRange.end.isAfter(slotStart) ? reservedRange.end : slotStart;
                if (!slotStart.isBefore(slot.end)) {
                    break;
                }
            }
            if (slotStart.isBefore(slot.end)) {
                free.add(new TimeRange(slotStart, slot.end));
            }
        }

        java.util.List<TimeRange> result = new java.util.ArrayList<>();
        for (TimeRange range : free) {
            if (range.durationHours() > 0) {
                result.add(range);
            }
        }
        return result;
    }

    private static void bookRange(java.time.LocalDate day,
                                  java.util.Map<java.time.LocalDate, java.util.List<TimeRange>> booked,
                                  TimeRange range) {
        java.util.List<TimeRange> list = booked.computeIfAbsent(day, k -> new java.util.ArrayList<>());
        list.add(range);
        list.sort((a, b) -> a.start.compareTo(b.start));
    }

    private static String formatSession(java.time.LocalDate day, TimeRange range) {
        return String.format("%s %s-%s",
                day.toString(),
                formatTime(range.start),
                formatTime(range.end));
    }

    private static String formatTime(java.time.LocalTime time) {
        int hour = time.getHour();
        int minute = time.getMinute();
        String suffix = hour >= 12 ? "PM" : "AM";
        int displayHour = hour % 12;
        if (displayHour == 0) {
            displayHour = 12;
        }
        return String.format("%d:%02d%s", displayHour, minute, suffix);
    }

    private static String extractStartDate(String schedule) {
        if (schedule == null || schedule.isEmpty()) {
            return null;
        }
        String first = schedule.split(",")[0].trim();
        if (first.isEmpty()) {
            return null;
        }
        return first.split(" ")[0];
    }

    private static class TimeRange {
        private final java.time.LocalTime start;
        private final java.time.LocalTime end;

        private TimeRange(java.time.LocalTime start, java.time.LocalTime end) {
            this.start = start;
            this.end = end;
        }

        private int durationHours() {
            return (int) java.time.Duration.between(start, end).toHours();
        }

        private boolean overlaps(java.time.LocalTime otherStart, java.time.LocalTime otherEnd) {
            return !otherStart.isAfter(end) && !otherEnd.isBefore(start);
        }
    }

    public static String calculateStartDate(String dueDate, String priority, int estimateHours) {
        String schedule = calculateStandaloneSchedule(dueDate, priority, estimateHours);
        return extractStartDate(schedule);
    }

    public static String calculateStandaloneSchedule(String dueDate, String priority, int estimateHours) {
        if (dueDate == null || dueDate.isEmpty() || estimateHours <= 0) {
            return null;
        }

        try {
            java.time.LocalDate due = java.time.LocalDate.parse(dueDate);
            int remaining = Math.max(1, estimateHours);
            java.time.LocalDate day = due;
            java.util.List<String> sessions = new java.util.ArrayList<>();

            while (remaining > 0 && !day.isBefore(java.time.LocalDate.now())) {
                java.util.List<TimeRange> freeRanges = calculateFreeRanges(day, new java.util.HashMap<>());
                for (int i = freeRanges.size() - 1; i >= 0 && remaining > 0; i--) {
                    TimeRange range = freeRanges.get(i);
                    int sessionHours = Math.min(remaining, Math.min(maxSessionHours(priority), range.durationHours()));
                    if (sessionHours <= 0) {
                        continue;
                    }
                    java.time.LocalTime sessionEnd = range.end;
                    java.time.LocalTime sessionStart = sessionEnd.minusHours(sessionHours);
                    sessions.add(0, formatSession(day, new TimeRange(sessionStart, sessionEnd)));
                    remaining -= sessionHours;
                }
                day = day.minusDays(1);
            }

            return String.join(", ", sessions);
        } catch (Exception e) {
            return null;
        }
    }
}
=======
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public int getEstimateHours() {
        return estimateHours;
    }

    public void setEstimateHours(int estimateHours) {
        this.estimateHours = estimateHours;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isRewardRedeemed() {
        return rewardRedeemed;
    }

    public void setRewardRedeemed(boolean rewardRedeemed) {
        this.rewardRedeemed = rewardRedeemed;
    }

    public boolean isPenaltyApplied() {
        return penaltyApplied;
    }

    public void setPenaltyApplied(boolean penaltyApplied) {
        this.penaltyApplied = penaltyApplied;
    }

    public boolean isOverdue() {
        if (dueDate == null || dueDate.isEmpty()) {
            return false;
        }
        try {
            LocalDate due = LocalDate.parse(dueDate);
            return !completed && due.isBefore(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public boolean isDueSoon() {
        if (dueDate == null || dueDate.isEmpty()) {
            return false;
        }
        try {
            LocalDate due = LocalDate.parse(dueDate);
            LocalDate now = LocalDate.now();
            return !completed && !due.isBefore(now) && due.isBefore(now.plusDays(4));
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public String getDueStatus() {
        if (completed) {
            return "Completed";
        }
        if (isOverdue()) {
            return "Overdue";
        }
        if (isDueSoon()) {
            return "Due soon";
        }
        return "On time";
    }
}
>>>>>>> 23118552fa6ea3b69cd5560f6eb69532c7f0c309
