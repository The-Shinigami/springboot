package com.first.firstproject.repository.neo4j;

import com.first.firstproject.entity.neo4j.ActorAndRoles;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ActorAndRolesRepository extends Neo4jRepository<ActorAndRoles,Long> {
}
