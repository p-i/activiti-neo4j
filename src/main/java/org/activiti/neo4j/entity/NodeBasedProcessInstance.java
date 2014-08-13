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
import org.activiti.neo4j.Constants;
import org.activiti.neo4j.Execution;
import org.activiti.neo4j.ProcessInstance;
import org.activiti.neo4j.persistence.entity.TaskNodeNeo;
import org.activiti.neo4j.persistence.entity.TaskRelationship;
import org.activiti.neo4j.persistence.repository.TaskNeoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.activiti.neo4j.utils.Utils.notImplemented;

/**
 * @author Joram Barrez
 */
public class NodeBasedProcessInstance implements ProcessInstance {

    protected TaskNodeNeo processInstance;

    @Autowired
    private TaskNeoRepository taskNeoRepository;

    @Autowired
    private Neo4jTemplate template;

    public NodeBasedProcessInstance() {

    }

    /*
    public NodeBasedProcessInstance(Node processInstance) {
        this.processInstance = processInstance;
    }
    */

    public void setVariable(String variableName, Object variableValue) {
        TaskNodeNeo variableNode = getVariableNode();
        if (variableNode == null) {
            //variableNode = processInstance.getGraphDatabase().createNode();
            //processInstance.createRelationshipTo(variableNode, RelTypes.VARIABLE);

            variableNode = new TaskNodeNeo(Constants.TYPE_VARIABLE);
            template.createRelationshipBetween(
                    processInstance,
                    variableNode,
                    TaskRelationship.class,
                    Constants.REL_TYPE_VARIABLE, false);
        }

        // TODO: fix it
        //variableNode.setProperty(variableName, variableValue);
    }

    protected TaskNodeNeo getVariableNode() {
        // Iterator<Relationship> variableRelationShipIterator =  processInstance.getRelationships(Direction.OUTGOING, RelTypes.VARIABLE).iterator();
        Iterator<TaskRelationship> variableRelationShipIterator = processInstance.getOutgoingVariableRelationships().iterator();

        TaskNodeNeo variableNode = null;
        if (variableRelationShipIterator.hasNext()) {
            TaskRelationship variableRelationship = variableRelationShipIterator.next();
            variableNode = variableRelationship.getTo();
        }

        return variableNode;
    }

    public Execution createNewExecutionInActivity(Activity activity) {
        TaskRelationship executionRelationship = template.createRelationshipBetween(
                processInstance,
                activity.getNode(),
                TaskRelationship.class,
                Constants.REL_TYPE_EXECUTION,
                false);

        NodeBasedExecution nodeBasedExecution = new NodeBasedExecution();
        nodeBasedExecution.setRelationshipExecution(executionRelationship);
        return nodeBasedExecution;
    }

    public void delete() {
        // Executions
        for (Execution execution : getExecutions()) {
            execution.delete();
        }

        // Variables
        TaskNodeNeo variableNode = getVariableNode();
        if (variableNode != null) {
            template.delete(variableNode);
        }

        // Delete relationship from process definition to process instance
        for (TaskRelationship relationship : processInstance.getRelationShipsByType(Constants.REL_TYPE_PROCESS_INSTANCE)) {
            template.delete(relationship);
        }

        template.delete(processInstance);
    }

    public List<Execution> getExecutions() {
        List<Execution> executions = new ArrayList<Execution>();
        // TODO: return only outgoing relationships
        for (TaskRelationship executionRelationship : processInstance.getRelationShipsByType(Constants.REL_TYPE_EXECUTION)) {
            executions.add(new NodeBasedExecution(executionRelationship));
        }
        return executions;
    }


    @Override
    public ProcessInstance getProcessInstance() {
        notImplemented();
        return null;
    }

    @Override
    public Activity getActivity() {
        notImplemented();
        return null;
    }

    @Override
    public Object getVariable(String name) {
        notImplemented();
        return null;
    }

    @Override
    public void setRelationshipExecution(TaskRelationship executionRelationship) {
        notImplemented();
    }

    @Override
    public boolean hasProperty(String property) {
        notImplemented();
        return false;
    }

    @Override
    public Object getProperty(String property) {
        notImplemented();
        return null;
    }

    @Override
    public void setProperty(String property, Object value) {
        notImplemented();
    }

    @Override
    public Object removeProperty(String property) {
        notImplemented();
        return null;
    }

    @Override
    public void setProcessInstance(TaskNodeNeo processInstanceNode) {
        this.processInstance = processInstanceNode;
    }

}
