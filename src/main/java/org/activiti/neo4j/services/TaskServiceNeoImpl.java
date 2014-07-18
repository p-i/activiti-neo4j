package org.activiti.neo4j.services;

import org.activiti.engine.TaskService;
import org.activiti.engine.impl.ServiceImpl;
import org.activiti.engine.task.*;
import org.activiti.neo4j.cmd.ICommand;
import org.activiti.neo4j.CommandContextNeo4j;
import org.activiti.neo4j.CommandExecutorNeo4j;

import org.activiti.neo4j.manager.TaskManager;
import org.activiti.neo4j.query.TaskQueryNeoImpl;
import org.apache.commons.lang.NotImplementedException;

import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TaskServiceNeoImpl extends ServiceImpl implements TaskService {

    // TODO: can be put in a command service super class
    protected CommandExecutorNeo4j commandExecutor;
    protected TaskManager taskManager;

    public TaskServiceNeoImpl(CommandExecutorNeo4j commandExecutor) {
        this.commandExecutor = commandExecutor;
    }


    public List<Task> findTasksFor(final String assignee) {
        return commandExecutor.execute(new ICommand<List<Task>>() {
            public List<Task> execute(CommandContextNeo4j<List<Task>> commandContext) {
                List<Task> tasks = taskManager.getTasksByAssignee(assignee);
                commandContext.setResult(tasks);
                return tasks;
            }
        });
    }

/*
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
*/


    @Override
    public Task newTask() {
        _notFound();
        return null;
    }

    @Override
    public Task newTask(String taskId) {
        _notFound();
        return null;
    }

    @Override
    public void saveTask(Task task) {
        _notFound();
    }

    @Override
    public void deleteTask(String taskId) {
        _notFound();
    }

    @Override
    public void deleteTasks(Collection<String> taskIds) {
        _notFound();
    }

    @Override
    public void deleteTask(String taskId, boolean cascade) {
        _notFound();
    }

    @Override
    public void deleteTasks(Collection<String> taskIds, boolean cascade) {
        _notFound();
    }

    @Override
    public void deleteTask(String taskId, String deleteReason) {
        _notFound();
    }

    @Override
    public void deleteTasks(Collection<String> taskIds, String deleteReason) {
        _notFound();
    }

    @Override
    public void claim(String taskId, String userId) {
        _notFound();
    }

    @Override
    public void unclaim(String taskId) {
        _notFound();
    }

    @Override
    public void complete(String taskId) {
        _notFound();
    }

    @Override
    public void delegateTask(String taskId, String userId) {
        _notFound();
    }

    @Override
    public void resolveTask(String taskId) {
        _notFound();
    }

    @Override
    public void resolveTask(String taskId, Map<String, Object> variables) {
        _notFound();
    }

    @Override
    public void complete(String taskId, Map<String, Object> variables) {
        _notFound();
    }

    @Override
    public void complete(String taskId, Map<String, Object> variables, boolean localScope) {
        _notFound();
    }

    @Override
    public void setAssignee(String taskId, String userId) {
        _notFound();
    }

    @Override
    public void setOwner(String taskId, String userId) {
        _notFound();
    }

    @Override
    public List<IdentityLink> getIdentityLinksForTask(String taskId) {
        _notFound();
        return null;
    }

    @Override
    public void addCandidateUser(String taskId, String userId) {
        _notFound();
    }

    @Override
    public void addCandidateGroup(String taskId, String groupId) {
        _notFound();
    }

    @Override
    public void addUserIdentityLink(String taskId, String userId, String identityLinkType) {
        _notFound();
    }

    @Override
    public void addGroupIdentityLink(String taskId, String groupId, String identityLinkType) {
        _notFound();
    }

    @Override
    public void deleteCandidateUser(String taskId, String userId) {
        _notFound();
    }

    @Override
    public void deleteCandidateGroup(String taskId, String groupId) {
        _notFound();
    }

    @Override
    public void deleteUserIdentityLink(String taskId, String userId, String identityLinkType) {
        _notFound();
    }

    @Override
    public void deleteGroupIdentityLink(String taskId, String groupId, String identityLinkType) {
        _notFound();
    }

    @Override
    public void setPriority(String taskId, int priority) {
        _notFound();
    }

    @Override
    public void setDueDate(String taskId, Date dueDate) {
        _notFound();
    }

    @Override
    public TaskQuery createTaskQuery() {
        return new TaskQueryNeoImpl(commandExecutor.getGraphDatabaseService());
    }

    @Override
    public NativeTaskQuery createNativeTaskQuery() {
        _notFound();
        return null;
    }

    @Override
    public void setVariable(String taskId, String variableName, Object value) {
        _notFound();
    }

    @Override
    public void setVariables(String taskId, Map<String, ? extends Object> variables) {
        _notFound();
    }

    @Override
    public void setVariableLocal(String taskId, String variableName, Object value) {
        _notFound();
    }

    @Override
    public void setVariablesLocal(String taskId, Map<String, ? extends Object> variables) {
        _notFound();
    }

    @Override
    public Object getVariable(String taskId, String variableName) {
        _notFound();
        return null;
    }

    @Override
    public boolean hasVariable(String taskId, String variableName) {
        _notFound();
        return false;
    }

    @Override
    public Object getVariableLocal(String taskId, String variableName) {
        _notFound();
        return null;
    }

    @Override
    public boolean hasVariableLocal(String taskId, String variableName) {
        _notFound();
        return false;
    }

    @Override
    public Map<String, Object> getVariables(String taskId) {
        _notFound();
        return null;
    }

    @Override
    public Map<String, Object> getVariablesLocal(String taskId) {
        _notFound();
        return null;
    }

    @Override
    public Map<String, Object> getVariables(String taskId, Collection<String> variableNames) {
        _notFound();
        return null;
    }

    @Override
    public Map<String, Object> getVariablesLocal(String taskId, Collection<String> variableNames) {
        _notFound();
        return null;
    }

    @Override
    public void removeVariable(String taskId, String variableName) {
        _notFound();
    }

    @Override
    public void removeVariableLocal(String taskId, String variableName) {
        _notFound();
    }

    @Override
    public void removeVariables(String taskId, Collection<String> variableNames) {
        _notFound();
    }

    @Override
    public void removeVariablesLocal(String taskId, Collection<String> variableNames) {
        _notFound();
    }

    @Override
    public Comment addComment(String taskId, String processInstanceId, String message) {
        _notFound();
        return null;
    }

    @Override
    public Comment addComment(String taskId, String processInstanceId, String type, String message) {
        _notFound();
        return null;
    }

    @Override
    public Comment getComment(String commentId) {
        _notFound();
        return null;
    }

    @Override
    public void deleteComments(String taskId, String processInstanceId) {
        _notFound();
    }

    @Override
    public void deleteComment(String commentId) {
        _notFound();
    }

    @Override
    public List<Comment> getTaskComments(String taskId) {
        _notFound();
        return null;
    }

    @Override
    public List<Comment> getTaskComments(String taskId, String type) {
        _notFound();
        return null;
    }

    @Override
    public List<Comment> getCommentsByType(String type) {
        _notFound();
        return null;
    }

    @Override
    public List<Event> getTaskEvents(String taskId) {
        _notFound();
        return null;
    }

    @Override
    public Event getEvent(String eventId) {
        _notFound();
        return null;
    }

    @Override
    public List<Comment> getProcessInstanceComments(String processInstanceId) {
        _notFound();
        return null;
    }

    @Override
    public Attachment createAttachment(String attachmentType, String taskId, String processInstanceId, String attachmentName, String attachmentDescription, InputStream content) {
        _notFound();
        return null;
    }

    @Override
    public Attachment createAttachment(String attachmentType, String taskId, String processInstanceId, String attachmentName, String attachmentDescription, String url) {
        _notFound();
        return null;
    }

    @Override
    public void saveAttachment(Attachment attachment) {
        _notFound();
    }

    @Override
    public Attachment getAttachment(String attachmentId) {
        _notFound();
        return null;
    }

    @Override
    public InputStream getAttachmentContent(String attachmentId) {
        _notFound();
        return null;
    }

    @Override
    public List<Attachment> getTaskAttachments(String taskId) {
        _notFound();
        return null;
    }

    @Override
    public List<Attachment> getProcessInstanceAttachments(String processInstanceId) {
        _notFound();
        return null;
    }

    @Override
    public void deleteAttachment(String attachmentId) {
        _notFound();
    }

    @Override
    public List<Task> getSubTasks(String parentTaskId) {
        _notFound();
        return null;
    }

    private void _notFound() {
        _notFound();
        throw new NotImplementedException("The method is not implemented in " + this.getClass().getSimpleName());
    }

}
