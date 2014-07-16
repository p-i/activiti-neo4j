package org.activiti.neo4j;

import org.activiti.engine.*;
import org.neo4j.graphdb.GraphDatabaseService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.FormService;
import org.activiti.engine.TaskService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;


public class ProcessEngineNeo4jImpl implements ProcessEngine {

    protected GraphDatabaseService graphDatabaseService;

    protected RepositoryService repositoryService;
    protected RuntimeService runtimeService;
    protected TaskService taskService;
    protected CommandExecutor commandExecutor;

    public ProcessEngineNeo4jImpl() {

    }

    public GraphDatabaseService getGraphDatabaseService() {
        return graphDatabaseService;
    }

    public void setGraphDatabaseService(GraphDatabaseService graphDatabaseService) {
        this.graphDatabaseService = graphDatabaseService;
    }
    public void setCommandExecutor(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void close() {

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
}