package br.com.ifsolutions.view;

import br.com.ifsolutions.controller.TagController;
import br.com.ifsolutions.entity.Produtos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TagView {

    public void renderMenu(){
        final JFrame frame = new JFrame();
        frame.setSize(400,100);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Tipo Etiqueta");

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);

        JButton avulso = new JButton("Avulso");
        avulso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                frame.setVisible(false);

                TagController tagAvulsoController = new TagController();
                tagAvulsoController.listarAvulso();
            }
        });

        JButton venda = new JButton("Venda");
        venda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);

                TagController tagMovenda = new TagController();
                tagMovenda.listarVendas();
            }
        });

        JButton fardo = new JButton("Fardo");
        fardo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                TagController tagFardo = new TagController();
                tagFardo.listarFardo();
            }
        });

        JPanel panel = new JPanel();


        panel.add(avulso);
        panel.add(venda);
        panel.add(fardo);
        frame.getContentPane().add(panel);
    }

    public void renderTagAvulso(List<Produtos> products){

    }

    public void renderNewMenu(){
        this.renderMenu();
    }
}
