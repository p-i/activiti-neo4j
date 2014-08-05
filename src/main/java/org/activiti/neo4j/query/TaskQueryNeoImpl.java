package org.activiti.neo4j.query;

import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.neo4j.persistence.repository.UserTaskNeoRepository;
import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Builder to combine a query for CRUD operations of Task entity with a Neo4j database
 */
public class TaskQueryNeoImpl implements TaskQuery {

    @Autowired
    private UserTaskNeoRepository userTaskRepo;

    private String owner;
    private String assignee;

/*
    public TaskQueryNeoImpl(GraphDatabaseService graphDb) {
        this.graphDb = graphDb;
    }

    public List<Task> executeList() {
        try (Transaction tx = graphDb.beginTx()) {

            List<Task> tasks = new ArrayList<Task>();
            Index<Relationship> taskIndex = graphDb.index().forRelationships(Constants.TASK_INDEX);

            // TODO: rethink, how to generalise it and apply any parameter to the search criteria
            for (Relationship execution : taskIndex.get(Constants.INDEX_KEY_TASK_ASSIGNEE, assignee)) {
                Task task = new TaskEntity(execution.getId() + "");
                task.setName((String) execution.getProperty("name"));
                tasks.add(task);
            }

            tx.success();

            return tasks;
        }
    }
*/
    @Override
    public List<Task> list() {
        if (this.assignee != null) {
            return IteratorUtils.toList(userTaskRepo.findUserTasksByAssignee(this.assignee).iterator());
        }
        return new ArrayList<Task>();
    }

    @Override
    public TaskQuery taskId(String taskId) {
        return this;
    }

    @Override
    public TaskQuery taskName(String name) {
        return this;
    }

    @Override
    public TaskQuery taskNameLike(String nameLike) {
        return this;
    }

    @Override
    public TaskQuery taskDescription(String description) {
        return this;
    }

    @Override
    public TaskQuery taskDescriptionLike(String descriptionLike) {
        return this;
    }

    @Override
    public TaskQuery taskPriority(Integer priority) {
        return this;
    }

    @Override
    public TaskQuery taskMinPriority(Integer minPriority) {
        return this;
    }

    @Override
    public TaskQuery taskMaxPriority(Integer maxPriority) {
        return this;
    }

    @Override
    public TaskQuery taskAssignee(String assignee) {
        this.assignee = assignee;
        return this;
    }

    @Override
    public TaskQuery taskAssigneeLike(String assigneeLike) {
        return this;
    }

    @Override
    public TaskQuery taskOwner(String owner) {
        this.owner = owner;
        return this;
    }

    @Override
    public TaskQuery taskOwnerLike(String ownerLike) {
        return this;
    }

    @Override
    public TaskQuery taskUnassigned() {
        return this;
    }

    @Override
    public TaskQuery taskUnnassigned() {
        return this;
    }

    @Override
    public TaskQuery taskDelegationState(DelegationState delegationState) {
        return this;
    }

    @Override
    public TaskQuery taskCandidateUser(String candidateUser) {
        return this;
    }

    @Override
    public TaskQuery taskInvolvedUser(String involvedUser) {
        return this;
    }

    @Override
    public TaskQuery taskCandidateGroup(String candidateGroup) {
        return this;
    }

    @Override
    public TaskQuery taskCandidateOrAssigned(String userIdForCandidateAndAssignee) {
        return this;
    }

    @Override
    public TaskQuery taskCandidateGroupIn(List<String> candidateGroups) {
        return this;
    }

    @Override
    public TaskQuery taskTenantId(String tenantId) {
        return this;
    }

    @Override
    public TaskQuery taskTenantIdLike(String tenantIdLike) {
        return this;
    }

    @Override
    public TaskQuery taskWithoutTenantId() {
        return this;
    }

    @Override
    public TaskQuery processInstanceId(String processInstanceId) {
        return this;
    }

    @Override
    public TaskQuery processInstanceBusinessKey(String processInstanceBusinessKey) {
        return this;
    }

    @Override
    public TaskQuery processInstanceBusinessKeyLike(String processInstanceBusinessKeyLike) {
        return this;
    }

    @Override
    public TaskQuery executionId(String executionId) {
        return this;
    }

    @Override
    public TaskQuery taskCreatedOn(Date createTime) {
        return this;
    }

    @Override
    public TaskQuery taskCreatedBefore(Date before) {
        return this;
    }

    @Override
    public TaskQuery taskCreatedAfter(Date after) {
        return this;
    }

    @Override
    public TaskQuery excludeSubtasks() {
        return this;
    }

    @Override
    public TaskQuery taskCategory(String category) {
        return this;
    }

