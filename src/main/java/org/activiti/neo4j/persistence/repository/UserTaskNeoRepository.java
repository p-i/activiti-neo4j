package org.activiti.neo4j.persistence.repository;

import org.activiti.neo4j.persistence.entity.UserTaskNodeNeo;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.SchemaIndexRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by ilja on 25/07/14.
 */
public interface UserTaskNeoRepository extends GraphRepository<UserTaskNodeNeo>, SchemaIndexRepository<UserTaskNodeNeo> {

    /**
     * Find all the nodes by assignee name
     *
     * @param assignee
     * @return Iterator<Task>
     */
    @Query("match (u:UserTaskNodeNeo {assignee: {0}}) return u")
    Iterable<UserTaskNodeNeo> findUserTasksByAssignee(String assignee);
}