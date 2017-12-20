package br.com.ifsolutions.view;

import br.com.ifsolutions.dao.VendasDao;
import br.com.ifsolutions.entity.Venda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OrderView {

    private JPanel btnPanel;
    private JButton btnCancel;
    private JButton btnPrint;
    private JPanel mainPanel;
    private JTable tableProdutos;
    private JPanel topPanel;
    private JTextField textFieldCodVenda;
    private JTextField textFieldNomeCli;
    private JPanel middlePanel;

    public OrderView(String codMovenda) {

        final JFrame frame = new JFrame();
        frame.setSize(800,600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        Container container = frame.getContentPane();
        container.add(mainPanel);

        //consulta e preenche a tabela

        VendasDao dao = new VendasDao();
        ArrayList<Venda> orderItems = dao.listOrderItems(codMovenda);

        btnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}