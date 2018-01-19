package br.com.ifsolutions.view;

import br.com.ifsolutions.controller.MenuController;
import br.com.ifsolutions.controller.ReportController;
import br.com.ifsolutions.dao.ClienteDao;
import br.com.ifsolutions.dao.VendasDao;
import br.com.ifsolutions.entity.Cliente;
import br.com.ifsolutions.entity.Venda;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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
import java.util.HashMap;

public class TagMovendaView {
    private JTextField codVendaField;
    private JButton pesquisarButton;
    private JPanel topPanel;
    private JTable tableVendas;
    private JPanel middlePanel;
    private JButton btnCancel;
    private JButton btnOpenOrder;
    private JPanel mainFrame;
    private JPanel bottomPanel;
    private JTextField dateOf;
    private JTextField dateTo;
    private JButton btnFardo;
    private JButton btnFiltrar;
    private DefaultTableModel defaultTable;

    public TagMovendaView() {

        final JFrame frame = new JFrame();
        frame.setSize(800,600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);

        Container container = frame.getContentPane();
        container.add(mainFrame);

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
                MenuController menuController = new MenuController();
                menuController.startApplication();
            }
        });

        pesquisarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            if (dateOf.getText().isEmpty() || dateTo.getText().isEmpty()){
                JOptionPane.showMessageDialog(frame, "Data De e Data Por não podem estar em branco\n Padrão: DD/MM/AAAA");

            }else {
                String codVenda = codVendaField.getText();
                if (!codVenda.isEmpty()) {
                    VendasDao dao = new VendasDao();
                    dao.findByCodVenda(codVenda);
                } else {
                    defaultTable = new DefaultTableModel();
                    defaultTable.addColumn("Codigo Venda");
                    defaultTable.addColumn("Nome Cliente");
                    defaultTable.addColumn("Data");
                    defaultTable.addColumn("Numero Nota");

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

        btnOpenOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer idRow = tableVendas.getSelectedRow();
                String codMovenda = (String) defaultTable.getValueAt(idRow, 0);
                new OrderView(codMovenda);
            }
        });

        btnFardo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer idRow = tableVendas.getSelectedRow();
                String codMovenda = (String) defaultTable.getValueAt(idRow, 0);

                VendasDao vendasDao = new VendasDao();
                ArrayList<Venda> vendas = vendasDao.findByCodVenda(codMovenda);

                String volumes = vendasDao.findVolumesByVenda(codMovenda);

                vendas.get(0).setVolumes(volumes);

                ClienteDao clienteDao = new ClienteDao();
                ArrayList<Cliente> cliente = clienteDao.findByCodVenda(codMovenda);


                Integer volumesCount = Integer.valueOf(volumes);


                for (Integer count = 1; count <= volumesCount; count++){
                    ArrayList fardo = new ArrayList();

                    HashMap<String, String> dadosFardo = new HashMap<>();
                    dadosFardo.put("nome",cliente.get(0).getName());
                    dadosFardo.put("logradouro", cliente.get(0).getAddress() + " - " + cliente.get(0).getNeighborhood());
                    dadosFardo.put("cidade", cliente.get(0).getCity() + "-" + cliente.get(0).getState());
                    dadosFardo.put("cep", cliente.get(0).getCEP());
                    dadosFardo.put("nf", vendas.get(0).getNumNota());
                    dadosFardo.put("total_volume", volumes);
                    dadosFardo.put("volume", String.valueOf(count));
                    fardo.add(dadosFardo);

                    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(fardo);
                    HashMap<String, Object> parameters = new HashMap<String, Object>();
                    parameters.put("ETIQUETA", dataSource);

                    ReportController reportController = new ReportController();
                    reportController.reportGenerate("C:\\tagGenerator\\report\\fardo.jasper", parameters);

                }
            }
        });
    }
}
