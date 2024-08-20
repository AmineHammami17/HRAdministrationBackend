package com.example.HRApplication.Services;

import com.example.HRApplication.DTO.ProjectTaskCountDTO;
import com.example.HRApplication.Models.Project;
import com.example.HRApplication.Repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public Project updateProject(Long id, Project updatedProject) {
        return projectRepository.findById(id).map(existingProject -> {
            existingProject.setName(updatedProject.getName());
            return projectRepository.save(existingProject);
        }).orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + id));
    }

    public void deleteProject(Long id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
        } else {
            throw new RuntimeException("Project not found with id: " + id);
        }
    }

    public long countTotalProjects() {
        return projectRepository.count();
    }
    public List<ProjectTaskCountDTO> getProjectTaskCounts() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .map(project -> new ProjectTaskCountDTO(project.getName(), project.getTasks().size()))
                .collect(Collectors.toList());
    }

}
