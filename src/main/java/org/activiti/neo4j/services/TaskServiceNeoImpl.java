package org.activiti.neo4j.services;

import org.activiti.engine.TaskService;
import org.activiti.engine.impl.ServiceImpl;
import org.activiti.engine.task.*;
import org.activiti.neo4j.cmd.ICommand;
import org.activiti.neo4j.CommandContextNeo4j;
import org.activiti.neo4j.CommandExecutorNeo4j;

import org.activiti.neo4j.manager.TaskManager;
import org.activiti.neo4j.query.TaskQueryNeoImpl;

import java.io.InputStream;
import java.util.*;

import static org.activiti.neo4j.utils.Utils.notImplemented;

public class TaskServiceNeoImpl extends ServiceImpl implements TaskService {

    // TODO: can be put in a command service super class
    protected CommandExecutorNeo4j commandExecutor;

    public TaskServiceNeoImpl(CommandExecutorNeo4j commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @Override
    public Task newTask() {
        notImplemented();
        return null;
    }

    @Override
    public Task newTask(String taskId) {
        notImplemented();
        return null;
    }

    @Override
    public void saveTask(Task task) {
        notImplemented();
    }

    @Override
    public void deleteTask(String taskId) {
        notImplemented();
    }

    @Override
    public void deleteTasks(Collection<String> taskIds) {
        notImplemented();
    }

    @Override
    public void deleteTask(String taskId, boolean cascade) {
        notImplemented();
    }

    @Override
    public void deleteTasks(Collection<String> taskIds, boolean cascade) {
        notImplemented();
    }

    @Override
    public void deleteTask(String taskId, String deleteReason) {
        notImplemented();
    }

    @Override
    public void deleteTasks(Collection<String> taskIds, String deleteReason) {
        notImplemented();
    }

    @Override
    public void claim(String taskId, String userId) {
        notImplemented();
    }

    @Override
    public void unclaim(String taskId) {
        notImplemented();
    }

    @Override
    public void complete(final String taskId) {
        commandExecutor.execute(new ICommand() {

            @Override
            public Object execute(CommandContextNeo4j commandContext) {
                commandContext.signal(commandContext.getExecutionManager().getExecutionById(Long.parseLong(taskId)));
                return null;
            }
        });
    }

    @Override
    public void delegateTask(String taskId, String userId) {
        notImplemented();
    }

    @Override
    public void resolveTask(String taskId) {
        notImplemented();
    }

    @Override
    public void resolveTask(String taskId, Map<String, Object> variables) {
        notImplemented();
    }

    @Override
    public void complete(String taskId, Map<String, Object> variables) {
        notImplemented();
    }

    @Override
    public void complete(String taskId, Map<String, Object> variables, boolean localScope) {
        notImplemented();
    }

    @Override
    public void setAssignee(String taskId, String userId) {
        notImplemented();
    }

    @Override
    public void setOwner(String taskId, String userId) {
        notImplemented();
    }

    @Override
    public List<IdentityLink> getIdentityLinksForTask(String taskId) {
        notImplemented();
        return new ArrayList<IdentityLink>();
    }

    @Override
    public void addCandidateUser(String taskId, String userId) {
        notImplemented();
    }

    @Override
    public void addCandidateGroup(String taskId, String groupId) {
        notImplemented();
    }

    @Override
    public void addUserIdentityLink(String taskId, String userId, String identityLinkType) {
        notImplemented();
    }

    @Override
    public void addGroupIdentityLink(String taskId, String groupId, String identityLinkType) {
        notImplemented();
    }

    @Override
    public void deleteCandidateUser(String taskId, String userId) {
        notImplemented();
    }

    @Override
    public void deleteCandidateGroup(String taskId, String groupId) {
        notImplemented();
    }

    @Override
    public void deleteUserIdentityLink(String taskId, String userId, String identityLinkType) {
        notImplemented();
    }

    @Override
    public void deleteGroupIdentityLink(String taskId, String groupId, String identityLinkType) {
        notImplemented();
    }

    @Override
    public void setPriority(String taskId, int priority) {
        notImplemented();
    }

    @Override
    public void setDueDate(String taskId, Date dueDate) {
        notImplemented();
    }

    @Override
    public TaskQuery createTaskQuery() {
        return new TaskQueryNeoImpl(commandExecutor.getGraphDatabaseService());
    }

    @Override
    public NativeTaskQuery createNativeTaskQuery() {
        notImplemented();
        return null;
    }

    @Override
    public void setVariable(String taskId, String variableName, Object value) {
        notImplemented();
    }

    @Override
    public void setVariables(String taskId, Map<String, ? extends Object> variables) {
        notImplemented();
    }

    @Override
    public void setVariableLocal(String taskId, String variableName, Object value) {
        notImplemented();
    }

    @Override
    public void setVariablesLocal(String taskId, Map<String, ? extends Object> variables) {
        notImplemented();
    }

    @Override
    public Object getVariable(String taskId, String variableName) {
        notImplemented();
        return null;
    }

    @Override
    public boolean hasVariable(String taskId, String variableName) {
        notImplemented();
        return false;
    }

    @Override
    public Object getVariableLocal(String taskId, String variableName) {
        notImplemented();
        return null;
    }

    @Override
    public boolean hasVariableLocal(String taskId, String variableName) {
        notImplemented();
        return false;
    }

    @Override
    public Map<String, Object> getVariables(String taskId) {
        notImplemented();
        return null;
    }

    @Override
    public Map<String, Object> getVariablesLocal(String taskId) {
        notImplemented();
        return null;
    }

    @Override
    public Map<String, Object> getVariables(String taskId, Collection<String> variableNames) {
        notImplemented();
        return null;
    }

    @Override
    public Map<String, Object> getVariablesLocal(String taskId, Collection<String> variableNames) {
        notImplemented();
        return null;
    }

    @Override
    public void removeVariable(String taskId, String variableName) {
        notImplemented();
    }

    @Override
    public void removeVariableLocal(String taskId, String variableName) {
        notImplemented();
    }

    @Override
    public void removeVariables(String taskId, Collection<String> variableNames) {
        notImplemented();
    }

    @Override
    public void removeVariablesLocal(String taskId, Collection<String> variableNames) {
        notImplemented();
    }

    @Override
    public Comment addComment(String taskId, String processInstanceId, String message) {
        notImplemented();
        return null;
    }

    @Override
    public Comment addComment(String taskId, String processInstanceId, String type, String message) {
        notImplemented();
        return null;
    }

    @Override
    public Comment getComment(String commentId) {
        notImplemented();
        return null;
    }

    @Override
    public void deleteComments(String taskId, String processInstanceId) {
        notImplemented();
    }

    @Override
    public void deleteComment(String commentId) {
        notImplemented();
    }

    @Override
    public List<Comment> getTaskComments(String taskId) {
        notImplemented();
        return null;
    }

    @Override
    public List<Comment> getTaskComments(String taskId, String type) {
        notImplemented();
        return null;
    }

    @Override
    public List<Comment> getCommentsByType(String type) {
        notImplemented();
        return null;
    }

    @Override
    public List<Event> getTaskEvents(String taskId) {
        notImplemented();
        return null;
    }

    @Override
    public Event getEvent(String eventId) {
        notImplemented();
        return null;
    }

    @Override
    public List<Comment> getProcessInstanceComments(String processInstanceId) {
        notImplemented();
        return null;
    }

    @Override
    public List<Comment> getProcessInstanceComments(String s, String s2) {
        notImplemented();
        return null;
    }

    @Override
    public Attachment createAttachment(String attachmentType, String taskId, String processInstanceId, String attachmentName, String attachmentDescription, InputStream content) {
        notImplemented();
        return null;
    }

    @Override
    public Attachment createAttachment(String attachmentType, String taskId, String processInstanceId, String attachmentName, String attachmentDescription, String url) {
        notImplemented();
        return null;
    }

    @Override
    public void saveAttachment(Attachment attachment) {
        notImplemented();
    }

    @Override
    public Attachment getAttachment(String attachmentId) {
        notImplemented();
        return null;
    }

    @Override
    public InputStream getAttachmentContent(String attachmentId) {
        notImplemented();
        return null;
    }

    @Override
    public List<Attachment> getTaskAttachments(String taskId) {
        notImplemented();
        return null;
    }

    @Override
    public List<Attachment> getProcessInstanceAttachments(String processInstanceId) {
        notImplemented();
        return null;
    }

    @Override
    public void deleteAttachment(String attachmentId) {
        notImplemented();
    }

    @Override
    public List<Task> getSubTasks(String parentTaskId) {
        notImplemented();
        return null;
    }
}
