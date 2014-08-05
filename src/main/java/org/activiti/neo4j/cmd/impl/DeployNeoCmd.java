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
import org.activiti.neo4j.cmd.ICommand;
import org.activiti.neo4j.helper.BpmnModelUtil;
import org.activiti.neo4j.helper.BpmnParser;
import org.activiti.neo4j.persistence.entity.*;
import org.activiti.neo4j.persistence.entity.TaskRelationship;
import org.activiti.neo4j.persistence.repository.TaskNeoRepository;
import org.activiti.neo4j.persistence.repository.UserTaskNeoRepository;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.SetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>Deployment command {@link org.activiti.engine.impl.cmd.DeployCmd DeployCmd}.
 * for the Neo4j
 */
@Component(value="deployCmd")
public class DeployNeoCmd<T> extends DeployCmd implements ICommand<Deployment>, Serializable {

    private Map<String, TaskNodeNeo> nodeMap;
    private Set<SequenceFlow> sequenceFlows;

    @Autowired
    private UserTaskNeoRepository userTaskNeoRepository;

    @Autowired
    private TaskNeoRepository taskNeoRepository;

    @Autowired
    private Neo4jTemplate template;

    public DeployNeoCmd(DeploymentBuilder deploymentBuilder) {
        super(deploymentBuilder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Deployment execute(CommandContextNeo4j commandContext) {
        DeploymentEntity deployment = deploymentBuilder.getDeployment();
        deployment.setDeploymentTime(commandContext.getProcessEngineConfiguration().getClock().getCurrentTime());

        Map<String, ResourceEntity> mapResources = deployment.getResources();

        if (null == mapResources) throw new RuntimeException("No resource was found!");

        // get all the resources (only bpmn 2.0 XML files)
        final Set<String> keySet = SetUtils.predicatedSet(mapResources.keySet(), new Predicate() {
            @Override
            public boolean evaluate(Object key) {
                return key.toString().endsWith(".bpmn20.xml") || key.toString().endsWith(".bpmn");
            }
        });

        // put each resource (process) to the underlying data store
        for (String resourceKey : keySet) {
            this._persistProcessInDatastore(deployment, resourceKey);
        }

        commandContext.setResult(deployment);

        return deployment;
    }

    /**
     * Process resource as BPMN model and put it to a datastore
     *
     * @param deployment
     * @param resourceKey
     */
    private void _persistProcessInDatastore(DeploymentEntity deployment, String resourceKey) {

        ResourceEntity resource = deployment.getResource(resourceKey);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(resource.getBytes());

        BpmnModel bpmnModel = BpmnParser.parse(inputStream);
        Process process = bpmnModel.getProcesses().get(0);

        nodeMap = new HashMap<String, TaskNodeNeo>();
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
        ProcessDefinitionNodeNeo processDefinitionNode = (ProcessDefinitionNodeNeo) taskNeoRepository.findBySchemaPropertyValue("id", Constants.PROCESS_DEFINITION_INDEX);
        if (processDefinitionNode == null) {
            processDefinitionNode = new ProcessDefinitionNodeNeo();
            processDefinitionNode = taskNeoRepository.save(processDefinitionNode);
        }

        // Create Node representation
        ProcessDefinition processDefinition = new ProcessDefinition();
        processDefinition.setId(processDefinitionNode.getId());
        processDefinition.setKey(process.getId());

        // Create relationship from process definition node to start event
        StartEvent startEvent = BpmnModelUtil.findFlowElementsOfType(process, StartEvent.class).get(0);
        TaskNodeNeo startEventNode = taskNeoRepository.findBySchemaPropertyValue("id", startEvent.getId());
        //TaskNodeNeo startEventNode = nodeMap.get(startEvent.getId());
        processDefinitionNode.addStartNode(startEventNode);
        taskNeoRepository.save(processDefinitionNode);

        // Add process definition to index
        //Index<Node> processDefinitionIndex = graphDb.index().forNodes(Constants.PROCESS_DEFINITION_INDEX);
        //processDefinitionIndex.putIfAbsent(processDefinitionNode, Constants.INDEX_KEY_PROCESS_DEFINITION_KEY, processDefinition.getKey());
    }


    private void addStartEvent(StartEvent startEvent) {
        TaskNodeNeo node = new TaskNodeNeo(startEvent.getId(), Constants.TYPE_START_EVENT);
        taskNeoRepository.save(node);
        nodeMap.put(node.getId(), node);
    }

    private void addEndEvent(EndEvent endEvent) {
        TaskNodeNeo node = new TaskNodeNeo(endEvent.getId(), Constants.TYPE_END_EVENT);
        taskNeoRepository.save(node);
        nodeMap.put(node.getId(), node);
    }

    private void addParallelGateway(ParallelGateway parallelGateway) {
        TaskNodeNeo node = new TaskNodeNeo(parallelGateway.getId(), Constants.TYPE_PARALLEL_GATEWAY);
        taskNeoRepository.save(node);
        nodeMap.put(node.getId(), node);
    }

    private void addExclusiveGateway(ExclusiveGateway exclusiveGateway) {
        ExclusiveGatewayNodeNeo exclusiveGatewayNode = new ExclusiveGatewayNodeNeo(exclusiveGateway);
        taskNeoRepository.save(exclusiveGatewayNode);
        nodeMap.put(exclusiveGatewayNode.getId(), exclusiveGatewayNode);
    }

    private void addUserTask(UserTask userTask) {
        UserTaskNodeNeo userTaskNode = new UserTaskNodeNeo(userTask);
        userTaskNeoRepository.save(userTaskNode);
        nodeMap.put(userTaskNode.getId(), userTaskNode);
    }

    private void addServiceTask(ServiceTask serviceTask) {
        ServiceTaskNodeNeo serviceTaskNode = new ServiceTaskNodeNeo(serviceTask);
        taskNeoRepository.save(serviceTaskNode);
        nodeMap.put(serviceTaskNode.getId(), serviceTaskNode);
    }

    private void processSequenceFlows() {
        for (SequenceFlow sequenceFlow : sequenceFlows) {

            TaskNodeNeo sourceNode = nodeMap.get(sequenceFlow.getSourceRef());
            TaskNodeNeo targetNode = nodeMap.get(sequenceFlow.getTargetRef());
/*
            TaskRelationship relationship = new TaskRelationship();
            relationship.setFrom(sourceNode);
            relationship.setTo(targetNode);
            relationship.setId(sequenceFlow.getId());
            relationship.setCondition(sequenceFlow.getConditionExpression());

            template.save(relationship);*/

            System.out.println(">> make relationshit between " + sourceNode + " and " + targetNode);
            TaskRelationship role = template.createRelationshipBetween(sourceNode, targetNode, TaskRelationship.class, "ACTS_IN", false);
        }
    }
}