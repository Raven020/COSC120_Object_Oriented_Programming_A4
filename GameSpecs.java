import java.util.HashMap;
import java.util.Map;

public class GameSpecs {
    private final Map<Specs, Object> specs;
    private final double minPrice;
    private final double maxPrice;

    /**
     * Constructor for a GameSpecs object
     * 
     * @param specs    the games specs e.g Genre, SubGenre, Platform
     * @param minPrice min price the user is searching for
     * @param maxPrice max price that the user is searching for
     */
    public GameSpecs(Map<Specs, Object> specs, double minPrice, double maxPrice) {
        if (specs == null)
            this.specs = new HashMap<>();
        else
            this.specs = new HashMap<>(specs);
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    /**
     * Alternate constructor if GameSpecs object is not a users search, min and max
     * price do not need to be entered
     * 
     * @param specs the games specs e.g Genre, SubGenre, Platform
     */
    public GameSpecs(Map<Specs, Object> specs) {
        if (specs == null)
            this.specs = new HashMap<>();
        else
            this.specs = new HashMap<>(specs);
        this.minPrice = -1;
        this.maxPrice = -1;
    }

    /**
     * Getter for the All of the specs of a game
     * 
     * @return all of the specs for a give gamespecs object
     */
    public Map<Specs, Object> getAllSpecs() {
        return new HashMap<>(specs);
    }

    /**
     * Getter for an individual Gamespec
     * 
     * @param spec the spec we wish to retrieve a value for
     * @return the given specs value
     */
    public Object getSpec(Specs spec) {
        return this.getAllSpecs().get(spec);
    }

    /**
     * Getter for users desired max price
     * 
     * @return the max price
     */
    public double getMaxPrice() {
        return maxPrice;
    }

    /**
     * Getter for users desired min price
     * 
     * @return the min price
     */
    public double getMinPrice() {
        return minPrice;
    }

    /**
     * Method to retrieve the specs in String form
     * 
     * @param specs the specs we wish to have the value for
     * @return a string description of the specs
     */
    public String getDescription(Map<Specs, Object> specs) {
        String description = "";
        for (Specs key : specs.keySet())
            description += "\n > " + key + ": " + getSpec(key);
        return description;
    }

    /**
     * Method to compare two GameSpecs objects
     * 
     * @param dreamGame the other game we are comparing
     * @return true if all field match else false
     */
    public boolean compareGames(GameSpecs dreamGame) {
        for (Specs key : dreamGame.getAllSpecs().keySet()) {
            if (!this.getSpec(key).equals(dreamGame.getSpec(key)))
                return false;
        }
        return true;
    }
}
