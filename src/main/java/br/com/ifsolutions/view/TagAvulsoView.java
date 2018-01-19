package br.com.ifsolutions.view;

import br.com.ifsolutions.controller.MenuController;
import br.com.ifsolutions.controller.ReportController;
import br.com.ifsolutions.dao.ProductDao;
import br.com.ifsolutions.entity.Cliente;
import br.com.ifsolutions.entity.Produtos;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TagAvulsoView {
    private JButton pesquisarButton;
    private JTextField fieldCode;
    private JTextField fieldName;
    private JPanel panelTop;
    private JPanel mainPanel;
    private JPanel panelMiddle;
    private JTable table;
    private JScrollPane scrollPanel;
    private JPanel panelBottom;
    private JButton btnPrint;
    private JButton btnCancel;
    private DefaultTableModel defaultTableModel;
    private DefaultTableModel codeTableModel;
    private DefaultTableModel nameTablemodel;

    public TagAvulsoView() {

        final JFrame frame = new JFrame("Avulso");
        frame.setSize(800,600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container container = frame.getContentPane();
        container.add(mainPanel);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);

        pesquisarButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(fieldCode.getText().isEmpty() && fieldName.getText().isEmpty()){
                    this.findAll();
                }else  if(!fieldCode.getText().isEmpty() && fieldName.getText().isEmpty()){
                    this.findByCode();
                }else  if(fieldCode.getText().isEmpty() && !fieldName.getText().isEmpty()){
                    this.findByName();
                }
            }

            private void findByCode() {
                codeTableModel = new DefaultTableModel();
                codeTableModel.addColumn("Codigo Produto");
                codeTableModel.addColumn("Nome Produto");
                codeTableModel.addColumn("Origem");
                codeTableModel.addColumn("Unidade");
                codeTableModel.addColumn("Tamanho");
                String codProduto = fieldCode.getText();
                ProductDao dao = new ProductDao();
                ArrayList<Produtos> items = dao.listByCodProduto(codProduto);

                if(items.isEmpty()){
                    codeTableModel.addRow(new String[]{"Sem Informação"});
                }

                for (Produtos product : items) {
                    codeTableModel.addRow(new String[]{product.getCodigo(),
                            product.getNome(),
                            product.getOrigem(),
                            product.getUnidade_medida(),
                            product.getTamanho()});
                }
                table.setModel(codeTableModel);
            }

            private void findByName() {
                nameTablemodel = new DefaultTableModel();
                nameTablemodel.addColumn("Codigo Produto");
                nameTablemodel.addColumn("Nome Produto");
                nameTablemodel.addColumn("Origem");
                nameTablemodel.addColumn("Unidade");
                nameTablemodel.addColumn("Tamanho");
                String nameProduct = fieldName.getText();
                ProductDao dao = new ProductDao();
                ArrayList<Produtos> items = dao.listByNameProduto(nameProduct);
                if(items == null){
                    nameTablemodel.addRow(new String[]{"Sem Informação"});
                }
                for (Produtos product : items) {

                    if(product.getCodigo() == null){
                        continue;
                    }

                    if(product.getNome() == null){
                        continue;
                    }

                    if(product.getOrigem() == null){
                        continue;
                    }

                    if(product.getUnidade_medida() == null){
                        continue;
                    }

                    if(product.getTamanho() == null){
                        continue;
                    }

                    nameTablemodel.addRow(new String[]{
                            product.getCodigo(),
                            product.getNome(),
                            product.getOrigem(),
                            product.getUnidade_medida(),
                            product.getTamanho()});
                }
                table.setModel(nameTablemodel);
            }

            private void findAll() {
                defaultTableModel = new DefaultTableModel();
                defaultTableModel.addColumn("Cod Produto");
                defaultTableModel.addColumn("Nome Produto");
                defaultTableModel.addColumn("Origem");
                defaultTableModel.addColumn("Unidade");
                defaultTableModel.addColumn("Tamanho");
                ProductDao dao = new ProductDao();
                ArrayList<Produtos> items = dao.findAll();

                if(items.isEmpty()){
                    defaultTableModel.addRow(new String[]{"Sem Informação"});
                }

                for (Produtos product : items) {

                    if(product.getCodigo() == null){
                        continue;
                    }

                    if(product.getNome() == null){
                        continue;
                    }

                    if(product.getOrigem() == null){
                        continue;
                    }

                    if(product.getUnidade_medida() == null){
                        continue;
                    }

                    if(product.getTamanho() == null){
                        continue;
                    }

                    defaultTableModel.addRow(new String[]{product.getCodigo(),
                            product.getNome(),
                            product.getOrigem(),
                            product.getUnidade_medida(),
                            product.getTamanho()});
                }
                table.setModel(defaultTableModel);
            }
        });


        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                MenuController menuController = new MenuController();
                menuController.startApplication();
            }
        });

        btnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer idRow = table.getSelectedRow();
                TableModel model = table.getModel();
                printAvulso(idRow, model);
            }
        });
    }

    private void printAvulso(Integer rowId, TableModel model){

        Produtos produtos = new Produtos();

        String codProd = (String) model.getValueAt(rowId, 0);
        ProductDao dao = new ProductDao();
        ArrayList<Produtos> produto = dao.listByCodProduto(codProd);

        if(produto.get(0).getQuantidade() == null){
            JFrame frame = new JFrame("InputDialog Example #2");
            String quantidade = JOptionPane.showInputDialog(
                    frame,
                    "Quantidade para o item",
                    "Selecione a quantidade",
                    JOptionPane.WARNING_MESSAGE
            );
            // if the user presses Cancel, this will be null
            produto.get(0).setQuantidade(quantidade);
        }

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(produto);
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("ETIQUETA", dataSource);

        ReportController reportController = new ReportController();
        reportController.reportGenerate("C:\\tagGenerator\\report\\avulso.jasper", parameters);
    }
}
