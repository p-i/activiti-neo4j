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
package org.activiti.neo4j.entity;

import org.activiti.neo4j.Activity;
import org.activiti.neo4j.Execution;
import org.activiti.neo4j.ProcessInstance;
import org.activiti.neo4j.RelTypes;
import org.activiti.neo4j.persistence.entity.TaskNodeNeo;
import org.activiti.neo4j.persistence.entity.TaskRelationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;

/**
 * @author Joram Barrez
 */
public class NodeBasedExecution implements Execution {

    // An execution is actually a thin wrapper around a Neo4j relationship
    // adding al sorts of convenience methods that hide the internal bits
    protected TaskRelationship relationship;

    @Autowired
    protected NodeBasedProcessInstance processInstance;

    protected NodeBasedActivity activity;

    @Autowired
    private Neo4jTemplate template;

    public NodeBasedExecution() {


    }

    public NodeBasedExecution(TaskRelationship relationship) {
        this.setRelationshipExecution(relationship);
    }

    public ProcessInstance getProcessInstance() {
        this.processInstance.setProcessInstance(this.relationship.getTo());
        return this.processInstance;
    }

    public Activity getActivity() {
        if (activity == null) {
            activity = new NodeBasedActivity(this.relationship.getTo());
        }
        return this.activity;
    }

    public void setVariable(String variableName, Object variableValue) {

        // TODO: need to have variable local, which is a bit trickier,
        // since executions are relationships.
        // Perhaps this needs to be revised

        //Node processInstanceNode = getProcessInstanceNode();

        // Check if the variable node already exists
    /*Iterator<Relationship> variableRelationShipIterator = processInstanceNode.getRelationships(Direction.OUTGOING, RelTypes.VARIABLE).iterator();



    Node variableNode = null;
    if (variableRelationShipIterator.hasNext()) {
      Relationship variableRelationship = variableRelationShipIterator.next();
      variableNode = variableRelationship.getEndNode();
    } else {
      variableNode = processInstanceNode.getGraphDatabase().createNode();
      processInstanceNode.createRelationshipTo(variableNode, RelTypes.VARIABLE);
    }
    
    variableNode.setProperty(variableName, variableValue);
*/
        TaskNodeNeo endNode = relationship.getTo();
        // TODO: save variable to the node
    }

    public Object getVariable(String variableName) {
        // TODO: implement me
    /*Node processInstanceNode = getProcessInstanceNode();
    Iterator<Relationship> variableRelationshipIterator = processInstanceNode.getRelationships(RelTypes.VARIABLE).iterator();
    
    if (variableRelationshipIterator.hasNext()) {
      Node variableNode = variableRelationshipIterator.next().getEndNode();
      return variableNode.getProperty(variableName);
    } else {
      // No variable associated with this process instance
      return null;
    }*/

        return null;
    }

    public Object getProperty(String property) {
        // return relationship.getProperty(property);
        return null;
    }

    public boolean hasProperty(String property) {
        // return relationship.hasProperty(property);
        return true;
    }

    public void setProperty(String property, Object value) {
        //relationship.setProperty(property, value);
    }


    public void delete() {
        // Delete actual execution relationship
        template.delete(relationship);
    }

    public Object removeProperty(String property) {
        //return relationship.removeProperty(property);
        return null;
    }

    @Override
    public void setRelationshipExecution(TaskRelationship executionRelationship) {
        this.relationship = executionRelationship;
    }
}
