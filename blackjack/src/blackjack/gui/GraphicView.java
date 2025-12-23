package blackjack.gui;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.util.Map;

import java.util.List;
import card.util.CardImageLoader;
import card.game.Card;
import blackjack.controller.BlackjackController;
import blackjack.model.Table;
import blackjack.model.player.Dealer;
import blackjack.model.player.Player;
import blackjack.util.*;

/**
 * Vue graphique principale du jeu de blackjack utilisant Swing.
 * Cette classe implémente le pattern MVC (Modèle-Vue-Contrôleur) en tant que vue
 * et le pattern Observer pour se synchroniser automatiquement avec les changements du modèle.
 * Elle affiche l'interface graphique complète du jeu incluant les cartes des joueurs,
 * leurs scores, mises, pots et les boutons de contrôle.
 */
public class GraphicView extends JFrame implements Observateur{
    
    /**
     * Contrôleur gérant la logique du jeu.
     */
    private BlackjackController controller;
    
    /**
     * Table de jeu contenant les joueurs et l'état de la partie.
     */
    private Table table;

    /**
     * Police par défaut pour les textes normaux.
     */
    private Font defaultFont = new Font("Arial", Font.BOLD, 15);
    
    /**
     * Police pour les titres (plus grande et en gras).
     */
    private Font titleFont = new Font("Arial", Font.BOLD, 18);

    /**
     * Couleur de fond principale simulant une table de jeu (vert foncé).
     */
    private Color tableColor = new Color(0, 75, 0);
    
    /**
     * Couleur du texte (blanc pour contraste sur fond vert).
     */
    private Color textColor = Color.WHITE;

    /**
     * Couleur de fond des panneaux de joueurs (vert légèrement plus clair).
     */
    private Color paneColor = new Color(0, 100, 0);

    /**
     * Label affichant le score visible du croupier.
     */
    private JLabel dealerScoreLabel;
    
    /**
     * Label affichant le score du joueur humain.
     */
    private JLabel playerScoreLabel;
    
    /**
     * Label affichant le score du bot (si présent).
     */
    private JLabel botScoreLabel;

    /**
     * Label affichant la mise du joueur humain.
     */
    private JLabel playerBetLabel;
    
    /**
     * Label affichant la mise du bot (si présent).
     */
    private JLabel botBetLabel;

    /**
     * Label affichant le pot (capital) du joueur humain.
     */
    private JLabel playerPotLabel;
    
    /**
     * Label affichant le pot (capital) du bot (si présent).
     */
    private JLabel botPotLabel;
    
    /**
     * Bouton pour démarrer une nouvelle partie.
     */
    private StartButton startButton;
    
    /**
     * Bouton pour tirer une carte (Hit).
     */
    private HitButton hitButton;
    
    /**
     * Bouton pour rester (Stand).
     */
    private StandButton standButton;
    
    /**
     * Bouton pour doubler la mise (Double Down).
     */
    private DoubleDumpPanel doubleDumpPanel;

    /**
     * Score actuel du croupier (visible).
     */
    private int dealerScore = 0;
    
    /**
     * Score actuel du joueur humain.
     */
    private int playerScore = 0; 
    
    /**
     * Score actuel du bot.
     */
    private int botScore = 0;

    /**
     * Montant de la mise du joueur humain.
     */
    private int betAmount = 0;
    
    /**
     * Montant de la mise du bot.
     */
    private int botBetAmount = 0;

    /**
     * Panneau contenant les cartes du croupier.
     */
    private JPanel dealerCardsPanel;
    
    /**
     * Panneau contenant les cartes du joueur humain.
     */
    private JPanel playerCardsPanel;
    
    /**
     * Panneau contenant les cartes du bot (si présent).
     */
    private JPanel botCardsPanel;

    /**
     * Construit la vue graphique du jeu de blackjack.
     * Initialise la fenêtre principale, s'enregistre comme observateur de la table,
     * configure les paramètres de la fenêtre et construit l'interface utilisateur.
     * 
     * @param table la table de jeu à afficher
     * @param controller le contrôleur gérant la logique du jeu
     */
    public GraphicView(Table table, BlackjackController controller) {

        this.table = table;
        this.controller = controller;
        table.addObservateur(this);

        setTitle("Blackjack Game");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        
        setVisible(true); 
    }

    /**
     * Initialise l'interface utilisateur complète.
     * Crée et organise tous les panneaux : dealer (nord), joueur (sud),
     * bot optionnel (centre), et contrôles (est).
     */
    public void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.setBackground(tableColor);

        mainPanel.add(createDealerPanel(), BorderLayout.NORTH);
        mainPanel.add(createPlayerPanel(), BorderLayout.SOUTH);

        if(controller.getHasBotPlayer()) {
            mainPanel.add(createBotPanel(), BorderLayout.CENTER);
        }
        mainPanel.add(createControlPanel(), BorderLayout.EAST);

        add(mainPanel);

