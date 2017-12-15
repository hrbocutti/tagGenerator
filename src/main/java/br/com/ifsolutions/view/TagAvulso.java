package br.com.ifsolutions.view;

import br.com.ifsolutions.controller.ReportController;
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
import java.util.Map;

public class TagAvulso {
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

    public void renderView(final List<Produtos> products) {

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
                    DefaultTableModel defaultTableModel = new DefaultTableModel();
                    defaultTableModel.addColumn("Cod");
                    defaultTableModel.addColumn("Nome");
                    defaultTableModel.addColumn("ORIGEM");
                    defaultTableModel.addColumn("UN");
                    defaultTableModel.addColumn("Tamanho");
                    if(products.isEmpty()){
                        defaultTableModel.addRow(new String[]{"Sem Informação"});
                    }else{
                        for (Produtos product : products) {
                            defaultTableModel.addRow(new String[]{product.getCodigo(),
                                    product.getNome(),
                                    product.getOrigem(),
                                    product.getUnidade_medida(),
                                    product.getTamanho()});
                        }
                    }

                    table.setModel(defaultTableModel);
                }
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

    public void printAvulso(Integer rowId, TableModel model){

        Produtos produtos = new Produtos();

        produtos.setCodigo((String) model.getValueAt(rowId, 0));
        produtos.setNome((String) model.getValueAt(rowId, 1));

        List<Produtos> listProdutos = new ArrayList<Produtos>();
        listProdutos.add(produtos);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listProdutos);
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("ETIQUETA", dataSource);

        ReportController reportController = new ReportController();
        reportController.reportGenerate("C:\\Users\\DEV01\\JaspersoftWorkspace\\MyReports\\avulso.jasper", parameters);
    }
}
