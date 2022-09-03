package com.first.firstproject.controller.neo4j;

import com.first.firstproject.dto.neo4j.MoviePersonDto;
import com.first.firstproject.entity.neo4j.ActorAndRoles;
import com.first.firstproject.entity.neo4j.MovieEntity;
import com.first.firstproject.entity.neo4j.PersonEntity;

import com.first.firstproject.repository.neo4j.ActorAndRolesRepository;
import com.first.firstproject.repository.neo4j.MovieRepository;

import com.first.firstproject.repository.neo4j.PersonRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final ActorAndRolesRepository actorAndRolesRepository;
    private final MovieRepository movieRepository;
    private  final PersonRepository personRepository;

    public MovieController(MovieRepository movieRepository,PersonRepository personRepository,ActorAndRolesRepository actorAndRolesRepository) {
        this.movieRepository = movieRepository;
        this.personRepository = personRepository;
        this.actorAndRolesRepository = actorAndRolesRepository;
    }

    @PutMapping
    MovieEntity createOrUpdateMovie(@RequestBody MovieEntity newMovie) {
        return movieRepository.save(newMovie);
    }
    @GetMapping
    List<MovieEntity> getMovies() {
        return movieRepository.findAll();
    }
    @GetMapping("/{title}")
    MovieEntity getMovies(@PathVariable String title) {
        return movieRepository.findOneByTitle(title);
    }

    @PutMapping("/person")
    MovieEntity  UpdateMovie(@RequestBody MoviePersonDto moviePersonDto) {
        MovieEntity movie = movieRepository.findOneByTitle(moviePersonDto.getTitle());
        PersonEntity actor = personRepository.findOneByName(moviePersonDto.getName());
//      actor
        ActorAndRoles actorAndRoles = new ActorAndRoles();
        actorAndRoles.setActor(actor);
//       movie and his actor
        List<ActorAndRoles> actorsAndRoles = movie.getActorsAndRoles();
        actorsAndRoles.add(actorAndRoles);
        movie.setActorsAndRoles(actorsAndRoles);
        return movieRepository.save(movie);
    }
    @PutMapping("/relationship")
    MovieEntity  UpdateRelationshipMovie(@RequestBody MoviePersonDto moviePersonDto) {
        MovieEntity movie = movieRepository.findOneByTitle(moviePersonDto.getTitle());
        PersonEntity actor = personRepository.findOneByName(moviePersonDto.getName());

//       roles
        List<String> roles = new ArrayList<>();
        roles.add(moviePersonDto.getRole());
//      actor and his roles
        ActorAndRoles actorAndRoles = new ActorAndRoles();
        actorAndRoles.setActor(actor);
        actorAndRoles.setRoles(roles);
//        movie and his actor and roles
      List<ActorAndRoles> actorsAndRoles = movie.getActorsAndRoles();
      actorsAndRoles.add(actorAndRoles);
       movie.setActorsAndRoles(actorsAndRoles);
        return movieRepository.save(movie);
    }
    @DeleteMapping("/relationship/{id}")
    MovieEntity deleteRelationshipMocie(@PathVariable Long id,@RequestBody MoviePersonDto moviePersonDto){

       movieRepository.deleteRelationshipById(id);
       MovieEntity movie = movieRepository.findOneByTitle(moviePersonDto.getTitle());
//
//        MovieEntity newMovie = new MovieEntity(movie.getTitle(),movie.getDescription());
//        //      actor and his roles
//        List<ActorAndRoles> actorsAndRoles = movie.getActorsAndRoles().stream()
//                .filter(actorAndRole ->  !(actorAndRole.getId().intValue() == id.intValue()))
//                .collect(Collectors.toList());
//
//        movieRepository.deleteById(movie.getTitle());
//
//        movieRepository.save(newMovie);
//
//        MovieEntity updatedMovie = movieRepository.findOneByTitle(moviePersonDto.getTitle());
//
//        updatedMovie.setActorsAndRoles(actorsAndRoles);

        return movie;
    }


}