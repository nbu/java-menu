package java_menu;

import java.util.LinkedList;
import java.util.List;

public abstract class MenuItem {
    private final String title;
    private final List<MenuOption<?>> options;

    public MenuItem(String title) {
        this.title = title;
        this.options = new LinkedList<>();
    }

    public<T> void addOption(MenuOption<T> option) {
        options.add(option);
    }

    String getTitle() {
        return title;
    }

    List<MenuOption<?>> getOptions() {
        return options;
    }

    protected abstract void handler(MenuCallValues values);
}
