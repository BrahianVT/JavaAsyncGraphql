package com.example.player;

import com.example.player.entities.Player;
import com.example.player.repositories.PlayerRepository;
import com.example.player.resources.PlayerResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = PlayerResource.class)
public class PlayerRepositoryUnitTests {
    private WebTestClient webTestClient;
    @MockBean PlayerRepository playerRepository;

    @Test
    public void create_then_save_then_returnSavedPlayer() {
        Player player = new Player(1l,"test1",32,"clubfc","mexican",
                                    LocalDate.parse("1994-07-12"));

        given(playerRepository.save(any(Player.class))).willReturn(Mono.just(player));

        Mono<Player> res = playerRepository.save(player);
        Long id = res.block().getId();
        Assertions.assertEquals(1l,id);
    }

    @Test
    public void find_all_players_validate(){
        List<Player> list = new ArrayList<>();
        list.add(new Player(1l,"test1",32,"clubfc","mexican",
                LocalDate.parse("1994-07-12")));
        list.add(new Player(2l,"test2",35,"clubfc","mexican",
                LocalDate.parse("1998-07-12")));

        when(playerRepository.findAll()).thenReturn(Flux.just(list.get(0),list.get(1)));

       List<Player>  res = playerRepository.findAll()
                .collectList().flatMap(Mono::just).block();
       Assertions.assertEquals(res, list);
    }


}
