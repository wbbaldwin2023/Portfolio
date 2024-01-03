/**
 * Provides the Symbol object for whackamole game
 * @author William Baldwin
 */
public class Symbol {

    /**
     * name
     */
    private String name;

    /**
     * points
     */
    private int points;

    /**
     * true/false for clicked
     */
    private boolean hasBeenClickedOn;

    /**
     * Symbol object constructor
     * @param name name 
     * @param points points
     * @throws IllegalArgumentException if null name or points < 1
     */
    public Symbol(String name, int points) {
        if (name == null) {
            throw new IllegalArgumentException("Null name");
        }
        if (points < 1) {
            throw new IllegalArgumentException("Invalid points");
        }
        this.name = name;
        this.points = points;
    }

    /**
     * return name
     * @return the type of bread
     */
    public String getName() {
        return name;
    }

    /**
     * return points
     * @return points
     */
    public int getPoints() {
        return points;
    }

    /**
     * return hasbeenclicked
     * @return true/false
     */
    public boolean hasBeenClickedOn() {
        return hasBeenClickedOn;
    }

    /**
     * sets has been clicked
     * @param hasBeenClickedOn if it was clicked
     */
    public void setHasBeenClickedOn(boolean hasBeenClickedOn) {
        this.hasBeenClickedOn = hasBeenClickedOn;
    }

    /**
     * checks if equal
     * @param o instance for omparison
     * @return boolean
     */
    public boolean equals(Object o) {
        if (o instanceof Symbol) {
            Symbol x = (Symbol) o;
            return x.name == this.name && x.points == this.points;
        }
        else {
            return false; 
        }
    }

    /**
     * returns string
     * @return string
     */
    public String toString() {
        return (name + " " + points + " " + hasBeenClickedOn);
    }









}