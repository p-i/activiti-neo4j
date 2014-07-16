/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.neo4j;

import org.activiti.engine.*;
import org.activiti.engine.impl.*;
import org.activiti.neo4j.behavior.BehaviorMapping;
import org.activiti.neo4j.behavior.BehaviorMappingImpl;
import org.activiti.neo4j.manager.ExecutionManager;
import org.activiti.neo4j.manager.NodeBaseExecutionManager;
import org.activiti.neo4j.manager.NodeBasedTaskManager;
import org.activiti.neo4j.manager.TaskManager;
import org.neo4j.graphdb.GraphDatabaseService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.FormService;
import org.activiti.engine.TaskService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;


/**
 * @author Joram Barrez
 */
public class ProcessEngineConfigurationNeo4jImpl extends ProcessEngineConfiguration {

    protected GraphDatabaseService graphDatabaseService;
    protected BehaviorMapping behaviorMapping;
    protected Core core;
    protected CommandExecutor commandExecutor;
    protected ExecutionManager executionManager;
    protected TaskManager taskManager;

    protected RepositoryService repositoryService = new RepositoryServiceImpl();
    protected RuntimeService runtimeService = new RuntimeServiceImpl();
    protected HistoryService historyService = new HistoryServiceImpl();
    protected IdentityService identityService = new IdentityServiceImpl();
    protected TaskService taskService = new TaskServiceImpl();
    protected FormService formService = new FormServiceImpl();
    protected ManagementService managementService = new ManagementServiceImpl();


    public ProcessEngineNeo4jImpl buildProcessEngine() {
        ProcessEngineNeo4jImpl processEngine = new ProcessEngineNeo4jImpl();
        processEngine.setGraphDatabaseService(graphDatabaseService);

        initBehaviorMapping();
        initCore();
        initManagers();
        initCommandExecutor(processEngine);
        initServices(processEngine);

        return processEngine;
    }

    protected void initBehaviorMapping() {
        this.behaviorMapping = new BehaviorMappingImpl();
    }

    protected void initCore() {
        CoreImpl core = new CoreImpl();
        core.setBehaviorMapping(behaviorMapping);
        this.core = core;
    }

    protected void initManagers() {
        NodeBaseExecutionManager nodeBaseExecutionManager = new NodeBaseExecutionManager();
        nodeBaseExecutionManager.setGraphDb(graphDatabaseService);
        this.executionManager = nodeBaseExecutionManager;

        NodeBasedTaskManager nodeBasedTaskManager = new NodeBasedTaskManager();
        nodeBasedTaskManager.setGraphDb(graphDatabaseService);
        this.taskManager = nodeBasedTaskManager;
    }

    protected void initCommandExecutor(ProcessEngineNeo4jImpl processEngine) {
        CommandExecutor commandExecutor = new CommandExecutor(graphDatabaseService);
        commandExecutor.setCore(core);
        commandExecutor.setExecutionManager(executionManager);

        processEngine.setCommandExecutor(commandExecutor);
        this.commandExecutor = commandExecutor;
    }

    protected void initServices(ProcessEngineNeo4jImpl processEngine) {
/*    RepositoryService repositoryService = new RepositoryService(graphDatabaseService, commandExecutor);
    processEngine.setRepositoryService(repositoryService);

    RuntimeService runtimeService = new RuntimeService(graphDatabaseService, commandExecutor);
    processEngine.setRuntimeService(runtimeService);

    TaskService taskService = new TaskService(commandExecutor);
    taskService.setTaskManager(taskManager);
    processEngine.setTaskService(taskService);*/
    }

    public GraphDatabaseService getGraphDatabaseService() {
        return graphDatabaseService;
    }

    public void setGraphDatabaseService(GraphDatabaseService graphDatabaseService) {
        this.graphDatabaseService = graphDatabaseService;
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
        return this.formService;
    }

    @Override
    public TaskService getTaskService() {
        return this.taskService;
    }

    @Override
    public HistoryService getHistoryService() {
        return this.historyService;
    }

    @Override
    public IdentityService getIdentityService() {
        return this.identityService;
    }

    @Override
    public ManagementService getManagementService() {
        return this.managementService;
    }
}