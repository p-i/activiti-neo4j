package org.activiti.neo4j.persistence.entity;

import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

import java.util.UUID;

/**
 * Created by ilja on 13/08/14.
 */
@NodeEntity
public class ExecutionNodeNeo extends ExecutionEntity {

    @GraphId
    private Long _generatedId;

    @Indexed(unique = true)
    private String id;

    public ExecutionNodeNeo() {
        this.id = UUID.randomUUID().toString();
    }
}
