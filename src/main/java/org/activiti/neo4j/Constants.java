package org.activiti.neo4j;


public interface Constants {

    // Indexes
    String PROCESS_DEFINITION_INDEX = "processDefinitionsIndex";
    String TASK_INDEX = "task-index";

    // Index key
    String INDEX_KEY_PROCESS_DEFINITION_KEY = "processDefinitionKey";

    String INDEX_KEY_TASK_ASSIGNEE = "taskAssignee";
    String INDEX_KEY_TASK_TITLE = "taskTitle";
    String INDEX_KEY_TASK_TYPE = "taskType";
    String INDEX_KEY_TASK_CLASS = "taskClass";

    // Behavior types
    String TYPE_START_EVENT = "startEvent";
    String TYPE_END_EVENT = "endEvent";
    String TYPE_DEFINITION = "definitionNode";
    String TYPE_USER_TASK = "userTask";
    String TYPE_PARALLEL_GATEWAY = "parallelGateway";
    String TYPE_EXCLUSIVE_GATEWAY = "exclusiveGateway";
    String TYPE_SERVICE_TASK = "serviceTask";
    String TYPE_VARIABLE = "variableNode";

    //Relationship types
    String REL_TYPE_VARIABLE = "VARIABLE";
    String REL_TYPE_BELONGS_TO = "BELONGS_TO";
    String REL_TYPE_SEQ_FLOW = "SEQ_FLOW";
    String REL_TYPE_EXECUTION = "REL_TYPE_EXECUTION";
    String REL_TYPE_STARTS_IN = "STARTS_IN";
    String REL_TYPE_PROCESS_INSTANCE = "PROCESS_INSTANCE";

}
