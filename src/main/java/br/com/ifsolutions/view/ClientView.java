package br.com.ifsolutions.view;

import br.com.ifsolutions.controller.ReportController;
import br.com.ifsolutions.controller.TagController;
import br.com.ifsolutions.dao.ClienteDao;
import br.com.ifsolutions.entity.Cliente;
import br.com.ifsolutions.entity.Produtos;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ClientView {
    private JPanel mainPanel;
    private JLabel lbNomeCliente;
    private JTextField tfNomeCliente;
    private JButton btnPesquisar;
    private JPanel bottomPanel;
    private JPanel topPanel;
    private JButton btnCancel;
    private JButton btnSelect;
    private JPanel middlePanel;
    private JTable tableClient;
    private Cliente dadosCliente;
    private DefaultTableModel defaultTableModel;

    public ClientView() {

        final JFrame frame = new JFrame("Selecionar Cliente");
        frame.setSize(800,600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container container = frame.getContentPane();
        container.add(mainPanel);

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                TagController controller = new TagController();
                controller.renderTagMenu();
            }
        });

        btnPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tfNomeCliente.getText().isEmpty()){
                    renderTable(null);
                }

                if(!tfNomeCliente.getText().isEmpty()){
                    renderTable(tfNomeCliente.getText());
                }
            }
        });

        btnSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer idRow = tableClient.getSelectedRow();
                Cliente cliente = new Cliente();
                cliente.setCode((String) defaultTableModel.getValueAt(idRow, 0));
                cliente.setName((String) defaultTableModel.getValueAt(idRow, 1));
                cliente.setAddress((String) defaultTableModel.getValueAt(idRow, 2));
                cliente.setCity((String) defaultTableModel.getValueAt(idRow, 3));
                cliente.setState((String) defaultTableModel.getValueAt(idRow, 4));

                printFardo(cliente);

            }
        });
    }

    private void renderTable(String cliName){
        defaultTableModel = new DefaultTableModel();
        defaultTableModel.addColumn("Codigo Cliente");
        defaultTableModel.addColumn("Nome Cliente");
        defaultTableModel.addColumn("Endereço");
        defaultTableModel.addColumn("Cidade");
        defaultTableModel.addColumn("Estado");
        defaultTableModel.addColumn("Telefone");
        ArrayList<Cliente> clientes = null;

        if (cliName == null){
            ClienteDao dao = new ClienteDao();
             clientes = dao.findAll();
        }

        if(cliName != null){
            ClienteDao dao = new ClienteDao();
            clientes = dao.findByName(cliName);
        }

        if(clientes.isEmpty()){
            defaultTableModel.addRow(new String[]{"Sem Informação"});
        }

        for (Cliente cliente: clientes) {
            defaultTableModel.addRow(new String[]{
                    cliente.getCode(),
                    cliente.getName(),
                    cliente.getAddress(),
                    cliente.getCity(),
                    cliente.getState(),
                    cliente.getPhone()});
        }
        tableClient.setModel(defaultTableModel);
    }

    private void printFardo(Cliente cliente){

        JFrame qtnVolumes = new JFrame("Insira quantidade de Volumes");
        String volumes = JOptionPane.showInputDialog(
                qtnVolumes,
                "Quantidade de Volumes",
                "Quantidade Volumes",
                JOptionPane.WARNING_MESSAGE
        );

        JFrame numNota = new JFrame("Numero da Nota");
        String numNf = JOptionPane.showInputDialog(
                numNota,
                "Insira o Numero Nf",
                "Numero da Nota",
                JOptionPane.WARNING_MESSAGE
        );


        Map printFardo = new HashMap<>();
        printFardo.put("nome",cliente.getName());
        printFardo.put("endereco",cliente.getAddress() + ", " + cliente.getCity() + " - " + cliente.getState());
        printFardo.put("volumes",volumes);
        printFardo.put("nf",numNf);


        ArrayList list = new ArrayList();
        list.add(printFardo);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("ETIQUETA", dataSource);

        ReportController reportController = new ReportController();
        reportController.reportGenerate("C:\\tagGenerator\\report\\fardo.jasper", parameters);
    }
}
