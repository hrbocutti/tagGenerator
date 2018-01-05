package br.com.ifsolutions.dao;
import br.com.ifsolutions.entity.Produtos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDao{
    public ArrayList<Produtos> findAll(){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            String url = "jdbc:firebirdsql:localhost/3050:c:/CPLUS.FDB";
            conn = DriverManager.getConnection(url, "sysdba", "masterkey");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT PROD.CODPROD, PROD.CODIGO, PROD.NOMEPROD, PROD.DESCRICAOEMBALAGEM, PROD.SERIALCARACTERES COR, PROD.APLICACAO COMPOSICAO, \n" +
                    "LOCALI.DESCRICAO as ORIGEM, UN.UNIDADE, FAB.NOMEFABRICANTE TAMANHO FROM PRODUTO PROD \n" +
                    "JOIN LOCALIZACAO LOCALI ON PROD.CODLOC = LOCALI.CODLOC \n" +
                    "JOIN UNIDADE UN ON PROD.UNIDADE = UN.CODIGO \n" +
                    "JOIN FABRICANTE FAB ON PROD.CODFABRICANTE = FAB.CODFABRICANTE;");

            return this.productInterator(rs);

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return null;
    }

    public ArrayList<Produtos> listByCodProduto(String codProd) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{

            Class.forName("org.firebirdsql.jdbc.FBDriver");
            String url = "jdbc:firebirdsql:localhost/3050:c:/CPLUS.FDB";
            conn = DriverManager.getConnection(url, "sysdba", "masterkey");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT PROD.CODPROD,PROD.CODIGO, PROD.NOMEPROD, PROD.DESCRICAOEMBALAGEM, PROD.SERIALCARACTERES COR, PROD.APLICACAO COMPOSICAO, \n" +
                    "LOCALI.DESCRICAO as ORIGEM, UN.UNIDADE, FAB.NOMEFABRICANTE TAMANHO FROM PRODUTO PROD\n" +
                    "JOIN LOCALIZACAO LOCALI ON PROD.CODLOC = LOCALI.CODLOC\n" +
                    "JOIN UNIDADE UN ON PROD.UNIDADE = UN.CODIGO\n" +
                    "JOIN FABRICANTE FAB ON PROD.CODFABRICANTE = FAB.CODFABRICANTE WHERE PROD.CODPROD = "+ codProd +";");

            return this.productInterator(rs);
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public ArrayList<Produtos> listByNameProduto(String nomeProduto) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            String url = "jdbc:firebirdsql:localhost/3050:c:/CPLUS.FDB";
            conn = DriverManager.getConnection(url, "sysdba", "masterkey");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT PROD.CODPROD, PROD.CODIGO, PROD.NOMEPROD, PROD.DESCRICAOEMBALAGEM, PROD.SERIALCARACTERES COR, PROD.APLICACAO COMPOSICAO, \n" +
                    "LOCALI.DESCRICAO as ORIGEM, UN.UNIDADE, FAB.NOMEFABRICANTE TAMANHO FROM PRODUTO PROD\n" +
                    "JOIN LOCALIZACAO LOCALI ON PROD.CODLOC = LOCALI.CODLOC\n" +
                    "JOIN UNIDADE UN ON PROD.UNIDADE = UN.CODIGO\n" +
                    "JOIN FABRICANTE FAB ON PROD.CODFABRICANTE = FAB.CODFABRICANTE WHERE PROD.NOMEPROD CONTAINING '"+ nomeProduto +"';");

            return this.productInterator(rs);
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    private ArrayList<Produtos> productInterator(ResultSet rs){
        ArrayList<Produtos> products = new ArrayList<>();
        try{
            while (rs.next()){
                Produtos produto = new Produtos();
                produto.setCodigo(rs.getString("CODIGO"));
                produto.setNome(rs.getString("NOMEPROD"));
                produto.setDescricao(rs.getString("DESCRICAOEMBALAGEM"));
                produto.setOrigem(rs.getString("ORIGEM"));
                produto.setComposicao(rs.getString("COMPOSICAO"));
                produto.setCor(rs.getString("COR"));
                produto.setQuantidade(null);
                produto.setUnidade_medida(rs.getString("UNIDADE"));
                produto.setTamanho(rs.getString("TAMANHO"));
                products.add(produto);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return products;
    }
}
