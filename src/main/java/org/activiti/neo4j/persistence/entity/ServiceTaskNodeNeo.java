package org.activiti.neo4j.persistence.entity;

import org.activiti.bpmn.model.ServiceTask;
import org.activiti.neo4j.Constants;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class ServiceTaskNodeNeo extends TaskNodeNeo {

    @Indexed
    protected String clazz;

    public ServiceTaskNodeNeo() {}

    public ServiceTaskNodeNeo(ServiceTask task) {
        this(task.getId(), task.getImplementation());
    }

    public ServiceTaskNodeNeo(String id, String clazz) {
        super(id, Constants.TYPE_SERVICE_TASK);
        this.clazz = clazz;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

}
