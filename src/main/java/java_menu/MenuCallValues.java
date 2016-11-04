package java_menu;

import java.util.HashMap;
import java.util.Map;

public class MenuCallValues {
    private Map<String, Object> values;

    public MenuCallValues() {
        values = new HashMap<>();
    }

    public<T> T getValue(String parameterName, Class<T> tClass) {
        return tClass.cast(values.get(parameterName));
    }

    <T> void addValue(String parameterName, T value) {
        values.put(parameterName, value);
    }
}
