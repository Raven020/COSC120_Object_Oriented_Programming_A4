import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AllGames {

    private final ArrayList<Game> allGames = new ArrayList<>();

    /**
     * addGames method for AllGames object adds a game to the allGames arrayList
     * 
     * @param game an individual game that will be added to arrayList
     */
    public void addGame(Game game) {
        this.allGames.add(game);
    }

    /**
     * 
     * @param genre the genre that we wish to retirive all the subgenres for
     * @return a set of subgenres relating to a particular genre
     */
    public Set<String> getAllSubGenres(Genre genre) {
        Set<String> allSubGenres = new HashSet<>();
        for (Game g : allGames) {
            if (genre.equals(g.getGameSpecs().getSpec(Specs.GENRE)))
                allSubGenres.add((String) g.getGameSpecs().getSpec(Specs.SUBGENRE));
        }
        allSubGenres.add("NA");
        return allSubGenres;
    }

    /**
     * 
     * @param dreamGame the users search parameters Genre, sub genre, min price
     *                  etc..
     * @return array list of games that match the users search specs
     */
    public ArrayList<Game> findGames(GameSpecs dreamGame) {
        ArrayList<Game> matchingGames = new ArrayList<>();
        for (Game game : allGames) {
            GameSpecs gameSpecs = game.getGameSpecs();
            if (!gameSpecs.compareGames(dreamGame))
                continue;
            if (game.getPrice() < dreamGame.getMinPrice() || game.getPrice() > dreamGame.getMaxPrice())
                continue;
            matchingGames.add(game);
        }
        return matchingGames;
    }

    /**
     * method to create a map of Genre -> related subgenres
     * 
     * @return a map which has a key of subgenres and a value of a set of
     *         corresponding subgenres
     */
    public Map<Genre, Set<String>> getGenreToSubgenreMapping() {
        Map<Genre, Set<String>> genreToSubgenre = new HashMap<>();
        for (Game game : this.allGames) {
            Genre genre = (Genre) game.getGameSpecs().getSpec(Specs.GENRE);
            if (!genreToSubgenre.containsKey(genre))
                genreToSubgenre.put(genre, this.getAllSubGenres(genre));
        }
        return genreToSubgenre;
    }
}
