package br.com.ifsolutions.view;

import br.com.ifsolutions.controller.ReportController;
import br.com.ifsolutions.dao.ProductDao;
import br.com.ifsolutions.dao.VendasDao;
import br.com.ifsolutions.entity.Produtos;
import br.com.ifsolutions.entity.Venda;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.xpath.operations.Bool;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class OrderView {

    private JPanel btnPanel;
    private JButton btnCancel;
    private JButton btnPrint;
    private JPanel mainPanel;
    private JTable tableProdutos;
    private JPanel middlePanel;
    private DefaultTableModel defaultTable;

    public OrderView(final String codMovenda) {

        final JFrame frame = new JFrame();
        frame.setSize(800,600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);

        Container container = frame.getContentPane();
        container.add(mainPanel);

        //consulta e preenche a tabela

        VendasDao dao = new VendasDao();
        final ArrayList<Venda> orderItems = dao.listOrderItems(codMovenda);
        //System.out.println(orderItems);
        final ArrayList<Produtos> produtos = orderItems.get(0).getProdutos();
        JCheckBox checkBoxAll = new JCheckBox();
        defaultTable = new DefaultTableModel();
        defaultTable.addColumn("CODIGO");
        defaultTable.addColumn("NOME");
        defaultTable.addColumn("UNIDADE");
        defaultTable.addColumn("QUANTIDADE");

        if (produtos.isEmpty()) {
            defaultTable.addRow(new String[]{"Sem Informação"});
        }


        for (Produtos produto: produtos) {
            JCheckBox boxProd = new JCheckBox();
            defaultTable.addRow(new Object[]{
                    produto.getCodigo(),
                    produto.getNome(),
                    produto.getUnidade_medida(),
                    produto.getQuantidade()});
        }
        tableProdutos.setModel(defaultTable);

        btnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tableProdutos.getSelectedRow() == -1){
                    for (Produtos produto: produtos) {
                        ArrayList productToPrint = new ArrayList<>();
                        Map map = new HashMap();
                        map.put("codmovenda", codMovenda);
                        map.put("nome", produto.getNome());
                        map.put("codigo", produto.getCodigo());
                        map.put("descricao", produto.getDescricao());
                        map.put("cor", produto.getCor());
                        map.put("tamanho", produto.getTamanho());
                        map.put("unidade_medida", produto.getUnidade_medida());
                        map.put("quantidade", produto.getQuantidade());
                        map.put("origem", produto.getOrigem());
                        map.put("composicao", produto.getComposicao());
                        productToPrint.add(map);
                        printTag(productToPrint);
                    }
                }

                if(tableProdutos.getSelectedRow() > -1){
                    ArrayList productToPrint = new ArrayList<>();
                    String codProd = (String) defaultTable.getValueAt(tableProdutos.getSelectedRow(), 0);
                    for (Produtos produto: produtos) {
                        if (Objects.equals(produto.getCodigo(), codProd)){
                            Map map = new HashMap();
                            map.put("codmovenda", codMovenda);
                            map.put("nome", produto.getNome());
                            map.put("codigo", produto.getCodigo());
                            map.put("descricao", produto.getDescricao());
                            map.put("cor", produto.getCor());
                            map.put("tamanho", produto.getTamanho());
                            map.put("unidade_medida", produto.getUnidade_medida());
                            map.put("quantidade", produto.getQuantidade());
                            map.put("origem", produto.getOrigem());
                            map.put("composicao", produto.getComposicao());
                            productToPrint.add(map);
                        }
                    }
                    printTag(productToPrint);
                }
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });
    }

    private void printTag(ArrayList produto){
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(produto);
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("ETIQUETA", dataSource);
        ReportController reportController = new ReportController();
        reportController.reportGenerate("C:\\tagGenerator\\report\\venda.jasper", parameters);
    }
}