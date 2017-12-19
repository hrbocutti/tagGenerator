package br.com.ifsolutions.view;

import br.com.ifsolutions.controller.TagController;
import br.com.ifsolutions.dao.VendasDao;
import br.com.ifsolutions.entity.Venda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TagMovendaView {
    private JTextField codVendaField;
    private JButton pesquisarButton;
    private JPanel topPanel;
    private JTable tableVendas;
    private JPanel middlePanel;
    private JButton btnCancel;
    private JButton btnPrint;
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JPanel datePanel;
    private JTextField dateOf;
    private JTextField dateTo;
    private JButton btnFiltrar;

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
                System.out.println("btn pesquisar !");
                String codVenda = codVendaField.getText();
                if (!codVenda.isEmpty()){
                    VendasDao dao = new VendasDao();
                    dao.findByCodVenda();
                }else{
                    VendasDao dao = new VendasDao();
                    ArrayList<Venda> vendas = dao.findAll();
                }

            }
        });

        btnFiltrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Btn " + btnFiltrar.getName() + " Clicado");
            }
        });

        btnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }
}
