package org.activiti.neo4j.persistence.entity;

import org.activiti.bpmn.model.ExclusiveGateway;
import org.activiti.neo4j.Constants;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

/**
 * Created by ilja on 29/07/14.
 */
@NodeEntity
public class ExclusiveGatewayNodeNeo extends TaskNodeNeo {

    @Indexed
    private String defaultFlow;

    public ExclusiveGatewayNodeNeo() {}

    public ExclusiveGatewayNodeNeo(ExclusiveGateway gateway) {
        this(gateway.getId(), gateway.getName(), gateway.getDefaultFlow());
    }

    public ExclusiveGatewayNodeNeo(String id, String name, String defaultFlow) {
        super(id, Constants.TYPE_EXCLUSIVE_GATEWAY);
        this.defaultFlow = defaultFlow;
    }

    public String getDefaultFlow() {
        return defaultFlow;
    }

    public void setDefaultFlow(String defaultFlow) {
        this.defaultFlow = defaultFlow;
    }
}
