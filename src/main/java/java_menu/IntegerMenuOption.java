package java_menu;

public class IntegerMenuOption extends MenuOption<Integer> {
    public IntegerMenuOption(String title, String shortForm) {
        super(title, shortForm);
    }

    @Override
    public Integer asObject(String str) throws InvalidMenuOptionValueException {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new InvalidMenuOptionValueException(String.format("Invalid integer number: [%s]", str));
        }
    }
}
