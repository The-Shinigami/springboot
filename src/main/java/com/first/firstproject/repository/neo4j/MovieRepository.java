package com.first.firstproject.repository.neo4j;

import com.first.firstproject.entity.neo4j.MovieEntity;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;


public interface MovieRepository extends Neo4jRepository<MovieEntity, String> {
    MovieEntity findOneByTitle(String title);

    @Query("MATCH ()-[r]->() WHERE id(r) = $id DELETE r")
    void deleteRelationshipById(Long id);
}
