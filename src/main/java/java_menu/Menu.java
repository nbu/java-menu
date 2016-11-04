package java_menu;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Menu {
    private final String title;
    private final char[] details;
    private final int minSpaceAfterMenuTitle;
    private final ExitMenuItem exitMenuItem;
    private List<MenuItem> menuItems;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private char[] startOfMenu;
    private char[] endOfMenu;
    private char[] inputPrompt;
    private int maxLength;
    private int exitIndex;

    public Menu(String title, char[] details) {
        this.title = title;
        this.menuItems = new LinkedList<>();
        this.printWriter = new PrintWriter(new OutputStreamWriter(System.out));
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        this.startOfMenu = "*****************************Main menu********************************".toCharArray();
        this.endOfMenu   = "****************************End of menu*******************************".toCharArray();
        this.inputPrompt = "$> ".toCharArray();
        this.minSpaceAfterMenuTitle = 10;
        this.exitMenuItem = new ExitMenuItem("Exit");
        this.details = details;
    }

    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
        if (menuItem.getTitle().length() > maxLength) {
            maxLength = menuItem.getTitle().length();
        }
    }

    public void start() {
        while (true) {
            boolean doExit = false;
            try {
                doExit = showMenu();
            } catch (IOException ignore) {}

            if (doExit) {
                break;
            }
        }
    }

    private boolean showMenu() throws IOException {
        printHeader();
        int i = 0;

        for (MenuItem menuItem : menuItems) {
            printMenuItem(menuItem, ++i);
        }

        printMenuItem(exitMenuItem, ++i);
        exitIndex = i;

        outputToConsole(endOfMenu);
        eol();
        printInputPrompt();

        flush();
        String cmd = bufferedReader.readLine();
        if (cmd == null) {
            printInvalidInputWarning();
            return false;
        }

        String[] args = cmd.split(" ");

        int cmdId;

        try {
            cmdId = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            printInvalidInputWarning();
            return false;
        }

        if (cmdId == exitIndex) {
            return true;
        }

        return processCommandArguments(cmdId, args);
    }

    private boolean processCommandArguments(int cmdId, String[] args) throws IOException {
        int index = cmdId - 1;
        if (index < 0 || index > menuItems.size() - 1) {
            printInvalidInputWarning();
            return false;
        }

        MenuItem menuItem = menuItems.get(index);
        List<MenuOption<?>> options = menuItem.getOptions();

        index = 1;
        MenuCallValues values = new MenuCallValues();

        if (options.size() != args.length - 1) {
            printInvalidInputWarning();
            return false;
        }

        for (MenuOption<?> option : options) {
            try {
                Object value = option.asObject(args[index++]);
                values.addValue(option.getShortForm(), value);
            } catch (InvalidMenuOptionValueException e) {
                printInvalidInputWarning();
                return false;
            }
        }

        try {
            menuItem.handler(values);
        } catch (Exception e) {
            printErrorDuringExecution(e);
            return false;
        }

        pressEnterToContinue();
        return false;
    }

    private void pressEnterToContinue() throws IOException {
        outputToConsole("Press Enter to continue...");
        flush();
        bufferedReader.readLine();
    }

    private void printErrorDuringExecution(Exception e) throws IOException {
        outputToConsole("Error during command execution", e);
        eol();
        flush();
        pressEnterToContinue();
    }

    private void printInvalidInputWarning() throws IOException {
        outputToConsole("Invalid input");
        eol();
        flush();
        pressEnterToContinue();
    }

    private void printHeader() throws IOException {
        outputToConsole(title);
        eol();
        if (details != null) {
            outputToConsole(details);
            eol();
        }
        outputToConsole(startOfMenu);
        eol();
        String title = "Title";
        outputToConsole(title);

        printSpaces(maxLength - title.length() + minSpaceAfterMenuTitle);
        outputToConsole("Command");
        eol();
    }

    private void printMenuItem(MenuItem menuItem, int index) throws IOException {
        String title = index + ". " + menuItem.getTitle();
        outputToConsole(title);
        printSpaces(maxLength - title.length() + minSpaceAfterMenuTitle);
        printOptions(menuItem.getOptions(), index);
        eol();
    }

    private void printOptions(List<MenuOption<?>> options, int index) throws IOException {
        outputToConsole(index);

        for (MenuOption option : options) {
            outputToConsole(String.format(" [%s]", option.getTitle()));
        }
    }

    private void printSpaces(int len) throws IOException {
        outputToConsole(new String(new char[len]).replace("\0", " "));
    }

    private void flush() throws IOException {
        printWriter.flush();
    }

    private void eol() throws IOException {
        printWriter.println();
    }

    private void printInputPrompt() throws IOException {
        outputToConsole(inputPrompt);
    }

    private void outputToConsole(String str, Exception e) throws IOException {
        printWriter.write(str);
        e.printStackTrace(printWriter);
    }

    private void outputToConsole(String str) throws IOException {
        printWriter.write(str);
    }

    private void outputToConsole(char[] chars) throws IOException {
        printWriter.write(chars);
    }

    private void outputToConsole(int num) throws IOException {
        printWriter.write(Integer.toString(num));
    }
}
