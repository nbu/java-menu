package java_menu;

import java.util.Set;

public class StringMenuOption extends MenuOption<String> {
    private final Set<String> availableOptions;

    public StringMenuOption(String title, String shortForm) {
        super(title, shortForm);

        this.availableOptions = null;
    }

    public StringMenuOption(String title, String shortForm, Set<String> availableOptions) {
        super(title, shortForm);

        this.availableOptions = availableOptions;
    }

    @Override
    public String asObject(String str) throws InvalidMenuOptionValueException {
        if (availableOptions != null && !availableOptions.contains(str)) {
            throw new InvalidMenuOptionValueException(String.format("Not allowed value [%s}", str));
        }

        return str;
    }
}
