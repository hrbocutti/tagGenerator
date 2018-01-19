package br.com.ifsolutions.view;

import br.com.ifsolutions.controller.MenuController;
import br.com.ifsolutions.controller.ReportController;
import br.com.ifsolutions.dao.ClienteDao;
import br.com.ifsolutions.entity.Cliente;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class TagFardoView {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JButton btnPesquisar;
    private JTextField txtNomeCliente;
    private JTable tabelaClientes;
    private JButton btnPrint;
    private JButton btnCancelar;
    private JFrame mainFrame;
    private DefaultTableModel defaultTable;

    public TagFardoView() {
        mainFrame = new JFrame("Menu");
        mainFrame.setSize(800,600);
        mainFrame.setVisible(true);
        mainFrame.getContentPane().add(mainPanel);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation(dim.width/2-mainFrame.getSize().width/2, dim.height/2-mainFrame.getSize().height/2);

        btnPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                defaultTable = new DefaultTableModel();
                defaultTable.addColumn("Codigo");
                defaultTable.addColumn("Nome Cliente");
                defaultTable.addColumn("Cidade");
                defaultTable.addColumn("Estado");

                ClienteDao clienteDao = new ClienteDao();
                ArrayList<Cliente> clientes = clienteDao.findByName(txtNomeCliente.getText());

                for (Cliente cliente:clientes) {
                    defaultTable.addRow(new String[]{
                            cliente.getCode(),
                            cliente.getName(),
                            cliente.getCity(),
                            cliente.getState()});
                }
                tabelaClientes.setModel(defaultTable);
            }
        });

        btnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer idRow = tabelaClientes.getSelectedRow();
                TableModel model = tabelaClientes.getModel();
                printFardo(idRow, model);
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                MenuController menuController = new MenuController();
                menuController.startApplication();
            }
        });
    }

    private static void printFardo(Integer idRow, TableModel model){
        Cliente cliente = new Cliente();

        String codCli = (String) model.getValueAt(idRow, 0);
        ClienteDao dao = new ClienteDao();
        ArrayList<Cliente> clientes = dao.findByIdFardo(codCli);

        HashMap<String, String> fardo = new HashMap<>();
        ArrayList fardos = new ArrayList();

        if(!clientes.isEmpty()){
            JFrame frame = new JFrame("Volume Quantidade");
            String quantidade = JOptionPane.showInputDialog(
                    frame,
                    "Volume do Fardo",
                    "Selecione a quantidade",
                    JOptionPane.WARNING_MESSAGE
            );

            String numeroNf = JOptionPane.showInputDialog(
                    frame,
                    "Numero da Nota Fiscal",
                    "Numero da Nota Fiscal",
                    JOptionPane.WARNING_MESSAGE
            );

            Integer quantidadeCount = Integer.valueOf(quantidade);

            for (int count = 1; count <= quantidadeCount; count++){
                fardo.put("nome", clientes.get(0).getName());
                fardo.put("logradouro", clientes.get(0).getAddress() + " - " + clientes.get(0).getNeighborhood());
                fardo.put("cidade", clientes.get(0).getCity() + "-" + clientes.get(0).getState());
                fardo.put("cep", clientes.get(0).getCEP());
                fardo.put("volume", String.valueOf(count));
                fardo.put("total_volume", quantidade);
                fardo.put("nf", numeroNf);
                fardos.add(fardo);

                JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(fardos);
                HashMap<String, Object> parameters = new HashMap<String, Object>();
                parameters.put("ETIQUETA", dataSource);

                ReportController reportController = new ReportController();
                reportController.reportGenerate("C:\\tagGenerator\\report\\fardo.jasper", parameters);

            }
        }
    }
}