package org.activiti.neo4j.cmd.impl;

import org.activiti.engine.impl.cmd.DeployCmd;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.neo4j.CommandContextNeo4j;
import org.activiti.neo4j.cmd.ICommand;
import org.activiti.neo4j.helper.DeploymentBuilderNeo4jImpl;

/**
 * <p>Wrapper for the Cmd class {@link org.activiti.engine.impl.cmd.DeployCmd DeployCmd}.
 * Unless this Cmd class has the construction with a concrete typed parameter (DeploymentBuilderImpl),
 * we can't use our own implementation.</p>
 *
 * <p><b>Note!</b> This class should be removed when the activity-engine changes this file with interface param
 * constructor</p>
 */
public class DeployNeoCmd<T> extends DeployCmd implements ICommand<Deployment> {

    public DeployNeoCmd(DeploymentBuilder builder) {
        super((DeploymentBuilderNeo4jImpl) builder);
    }

    public DeployNeoCmd(DeploymentBuilderNeo4jImpl deploymentBuilder) {
        super(deploymentBuilder);
    }

    @Override
    public Deployment execute(CommandContextNeo4j commandContext) {
        // FIXME:
        return super.execute(null);
    }
}