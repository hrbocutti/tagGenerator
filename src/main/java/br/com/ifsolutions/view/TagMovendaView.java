package br.com.ifsolutions.view;

import br.com.ifsolutions.entity.Venda;

import javax.swing.*;
import java.awt.*;
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

    public void renderView(List<Venda> vendas) {

        JFrame frame = new JFrame();
        frame.setSize(800,600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Container container = frame.getContentPane();
        container.add(mainPanel);
    }
}
