package blackjack.model;
import java.util.*;

import card.game.*;
import blackjack.model.player.*;
import card.util.FactoryCard;

/**
 * fait des Test sur toutes les méthodes du model (quand c'est possible)
 */
public class TestModel{

    public static void main(String[] args){

        // initialisation des instance necessaire
        List<Player> players = new ArrayList<Player>();
        Dealer dealer = new Dealer("Dealer", 10000);
        Human humanPlayer = new Human("Human", 200);
        Bot ia = new Bot("IA");

        players.add(dealer);
        players.add(humanPlayer);
        players.add(ia);

        //tests sur les méthodes des players
        Card carte1 = new Card("Coeur", "10", 10);
        Card carte2 = new Card("Pique", "As", 1);
        Card carte3 = new Card("Carreau", "5", 5);

        boolean ok = true;

        ok = ok && (humanPlayer.getHand().getDeck().isEmpty() == true);
        humanPlayer.addNewCard(carte1);
        ok = ok && (humanPlayer.getHand().getDeck().contains(carte1));
        ok = ok && (humanPlayer.getHandValue()==10);
        humanPlayer.addNewCard(carte3);
        humanPlayer.addNewCard(carte2);
        ok = ok && (humanPlayer.getHandValue()==16);
        humanPlayer.clearHand();
        ok = ok && (humanPlayer.getHand().getDeck().isEmpty() == true);
        System.out.println(ok ? "(human) test Ok" : "(human) test KO");

        dealer.addNewCard(carte1);
        ok = ok && (dealer.continueChoice()==true);
        dealer.addNewCard(carte2);
        ok = ok && (dealer.continueChoice()==false);
        ok = ok && (dealer.getHandValue()==21);
        ok = ok && (dealer.getVisibleHandValue()==11);
        ok = ok && (dealer.getHand().toString() != dealer.getVisibleHand());
        System.out.println(ok ? "(dealer) tests OK" : "(dealer) test KO");

        ok = ok && (ia.placeBet(1, 10) >= 1 && ia.placeBet(1, 10) <=10);
        ia.addNewCard(carte2);
        ok = ok && (ia.doubleDump() == true);
        ok = ok && (ia.continueChoice()==true);
        ia.addNewCard(carte1);
        ok = ok && (ia.doubleDump() == false);
        ok = ok && (ia.continueChoice()==false);
        System.out.println(ok ? "(ia) tests OK" : "(ia) test KO");

        // tests sur la table
        Table table = new Table(players, 5, 10);

        table.resetRound();
        ok = ok && (dealer.getHand().getDeck().isEmpty() == true);
        ok = ok && (ia.getHand().getDeck().isEmpty() == true);
        System.out.println(ok ? "(resetRound) tests OK" : "(resetRound) test KO");

        humanPlayer.addNewCard(carte1);
        ok = ok && (table.blackjack() == false);
        humanPlayer.addNewCard(carte2);
        dealer.addNewCard(carte1);
        ia.addNewCard(carte3);
        ok = ok && (table.blackjack() == true);
        System.out.println(ok ? "(blackjack) tests OK" : "(blackjack) test KO");

        table.resetRound();
        humanPlayer.addNewCard(carte1);
        humanPlayer.addNewCard(carte2);
        dealer.addNewCard(carte1);
        dealer.addNewCard(carte2);
        ia.addNewCard(carte3);
        table.findWinners();
        ok = ok && (table.getWinners().isEmpty());
        ok = ok && (table.getWinners().contains(humanPlayer) == false);
        ok = ok && (table.getWinners().contains(ia) == false);

        table.resetRound();
        humanPlayer.addNewCard(carte1);
        humanPlayer.addNewCard(carte2);
        dealer.addNewCard(carte1);
        ia.addNewCard(carte3);
        table.findWinners();
        ok = ok && (table.getWinners().contains(humanPlayer));
        ok = ok && (table.getWinners().contains(dealer) == false);
        ok = ok && (table.getWinners().contains(ia) == false);
        System.out.println(ok ? "(findWinners) tests OK" : "(findWinners) test KO");

        Map<Player, Integer> pari = new HashMap<>();
        table.setBets(pari);
        pari.put(humanPlayer, 20);
        pari.put(ia, 20);
        table.dealCoins();
        ok = ok && (humanPlayer.getPot()==220);
        ok = ok && (ia.getPot()==80);
        System.out.println(ok ? "(dealCoins) tests OK" : "(dealCoins) test KO");

    }
}