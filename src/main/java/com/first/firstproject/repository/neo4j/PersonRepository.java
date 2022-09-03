package com.first.firstproject.repository.neo4j;


import com.first.firstproject.entity.neo4j.PersonEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PersonRepository extends Neo4jRepository<PersonEntity, String> {
    PersonEntity findOneByName(String name);

}
