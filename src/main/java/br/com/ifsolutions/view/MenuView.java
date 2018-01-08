package br.com.ifsolutions.view;

import br.com.ifsolutions.controller.SettingsController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MenuView {
    private JPanel MainPanel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel bottomPanel;
    private JButton btnAvulso;
    private JButton btnVenda;
    private JButton btnSair;
    private JButton btnReferencia;
    private JButton btnFardo;
    private JFrame mainFrame;


    public MenuView() {
        mainFrame = new JFrame("Menu");
        mainFrame.setSize(500,300);
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
        btnReferencia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SettingsController settingsController = new SettingsController();
                HashMap<String, String> settings = settingsController.readSettings();

                String fileName = "c:\\tagGenerator\\img\\Referencia.png";

                for (Map.Entry<String,String> setting : settings.entrySet()) {
                    String key = setting.getKey();
                    if (key.equals("img")){
                        String value = setting.getValue();
                        fileName = value;
                    }
                }

                File file = new File( fileName );
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnFardo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                TagFardoView fardoView = new TagFardoView();
            }
        });
    }
}
