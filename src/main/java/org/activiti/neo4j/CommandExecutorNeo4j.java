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
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author Joram Barrez
 * 
 */
public class CommandExecutorNeo4j {

    private Core core;

    private IProcessEngineConfiguration processEngineConfiguration;

    private ExecutionManager executionManager;

    public <T> T execute(final ICommand<T> command) {

        // TODO: create interceptor stack analogue to the Activiti interceptor stack
        // to separate transaction interceptor from command execution interceptor

        final CommandContextNeo4j<T> commandContext = initialiseCommandContext(command);

        while (!commandContext.getAgenda().isEmpty()) {
            Runnable runnable = commandContext.getAgenda().poll();
            runnable.run();
        }

        return commandContext.getResult();
    }

    protected <T> CommandContextNeo4j<T> initialiseCommandContext(final ICommand<T> command) {
        final CommandContextNeo4j<T> commandContext = new CommandContextNeo4j<T>(processEngineConfiguration);
        commandContext.setCore(core);
        //commandContext.setExecutionManager(executionManager);

        commandContext.getAgenda().add(new Runnable() {

            public void run() {
                command.execute(commandContext);
            }

        });
        return commandContext;
    }


  public Core getCore() {
    return core;
  }
  
  public void setCore(Core core) {
    this.core = core;
  }

    public void setProcessEngineConfiguration(IProcessEngineConfiguration processEngineConfiguration) {
        this.processEngineConfiguration = processEngineConfiguration;
    }

    public void setExecutionManager(ExecutionManager executionManager) {
        this.executionManager = executionManager;
    }
}
