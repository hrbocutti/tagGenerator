package br.com.ifsolutions.view;

import br.com.ifsolutions.controller.TagController;
import br.com.ifsolutions.dao.VendasDao;
import br.com.ifsolutions.entity.Venda;
import org.hibernate.criterion.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TagMovendaView {
    private JTextField codVendaField;
    private JButton pesquisarButton;
    private JPanel topPanel;
    private JTable tableVendas;
    private JPanel middlePanel;
    private JButton btnCancel;
    private JButton btnOpenOrder;
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JPanel datePanel;
    private JTextField dateOf;
    private JTextField dateTo;
    private JButton btnFiltrar;
    private DefaultTableModel defaultTable;

    public TagMovendaView() {

        final JFrame frame = new JFrame();
        frame.setSize(800,600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        Container container = frame.getContentPane();
        container.add(mainPanel);

        //setar data default

        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("dd/MM/yyyy");
        Date date = new Date();

        dateTo.setText(dateFormat.format(date));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.roll(Calendar.DATE, -5);
        date = calendar.getTime();

        dateOf.setText(dateFormat.format(date));

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                TagController menu = new TagController();
                menu.renderTagMenu();
            }
        });

        pesquisarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            if (dateOf.getText().isEmpty() || dateTo.getText().isEmpty()){
                JOptionPane.showMessageDialog(frame, "Data De e Data Por não podem estar em branco\n Padrão: DD/MM/AAAA");

            }else {

                System.out.println("btn pesquisar !");
                String codVenda = codVendaField.getText();
                if (!codVenda.isEmpty()) {
                    VendasDao dao = new VendasDao();
                    dao.findByCodVenda(codVenda);
                } else {
                    defaultTable = new DefaultTableModel();
                    defaultTable.addColumn("CODMOVENDA");
                    defaultTable.addColumn("NOMECLI");
                    defaultTable.addColumn("DATA");
                    defaultTable.addColumn("NUMNOTA");

                    try {

                        SimpleDateFormat dateFormatInput = new SimpleDateFormat("dd/mm/yyyy");
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                        Date dateInitial = dateFormatInput.parse(dateOf.getText());
                        Date dateFinal = dateFormatInput.parse(dateTo.getText());

                        VendasDao dao = new VendasDao();
                        ArrayList<Venda> vendas = dao.findAll(dateFormat.format(dateInitial), dateFormat.format(dateFinal));
                        if (vendas.isEmpty()) {
                            defaultTable.addRow(new String[]{"Sem Informação"});
                        }

                        for (Venda venda : vendas) {
                            defaultTable.addRow(new String[]{
                                    venda.getCodmovenda(),
                                    venda.getNomeCliente(),
                                    venda.getDataVenda(),
                                    venda.getNumNota()});
                        }
                        tableVendas.setModel(defaultTable);
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            }
        });

        btnFiltrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Btn " + btnFiltrar.getName() + " Clicado");
            }
        });

        btnOpenOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer idRow = tableVendas.getSelectedRow();
                String codMovenda = (String) defaultTable.getValueAt(idRow, 0);
                new OrderView(codMovenda);
            }
        });

    }
}
