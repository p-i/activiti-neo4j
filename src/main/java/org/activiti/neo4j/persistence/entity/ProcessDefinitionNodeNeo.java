package org.activiti.neo4j.persistence.entity;

import org.activiti.neo4j.Constants;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Set;

/**
 * Created by ilja on 29/07/14.
 */
@NodeEntity
public class ProcessDefinitionNodeNeo extends TaskNodeNeo {

    @RelatedTo(type = "STARTS_IN", direction = Direction.OUTGOING)
    private Set<TaskNodeNeo> start;

    /**
     * Add start node to the definition entity
     *
     * @param node
     */
    public void addStartNode(TaskNodeNeo node) {
        this.start.add(node);
    }

    public Set<TaskNodeNeo> getStart() {
        return start;
    }

    public ProcessDefinitionNodeNeo(String id, String type) {
        super(id, type);
    }

    public ProcessDefinitionNodeNeo() {
        super(Constants.PROCESS_DEFINITION_INDEX, Constants.TYPE_DEFINITION);
    }
}
