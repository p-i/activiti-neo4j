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
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.neo4j.behavior.BehaviorMapping;
import org.activiti.neo4j.behavior.BehaviorMappingImpl;
import org.activiti.neo4j.manager.ExecutionManager;
import org.activiti.neo4j.manager.NodeBaseExecutionManager;
import org.activiti.neo4j.manager.TaskManager;
import org.activiti.neo4j.services.RepositoryServiceNeo4jImpl;
import org.activiti.neo4j.services.RuntimeServiceNeoImpl;
import org.activiti.neo4j.services.TaskServiceNeoImpl;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


/**
 * @author Joram Barrez
 */
@Configurable
@Component
public class ProcessEngineConfigurationNeo4jImpl extends SpringProcessEngineConfiguration implements IProcessEngineConfiguration {

    protected GraphDatabaseService graphDatabaseService;
    protected BehaviorMapping behaviorMapping;
    protected ExecutionManager executionManager;
    protected TaskManager taskManager;
    protected CommandExecutorNeo4j commandExecutor;
    protected Core core;

    protected RepositoryService repositoryService;
    protected RuntimeService runtimeService;
    protected TaskService taskService;
    protected HistoryService historyService;
    protected IdentityService identityService;
    protected FormService formService;
    protected ManagementService managementService;

    @Bean
    @Override
    public ProcessEngine buildProcessEngine() {

        initBehaviorMapping();
        initCore();
        //initManagers();
        initCommandExecutor();
        initServices();

        ProcessEngine processEngine = new ProcessEngineNeo4jImpl(this);
        //processEngine.setGraphDatabaseService(graphDatabaseService);
        ProcessEngines.setInitialized(true);
        Context.setProcessEngineConfiguration(this);
        super.autoDeployResources(processEngine);

        super.setClock(new DefaultClockImpl());

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

    // TODO: re-think, do we need managers?
    protected void initManagers() {
        NodeBaseExecutionManager nodeBaseExecutionManager = new NodeBaseExecutionManager();
        nodeBaseExecutionManager.setGraphDb(graphDatabaseService);
        this.executionManager = nodeBaseExecutionManager;
    }

    protected void initCommandExecutor() {
        CommandExecutorNeo4j commandExecutor = new CommandExecutorNeo4j();
        commandExecutor.setCore(core);
        commandExecutor.setProcessEngineConfiguration(this);
        commandExecutor.setExecutionManager(executionManager);
        this.commandExecutor = commandExecutor;
    }

    protected void initServices() {
        this.repositoryService = new RepositoryServiceNeo4jImpl(commandExecutor);
        this.runtimeService = new RuntimeServiceNeoImpl(commandExecutor);
        this.taskService = new TaskServiceNeoImpl(commandExecutor);
    }

    @Bean
    @Override
    public RepositoryService getRepositoryService() {
        return this.repositoryService;
    }

    @Bean
    @Override
    public RuntimeService getRuntimeService() {
        return this.runtimeService;
    }

    @Bean
    @Override
    public FormService getFormService() {
        return this.formService;
    }

    @Bean
    @Override
    public TaskService getTaskService() {
        return this.taskService;
    }

    @Bean
    @Override
    public HistoryService getHistoryService() {
        return this.historyService;
    }

    @Bean
    @Override
    public IdentityService getIdentityService() {
        return this.identityService;
    }

    @Bean
    @Override
    public ManagementService getManagementService() {
        return this.managementService;
    }

    @Bean
    @Override
    protected CommandInterceptor createTransactionInterceptor() {
        return null;
    }

}