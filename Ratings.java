/**
 * Enum for Avaliable Ratings
 */
public enum Ratings {
    G, PG, M, MA, R, NA, SELECT_RATING;

    public String toString() {
        switch (this) {
            case G:
                return "General audience";
            case PG:
                return "Parental guidance";
            case M:
                return "Mature audience";
            case MA:
                return "Mature audience (15+)";
            case R:
                return "Restricted (18+)";
            case SELECT_RATING:
                return "Select Rating";
            default:
                return "NA";
        }
    }
}
