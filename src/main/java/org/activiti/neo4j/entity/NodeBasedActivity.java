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
import org.activiti.neo4j.SequenceFlow;
import org.activiti.neo4j.persistence.entity.TaskNodeNeo;
import org.activiti.neo4j.persistence.entity.TaskRelationship;
import org.apache.commons.beanutils.BeanMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.activiti.neo4j.utils.Utils.notImplemented;


/**
 * @author Joram Barrez
 */
public class NodeBasedActivity implements Activity {

    private TaskNodeNeo activityNode;

    protected List<SequenceFlow> incomingSequenceFlows;
    protected List<SequenceFlow> outgoingSequenceFlows;

    //@Autowired
    //private Neo4jTemplate template;

    public NodeBasedActivity(TaskNodeNeo activityNode) {
        this.activityNode = activityNode;
    }

    public List<SequenceFlow> getOutgoingSequenceFlow() {

        if (outgoingSequenceFlows == null) {

            outgoingSequenceFlows = new ArrayList<SequenceFlow>();
            for (TaskRelationship sequenceFlowRelationship : activityNode.getOutgoingRelationships()) {
                outgoingSequenceFlows.add(new NodeBasedSequenceFlow(sequenceFlowRelationship));
            }
        }
        return outgoingSequenceFlows;
    }

    public List<SequenceFlow> getIncomingSequenceFlow() {
        if (incomingSequenceFlows == null) {
            incomingSequenceFlows = new ArrayList<SequenceFlow>();
            for (TaskRelationship sequenceFlowRelationship : activityNode.getIncomingRelationships()) {
                incomingSequenceFlows.add(new NodeBasedSequenceFlow(sequenceFlowRelationship));
            }
        }
        return incomingSequenceFlows;
    }

    public String getId() {
        return activityNode.getId();
    }

    public Object getProperty(String property) {
        // TODO: cache this map avoiding to re-create it every time this method is called
        Map<String, Object> introspected = new BeanMap(activityNode);
        return introspected.get(property);
    }

    public boolean hasProperty(String property) {
        notImplemented();
        return false;
        //return activityNode.hasProperty(property);
    }

    public void setProperty(String property, Object value) {
        notImplemented();
        //activityNode.setProperty(property, value);
    }

    public Object removeProperty(String property) {
        notImplemented();
        return null;
        //return activityNode.removeProperty(property);
    }

    @Override
    public void setActivityNode(TaskNodeNeo activityNode) {
        this.activityNode = activityNode;
    }

    @Override
    public TaskNodeNeo getNode() {
        return this.activityNode;
    }
}
