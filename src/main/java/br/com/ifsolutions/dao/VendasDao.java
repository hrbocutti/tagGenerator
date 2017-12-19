package br.com.ifsolutions.dao;

import br.com.ifsolutions.entity.Venda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class VendasDao {
    public ArrayList<Venda> findAll() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            String url = "jdbc:firebirdsql:localhost/3050:c:/CPLUS.FDB";
            conn = DriverManager.getConnection(url, "sysdba", "masterkey");

            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("yyyy-MM-dd");
            Date date = new Date();
            String dateFormated = dateFormat.format(date);

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM MOVENDA;");

            return this.vendasInterator(rs);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void findByCodVenda() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
    }

    private ArrayList<Venda> vendasInterator(ResultSet rs){
        ArrayList<Venda> vendas = new ArrayList<>();
        try{
            while (rs.next()){
                Venda venda = new Venda();
                venda.setCodmovenda(rs.getString("CODMOVENDA"));
                venda.setCodEmpresa(rs.getString("CODEMPRESA"));
                venda.setNomeCliente(rs.getString("NOMECLI"));
                venda.setDataVenda(rs.getString("DATA"));
                venda.setNumNota(rs.getString("NUMNOTA"));
                //venda.setProdutos(rs.getString("NUMNOTA"));
                vendas.add(venda);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return vendas;
    }
}
