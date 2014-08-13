package org.activiti.neo4j.persistence.entity;

import org.activiti.bpmn.model.UserTask;
import org.activiti.neo4j.Constants;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

/**
 * Created by ilja on 25/07/14.
 */
@NodeEntity
public class UserTaskNodeNeo extends TaskNodeNeo {

    @Indexed
    protected String name;

    @Indexed
    protected String assignee;

    public UserTaskNodeNeo() {}

    public UserTaskNodeNeo(UserTask task) {
        this(task.getId(), task.getName(), task.getAssignee());
    }

    public UserTaskNodeNeo(String id, String name, String assignee) {
        super(id, Constants.TYPE_USER_TASK);
        this.name = name;
        this.assignee = assignee;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}