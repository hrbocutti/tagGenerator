package br.com.ifsolutions.controller;

import br.com.ifsolutions.model.SqliteModel;
import br.com.ifsolutions.view.SqliteView;

public class SQLiteController {

    public boolean checkIfHaveSqliteBase(String path){

        SqliteModel sqliteModel = new SqliteModel();
        if(!sqliteModel.verifySqlite(path)){
            this.createDataBase(path);
            this.createTable(path);
            this.renderViewDB();
            return true;
        }else{
            this.renderViewDB();
            return false;
        }
    }

    private void renderViewDB() {
        SqliteView sqliteView = new SqliteView();
        String msg;
        sqliteView.renderViewAlert(msg = "Success !");
    }

    private void createTable(String url) {
        String tableName = "tb_settings";
        SqliteModel sqliteModel = new SqliteModel();
        sqliteModel.createTable(url, tableName);
    }

    private void createDataBase(String path){
        String url = "jdbc:sqlite:" + path;
        SqliteModel sqliteModel = new SqliteModel();
        sqliteModel.createDataBase(url);
    }

}
