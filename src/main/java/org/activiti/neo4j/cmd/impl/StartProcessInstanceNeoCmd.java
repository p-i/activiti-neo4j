package org.activiti.neo4j.cmd.impl;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.neo4j.CommandContextNeo4j;
import org.activiti.neo4j.Constants;
import org.activiti.neo4j.Execution;
import org.activiti.neo4j.cmd.ICommand;
import org.activiti.neo4j.persistence.entity.ExecutionNodeNeo;
import org.activiti.neo4j.persistence.entity.ProcessDefinitionNodeNeo;
import org.activiti.neo4j.persistence.entity.TaskNodeNeo;
import org.activiti.neo4j.persistence.entity.TaskRelationship;
import org.activiti.neo4j.persistence.repository.TaskNeoRepository;
import org.activiti.neo4j.persistence.repository.UserTaskNeoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Component;
import scala.collection.immutable.Stream;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by ilja on 05/08/14.
 */
@Component
public class StartProcessInstanceNeoCmd<T> implements ICommand<ProcessInstance>, Serializable {

    @Autowired
    private UserTaskNeoRepository userTaskNeoRepository;

    @Autowired
    private TaskNeoRepository taskNeoRepository;

    @Autowired
    private Neo4jTemplate template;

    @Autowired
    private Execution nodeBasedExecution;

    private String processDefinitionKey;

    public StartProcessInstanceNeoCmd() {}
    public StartProcessInstanceNeoCmd(String key) {
        this.processDefinitionKey = key;
    }

    /**
     * Execute an activiti-neo4j specific command within the context.
     *
     * @param commandContext
     */
    @Override
    public ProcessInstance execute(CommandContextNeo4j<ProcessInstance> commandContext) {
        // Find process definition node

        //Index<Node> processDefinitionIndex = graphDb.index().forNodes(Constants.PROCESS_DEFINITION_INDEX);
        ProcessDefinitionNodeNeo processDefinitionNode =
                (ProcessDefinitionNodeNeo) taskNeoRepository.findBySchemaPropertyValue("id", Constants.PROCESS_DEFINITION_INDEX);

        //Node startEventNode = processDefinitionNode.getRelationships(Direction.OUTGOING, RelTypes.IS_STARTED_FROM).iterator().next().getEndNode();
        TaskNodeNeo startEventNode = processDefinitionNode.getStart();

        // Create process instance node and link it to the process definition
        //Node processInstance = graphDb.createNode();
        ExecutionNodeNeo processInstanceNode = new ExecutionNodeNeo();
        processInstanceNode = template.save(processInstanceNode);
        processDefinitionNode.addProcessInstance(processInstanceNode);

        //processDefinitionNode.createRelationshipTo(processInstance, RelTypes.PROCESS_INSTANCE);


//        // Traverse the process definition
//        TraversalDescription traversalDescription = Traversal.description()
//                .breadthFirst()
//                .relationships( RelTypes.SEQ_FLOW, Direction.OUTGOING )
//                .evaluator(Evaluators.all());
//        Traverser traverser = traversalDescription.traverse(startEventNode);

        // Add one execution link to the startnode
        //Relationship relationShipExecution = processInstance.createRelationshipTo(startEventNode, RelTypes.REL_TYPE_EXECUTION);
        TaskRelationship relationShipExecution = template.createRelationshipBetween(
                processInstanceNode,
                startEventNode,
                TaskRelationship.class,
                Constants.REL_TYPE_EXECUTION,
                false);

        // Execute the process
//        Execution execution = new NodeBasedExecution(relationShipExecution);

        this.nodeBasedExecution.setRelationshipExecution(relationShipExecution);
        commandContext.continueProcess(this.nodeBasedExecution);

        return null;

    }

}
