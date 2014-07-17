package org.activiti.neo4j.services;

import org.activiti.neo4j.cmd.ICommand;
import org.activiti.neo4j.CommandContextNeo4j;
import org.activiti.neo4j.CommandExecutorNeo4j;
import org.activiti.neo4j.Task;
import org.activiti.neo4j.manager.TaskManager;

import java.util.List;

public class TaskService {

  // TODO: can be put in a command service super class
  protected CommandExecutorNeo4j commandExecutor;
  protected TaskManager taskManager;

  public TaskService(CommandExecutorNeo4j commandExecutor) {
    this.commandExecutor = commandExecutor;
  }

  public List<Task> findTasksFor(final String assignee) {
    return commandExecutor.execute(new ICommand<List<Task>>() {
      
      public void execute(CommandContextNeo4j<List<Task>> commandContext) {
        commandContext.setResult(taskManager.getTasksByAssignee(assignee));
      }

    });
  }

  public void complete(final long taskId) {
    commandExecutor.execute(new ICommand<Void>() {
      
      public void execute(CommandContextNeo4j<Void> commandContext) {
        commandContext.signal(commandContext.getExecutionManager().getExecutionById(taskId));
      }
      
    });
  }

  public TaskManager getTaskManager() {
    return taskManager;
  }
  
  public void setTaskManager(TaskManager taskManager) {
    this.taskManager = taskManager;
  }

}
