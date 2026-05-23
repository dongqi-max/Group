package edu.cs;

public class Assignment {
    private int id;
    private String course;
    private String title;
    private String dueDate;
    private int estimateHours;
    private String priority;
    private String notes;
    private String status;
    private boolean completed;
    private int points;
    private boolean rewardRedeemed;
    private boolean penaltyApplied;

    public Assignment() {
        this.priority = "Low";
        this.notes = "";
        this.status = "Not Started";
    }

    public Assignment(String course, String title, String dueDate,
                      int estimateHours, String priority, String notes,
                      String status, boolean completed, int points) {
        this.course = course;
        this.title = title;
        this.dueDate = dueDate;
        this.estimateHours = estimateHours;
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
        this.id = id;
        this.course = course;
        this.title = title;
        this.dueDate = dueDate;
        this.estimateHours = estimateHours;
        this.priority = priority;
        this.notes = notes;
        this.status = status;
        this.completed = completed;
        this.points = points;
        this.rewardRedeemed = rewardRedeemed;
        this.penaltyApplied = penaltyApplied;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public int getEstimateHours() { return estimateHours; }
    public void setEstimateHours(int estimateHours) { this.estimateHours = estimateHours; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public boolean isRewardRedeemed() { return rewardRedeemed; }
    public void setRewardRedeemed(boolean rewardRedeemed) { this.rewardRedeemed = rewardRedeemed; }

    public boolean isPenaltyApplied() { return penaltyApplied; }
    public void setPenaltyApplied(boolean penaltyApplied) { this.penaltyApplied = penaltyApplied; }
}