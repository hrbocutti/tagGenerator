package br.com.ifsolutions.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuView {
    private JPanel MainPanel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel bottomPanel;
    private JButton btnAvulso;
    private JButton btnVenda;
    private JButton btnSair;
    private JButton btnReferencia;
    private JFrame mainFrame;


    public MenuView() {
        mainFrame = new JFrame("Menu");
        mainFrame.setSize(500,250);
        mainFrame.setVisible(true);
        mainFrame.getContentPane().add(MainPanel);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation(dim.width/2-mainFrame.getSize().width/2, dim.height/2-mainFrame.getSize().height/2);


        btnAvulso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                TagAvulsoView tagAvulsoView = new TagAvulsoView();
            }
        });
        btnVenda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                TagMovendaView tagMovendaView = new TagMovendaView();
            }
        });

        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
