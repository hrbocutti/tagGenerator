package br.com.ifsolutions.view;

import br.com.ifsolutions.controller.ReportController;
import br.com.ifsolutions.entity.Produtos;

import javax.swing.*;
import javax.swing.event.RowSorterListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        String codeProduct = (String) model.getValueAt(rowId, 0);
        String nameProduct = (String) model.getValueAt(rowId, 1);
        //Map<String, String> map = new HashMap<>();
        HashMap<String, String> map = new HashMap<>();

        map.put("CODE", codeProduct);
        map.put("TITLE", nameProduct);

        ReportController reportController = new ReportController();
        reportController.reportGenerate("/home/higor/JaspersoftWorkspace/MyReports/avulso.jasper", map);
    }
}
