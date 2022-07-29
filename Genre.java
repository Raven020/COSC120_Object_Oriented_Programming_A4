/**
 * Enum for Avaliable Genres
 */
public enum Genre {
    ACTION_AND_ADVENTURE, SHOOTER, SPORTS, ROLE_PLAYING, COMBAT, SELECT_GENRE;

    public String toString() {
        switch (this) {
            case COMBAT:
                return "Combat or Fighting";
            case SPORTS:
                return "Sports, e.g. racing, soccer, flying etc.";
            case SHOOTER:
                return "Shooter";
            case ROLE_PLAYING:
                return "Role playing";
            case ACTION_AND_ADVENTURE:
                return "Action and Adventure";
            default:
                return "Select preferred Genre";
        }
    }
}
