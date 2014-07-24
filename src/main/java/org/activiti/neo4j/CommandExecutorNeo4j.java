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

import org.activiti.engine.IProcessEngineConfiguration;
import org.activiti.neo4j.cmd.ICommand;
import org.activiti.neo4j.manager.ExecutionManager;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;


/**
 * @author Joram Barrez
 * 
 */
public class CommandExecutorNeo4j {
  
  protected GraphDatabaseService graphDatabaseService;
  protected Core core;
  protected ExecutionManager executionManager;
  private final IProcessEngineConfiguration processEngineConfiguration;
  
  public CommandExecutorNeo4j(GraphDatabaseService graphDatabaseService, IProcessEngineConfiguration processEngineConfiguration) {
        this.graphDatabaseService = graphDatabaseService;
        this.processEngineConfiguration = processEngineConfiguration;
  }
  
  public <T> T execute(final ICommand<T> command) {
    
    // TODO: create interceptor stack analogue to the Activiti interceptor stack
    // to separate transaction interceptor from command execution interceptor
    
    final CommandContextNeo4j<T> commandContext = initialiseCommandContext(command);

    try (Transaction tx = graphDatabaseService.beginTx()) {
      
      while (!commandContext.getAgenda().isEmpty()) {
        Runnable runnable = commandContext.getAgenda().poll();
        runnable.run();
      }
      
      tx.success();
    }
    
    return commandContext.getResult();
    
  }

  protected <T> CommandContextNeo4j<T> initialiseCommandContext(final ICommand<T> command) {
    final CommandContextNeo4j<T> commandContext = new CommandContextNeo4j<T>(processEngineConfiguration);
    commandContext.setCore(core);
    commandContext.setExecutionManager(executionManager);

    commandContext.getAgenda().add(new Runnable() {
      
      public void run() {
        command.execute(commandContext);
      }
      
    });
    return commandContext;
  }

  
  public GraphDatabaseService getGraphDatabaseService() {
    return graphDatabaseService;
  }

  
  public void setGraphDatabaseService(GraphDatabaseService graphDatabaseService) {
    this.graphDatabaseService = graphDatabaseService;
  }

  public Core getCore() {
    return core;
  }
  
  public void setCore(Core core) {
    this.core = core;
  }

  public ExecutionManager getExecutionManager() {
    return executionManager;
  }

  public void setExecutionManager(ExecutionManager executionManager) {
    this.executionManager = executionManager;
  }
  
}
