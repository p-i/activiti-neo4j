package org.activiti.neo4j.cmd.impl;

import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.impl.cmd.DeployCmd;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.ResourceEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.neo4j.CommandContextNeo4j;
import org.activiti.neo4j.Constants;
import org.activiti.neo4j.ProcessDefinition;
import org.activiti.neo4j.RelTypes;
import org.activiti.neo4j.cmd.ICommand;
import org.activiti.neo4j.helper.BpmnModelUtil;
import org.activiti.neo4j.helper.BpmnParser;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.index.Index;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>Deployment command {@link org.activiti.engine.impl.cmd.DeployCmd DeployCmd}.
 * for the Neo4j
 */
public class DeployNeoCmd<T> extends DeployCmd implements ICommand<Deployment> {

    private Map<String, Node> nodeMap;
    private Set<SequenceFlow> sequenceFlows;
    private final GraphDatabaseService graphDb;

    public DeployNeoCmd(DeploymentBuilder deploymentBuilder, GraphDatabaseService db) {
        super(deploymentBuilder);
        this.graphDb = db;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Deployment execute(CommandContextNeo4j commandContext) {
        DeploymentEntity deployment = deploymentBuilder.getDeployment();
        deployment.setDeploymentTime(commandContext.getProcessEngineConfiguration().getClock().getCurrentTime());

        // TODO: Remove the hardcode
        ResourceEntity resource = deployment.getResource("one-task-process.bpmn20.xml");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(resource.getBytes());

        BpmnModel bpmnModel = BpmnParser.parse(inputStream);
        Process process = bpmnModel.getProcesses().get(0);

        // Create Node representation
        ProcessDefinition processDefinition = null;

        nodeMap = new HashMap<String, Node>();
        sequenceFlows = new HashSet<SequenceFlow>();
        for (FlowElement flowElement : process.getFlowElements()) {
            if (flowElement instanceof StartEvent) {
                addStartEvent((StartEvent) flowElement);
            } else if (flowElement instanceof UserTask) {
                addUserTask((UserTask) flowElement);
            } else if (flowElement instanceof EndEvent) {
                addEndEvent((EndEvent) flowElement);
            } else if (flowElement instanceof ParallelGateway) {
                addParallelGateway((ParallelGateway) flowElement);
            } else if (flowElement instanceof ExclusiveGateway) {
                addExclusiveGateway((ExclusiveGateway) flowElement);
            } else if (flowElement instanceof ServiceTask) {
                addServiceTask((ServiceTask) flowElement);
            } else if (flowElement instanceof SequenceFlow) {
                sequenceFlows.add((SequenceFlow) flowElement);
            }
        }
        processSequenceFlows();

        // Create process definition node
        Node processDefinitionNode = graphDb.createNode();
        processDefinition = new ProcessDefinition();
        processDefinition.setId(processDefinitionNode.getId());
        processDefinition.setKey(process.getId());

        // Temporary (for visualization)
        //graphDb.getReferenceNode().createRelationshipTo(processDefinitionNode, RelTypes.PROCESS_DEFINITION);

        // Create relationship from process definition node to start event
        StartEvent startEvent = BpmnModelUtil.findFlowElementsOfType(process, StartEvent.class).get(0);
        Node startEventNode = nodeMap.get(startEvent.getId());
        processDefinitionNode.createRelationshipTo(startEventNode, RelTypes.IS_STARTED_FROM);

        // Add process definition to index
        Index<Node> processDefinitionIndex = graphDb.index().forNodes(Constants.PROCESS_DEFINITION_INDEX);
        processDefinitionIndex.putIfAbsent(processDefinitionNode, Constants.INDEX_KEY_PROCESS_DEFINITION_KEY, processDefinition.getKey());

        commandContext.setResult(deployment);

        return deployment;
    }


    private void addStartEvent(StartEvent startEvent) {
        Node startEventNode = createNode(startEvent);
        startEventNode.setProperty("type", Constants.TYPE_START_EVENT);
    }

    private void addEndEvent(EndEvent endEvent) {
        Node endEventNode = createNode(endEvent);
        endEventNode.setProperty("type", Constants.TYPE_END_EVENT);
    }

    private void addParallelGateway(ParallelGateway parallelGateway) {
        Node parallelGwNode = createNode(parallelGateway);
        parallelGwNode.setProperty("type", Constants.TYPE_PARALLEL_GATEWAY);
    }

    private void addExclusiveGateway(ExclusiveGateway exclusiveGateway) {
        Node exclusiveGwNode = createNode(exclusiveGateway);
        exclusiveGwNode.setProperty("type", Constants.TYPE_EXCLUSIVE_GATEWAY);

        if (exclusiveGateway.getDefaultFlow() != null) {
            exclusiveGwNode.setProperty("defaultFlow", exclusiveGateway.getDefaultFlow());
        }
    }

    private void addUserTask(UserTask userTask) {
        Node userTaskNode = createNode(userTask);
        userTaskNode.setProperty("type", Constants.TYPE_USER_TASK);

        if (userTask.getName() != null) {
            userTaskNode.setProperty("name", userTask.getName());
        }

        if (userTask.getAssignee() != null) {
            userTaskNode.setProperty("assignee", userTask.getAssignee());
        }
    }

    private void addServiceTask(ServiceTask serviceTask) {
        Node serviceTaskNode = createNode(serviceTask);
        serviceTaskNode.setProperty("type", Constants.TYPE_SERVICE_TASK);
        serviceTaskNode.setProperty("class", serviceTask.getImplementation());
    }

    private Node createNode(FlowNode flowNode) {
        Node node = graphDb.createNode();
        node.setProperty("id", flowNode.getId());

        nodeMap.put(flowNode.getId(), node);

        return node;
    }

    private void processSequenceFlows() {
        for (SequenceFlow sequenceFlow : sequenceFlows) {
            Node sourceNode = nodeMap.get(sequenceFlow.getSourceRef());
            Node targetNode = nodeMap.get(sequenceFlow.getTargetRef());

            Relationship sequenceflowRelationship = sourceNode.createRelationshipTo(targetNode, RelTypes.SEQ_FLOW);
            sequenceflowRelationship.setProperty("id", sequenceFlow.getId());
            if (sequenceFlow.getConditionExpression() != null) {
                sequenceflowRelationship.setProperty("condition", sequenceFlow.getConditionExpression());
            }
        }
    }
}