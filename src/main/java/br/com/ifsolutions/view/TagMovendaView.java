package br.com.ifsolutions.view;

import br.com.ifsolutions.controller.TagController;
import br.com.ifsolutions.entity.Venda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TagMovendaView {
    private JTextField textField1;
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
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Container container = frame.getContentPane();
        container.add(mainPanel);

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                TagController menu = new TagController();
                menu.renderTagMenu();
            }
        });

        btnFiltrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Btn " + btnFiltrar.getName() + " Clicado");
            }
        });

        pesquisarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Btn " + pesquisarButton.getName() + " Clicado");
            }
        });

        btnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }
}
