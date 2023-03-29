package com.example.employeeManager.service;

import com.example.employeeManager.entity.Task;
import com.example.employeeManager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    public List<Task> getTasks(){
        return taskRepository.findAll();
    }

    public void addNewTask(Task task) {
        taskRepository.save(task);
    }

    public Task getTaskID(Long id) {
        Optional<Task> taskByID = taskRepository.findById(id);
        if (!taskByID.isPresent()){
            throw new IllegalStateException("Can`t find employee with this id");
        }
        return taskByID.get();
    }

    public void updateTask(Task task, Long id) {
        Task old = getTaskID(id);
        if (task.getTitle() != old.getTitle()){
            old.setTitle(task.getTitle());
        }
        if (task.getDescription() != old.getDescription()){
            old.setDescription(task.getDescription());
        }
        if (task.getDueDate() != old.getDueDate()){
            old.setDueDate(task.getDueDate());
        }
        if (!task.getEmployees().equals(old.getEmployees())){
            old.setEmployees(task.getEmployees());
        }

        taskRepository.save(old);
    }

    public void removeTask(Long id) {
        boolean exists = taskRepository.existsById(id);
        if(!exists){
            throw new IllegalStateException("student with id" + id + " does not exist");
        }
        taskRepository.deleteById(id);
    }
}
