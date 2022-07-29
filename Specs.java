/**
 * Enum for Avaliable Specs
 */
public enum Specs {
    GENRE, PLATFORM, SUBGENRE, RATING;

    public String toString() {
        switch (this) {
            case GENRE:
                return "Genre";
            case PLATFORM:
                return "Platform";
            case SUBGENRE:
                return "Sub-genre";
            case RATING:
                return "Rating";
            default:
                return "NA";
        }
    }

}
