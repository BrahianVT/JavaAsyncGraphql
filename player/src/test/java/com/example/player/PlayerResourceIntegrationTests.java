package com.example.player;

import com.example.player.entities.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureGraphQlTester
@AutoConfigureTestDatabase
public class PlayerResourceIntegrationTests {
    @Autowired
    private GraphQlTester tester;

    // Just check with queries not mutation because it will insert to the database
    @Test
    void findAll(){
        String query = "query{ getAllPlayers { id }}";
        List<Player> players = tester.document(query)
                .execute()
                .path("data.getAllPlayers[*]")
                .entityList(Player.class)
                .get();

        Assertions.assertNotNull(players.size() > 0);
        Assertions.assertNotNull(players.get(0).getId());
    }

    @Test
    void findPlayerById(){
        String query = "query { getPlayerById(id:5){ id name }} ";
        Player player = tester.document(query)
                .execute()
                .path("data.getPlayerById")
                .entity(Player.class)
                .get();

        Assertions.assertNotNull(player);
        Assertions.assertNotNull(player.getId());
        Assertions.assertNotNull(player.getName());
    }

    @Test
    void findPlayersByClub(){
        String query = "query{ getPlayersByClub(club:\"Tottenham Hotspur\"){  club } }";
        List<Player> players = tester.document(query)
                .execute()
                .path("data.getPlayersByClub[*]")
                .entityList(Player.class)
                .get();


        Assertions.assertNotNull(players.size() > 0);
        Assertions.assertEquals(players.get(0).getClub(),"Tottenham Hotspur");
    }
}
