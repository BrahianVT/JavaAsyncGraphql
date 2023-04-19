package com.example.player.resources;

import com.example.player.entities.Player;
import com.example.player.entities.PlayerInput;
import com.example.player.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class PlayerResource {
    private final PlayerRepository repository;

    @Autowired
    public PlayerResource(PlayerRepository repository){
        this.repository = repository;
    }

    @QueryMapping("getAllPlayers")
    Flux<Player> getAllPlayers(){
        System.out.println("Getting all the players...");
        Flux<Player> r = repository.findAll();
        r.subscribe(v -> System.out.println(v.getBirthDate()));
        return r;
    }

    @QueryMapping("getPlayerById")
    Mono<Player> getPlayerById(@Argument Long id){
        System.out.println("Getting player by id:  " + id);
        return repository.findById(id).switchIfEmpty(Mono.defer(() -> { return Mono.error(new Exception("Player not found")); }));
    }

    @QueryMapping("getPlayerByName")
    Mono<Player> getPlayerByName(@Argument String name){
        System.out.println("Getting players by names: " + name);
        return repository.findByName(name);
    }

    @QueryMapping("getPlayersByClub")
    Flux<Player> getPlayersByClub(@Argument String club){
        System.out.println("getting players by club " + club);
        return repository.findByClub(club);
    }

    @QueryMapping("getPlayersByNationality")
    Flux<Player> getPlayersByNationality(@Argument String nationality){
        System.out.println("getting players by nationality " + nationality);
        return repository.findByNationality(nationality);
    }

    @MutationMapping("addPlayer")
    Mono<Player> addPlayer(@Argument PlayerInput playerInput){
        System.out.println("Add player using 'addPlayer' mutation");
        return repository.save( new Player(playerInput));
    }

    @MutationMapping("updatePlayer")
    Mono<Player> updatePlayer(@Argument Long id, @Argument PlayerInput playerInput){
        System.out.println("update player using 'updatePlayer' mutation");
        return repository.findById(id)
                .flatMap(v -> { return repository.save(new Player(playerInput)); });

    }
    @MutationMapping("deletePlayerById")
    Mono<Player> deletePlayerById(@Argument Long id){
        System.out.println("Delete player using 'deletePlayerById' mutation");
        return repository.findById(id).map(p ->{
            repository.deleteById(id).subscribe();
            return p;
        });
    }

    @MutationMapping("deletePlayerByName")
    Mono<Player> deletePlayerByName(@Argument String name){
        System.out.println("Delete player using 'deletePlayerById' mutation");
        return repository.findByName(name).map(p -> {
            repository.deleteByName(name).subscribe();
            return p;
        });
    }
}
