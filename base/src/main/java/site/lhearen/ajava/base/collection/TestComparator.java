package site.lhearen.ajava.base.collection;

import site.lhearen.ajava.mytools.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.lang.System.out;
public class TestComparator {

    public static void main(String[] args) {
        List<Player> footballTeam = new ArrayList<>();
        Player player1 = new Player(59, "John", 20);
        Player player2 = new Player(67, "Jack", 22);
        Player player3 = new Player(45, "Steven", 24);
        footballTeam.add(player1);
        footballTeam.add(player2);
        footballTeam.add(player3);

        out.println("Before Sorting : " + footballTeam);
        Collections.sort(footballTeam);
        out.println("After Sorting : " + footballTeam);

        out.println();
        out.println("Before Sorting by age: " + footballTeam);
        footballTeam.sort(Comparator.comparing(Player::getAge));
        out.println("After Sorting by age: " + footballTeam);

        out.println();
        out.println("Before Sorting by age reversed: " + footballTeam);
        footballTeam.sort(Comparator.comparing(Player::getAge).reversed());
        out.println("After Sorting by age reversed: " + footballTeam);

        out.println();
        out.println("Before Sorting by name: " + footballTeam);
        footballTeam.sort(Comparator.comparing(Player::getName));
        out.println("After Sorting by name: " + footballTeam);

        out.println();
        Player player4 = new Player(45, null, 24);
        footballTeam.add(player4);
        out.println("Before Sorting by name null first: " + footballTeam);
        footballTeam.sort(Comparator.comparing(Player::getName, Comparator.nullsFirst(Comparator.naturalOrder())));
        out.println("After Sorting by name null first: " + footballTeam);
    }
}

