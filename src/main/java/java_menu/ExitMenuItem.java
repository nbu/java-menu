package java_menu;

class ExitMenuItem extends MenuItem{
    public ExitMenuItem(String title) {
        super(title);
    }

    @Override
    protected void handler(MenuCallValues values) {
        System.exit(0);
    }
}
