package br.com.ifsolutions.view;

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
                codeTableModel.addColumn("Cod");
                codeTableModel.addColumn("Nome");
                codeTableModel.addColumn("ORIGEM");
                codeTableModel.addColumn("UN");
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
                nameTablemodel.addColumn("Cod");
                nameTablemodel.addColumn("Nome");
                nameTablemodel.addColumn("ORIGEM");
                nameTablemodel.addColumn("UN");
                nameTablemodel.addColumn("Tamanho");
                String nameProduct = fieldName.getText();
                ProductDao dao = new ProductDao();
                ArrayList<Produtos> items = dao.listByNameProduto(nameProduct);
                if(items.isEmpty()){
                    nameTablemodel.addRow(new String[]{"Sem Informação"});
                }
                for (Produtos product : items) {
                    nameTablemodel.addRow(new String[]{product.getCodigo(),
                            product.getNome(),
                            product.getOrigem(),
                            product.getUnidade_medida(),
                            product.getTamanho()});
                }
                table.setModel(nameTablemodel);
            }

            private void findAll() {
                defaultTableModel = new DefaultTableModel();
                defaultTableModel.addColumn("Cod");
                defaultTableModel.addColumn("Nome");
                defaultTableModel.addColumn("ORIGEM");
                defaultTableModel.addColumn("UN");
                defaultTableModel.addColumn("Tamanho");
                ProductDao dao = new ProductDao();
                ArrayList<Produtos> items = dao.findAll();

                if(items.isEmpty()){
                    defaultTableModel.addRow(new String[]{"Sem Informação"});
                }

                for (Produtos product : items) {
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
                TagView tagView = new TagView();
                tagView.renderMenu();
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
