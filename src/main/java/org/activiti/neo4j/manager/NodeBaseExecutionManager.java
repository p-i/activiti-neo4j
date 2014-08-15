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
package org.activiti.neo4j.manager;

import org.activiti.neo4j.Execution;
import org.activiti.neo4j.entity.NodeBasedExecution;
import org.activiti.neo4j.persistence.entity.TaskNodeNeo;
import org.activiti.neo4j.persistence.repository.TaskNeoRepository;
import org.springframework.context.ApplicationContext;


/**
 * @author Joram Barrez
 */
public class NodeBaseExecutionManager implements ExecutionManager {

    private final ApplicationContext context;
    private TaskNeoRepository taskNeoRepository;

    public NodeBaseExecutionManager(ApplicationContext ctx) {
        this.context = ctx;
        this.taskNeoRepository = (TaskNeoRepository) context.getBean("taskNeoRepository");
    }

    public Execution getExecutionById(String taskId) {
        TaskNodeNeo nodeNeo = taskNeoRepository.findAllBySchemaPropertyValue("id", taskId).single();
        // return new NodeBasedExecution(graphDb.getRelationshipById(id));
        return new NodeBasedExecution(nodeNeo);
    }


}
