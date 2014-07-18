package org.activiti.neo4j.services;

import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.*;
import org.activiti.engine.task.IdentityLink;
import org.activiti.neo4j.*;
import org.activiti.neo4j.ProcessDefinition;
import org.activiti.neo4j.cmd.ICommand;
import org.activiti.neo4j.cmd.impl.DeployNeoCmd;
import org.activiti.neo4j.helper.BpmnModelUtil;
import org.activiti.neo4j.helper.BpmnParser;
import org.activiti.neo4j.helper.DeploymentBuilderNeo4jImpl;
import org.activiti.validation.ValidationError;
import org.apache.commons.lang.NotImplementedException;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.index.Index;

import java.io.InputStream;
import java.util.*;

public class RepositoryServiceNeo4jImpl implements RepositoryService {

  protected GraphDatabaseService graphDb;
  protected CommandExecutorNeo4j commandExecutor;

  protected Map<String, Node> nodeMap;
  protected Set<SequenceFlow> sequenceFlows;

  public RepositoryServiceNeo4jImpl(GraphDatabaseService graphDb, CommandExecutorNeo4j commandExecutor) {
    this.graphDb = graphDb;
    this.commandExecutor = commandExecutor;
  }

    public Deployment deploy(DeploymentBuilder deploymentBuilder) {
        ICommand<Deployment> deploy = new DeployNeoCmd<Deployment>(deploymentBuilder);
        return commandExecutor.execute(deploy);
    }

  public ProcessDefinition deploy(final InputStream inputStream) {

    return commandExecutor.execute(new ICommand<ProcessDefinition>() {

      public ProcessDefinition execute(CommandContextNeo4j<ProcessDefinition> commandContext) {
          Process process = null;
          BpmnModel bpmnModel = BpmnParser.parse(inputStream);

          // TODO: move the below stuff to a parser / behaviour / BPMNParseHandler thingy

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

          commandContext.setResult(processDefinition);

          return processDefinition;
      }
    });
  }
  protected void addStartEvent(StartEvent startEvent) {
    Node startEventNode = createNode(startEvent);
    startEventNode.setProperty("type", Constants.TYPE_START_EVENT);
  }

  protected void addEndEvent(EndEvent endEvent) {
    Node endEventNode = createNode(endEvent);
    endEventNode.setProperty("type", Constants.TYPE_END_EVENT);
  }

  protected void addParallelGateway(ParallelGateway parallelGateway) {
    Node parallelGwNode = createNode(parallelGateway);
    parallelGwNode.setProperty("type", Constants.TYPE_PARALLEL_GATEWAY);
  }
  
  protected void addExclusiveGateway(ExclusiveGateway exclusiveGateway) {
    Node exclusiveGwNode = createNode(exclusiveGateway);
    exclusiveGwNode.setProperty("type", Constants.TYPE_EXCLUSIVE_GATEWAY);
    
    if (exclusiveGateway.getDefaultFlow() != null) {
      exclusiveGwNode.setProperty("defaultFlow", exclusiveGateway.getDefaultFlow());
    }
  }

  protected void addUserTask(UserTask userTask) {
    Node userTaskNode = createNode(userTask);
    userTaskNode.setProperty("type", Constants.TYPE_USER_TASK);

    if (userTask.getName() != null) {
      userTaskNode.setProperty("name", userTask.getName());
    }

    if (userTask.getAssignee() != null) {
      userTaskNode.setProperty("assignee", userTask.getAssignee());
    }
  }

  protected void addServiceTask(ServiceTask serviceTask) {
    Node serviceTaskNode = createNode(serviceTask);
    serviceTaskNode.setProperty("type", Constants.TYPE_SERVICE_TASK);
    serviceTaskNode.setProperty("class", serviceTask.getImplementation());
  }
  
  protected Node createNode(FlowNode flowNode) {
    Node node = graphDb.createNode();
    node.setProperty("id", flowNode.getId());
    
    nodeMap.put(flowNode.getId(), node);
    
    return node;
  }

