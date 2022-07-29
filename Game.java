import java.text.DecimalFormat;
import java.util.Map;

public class Game {

    private final String title;
    private final long productCode;
    private final double price;
    private final GameSpecs gameSpecs;

    /**
     * Constructor for game object
     * 
     * @param title       games title
     * @param productCode games product code
     * @param price       games price
     * @param gameSpecs   games genre, platform rating etc..
     */
    public Game(String title, long productCode, double price, GameSpecs gameSpecs) {
        this.title = title;
        this.productCode = productCode;
        this.price = price;
        this.gameSpecs = gameSpecs;
    }

    /**
     * Getter for a games title
     * 
     * @return games title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for a games product code
     * 
     * @return games product code
     */
    public long getProductCode() {
        return productCode;
    }

    /**
     * Getter for a games price
     * 
     * @return games price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Getter for a games gameSpecs
     * 
     * @return games gameSpecs
     */
    public GameSpecs getGameSpecs() {
        return gameSpecs;
    }

    /**
     * Method which is a toString for a game, giving its title product code, genre
     * subgenre etc..
     * 
     * @param specs the specs that we wish to get as a string
     * @return a string description of a game
     */
    public String getDescription(Map<Specs, Object> specs) {
        DecimalFormat df = new DecimalFormat("0.00");
        return "\n" + this.getTitle() + " (" + this.getProductCode() + ")" +
                this.getGameSpecs().getDescription(specs) + "\n > Price: $" + df.format(this.getPrice()) + "\n";

    }
}
