package com.first.firstproject.entity.neo4j;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.List;



@RelationshipProperties
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ActorAndRoles {
    @RelationshipId
    @GeneratedValue
    private Long id;

    private  List<String> roles;
    @TargetNode
    PersonEntity actor;

    public ActorAndRoles(PersonEntity person, List<String> roles) {
        this.actor = person;
        this.roles = roles;
    }

}
