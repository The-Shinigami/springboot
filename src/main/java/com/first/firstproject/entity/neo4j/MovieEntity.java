package com.first.firstproject.entity.neo4j;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Node("Movie")
@Setter
@Getter
public class MovieEntity {
    @Id
    private final String title;

    @Property("tagline")
    private final String description;



    @Relationship(type = "ACTED_IN", direction = Relationship.Direction.INCOMING)
    private List<ActorAndRoles> actorsAndRoles = new ArrayList<>();



    @Relationship(type = "DIRECTED", direction = Relationship.Direction.INCOMING)
    private Set<PersonEntity> directors = new HashSet<>();



    public MovieEntity(String title, String description) {
        this.title = title;
        this.description = description;
    }

}
