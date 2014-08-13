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
import org.activiti.neo4j.persistence.repository.TaskNeoRepository;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;


/**
 * @author Joram Barrez
 */
public class NodeBaseExecutionManager implements ExecutionManager {
  
  protected GraphDatabaseService graphDb;

    @Autowired
    private TaskNeoRepository taskNeoRepository;

    @Autowired
    private Neo4jTemplate template;

  public Execution getExecutionById(String id) {
    // return new NodeBasedExecution(graphDb.getRelationshipById(id));
    return new NodeBasedExecution();
  }

  public GraphDatabaseService getGraphDb() {
    return graphDb;
  }

  public void setGraphDb(GraphDatabaseService graphDb) {
    this.graphDb = graphDb;
  }
  

}
