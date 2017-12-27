package br.com.ifsolutions.dao;

import br.com.ifsolutions.entity.Cliente;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ClienteDao {
    public ArrayList<Cliente> findAll() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            String url = "jdbc:firebirdsql:localhost/3050:c:/CPLUS.FDB";
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
            String url = "jdbc:firebirdsql:localhost/3050:c:/CPLUS.FDB";
            conn = DriverManager.getConnection(url, "sysdba", "masterkey");
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM CLIENTE WHERE NOMECLI CONTAINING '"+cliName+"';");
            return this.clienteInterator(rs);
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
}