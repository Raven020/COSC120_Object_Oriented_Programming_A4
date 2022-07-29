/**
 * Enum for Avaliable Platforms
 */
public enum Platform {

    NINTENDO_SWITCH, PLAYSTATION_4, PLAYSTATION_5, XBOX_ONE, PC, XBOX_SERIES_X, SELECT_PLATFORM, NA;

    public String toString() {
        switch (this) {
            case PC:
                return "PC";
            case NINTENDO_SWITCH:
                return "Nintendo Switch";
            case XBOX_ONE:
                return "Xbox One";
            case XBOX_SERIES_X:
                return "Xbox Series X";
            case PLAYSTATION_4:
                return "PlayStation 4";
            case PLAYSTATION_5:
                return "PlayStation 5";
            case SELECT_PLATFORM:
                return "Select Platform";
            default:
                return "NA";
        }
    }

}