    @Override
    public TaskQuery taskDefinitionKey(String key) {
        return this;
    }

    @Override
    public TaskQuery taskDefinitionKeyLike(String keyLike) {
        return this;
    }

    @Override
    public TaskQuery taskVariableValueEquals(String variableName, Object variableValue) {
        return this;
    }

    @Override
    public TaskQuery taskVariableValueEquals(Object variableValue) {
        return this;
    }

    @Override
    public TaskQuery taskVariableValueEqualsIgnoreCase(String name, String value) {
        return this;
    }

    @Override
    public TaskQuery taskVariableValueNotEquals(String variableName, Object variableValue) {
        return this;
    }

    @Override
    public TaskQuery taskVariableValueNotEqualsIgnoreCase(String name, String value) {
        return this;
    }

    @Override
    public TaskQuery taskVariableValueGreaterThan(String name, Object value) {
        return this;
    }

    @Override
    public TaskQuery taskVariableValueGreaterThanOrEqual(String name, Object value) {
        return this;
    }

    @Override
    public TaskQuery taskVariableValueLessThan(String name, Object value) {
        return this;
    }

    @Override
    public TaskQuery taskVariableValueLessThanOrEqual(String name, Object value) {
        return this;
    }

    @Override
    public TaskQuery taskVariableValueLike(String name, String value) {
        return null;
    }

    @Override
    public TaskQuery processVariableValueEquals(String variableName, Object variableValue) {
        return null;
    }

    @Override
    public TaskQuery processVariableValueEquals(Object variableValue) {
        return null;
    }

    @Override
    public TaskQuery processVariableValueEqualsIgnoreCase(String name, String value) {
        return null;
    }

    @Override
    public TaskQuery processVariableValueNotEquals(String variableName, Object variableValue) {
        return null;
    }

    @Override
    public TaskQuery processVariableValueNotEqualsIgnoreCase(String name, String value) {
        return null;
    }

    @Override
    public TaskQuery processVariableValueGreaterThan(String name, Object value) {
        return null;
    }

    @Override
    public TaskQuery processVariableValueGreaterThanOrEqual(String name, Object value) {
        return null;
    }

    @Override
    public TaskQuery processVariableValueLessThan(String name, Object value) {
        return null;
    }

    @Override
    public TaskQuery processVariableValueLessThanOrEqual(String name, Object value) {
        return null;
    }

    @Override
    public TaskQuery processVariableValueLike(String name, String value) {
        return null;
    }

    @Override
    public TaskQuery processDefinitionKey(String processDefinitionKey) {
        return null;
    }

    @Override
    public TaskQuery processDefinitionKeyLike(String processDefinitionKeyLike) {
        return null;
    }

    @Override
    public TaskQuery processDefinitionId(String processDefinitionId) {
        return null;
    }

    @Override
    public TaskQuery processDefinitionName(String processDefinitionName) {
        return null;
    }

    @Override
    public TaskQuery processDefinitionNameLike(String processDefinitionNameLike) {
        return null;
    }

    @Override
    public TaskQuery dueDate(Date dueDate) {
        return null;
    }

    @Override
    public TaskQuery dueBefore(Date dueDate) {
        return null;
    }

    @Override
    public TaskQuery dueAfter(Date dueDate) {
        return null;
    }

    @Override
    public TaskQuery withoutDueDate() {
        return null;
    }

    @Override
    public TaskQuery suspended() {
        return null;
    }

    @Override
    public TaskQuery active() {
        return null;
    }

    @Override
    public TaskQuery includeTaskLocalVariables() {
        return null;
    }

    @Override
    public TaskQuery includeProcessVariables() {
        return null;
    }

    @Override
    public TaskQuery orderByTaskId() {
        return null;
    }

    @Override
    public TaskQuery orderByTaskName() {
        return null;
    }

    @Override
    public TaskQuery orderByTaskDescription() {
        return null;
    }

    @Override
    public TaskQuery orderByTaskPriority() {
        return null;
    }

    @Override
    public TaskQuery orderByTaskAssignee() {
        return null;
    }

    @Override
    public TaskQuery orderByTaskCreateTime() {
        return null;
    }

    @Override
    public TaskQuery orderByProcessInstanceId() {
        return null;
    }

    @Override
    public TaskQuery orderByExecutionId() {
        return null;
    }

    @Override
    public TaskQuery orderByDueDate() {
        return null;
    }

    @Override
    public TaskQuery orderByTenantId() {
        return null;
    }

    @Override
    public TaskQuery asc() {
        return null;
    }

    @Override
    public TaskQuery desc() {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public Task singleResult() {
        return null;
    }

    @Override
    public List<Task> listPage(int firstResult, int maxResults) {
        return null;
    }
}
