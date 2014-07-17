package org.activiti.neo4j;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author jbarrez
 */
public class Task {

    protected long id;
    protected String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("name", name).
                append("id", id).
                toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).
                append(name).
                append(id).
                toHashCode();
    }
}