        mettreAJour();
    }

    /**
     * Crée le panneau d'affichage du croupier.
     * Contient le titre, le score visible et les cartes du croupier.
     * Le panneau est encadré en jaune pour le distinguer.
     * 
     * @return le panneau du croupier configuré
     */
    private JPanel createDealerPanel() {
        JPanel dealerPanel = new JPanel(new BorderLayout());
        dealerPanel.setBackground(paneColor);

        dealerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(15,15,15,15),
            BorderFactory.createLineBorder(Color.YELLOW,2)
        ));

        JLabel dealerLabel = new JLabel("DEALER :");
        dealerLabel.setFont(titleFont);
        dealerLabel.setForeground(Color.YELLOW);

        dealerScoreLabel = new JLabel();
        dealerScoreLabel.setFont(defaultFont);
        dealerScoreLabel.setForeground(textColor);

        dealerCardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10,10));
        dealerCardsPanel.setBackground(paneColor);
        dealerCardsPanel.setPreferredSize(new Dimension(400, 150));

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        infoPanel.setBackground(paneColor);
        infoPanel.add(dealerLabel);
        infoPanel.add(dealerScoreLabel);

        dealerPanel.add(infoPanel, BorderLayout.NORTH);
        dealerPanel.add(dealerCardsPanel, BorderLayout.CENTER);

        return dealerPanel;
    }

    /**
     * Crée le panneau d'affichage du joueur humain.
     * Contient le titre, le score, la mise actuelle, le pot et les cartes du joueur.
     * Le panneau est encadré en cyan pour le distinguer.
     * 
     * @return le panneau du joueur configuré
     */
    private JPanel createPlayerPanel() {

        JPanel playerPanel = new JPanel(new BorderLayout());
        playerPanel.setBackground(paneColor);
        playerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(15,15,15,15),
            BorderFactory.createLineBorder(Color.CYAN, 2)
        ));

        JLabel playerLabel = new JLabel("JOUEUR :");
        playerLabel.setFont(titleFont);
        playerLabel.setForeground(textColor);
        
        playerScoreLabel = new JLabel("Score: " + this.playerScore);
        playerScoreLabel.setFont(defaultFont);
        playerScoreLabel.setForeground(textColor);

        Player player = table.getPlayers().get(1);
        Integer bet = null;
        Map<Player, Integer> bets = table.getBets();
        if(bets != null) {
            bet = bets.get(player);
        }

        this.betAmount = (bet != null) ? bet.intValue() : 0;
        
        playerBetLabel = new JLabel("Mise: " + this.betAmount);
        playerBetLabel.setFont(defaultFont);
        playerBetLabel.setForeground(Color.GREEN);

        playerPotLabel = new JLabel("Pot: " + player.getPot());
        playerPotLabel.setFont(defaultFont);
        playerPotLabel.setForeground(Color.GREEN);

        playerCardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10,10));
        playerCardsPanel.setBackground(paneColor);
        playerCardsPanel.setPreferredSize(new Dimension(700, 150));

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        infoPanel.setBackground(paneColor);
        infoPanel.add(playerLabel);
        infoPanel.add(playerScoreLabel);
        infoPanel.add(playerBetLabel);
        infoPanel.add(playerPotLabel);
        playerPanel.add(infoPanel, BorderLayout.NORTH);
        playerPanel.add(playerCardsPanel, BorderLayout.CENTER);

        return playerPanel;
    }

    /**
     * Crée le panneau d'affichage du bot joueur.
     * Contient le titre, le score, la mise actuelle, le pot et les cartes du bot.
     * Le panneau est encadré en bleu pour le distinguer.
     * 
     * @return le panneau du bot configuré
     */
    private JPanel createBotPanel() {
        JPanel botPanel = new JPanel(new BorderLayout());
        botPanel.setBackground(paneColor);
        botPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(15,15,15,15),
            BorderFactory.createLineBorder(Color.BLUE, 2)
        ));

        JLabel botLabel = new JLabel("Bot :");
        botLabel.setFont(titleFont);
        botLabel.setForeground(textColor);
        
        botScoreLabel = new JLabel("Score: " + this.playerScore);
        botScoreLabel.setFont(defaultFont);
        botScoreLabel.setForeground(textColor);

        Player player = table.getPlayers().get(2);
        Integer bet = null;
        Map<Player, Integer> bets = table.getBets();
        if(bets != null) {
            bet = bets.get(player);
        }

        this.botBetAmount = (bet != null) ? bet.intValue() : 0;
        
        botBetLabel = new JLabel("Mise: " + this.botBetAmount);
        botBetLabel.setFont(defaultFont);
        botBetLabel.setForeground(Color.GREEN);

        botPotLabel = new JLabel("Pot: " + player.getPot());
        botPotLabel.setFont(defaultFont);
        botPotLabel.setForeground(Color.GREEN);

        botCardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10,10));
        botCardsPanel.setBackground(paneColor);
        botCardsPanel.setPreferredSize(new Dimension(700, 150));

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        infoPanel.setBackground(paneColor);
        infoPanel.add(botLabel);
        infoPanel.add(botScoreLabel);
        infoPanel.add(botBetLabel);
        infoPanel.add(botPotLabel);
        botPanel.add(infoPanel, BorderLayout.NORTH);
        botPanel.add(botCardsPanel, BorderLayout.CENTER);

        return botPanel;
    }

    /**
     * Crée le panneau de contrôle contenant les boutons d'action.
     * Les boutons sont empilés verticalement avec un espacement uniforme :
     * Start Game, Piocher (Hit), Arrêter (Stand), et Double Down.
     * 
     * @return le panneau de contrôle configuré
     */
    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(new Color(0, 50, 0));
        controlPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(20, 10, 20, 10),
            BorderFactory.createLineBorder(Color.GRAY,2)
        ));


        startButton = new StartButton(controller);
        hitButton = new HitButton(controller);
        standButton = new StandButton(controller);
        doubleDumpPanel = new DoubleDumpPanel(controller);
 
        Dimension buttonSize = new Dimension(120, 40);
        for (JButton btn : new JButton[]{startButton, hitButton, standButton, doubleDumpPanel}) {
            btn.setMaximumSize(buttonSize);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setFont(defaultFont);
            controlPanel.add(btn);
            controlPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }
        
        return controlPanel;
    }

    /**
     * Met à jour l'affichage graphique en fonction de l'état actuel du jeu.
     * Cette méthode est appelée automatiquement par le pattern Observer
     * lorsque la table notifie ses observateurs d'un changement.
     * 
     * Met à jour :
     * - Les scores de tous les joueurs
     * - Les mises et pots
     * - Les cartes affichées (avec la première carte du croupier cachée jusqu'à la fin)
     * - L'affichage du bot si présent
     */
    @Override
    public void mettreAJour() {
        Dealer dealer = (Dealer) table.getPlayers().get(0);
        this.dealerScore = dealer.getVisibleHandValue();

        Player player = table.getPlayers().get(1);
        
        this.playerScore = player.getHandValue();

        dealerScoreLabel.setText("Score: " + dealerScore);
        playerScoreLabel.setText("Score: " + playerScore);
        
        Integer playerBet = null;

        Map<Player, Integer> bets = table.getBets();
        if(bets != null) {
            playerBet = bets.get(player);
        }
        this.betAmount = (playerBet != null) ? playerBet.intValue() : 0;
        playerBetLabel.setText("Mise: " + this.betAmount);
        playerPotLabel.setText("Pot: " + player.getPot());

        dealerCardsPanel.removeAll();
        for(int i = 0; i < dealer.getHand().getDeck().size(); i++) {
            Card c = dealer.getHand().getDeck().get(i);
            ImageIcon cardIcon;
            if (i == 0 && !(this.controller.getIsFinished())) {
                cardIcon = CardImageLoader.loadBack();
            } else {
                cardIcon = CardImageLoader.load(c);
            }
            JLabel cardLabel = new JLabel(cardIcon);
            dealerCardsPanel.add(cardLabel);
        }

        playerCardsPanel.removeAll();
        for (Card c : player.getHand().getDeck()) {
            ImageIcon cardIcon = CardImageLoader.load(c);
            JLabel cardLabel = new JLabel(cardIcon);
            playerCardsPanel.add(cardLabel);
        }

        if(controller.getHasBotPlayer()) {
            Player bot = table.getPlayers().get(2);
            this.botScore = bot.getHandValue();
            botScoreLabel.setText("Score: " + botScore);

            Integer botBet = (bets != null) ? bets.get(bot) : null;
            this.botBetAmount = (botBet != null) ? botBet.intValue() : 0;
            botBetLabel.setText("Mise: " + this.botBetAmount);
            botPotLabel.setText("Pot: " + bot.getPot());

            botCardsPanel.removeAll();
            for (Card c : bot.getHand().getDeck()) {
                ImageIcon cardIcon = CardImageLoader.load(c);
                JLabel cardLabel = new JLabel(cardIcon);
                botCardsPanel.add(cardLabel);
            }
            botCardsPanel.revalidate();
            botCardsPanel.repaint();
        }

        dealerCardsPanel.revalidate();
        dealerCardsPanel.repaint();

        playerCardsPanel.revalidate();
        playerCardsPanel.repaint();

        System.out.println(this.playerScore);
        System.out.println(this.dealerScore);
        System.out.println(controller.getIsFinished());
        
        repaint();
    }

    /**
     * Affiche une fenêtre popup annonçant le(s) gagnant(s) de la manche.
     * Si aucun joueur n'a gagné, annonce la victoire du croupier.
     * Cette méthode est appelée à la fin de chaque partie.
     */
    public void popWinner() {
        
        List<Player> winners = table.getWinners();
        if ((winners != null && !winners.isEmpty())) {
            
            StringBuilder winnerNames = new StringBuilder();
            for (Player p : winners) {
                winnerNames.append(p.getName()).append(" ");
            }
            JOptionPane.showMessageDialog(this, "Gagnant(s): " + winnerNames.toString());
        }

        else {
            JOptionPane.showMessageDialog(this, "Le Dealer a gagné cette manche.");
        }
        repaint();
    }
}