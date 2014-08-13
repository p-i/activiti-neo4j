package org.activiti.neo4j.persistence.entity;

import org.activiti.neo4j.Constants;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.util.Set;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by ilja on 29/07/14.
 */
@NodeEntity
public class ProcessDefinitionNodeNeo extends TaskNodeNeo {

    @RelatedTo(type = Constants.REL_TYPE_STARTS_IN, direction = Direction.OUTGOING)
    private TaskNodeNeo start;

    @RelatedTo(type = Constants.REL_TYPE_PROCESS_INSTANCE, direction = Direction.OUTGOING)
    private Set<ExecutionNodeNeo> processInstances;

    /**
     * Add start node to the definition entity
     *
     * @param node
     */
    public void setStartNode(TaskNodeNeo node) {
        notNull(node);
        this.start = node;
    }

    /**
     * Adds the given node to the instances stack
     *
     * @param node
     */
    public void addProcessInstance(ExecutionNodeNeo node) {
        notNull(node);
        this.processInstances.add(node);
    }

    public TaskNodeNeo getStart() {
        return start;
    }

    public ProcessDefinitionNodeNeo(String id, String type) {
        super(id, type);
    }

    public ProcessDefinitionNodeNeo() {
        super(Constants.PROCESS_DEFINITION_INDEX, Constants.TYPE_DEFINITION);
    }

    public Set<ExecutionNodeNeo> getProcessInstances() {
        return this.processInstances;
    }

    @Override
    public Set<TaskRelationship> getRelationShipsByType(String type) {
        switch (type) {
            default:
                return super.getRelationShipsByType(type);
        }

    }

}

