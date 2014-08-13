package org.activiti.neo4j.persistence.repository;

import org.activiti.neo4j.persistence.entity.TaskNodeNeo;
import org.activiti.neo4j.persistence.entity.UserTaskNodeNeo;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.NamedIndexRepository;
import org.springframework.data.neo4j.repository.SchemaIndexRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by ilja on 25/07/14.
 */
public interface TaskNeoRepository extends GraphRepository<TaskNodeNeo>, SchemaIndexRepository<TaskNodeNeo> {

    /**
     * Find a node by task ID
     *
     * @param id
     * @return
     */
    @Query("start u=node:TaskNodeNeo(id='{id}') return u")
    TaskNodeNeo findUserTasksById(String id);

}
