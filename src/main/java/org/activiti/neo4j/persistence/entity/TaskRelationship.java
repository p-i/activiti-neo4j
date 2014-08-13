package org.activiti.neo4j.persistence.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.neo4j.annotation.*;

import java.util.UUID;


/**
 * Created by ilja on 31/07/14.
 */
@RelationshipEntity
public class TaskRelationship {

    @GraphId
    protected Long _id;
    @Indexed(unique=true)
    private String id; // TODO: solve this conflict: Neo4j uses ID as number, Activiti could have a string values

    @Fetch
    @StartNode
    private TaskNodeNeo from;

    @Fetch
    @EndNode
    private TaskNodeNeo to;

    // cached hashcode
    transient private Integer hash;

    public TaskRelationship() {
        this.id = UUID.randomUUID().toString();
    }

    public TaskRelationship(TaskNodeNeo from, TaskNodeNeo to) {
        this();
        this.from = from;
        this.to = to;
    }

    private String condition;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public TaskNodeNeo getFrom() {
        return from;
    }

    public void setFrom(TaskNodeNeo from) {
        this.from = from;
    }

    public TaskNodeNeo getTo() {
        return to;
    }


    public void setTo(TaskNodeNeo to) {
        this.to = to;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    /**
     * {@inheritDoc}
     */
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        TaskRelationship rhs = (TaskRelationship) obj;

        return new EqualsBuilder()
                .appendSuper(super.equals(rhs))
                .append(_id, rhs._id)
                .append(condition, rhs.condition)
                .append(from, rhs.from)
                .append(to, rhs.to)
                .isEquals();
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {

        if (this.hash == null) {
            this.hash = new HashCodeBuilder(17, 37).
                    append(_id).
                    append(from).
                    append(to).
                    toHashCode();
        }

        return this.hash.hashCode();
    }
}
