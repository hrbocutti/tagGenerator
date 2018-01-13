package br.com.ifsolutions.dao;

import br.com.ifsolutions.controller.SettingsController;
import br.com.ifsolutions.entity.Produtos;
import br.com.ifsolutions.entity.Venda;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VendasDao {
    public ArrayList<Venda> findAll(String dateOf, String dateTo) {
        Connection conn;
        Statement stmt;
        ResultSet rs;

        try{
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            String url = getUrlDb();
            conn = DriverManager.getConnection(url, "sysdba", "masterkey");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT venda.CODMOVENDA, venda.NUMPED, venda.CODCLI, cli.NOMECLI, venda.DATA, venda.NUMNOTA, venda.CODEMPRESA, venda.NUMCUPOM FROM MOVENDA venda\n" +
                                        "JOIN CLIENTE cli ON venda.CODCLI = cli.CODCLI WHERE venda.DATA >= '"+ dateOf  +"' AND venda.DATA <= '"+ dateTo +"' ");


            return this.vendasInterator(rs);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Venda> listOrderItems(String codMovenda) {
        Connection conn;
        Statement stmt;
        ResultSet rs;

        try{
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            String url = getUrlDb();
            conn = DriverManager.getConnection(url, "sysdba", "masterkey");
            stmt = conn.createStatement();

            ArrayList<Venda> venda = this.findByCodVenda(codMovenda);

            rs = stmt.executeQuery(
                    "SELECT venda.CODMOVENDA, prodVenda.CODPROD, prodVenda.QUANTIDADE, " +
                    "produto.NOMEPROD, produto.CODIGO, produto.DESCRICAOEMBALAGEM, produto.SERIALCARACTERES COR, \n" +
                    "produto.APLICACAO COMPOSICAO, LOCALI.DESCRICAO as ORIGEM, UN.UNIDADE, FAB.NOMEFABRICANTE TAMANHO FROM MOVENDA venda \n" +
                    "JOIN MOVENDAPROD prodVenda ON venda.CODMOVENDA = prodVenda.CODMOVENDA \n" +
                    "JOIN PRODUTO produto ON produto.CODPROD = prodVenda.CODPROD\n" +
                    "JOIN LOCALIZACAO LOCALI ON produto.CODLOC = LOCALI.CODLOC\n" +
                    "JOIN UNIDADE UN ON produto.UNIDADE = UN.UNIDADE\n" +
                    "JOIN FABRICANTE FAB ON produto.CODFABRICANTE = FAB.CODFABRICANTE\n" +
                    "WHERE venda.NUMPED = '"+ codMovenda +"';");

            ArrayList<Produtos> produtos = this.produtosOrderInterator(rs);
            venda.get(0).setProdutos(produtos);
            return venda;

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Venda> findByCodVenda(String codMovenda) {
        Connection conn;
        Statement stmt;
        ResultSet rs;

        try{
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            String url = getUrlDb();
            conn = DriverManager.getConnection(url, "sysdba", "masterkey");

            stmt = conn.createStatement();
            rs = stmt.executeQuery(
                    "SELECT venda.CODMOVENDA, venda.NUMPED, venda.CODCLI, cli.NOMECLI, venda.DATA, venda.NUMNOTA, venda.CODEMPRESA, venda.NUMCUPOM FROM MOVENDA venda\n" +
                            "JOIN CLIENTE cli ON venda.CODCLI = cli.CODCLI\n" +
                            "WHERE venda.NUMPED = '"+ codMovenda +"';");
            return this.vendasInterator(rs);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public String findVolumesByVenda(String numped){
        Connection conn;
        Statement stmt;
        ResultSet rs;
        String volumes = null;

        try{
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            String url = getUrlDb();
            conn = DriverManager.getConnection(url, "sysdba", "masterkey");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT venda.QUANTIDADEVOLUMES as VOLUMES FROM MOVENDA venda WHERE venda.NUMPED = '" + numped + "';");

            while (rs.next()){
                volumes = rs.getString("VOLUMES");
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return volumes;
    }

    private ArrayList<Venda> vendasInterator(ResultSet rs){
        ArrayList<Venda> vendas = new ArrayList<>();
        try {
            while (rs.next()) {
                Venda venda = new Venda();
                venda.setCodmovenda(rs.getString("NUMPED"));
                venda.setCodEmpresa(rs.getString("CODEMPRESA"));
                venda.setNomeCliente(rs.getString("NOMECLI"));
                venda.setDataVenda(rs.getString("DATA"));
                venda.setNumNota(rs.getString("NUMNOTA"));
                venda.setNumCupom(rs.getString("NUMCUPOM"));
                vendas.add(venda);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return vendas;
    }

    private ArrayList<Produtos> produtosOrderInterator(ResultSet rs){
        ArrayList<Produtos> produtos = new ArrayList<>();
        try {
            while (rs.next()){
                Produtos produto = new Produtos();
                produto.setCodigo(rs.getString("CODIGO"));
                produto.setNome(rs.getString("NOMEPROD"));
                produto.setDescricao(rs.getString("DESCRICAOEMBALAGEM"));
                produto.setOrigem(rs.getString("ORIGEM"));
                produto.setComposicao(rs.getString("COMPOSICAO"));
                produto.setCor(rs.getString("COR"));
                produto.setQuantidade(rs.getString("QUANTIDADE"));
                produto.setTamanho(rs.getString("TAMANHO"));
                produto.setUnidade_medida(rs.getString("UNIDADE"));
                produtos.add(produto);
            }
            return produtos;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
