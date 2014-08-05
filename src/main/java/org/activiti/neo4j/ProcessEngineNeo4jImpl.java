package org.activiti.neo4j;

import org.activiti.engine.*;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;


public class ProcessEngineNeo4jImpl implements ProcessEngine {

    protected GraphDatabaseService graphDatabaseService;

    @Autowired
    protected RepositoryService repositoryService;

    protected RuntimeService runtimeService;
    protected TaskService taskService;

    protected CommandExecutorNeo4j commandExecutor;
    private ProcessEngineConfiguration processEngineConfiguration;

    public ProcessEngineNeo4jImpl(ProcessEngineConfiguration processEngineConfiguration) {
        this.processEngineConfiguration = processEngineConfiguration;

        this.runtimeService = this.processEngineConfiguration.getRuntimeService();
        this.taskService = this.processEngineConfiguration.getTaskService();
        this.repositoryService = this.processEngineConfiguration.getRepositoryService();
    }

    public GraphDatabaseService getGraphDatabaseService() {
        return graphDatabaseService;
    }

    public void setGraphDatabaseService(GraphDatabaseService graphDatabaseService) {
        this.graphDatabaseService = graphDatabaseService;
    }
    public void setCommandExecutor(CommandExecutorNeo4j commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void close() {
        graphDatabaseService.shutdown();
    }

    @Override
    public RepositoryService getRepositoryService() {
        return this.repositoryService;
    }

    @Override
    public RuntimeService getRuntimeService() {
        return this.runtimeService;
    }

    @Override
    public FormService getFormService() {
        return null;
    }

    @Override
    public TaskService getTaskService() {
        return this.taskService;
    }

    @Override
    public HistoryService getHistoryService() {
        return null;
    }

    @Override
    public IdentityService getIdentityService() {
        return null;
    }

    @Override
    public ManagementService getManagementService() {
        return null;
    }

    @Override
    public ProcessEngineConfiguration getProcessEngineConfiguration() {
        return this.processEngineConfiguration;
    }

}