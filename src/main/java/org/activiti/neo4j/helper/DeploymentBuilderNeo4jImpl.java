package org.activiti.neo4j.helper;

import org.activiti.engine.impl.repository.DeploymentBuilderImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.neo4j.services.RepositoryServiceNeo4jImpl;

/**
 * <p>Hack for {@link org.activiti.engine.impl.repository.DeploymentBuilderImpl DeploymentBuilderImpl}.
 * We need to use DeploymentBuilder with our Repository service, but original class
 * has the constructor with concrete class RepositoryServiceImpl instead of its interface.</p>
 *
 * <pre>
 *       public DeploymentBuilderImpl(RepositoryServiceImpl repositoryService) {
 *          this.repositoryService = repositoryService;
 *        }
 * </pre>
 *
 * <p>Thus, the polymorphism is not possible at this moment and we can't put our service to the
 * aforementioned builder.</p>
 *
 * <p>This class is simple "hack" to use our own implementation of RepositoryService instead
 * of hard-coded one</p>
 *
 * <p><b>Note!</b> This class should be destroyed and replaced with <b>DeploymentBuilderImpl</b>
 * in case if the Activiti engine updates the constructor in the builder</p>
 */
public class DeploymentBuilderNeo4jImpl extends DeploymentBuilderImpl {

    protected transient RepositoryServiceNeo4jImpl repositoryService;

    public DeploymentBuilderNeo4jImpl(RepositoryServiceNeo4jImpl repositoryService) {
        super(null);
        this.repositoryService = repositoryService;
    }

    @Override
    public Deployment deploy() {
        return repositoryService.deploy(this);
    }
}
