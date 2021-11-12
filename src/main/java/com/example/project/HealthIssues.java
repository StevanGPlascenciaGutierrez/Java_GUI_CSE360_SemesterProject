package com.example.project;

public class HealthIssues {
    private String issue;
    private String description;

    public HealthIssues() {
    }

    public HealthIssues(String issue, String description){
        this.issue = issue;
        this.description = description;
    }

    public String getIssue() {
        return issue;
    }

    public String getDescription() {
        return description;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public void setDescription(String newDescription){
        this.description = newDescription;
    }
}