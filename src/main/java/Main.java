import br.com.ifsolutions.controller.MenuController;
import br.com.ifsolutions.controller.SQLiteController;
import br.com.ifsolutions.controller.SettingsController;
import br.com.ifsolutions.controller.TagController;

public class Main {
    public static void main(String args[]) {
        //read settings
        //SettingsController settingsController = new SettingsController();
        //settingsController.readSettings();

        MenuController menuController = new MenuController();
        menuController.startApplication();

    }
}