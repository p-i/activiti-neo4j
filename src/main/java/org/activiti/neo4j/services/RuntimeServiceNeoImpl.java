package org.activiti.neo4j.services;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.*;
import org.activiti.engine.task.Event;
import org.activiti.engine.task.IdentityLink;
import org.activiti.neo4j.CommandExecutorNeo4j;
import org.activiti.neo4j.cmd.ICommand;
import org.activiti.neo4j.cmd.impl.StartProcessInstanceNeoCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.activiti.neo4j.utils.Utils.notImplemented;

public class RuntimeServiceNeoImpl implements RuntimeService {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private StartProcessInstanceNeoCmd<ProcessInstance> startProcessInstanceCmd;

  protected CommandExecutorNeo4j commandExecutor;

  public RuntimeServiceNeoImpl(CommandExecutorNeo4j commandExecutor) {
    this.commandExecutor =  commandExecutor;
  }

    /**
     * {@inheritDoc}
     *
     * @param processDefinitionKey
     * @return
     */
    @Override
    public ProcessInstance startProcessInstanceByKey(final String processDefinitionKey) {
        return commandExecutor.execute(startProcessInstanceCmd);
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public ProcessInstance startProcessInstanceByKey(String s, String s2) {
        notImplemented();
        return null;
    }


    /**
     * {@inheritDoc}
     **/
    @Override
    public ProcessInstance startProcessInstanceByKey(String s, Map<String, Object> stringObjectMap) {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public ProcessInstance startProcessInstanceByKey(String s, String s2, Map<String, Object> stringObjectMap) {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public ProcessInstance startProcessInstanceByKeyAndTenantId(String s, String s2) {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public ProcessInstance startProcessInstanceByKeyAndTenantId(String s, String s2, String s3) {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public ProcessInstance startProcessInstanceByKeyAndTenantId(String s, Map<String, Object> stringObjectMap, String s2) {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public ProcessInstance startProcessInstanceByKeyAndTenantId(String s, String s2, Map<String, Object> stringObjectMap, String s3) {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public ProcessInstance startProcessInstanceById(String s) {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public ProcessInstance startProcessInstanceById(String s, String s2) {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public ProcessInstance startProcessInstanceById(String s, Map<String, Object> stringObjectMap) {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public ProcessInstance startProcessInstanceById(String s, String s2, Map<String, Object> stringObjectMap) {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public ProcessInstance startProcessInstanceByMessage(String s) {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public ProcessInstance startProcessInstanceByMessageAndTenantId(String s, String s2) {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public ProcessInstance startProcessInstanceByMessage(String s, String s2) {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public ProcessInstance startProcessInstanceByMessageAndTenantId(String s, String s2, String s3) {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public ProcessInstance startProcessInstanceByMessage(String s, Map<String, Object> stringObjectMap) {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public ProcessInstance startProcessInstanceByMessageAndTenantId(String s, Map<String, Object> stringObjectMap, String s2) {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public ProcessInstance startProcessInstanceByMessage(String s, String s2, Map<String, Object> stringObjectMap) {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public ProcessInstance startProcessInstanceByMessageAndTenantId(String s, String s2, Map<String, Object> stringObjectMap, String s3) {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void deleteProcessInstance(String s, String s2) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public List<String> getActiveActivityIds(String s) {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void signal(String s) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void signal(String s, Map<String, Object> stringObjectMap) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void updateBusinessKey(String s, String s2) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void addUserIdentityLink(String s, String s2, String s3) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void addGroupIdentityLink(String s, String s2, String s3) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void addParticipantUser(String s, String s2) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void addParticipantGroup(String s, String s2) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void deleteParticipantUser(String s, String s2) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void deleteParticipantGroup(String s, String s2) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void deleteUserIdentityLink(String s, String s2, String s3) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void deleteGroupIdentityLink(String s, String s2, String s3) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public List<IdentityLink> getIdentityLinksForProcessInstance(String s) {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public Map<String, Object> getVariables(String s) {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public Map<String, Object> getVariablesLocal(String s) {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public Map<String, Object> getVariables(String s, Collection<String> strings) {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public Map<String, Object> getVariablesLocal(String s, Collection<String> strings) {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public Object getVariable(String s, String s2) {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public boolean hasVariable(String s, String s2) {
        notImplemented();
        return false;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public Object getVariableLocal(String s, String s2) {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public boolean hasVariableLocal(String s, String s2) {
        notImplemented();
        return false;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void setVariable(String s, String s2, Object o) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void setVariableLocal(String s, String s2, Object o) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void setVariables(String s, Map<String, ? extends Object> stringMap) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void setVariablesLocal(String s, Map<String, ? extends Object> stringMap) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void removeVariable(String s, String s2) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void removeVariableLocal(String s, String s2) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void removeVariables(String s, Collection<String> strings) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void removeVariablesLocal(String s, Collection<String> strings) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public ExecutionQuery createExecutionQuery() {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public NativeExecutionQuery createNativeExecutionQuery() {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public ProcessInstanceQuery createProcessInstanceQuery() {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public NativeProcessInstanceQuery createNativeProcessInstanceQuery() {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void suspendProcessInstanceById(String s) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void activateProcessInstanceById(String s) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void signalEventReceived(String s) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void signalEventReceivedWithTenantId(String s, String s2) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void signalEventReceivedAsync(String s) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void signalEventReceivedAsyncWithTenantId(String s, String s2) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void signalEventReceived(String s, Map<String, Object> stringObjectMap) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void signalEventReceivedWithTenantId(String s, Map<String, Object> stringObjectMap, String s2) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void signalEventReceived(String s, String s2) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void signalEventReceived(String s, String s2, Map<String, Object> stringObjectMap) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void signalEventReceivedAsync(String s, String s2) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void messageEventReceived(String s, String s2) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void messageEventReceived(String s, String s2, Map<String, Object> stringObjectMap) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void messageEventReceivedAsync(String s, String s2) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void addEventListener(ActivitiEventListener activitiEventListener) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void addEventListener(ActivitiEventListener activitiEventListener, ActivitiEventType... activitiEventTypes) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void removeEventListener(ActivitiEventListener activitiEventListener) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void dispatchEvent(ActivitiEvent activitiEvent) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void setProcessInstanceName(String s, String s2) {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public List<Event> getProcessInstanceEvents(String s) {
        notImplemented();
        return null;
    }

}
