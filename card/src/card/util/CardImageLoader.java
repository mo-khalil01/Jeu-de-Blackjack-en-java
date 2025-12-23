package card.util;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import card.game.Card;

/**
 * Utilitaire pour charger les images des cartes à jouer.
 * Cette classe gère le chargement et le redimensionnement des images
 * des cartes depuis les ressources du projet.
 */
public class CardImageLoader {
    // Dimensions standard des cartes
    private static final int CARD_WIDTH = 80;
    private static final int CARD_HEIGHT = 120;

    /**
     * Charge l'image correspondant à une carte donnée.
     * L'image est recherchée dans le chemin /card/cards/{couleur}/{nom}.png
     * et redimensionnée aux dimensions standard (80×120 pixels).
     * 
     * @param card la carte dont on veut charger l'image
     * @return une ImageIcon contenant l'image redimensionnée, ou null si l'image n'est pas trouvée
     */
    public static ImageIcon load(Card card) {
        
         
        String path = "/card/cards/" + card.getColor() + "/" + card.getName() + ".png";


        URL imgURL = CardImageLoader.class.getResource(path);
        
        if (imgURL == null) {
            System.err.println("Image Non Trouvé: " + path);
            return null;
        }
        System.out.println("Image Trouvé: " + path);
        ImageIcon icon = new ImageIcon(imgURL);
        Image scaledImage = icon.getImage().getScaledInstance(
            CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH
        );
        return new ImageIcon(scaledImage);
        
    }

    /**
     * Charge l'image du dos de carte standard.
     * L'image est recherchée dans le chemin /card/cards/back.jpg
     * et redimensionnée aux dimensions standard (80×120 pixels).
     * 
     * @return une ImageIcon contenant l'image du dos redimensionnée, ou null si l'image n'est pas trouvée
     */
    public static ImageIcon loadBack() {
        

        String path = "/card/cards/back.jpg";
        URL imgURL = CardImageLoader.class.getResource(path);
        
        if (imgURL == null) {
            System.err.println("Image Non Trouvé: " + path);
            return null;
        }
        ImageIcon icon = new ImageIcon(imgURL);
        Image scaledImage = icon.getImage().getScaledInstance(
            CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH
        );
        return new ImageIcon(scaledImage);
    }
}