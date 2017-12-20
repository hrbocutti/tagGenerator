package br.com.ifsolutions.dao;

import br.com.ifsolutions.entity.Produtos;
import br.com.ifsolutions.entity.Venda;

import java.sql.*;
import java.util.ArrayList;

public class VendasDao {
    public ArrayList<Venda> findAll(String dateOf, String dateTo) {
        Connection conn;
        Statement stmt;
        ResultSet rs;

        try{
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            String url = "jdbc:firebirdsql:localhost/3050:c:/CPLUS.FDB";
            conn = DriverManager.getConnection(url, "sysdba", "masterkey");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT venda.CODMOVENDA, venda.CODCLI, cli.NOMECLI, venda.DATA, venda.NUMNOTA, venda.CODEMPRESA, venda.NUMCUPOM FROM MOVENDA venda\n" +
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
            String url = "jdbc:firebirdsql:localhost/3050:c:/CPLUS.FDB";
            conn = DriverManager.getConnection(url, "sysdba", "masterkey");

            stmt = conn.createStatement();

            ArrayList<Venda> venda = this.findByCodVenda(codMovenda);

            rs = stmt.executeQuery(
                    "SELECT venda.CODMOVENDA, prodVenda.CODPROD, prodVenda.QUANTIDADE, " +
                    "produto.NOMEPROD, produto.DESCRICAOEMBALAGEM, produto.SERIALCARACTERES COR, \n" +
                    "produto.APLICACAO COMPOSICAO, LOCALI.DESCRICAO as ORIGEM, UN.UNIDADE, FAB.NOMEFABRICANTE TAMANHO FROM MOVENDA venda \n" +
                    "JOIN MOVENDAPROD prodVenda ON venda.CODMOVENDA = prodVenda.CODMOVENDA \n" +
                    "JOIN PRODUTO produto ON produto.CODPROD = prodVenda.CODPROD\n" +
                    "JOIN LOCALIZACAO LOCALI ON produto.CODLOC = LOCALI.CODLOC\n" +
                    "JOIN UNIDADE UN ON produto.UNIDADE = UN.UNIDADE\n" +
                    "JOIN FABRICANTE FAB ON produto.CODFABRICANTE = FAB.CODFABRICANTE\n" +
                    "WHERE venda.CODMOVENDA = '"+ codMovenda +"';");

            ArrayList<Produtos> produtos = this.produtosOrderInterator(rs);
            produtos.get(0);

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
            String url = "jdbc:firebirdsql:localhost/3050:c:/CPLUS.FDB";
            conn = DriverManager.getConnection(url, "sysdba", "masterkey");

            stmt = conn.createStatement();
            rs = stmt.executeQuery(
                    "SELECT venda.CODMOVENDA, venda.CODCLI, cli.NOMECLI, venda.DATA, venda.NUMNOTA, venda.CODEMPRESA, venda.NUMCUPOM  FROM MOVENDA venda\n" +
                            "JOIN CLIENTE cli ON venda.CODCLI = cli.CODCLI\n" +
                            "WHERE venda.CODMOVENDA = '"+ codMovenda +"';");

            return this.vendasInterator(rs);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    private ArrayList<Venda> vendasInterator(ResultSet rs){
        ArrayList<Venda> vendas = new ArrayList<>();
        try {
            while (rs.next()) {
                Venda venda = new Venda();
                venda.setCodmovenda(rs.getString("CODMOVENDA"));
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
                produto.setCodigo(rs.getString("CODPROD"));
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
}
