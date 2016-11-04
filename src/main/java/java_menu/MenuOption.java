package java_menu;

public abstract class MenuOption<T> {
    private final String title;
    private final String shortForm;

    public MenuOption(String title, String shortForm) {
        this.title = title;
        this.shortForm = shortForm;
    }

    public String getTitle() {
        return title;
    }

    public String getShortForm() {
        return shortForm;
    }

    public abstract T asObject(String str) throws InvalidMenuOptionValueException;
}
