package org.activiti.neo4j.services;

import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.repository.DeploymentBuilderImpl;
import org.activiti.engine.repository.*;
import org.activiti.engine.task.IdentityLink;
import org.activiti.neo4j.*;
import org.activiti.neo4j.ProcessDefinition;
import org.activiti.neo4j.cmd.ICommand;
import org.activiti.neo4j.cmd.impl.DeployNeoCmd;
import org.activiti.neo4j.helper.BpmnModelUtil;
import org.activiti.neo4j.helper.BpmnParser;
import org.activiti.validation.ValidationError;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.index.Index;

import java.io.InputStream;
import java.util.*;

import static org.activiti.neo4j.utils.Utils.notImplemented;

public class RepositoryServiceNeo4jImpl implements RepositoryService {

    protected GraphDatabaseService graphDb;
    protected CommandExecutorNeo4j commandExecutor;

    protected Map<String, Node> nodeMap;
    protected Set<SequenceFlow> sequenceFlows;

    public RepositoryServiceNeo4jImpl(GraphDatabaseService graphDb, CommandExecutorNeo4j commandExecutor) {
        this.graphDb = graphDb;
        this.commandExecutor = commandExecutor;
    }

    @Override
    public Deployment deploy(DeploymentBuilder deploymentBuilder) {
        ICommand<Deployment> deploy = new DeployNeoCmd<Deployment>(deploymentBuilder, this.graphDb);
        return commandExecutor.execute(deploy);
    }

    @Override
    public DeploymentBuilder createDeployment() {
        return new DeploymentBuilderImpl(this);
    }

    @Override
    public void deleteDeployment(String deploymentId) {
        notImplemented();
    }

    @Override
    public void deleteDeploymentCascade(String deploymentId) {
        notImplemented();
    }

    @Override
    public void deleteDeployment(String deploymentId, boolean cascade) {
        notImplemented();
    }

    @Override
    public void setDeploymentCategory(String deploymentId, String category) {
        notImplemented();
    }

    @Override
    public List<String> getDeploymentResourceNames(String deploymentId) {
        notImplemented();
        return null;
    }

    @Override
    public InputStream getResourceAsStream(String deploymentId, String resourceName) {
        notImplemented();
        return null;
    }

    @Override
    public void changeDeploymentTenantId(String deploymentId, String newTenantId) {
        notImplemented();
    }

    @Override
    public ProcessDefinitionQuery createProcessDefinitionQuery() {
        notImplemented();
        return null;
    }

    @Override
    public NativeProcessDefinitionQuery createNativeProcessDefinitionQuery() {
        notImplemented();
        return null;
    }

    @Override
    public DeploymentQuery createDeploymentQuery() {
        notImplemented();
        return null;
    }

    @Override
    public NativeDeploymentQuery createNativeDeploymentQuery() {
        notImplemented();
        return null;
    }

    @Override
    public void suspendProcessDefinitionById(String processDefinitionId) {
        notImplemented();
    }

    @Override
    public void suspendProcessDefinitionById(String processDefinitionId, boolean suspendProcessInstances, Date suspensionDate) {
        notImplemented();
    }

    @Override
    public void suspendProcessDefinitionByKey(String processDefinitionKey) {
        notImplemented();
    }

    @Override
    public void suspendProcessDefinitionByKey(String processDefinitionKey, boolean suspendProcessInstances, Date suspensionDate) {
        notImplemented();
    }

    @Override
    public void suspendProcessDefinitionByKey(String processDefinitionKey, String tenantId) {
        notImplemented();
    }

    @Override
    public void suspendProcessDefinitionByKey(String processDefinitionKey, boolean suspendProcessInstances, Date suspensionDate, String tenantId) {
        notImplemented();
    }

    @Override
    public void activateProcessDefinitionById(String processDefinitionId) {
        notImplemented();
    }

    @Override
    public void activateProcessDefinitionById(String processDefinitionId, boolean activateProcessInstances, Date activationDate) {
        notImplemented();
    }

    @Override
    public void activateProcessDefinitionByKey(String processDefinitionKey) {
        notImplemented();
    }

    @Override
    public void activateProcessDefinitionByKey(String processDefinitionKey, boolean activateProcessInstances, Date activationDate) {
        notImplemented();
    }

    @Override
    public void activateProcessDefinitionByKey(String processDefinitionKey, String tenantId) {
        notImplemented();
    }

    @Override
    public void activateProcessDefinitionByKey(String processDefinitionKey, boolean activateProcessInstances, Date activationDate, String tenantId) {
        notImplemented();
    }

    @Override
    public void setProcessDefinitionCategory(String processDefinitionId, String category) {
        notImplemented();
    }

    @Override
    public InputStream getProcessModel(String processDefinitionId) {
        notImplemented();
        return null;
    }

    @Override
    public InputStream getProcessDiagram(String processDefinitionId) {
        notImplemented();
        return null;
    }

    @Override
    public org.activiti.engine.repository.ProcessDefinition getProcessDefinition(String processDefinitionId) {
        notImplemented();
        return null;
    }

    @Override
    public BpmnModel getBpmnModel(String processDefinitionId) {
        notImplemented();
        return null;
    }

    @Override
    public DiagramLayout getProcessDiagramLayout(String processDefinitionId) {
        notImplemented();
        return null;
    }

    @Override
    public Model newModel() {
        notImplemented();
        return null;
    }

    @Override
    public void saveModel(Model model) {
        notImplemented();
    }

    @Override
    public void deleteModel(String modelId) {
        notImplemented();
    }

    @Override
    public void addModelEditorSource(String modelId, byte[] bytes) {
        notImplemented();
    }

    @Override
    public void addModelEditorSourceExtra(String modelId, byte[] bytes) {
        notImplemented();
    }

    @Override
    public ModelQuery createModelQuery() {
        notImplemented();
        return null;
    }

    @Override
    public NativeModelQuery createNativeModelQuery() {
        notImplemented();
        return null;
    }

    @Override
    public Model getModel(String modelId) {
        notImplemented();
        return null;
    }

    @Override
    public byte[] getModelEditorSource(String modelId) {
        notImplemented();
        return new byte[0];
    }

    @Override
    public byte[] getModelEditorSourceExtra(String modelId) {
        notImplemented();
        return new byte[0];
    }

    @Override
    public void addCandidateStarterUser(String processDefinitionId, String userId) {
        notImplemented();
    }

    @Override
    public void addCandidateStarterGroup(String processDefinitionId, String groupId) {
        notImplemented();
    }

    @Override
    public void deleteCandidateStarterUser(String processDefinitionId, String userId) {
        notImplemented();
    }

    @Override
    public void deleteCandidateStarterGroup(String processDefinitionId, String groupId) {
        notImplemented();
    }

    @Override
    public List<IdentityLink> getIdentityLinksForProcessDefinition(String processDefinitionId) {
        notImplemented();
        return null;
    }

    @Override
    public List<ValidationError> validateProcess(BpmnModel bpmnModel) {
        notImplemented();
        return null;
    }
}