package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameSession {
	private String sessionName;
	private ArrayList<User> users;
	private User currentUser;
    private Map<String, ArrayList<Card>> playerCards;
	private ArrayList<Card> drawPile;
	private ArrayList<Card> discardPile;
	
   private boolean playDirectionClockwise;

    public GameSession(String sessionName, ArrayList<User> users) {
        this.sessionName = sessionName;
        this.users = users;
        this.currentUser = users.get(0);  
        this.playerCards = new HashMap<>();
        for (User user : users) {
            this.playerCards.put(user.getUsername(), new ArrayList<>()); 
        }
        this.drawPile = getNewDrawPile();
        this.discardPile = new ArrayList<>(); 
        this.playDirectionClockwise = true; 
    }
    
    public ArrayList<Card> getNewDrawPile() {
    	// TODO
    	return new ArrayList<Card>();
    }
    
    public void drawCard() {
        
    }

    public void playCard(Card card) {
        
    }

    public User getNextUser() {
        int index = users.indexOf(currentUser);
        var size = users.size();
        if (playDirectionClockwise) {
            index = (index + 1) % size;  // Move to the next player clockwise
        } else {
            index = (index - 1 + size) % size;  // Move to the next player counter-clockwise
        }
        return users.get(index);
    }

    public void declareUno() {
       
    }

    public void penalizeNoUno() {
      
    }
}
	