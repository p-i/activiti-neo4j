package org.activiti.neo4j.persistence.entity;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

/**
 * <p>Task node playing role as an entity, i.e. it should be persisted in a Neo4j store.</p>
 *
 */
@NodeEntity
public class TaskNodeNeo extends TaskEntity {

    @GraphId
    private Long _generatedId;

    // TODO: it is shadowing, basically it is a bad design. Re-think, how we could add @Indexed to parent id
    @Indexed(unique = true)
    private String id;

    @Indexed
    private String type;

    private String name;

    // cached hashcode
    transient private Integer hash;

    public TaskNodeNeo(){ }

    public TaskNodeNeo(String type) {
        this.type = type;
    }

    public TaskNodeNeo(String id, String type) {
        super(id);
        this.setId(id);
        this.type = type;
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
        TaskNodeNeo rhs = (TaskNodeNeo) obj;

        return new EqualsBuilder()
                .appendSuper(super.equals(rhs))
                .append(_generatedId, rhs._generatedId)
                .append(name, rhs.name)
                .append(type, rhs.type)
                .isEquals();
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {

        if (this.hash == null) {
            this.hash = new HashCodeBuilder(17, 37).
                    append(_generatedId).
                    append(type).
                    append(name).
                    toHashCode();
        }

        return this.hash.hashCode();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public Long getGeneratedId() {
        return _generatedId;
    }
}