package br.com.ifsolutions.dao;
import br.com.ifsolutions.entity.Produtos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDao{
    public List<Produtos> findAll(){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            String url = "jdbc:firebirdsql:localhost/3050:c:/CPLUS.FDB";
            conn = DriverManager.getConnection(url, "sysdba", "masterkey");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT PROD.CODPROD, PROD.NOMEPROD, PROD.SERIALCARACTERES COR, PROD.APLICACAO COMPOSICAO, \n" +
                    "LOCALI.DESCRICAO as ORIGEM, UN.UNIDADE, FAB.NOMEFABRICANTE TAMANHO FROM PRODUTO PROD\n" +
                    "JOIN LOCALIZACAO LOCALI ON PROD.CODLOC = LOCALI.CODLOC\n" +
                    "JOIN UNIDADE UN ON PROD.UNIDADE = UN.UNIDADE\n" +
                    "JOIN FABRICANTE FAB ON PROD.CODFABRICANTE = FAB.CODFABRICANTE;");

            ArrayList<Produtos> produtos = new ArrayList<Produtos>();

            while (rs.next()){
                Produtos produto = new Produtos();
                produto.setCodigo(rs.getString("CODPROD"));
                produto.setNome(rs.getString("NOMEPROD"));
                produto.setDescricao(rs.getString("NOMEPROD"));
                produto.setOrigem(rs.getString("ORIGEM"));
                produto.setComposicao(rs.getString("COMPOSICAO"));
                produto.setCor(rs.getString("COR"));
                produto.setQuantidade(null);
                produto.setUnidade_medida(rs.getString("UNIDADE"));
                produto.setTamanho(rs.getString("TAMANHO"));
                produtos.add(produto);
            }

            return produtos;

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
            rs = stmt.executeQuery("SELECT PROD.CODPROD, PROD.NOMEPROD, PROD.DESCRICAOEMBALAGEM, PROD.SERIALCARACTERES COR, PROD.APLICACAO COMPOSICAO, \n" +
                    "LOCALI.DESCRICAO as ORIGEM, UN.UNIDADE, FAB.NOMEFABRICANTE TAMANHO FROM PRODUTO PROD\n" +
                    "JOIN LOCALIZACAO LOCALI ON PROD.CODLOC = LOCALI.CODLOC\n" +
                    "JOIN UNIDADE UN ON PROD.UNIDADE = UN.UNIDADE\n" +
                    "JOIN FABRICANTE FAB ON PROD.CODFABRICANTE = FAB.CODFABRICANTE WHERE PROD.CODPROD = "+ codProd +";");

            ArrayList<Produtos> produtos = new ArrayList<Produtos>();

            while (rs.next()){
                Produtos produto = new Produtos();
                produto.setCodigo(rs.getString("CODPROD"));
                produto.setNome(rs.getString("NOMEPROD"));
                produto.setDescricao(rs.getString("DESCRICAOEMBALAGEM"));
                produto.setOrigem(rs.getString("ORIGEM"));
                produto.setComposicao(rs.getString("COMPOSICAO"));
                produto.setCor(rs.getString("COR"));
                produto.setQuantidade(null);
                produto.setUnidade_medida(rs.getString("UNIDADE"));
                produto.setTamanho(rs.getString("TAMANHO"));
                produtos.add(produto);
            }

            return produtos;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
