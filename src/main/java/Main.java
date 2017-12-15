import br.com.ifsolutions.controller.SQLiteController;
import br.com.ifsolutions.controller.TagController;

public class Main {
    public static void main(String args[]) {
        //try to access sqllite

        Boolean baseExist;
        SQLiteController sqlite = new SQLiteController();
        //String sqlitePath = "C:\\tagGenerator\\tagGenerator.sqlite";
        String sqlitePath = "c:/tagGenerator/tagGenerator.sqlite";
        sqlite.checkIfHaveSqliteBase(sqlitePath);
        System.out.println("Entrou aqui");
        //Opções de Impressão e busca
        TagController tagController = new TagController();
        tagController.renderTagMenu();
    }
}