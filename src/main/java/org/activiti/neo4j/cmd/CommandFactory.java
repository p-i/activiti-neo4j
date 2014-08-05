package org.activiti.neo4j.cmd;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.neo4j.cmd.impl.DeployNeoCmd;
import org.activiti.neo4j.cmd.impl.StartProcessInstanceNeoCmd;

/**
 * The factory is used to create beans of ICommand type on-demand,
 * inserting an instance of Deployment Builder to its constructor
 */
public class CommandFactory {

    public static ICommand<Deployment> createCommandBean(DeploymentBuilder builder) {
        return new DeployNeoCmd<Deployment>(builder);
    }

}
