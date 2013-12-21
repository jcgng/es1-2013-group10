package moodishclient.client.states;

/**
 * Created with IntelliJ IDEA.
 * User: J-Pac123
 * Date: 12/9/13
 * Time: 3:31 AM
 * To change this template use File | Settings | File Templates.
 */
public enum Mood {
    CHEATED, CONFUSED, CLUELESS, ANNOYED, PARANOID, SLEEPY, WONDERFUL, HUNGRY, CARELESS, TIRED, AT_EASE, FRAZZLED, CONTENT, LOVED, HAPPY, NEEDY, DETERMINED,
    ACCEPTED, UNCOMFORTABLE, CHALLENGED, CREATIVE, DISGRUNTLED, RUNDOWN, CHEERFUL, SHY, LONELY, BORED, GOOD;

    public static String lookupValue(Mood mood) {
        switch (mood) {
            case CHEATED:
                return "Cheated";
            case CONFUSED:
                return "Confused";
            case CLUELESS:
                return "Clueless";
            case ANNOYED:
                return "Annoyed";
            case PARANOID:
                return "Paranoid";
            case SLEEPY:
                return "Sleepy";
            case WONDERFUL:
                return "Wonderful";
            case HUNGRY:
                return "Hungry";
            case CARELESS:
                return "Careless";
            case TIRED:
                return "Tired";
            case AT_EASE:
                return "At ease";
            case FRAZZLED:
                return "Frazzled";
            case CONTENT:
                return "Content";
            case LOVED:
                return "Loved";
            case HAPPY:
                return "Happy";
            case NEEDY:
                return "Needy";
            case DETERMINED:
                return "Determined";
            case ACCEPTED:
                return "Accepted";
            case UNCOMFORTABLE:
                return "Uncomfortable";
            case CHALLENGED:
                return "Challenged";
            case CREATIVE:
                return "Creative";
            case DISGRUNTLED:
                return "Disgruntled";
            case RUNDOWN:
                return "Rundown";
            case CHEERFUL:
                return "Cheerful";
            case SHY:
                return "Shy";
            case LONELY:
                return "Lonely";
            case BORED:
                return "Bored";
            case GOOD:
                return "Good";
        }
        return "";
    }
}
