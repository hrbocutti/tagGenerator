package br.com.ifsolutions.dao;

import br.com.ifsolutions.controller.SettingsController;
import br.com.ifsolutions.entity.Cliente;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClienteDao {
    public ArrayList<Cliente> findAll() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            String url = getUrlDb();
            conn = DriverManager.getConnection(url, "sysdba", "masterkey");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM CLIENTE");
            return this.clienteInterator(rs);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Cliente> findByName(String cliName) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            String url = getUrlDb();
            conn = DriverManager.getConnection(url, "sysdba", "masterkey");
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM CLIENTE WHERE NOMECLI CONTAINING '"+cliName+"';");
            return this.clienteInterator(rs);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Cliente> findById(String codCli) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            String url = getUrlDb();
            conn = DriverManager.getConnection(url, "sysdba", "masterkey");
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM CLIENTE WHERE CODCLI = '"+codCli+"';");
            return this.clienteInterator(rs);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Cliente> findByCodVenda(String codVenda) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            String url = getUrlDb();
            conn = DriverManager.getConnection(url, "sysdba", "masterkey");
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT cli.* FROM CLIENTE cli JOIN MOVENDA venda ON venda.CODCLI = cli.CODCLI WHERE venda.NUMPED = '"+codVenda+"';");
            return this.clienteInterator(rs);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Cliente> findByIdFardo(String codCli) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            String url = getUrlDb();
            conn = DriverManager.getConnection(url, "sysdba", "masterkey");
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM CLIENTE WHERE CODCLI = '"+codCli+"';");
            return this.clienteInteratorFardo(rs);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    private ArrayList<Cliente> clienteInterator(ResultSet rs){
        ArrayList<Cliente> clientes = new ArrayList<>();
        try{
            while (rs.next()){
                Cliente cliente = new Cliente();
                cliente.setCode(rs.getString("CODCLI"));
                cliente.setName(rs.getString("NOMECLI"));
                cliente.setAddress(rs.getString("ENDERECO"));
                cliente.setNeighborhood(rs.getString("BAIRRO"));
                cliente.setCity(rs.getString("CIDADE"));
                cliente.setState(rs.getString("ESTADO"));
                cliente.setCEP(rs.getString("CEP"));
                cliente.setPhone(rs.getString("TELEFONE"));
                clientes.add(cliente);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return clientes;
    }

    private ArrayList<Cliente> clienteInteratorFardo(ResultSet rs){
        ArrayList<Cliente> clientes = new ArrayList<>();
        try{
            while (rs.next()){
                String DOCUMENTO, DOCTIPO;
                if (!rs.getString("CNPJ").isEmpty()){
                    DOCTIPO = "CNPJ";
                    DOCUMENTO = rs.getString("CNPJ");
                }else{
                    DOCTIPO = "CPF";
                    DOCUMENTO = rs.getString("CPF");
                }

                Cliente cliente = new Cliente();
                cliente.setCode(rs.getString("CODCLI"));
                cliente.setName(rs.getString("CONJFANTASIA"));
                cliente.setAddress(rs.getString("ENDERECO")
                        + ", " + rs.getString("NUMEROLOGRADOURO")
                        + " - " + rs.getString("COMPLEMENTOLOGRADOURO"));
                cliente.setNeighborhood(rs.getString("BAIRRO"));
                cliente.setCity(rs.getString("CIDADE"));
                cliente.setState(rs.getString("ESTADO"));
                cliente.setCEP(rs.getString("CEP"));
                cliente.setPhone(rs.getString("TELEFONE"));
                clientes.add(cliente);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return clientes;
    }

    private String getUrlDb(){
        SettingsController settingsController = new SettingsController();
        HashMap<String, String> settings = settingsController.readSettings();

        String url = "jdbc:firebirdsql:localhost/3050:";

        for (Map.Entry<String,String> setting : settings.entrySet()) {
            String key = setting.getKey();
            if (key.equals("db")){
                String value = setting.getValue();
                url = url + value;
            }
        }
        return url;
    }

}