  protected void processSequenceFlows() {
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


    @Override
    public DeploymentBuilder createDeployment() {
        return new DeploymentBuilderNeo4jImpl(this);
    }

    @Override
    public void deleteDeployment(String deploymentId) {
        _notImplemented();
    }

    @Override
    public void deleteDeploymentCascade(String deploymentId) {
        _notImplemented();
    }

    @Override
    public void deleteDeployment(String deploymentId, boolean cascade) {
        _notImplemented();
    }

    @Override
    public void setDeploymentCategory(String deploymentId, String category) {
        _notImplemented();
    }

    @Override
    public List<String> getDeploymentResourceNames(String deploymentId) {
        _notImplemented();
        return null;
    }

    @Override
    public InputStream getResourceAsStream(String deploymentId, String resourceName) {
        _notImplemented();
        return null;
    }

    @Override
    public void changeDeploymentTenantId(String deploymentId, String newTenantId) {
        _notImplemented();
    }

    @Override
    public ProcessDefinitionQuery createProcessDefinitionQuery() {
        _notImplemented();
        return null;
    }

    @Override
    public NativeProcessDefinitionQuery createNativeProcessDefinitionQuery() {
        _notImplemented();
        return null;
    }

    @Override
    public DeploymentQuery createDeploymentQuery() {
        _notImplemented();
        return null;
    }

    @Override
    public NativeDeploymentQuery createNativeDeploymentQuery() {
        _notImplemented();
        return null;
    }

    @Override
    public void suspendProcessDefinitionById(String processDefinitionId) {
        _notImplemented();
    }

    @Override
    public void suspendProcessDefinitionById(String processDefinitionId, boolean suspendProcessInstances, Date suspensionDate) {
        _notImplemented();
    }

    @Override
    public void suspendProcessDefinitionByKey(String processDefinitionKey) {
        _notImplemented();
    }

    @Override
    public void suspendProcessDefinitionByKey(String processDefinitionKey, boolean suspendProcessInstances, Date suspensionDate) {
        _notImplemented();
    }

    @Override
    public void suspendProcessDefinitionByKey(String processDefinitionKey, String tenantId) {
        _notImplemented();
    }

    @Override
    public void suspendProcessDefinitionByKey(String processDefinitionKey, boolean suspendProcessInstances, Date suspensionDate, String tenantId) {
        _notImplemented();
    }

    @Override
    public void activateProcessDefinitionById(String processDefinitionId) {
        _notImplemented();
    }

    @Override
    public void activateProcessDefinitionById(String processDefinitionId, boolean activateProcessInstances, Date activationDate) {
        _notImplemented();
    }

    @Override
    public void activateProcessDefinitionByKey(String processDefinitionKey) {
        _notImplemented();
    }

    @Override
    public void activateProcessDefinitionByKey(String processDefinitionKey, boolean activateProcessInstances, Date activationDate) {
        _notImplemented();
    }

    @Override
    public void activateProcessDefinitionByKey(String processDefinitionKey, String tenantId) {
        _notImplemented();
    }

    @Override
    public void activateProcessDefinitionByKey(String processDefinitionKey, boolean activateProcessInstances, Date activationDate, String tenantId) {
        _notImplemented();
    }

    @Override
    public void setProcessDefinitionCategory(String processDefinitionId, String category) {
        _notImplemented();
    }

    @Override
    public InputStream getProcessModel(String processDefinitionId) {
        _notImplemented();
        return null;
    }

    @Override
    public InputStream getProcessDiagram(String processDefinitionId) {
        _notImplemented();
        return null;
    }

    @Override
    public org.activiti.engine.repository.ProcessDefinition getProcessDefinition(String processDefinitionId) {
        _notImplemented();
        return null;
    }

    @Override
    public BpmnModel getBpmnModel(String processDefinitionId) {
        _notImplemented();
        return null;
    }

    @Override
    public DiagramLayout getProcessDiagramLayout(String processDefinitionId) {
        _notImplemented();
        return null;
    }

    @Override
    public Model newModel() {
        _notImplemented();
        return null;
    }

    @Override
    public void saveModel(Model model) {
        _notImplemented();
    }

    @Override
    public void deleteModel(String modelId) {
        _notImplemented();
    }

    @Override
    public void addModelEditorSource(String modelId, byte[] bytes) {
        _notImplemented();
    }

    @Override
    public void addModelEditorSourceExtra(String modelId, byte[] bytes) {
        _notImplemented();
    }

    @Override
    public ModelQuery createModelQuery() {
        _notImplemented();
        return null;
    }

    @Override
    public NativeModelQuery createNativeModelQuery() {
        _notImplemented();
        return null;
    }

    @Override
    public Model getModel(String modelId) {
        _notImplemented();
        return null;
    }

    @Override
    public byte[] getModelEditorSource(String modelId) {
        _notImplemented();
        return new byte[0];
    }

    @Override
    public byte[] getModelEditorSourceExtra(String modelId) {
        _notImplemented();
        return new byte[0];
    }

    @Override
    public void addCandidateStarterUser(String processDefinitionId, String userId) {
        _notImplemented();
    }

    @Override
    public void addCandidateStarterGroup(String processDefinitionId, String groupId) {
        _notImplemented();
    }

    @Override
    public void deleteCandidateStarterUser(String processDefinitionId, String userId) {
        _notImplemented();
    }

    @Override
    public void deleteCandidateStarterGroup(String processDefinitionId, String groupId) {
        _notImplemented();
    }

    @Override
    public List<IdentityLink> getIdentityLinksForProcessDefinition(String processDefinitionId) {
        _notImplemented();
        return null;
    }

    @Override
    public List<ValidationError> validateProcess(BpmnModel bpmnModel) {
        _notImplemented();
        return null;
    }

    private void _notImplemented() {
        throw new NotImplementedException("The method is not implemented in " + this.getClass().getSimpleName());
    }
}
