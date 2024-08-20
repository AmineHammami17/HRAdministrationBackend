package com.example.HRApplication.DTO;

public class ProjectTaskCountDTO {
    private String projectName;
    private long taskCount;

    public ProjectTaskCountDTO(String projectName, long taskCount) {
        this.projectName = projectName;
        this.taskCount = taskCount;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public long getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(long taskCount) {
        this.taskCount = taskCount;
    }

}
