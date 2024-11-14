import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AplikasiPemesananMakanan extends JFrame {
    private final JTextField txtNama, txtAlamat, txtTelp, txtBayar;
    private final JCheckBox chkSteak, chkSpagheti, chkPizza;
    private final JSpinner spinSteak, spinSpagheti, spinPizza;
    private final JLabel lblTotalBayar, lblKembalian;
    private final JTextArea txtAreaPesanan;
    private final JButton btnTambah;
    
    // Harga menu
    private final int HARGA_STEAK = 75000;
    private final int HARGA_SPAGHETI = 40000;
    private final int HARGA_PIZZA = 80000;
    
    public AplikasiPemesananMakanan() {
        setTitle("Aplikasi Pemesanan Makanan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setSize(500, 500);  // Increased height for new components
        
        // Panel Data Customer
        JPanel panelCustomer = new JPanel(null);
        panelCustomer.setBorder(BorderFactory.createTitledBorder("Data Customer"));
        panelCustomer.setBounds(10, 10, 230, 120);
        
        // Komponen Data Customer
        JLabel lblNama = new JLabel("Nama:");
        lblNama.setBounds(10, 20, 50, 25);
        txtNama = new JTextField();
        txtNama.setBounds(70, 20, 150, 25);
        
        JLabel lblAlamat = new JLabel("Alamat:");
        lblAlamat.setBounds(10, 50, 50, 25);
        txtAlamat = new JTextField();
        txtAlamat.setBounds(70, 50, 150, 25);
        
        JLabel lblTelp = new JLabel("No Telp:");
        lblTelp.setBounds(10, 80, 50, 25);
        txtTelp = new JTextField();
        txtTelp.setBounds(70, 80, 150, 25);
        
        panelCustomer.add(lblNama);
        panelCustomer.add(txtNama);
        panelCustomer.add(lblAlamat);
        panelCustomer.add(txtAlamat);
        panelCustomer.add(lblTelp);
        panelCustomer.add(txtTelp);
        
        // Panel Menu
        JPanel panelMenu = new JPanel(null);
        panelMenu.setBorder(BorderFactory.createTitledBorder("Pilih Menu"));
        panelMenu.setBounds(250, 10, 230, 120);
        
        // Create spinners
        spinSteak = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        spinSpagheti = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        spinPizza = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        
        // Layout for menu items
        chkSteak = new JCheckBox("Steak");
        chkSteak.setBounds(10, 20, 100, 25);
        spinSteak.setBounds(150, 20, 60, 25);
        
        chkSpagheti = new JCheckBox("Spagheti");
        chkSpagheti.setBounds(10, 45, 100, 25);
        spinSpagheti.setBounds(150, 45, 60, 25);
        
        chkPizza = new JCheckBox("Pizza");
        chkPizza.setBounds(10, 70, 100, 25);
        spinPizza.setBounds(150, 70, 60, 25);
        
        // Add listeners
        ChangeListener spinnerListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                hitungTotal();
            }
        };
        
        spinSteak.addChangeListener(spinnerListener);
        spinSpagheti.addChangeListener(spinnerListener);
        spinPizza.addChangeListener(spinnerListener);
        
        ItemListener checkboxListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                JCheckBox source = (JCheckBox) e.getSource();
                JSpinner relatedSpinner;
                if (source == chkSteak) {
                    relatedSpinner = spinSteak;
                } else if (source == chkSpagheti) {
                    relatedSpinner = spinSpagheti;
                } else {
                    relatedSpinner = spinPizza;
                }
                relatedSpinner.setEnabled(source.isSelected());
                hitungTotal();
            }
        };
        
        chkSteak.addItemListener(checkboxListener);
        chkSpagheti.addItemListener(checkboxListener);
        chkPizza.addItemListener(checkboxListener);
        
        // Initially disable spinners
        spinSteak.setEnabled(false);
        spinSpagheti.setEnabled(false);
        spinPizza.setEnabled(false);
        
        panelMenu.add(chkSteak);
        panelMenu.add(spinSteak);
        panelMenu.add(chkSpagheti);
        panelMenu.add(spinSpagheti);
        panelMenu.add(chkPizza);
        panelMenu.add(spinPizza);
        
        // Panel Total dan Pembayaran
        JPanel panelPembayaran = new JPanel(null);
        panelPembayaran.setBorder(BorderFactory.createTitledBorder("Pembayaran"));
        panelPembayaran.setBounds(10, 140, 470, 80);
        
        JLabel lblTotal = new JLabel("Total Bayar:");
        lblTotal.setBounds(10, 20, 80, 25);
        lblTotalBayar = new JLabel("0");
        lblTotalBayar.setBounds(90, 20, 100, 25);
        lblTotalBayar.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel lblBayar = new JLabel("Bayar:");
        lblBayar.setBounds(200, 20, 50, 25);
        txtBayar = new JTextField();
        txtBayar.setBounds(250, 20, 100, 25);
        txtBayar.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                hitungKembalian();
            }
        });
        
        JLabel lblKembali = new JLabel("Kembalian:");
        lblKembali.setBounds(10, 50, 80, 25);
        lblKembalian = new JLabel("0");
        lblKembalian.setBounds(90, 50, 100, 25);
        
        panelPembayaran.add(lblTotal);
        panelPembayaran.add(lblTotalBayar);
        panelPembayaran.add(lblBayar);
        panelPembayaran.add(txtBayar);
        panelPembayaran.add(lblKembali);
        panelPembayaran.add(lblKembalian);
        
        // Button Tambah
        btnTambah = new JButton("TAMBAH");
        btnTambah.setBounds(380, 230, 100, 30);
        btnTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahPesanan();
            }
        });
        
        // Text Area Pesanan
        txtAreaPesanan = new JTextArea();
        txtAreaPesanan.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaPesanan);
        scrollPane.setBounds(10, 270, 470, 180);
        
        // Add all components
        add(panelCustomer);
        add(panelMenu);
        add(panelPembayaran);
        add(btnTambah);
        add(scrollPane);
        
        setLocationRelativeTo(null);
    }
    
    private void hitungTotal() {
        int total = 0;
        if (chkSteak.isSelected()) 
            total += HARGA_STEAK * (Integer)spinSteak.getValue();
        if (chkSpagheti.isSelected()) 
            total += HARGA_SPAGHETI * (Integer)spinSpagheti.getValue();
        if (chkPizza.isSelected()) 
            total += HARGA_PIZZA * (Integer)spinPizza.getValue();
        lblTotalBayar.setText(String.format("%,d", total));
        hitungKembalian();
    }
    
    private void hitungKembalian() {
        try {
            int total = Integer.parseInt(lblTotalBayar.getText().replace(",", ""));
            int bayar = Integer.parseInt(txtBayar.getText().replace(",", ""));
            int kembalian = bayar - total;
            lblKembalian.setText(String.format("%,d", kembalian));
        } catch (NumberFormatException e) {
            lblKembalian.setText("0");
        }
    }
    
    private void tambahPesanan() {
        if (txtNama.getText().isEmpty() || txtAlamat.getText().isEmpty() || txtTelp.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mohon lengkapi data customer!");
            return;
        }
        
        if (!chkSteak.isSelected() && !chkSpagheti.isSelected() && !chkPizza.isSelected()) {
            JOptionPane.showMessageDialog(this, "Pilih minimal satu menu!");
            return;
        }
        
        try {
            int bayar = Integer.parseInt(txtBayar.getText().replace(",", ""));
            int total = Integer.parseInt(lblTotalBayar.getText().replace(",", ""));
            if (bayar < total) {
                JOptionPane.showMessageDialog(this, "Pembayaran kurang!");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Masukkan jumlah pembayaran!");
            return;
        }
        
        StringBuilder struk = new StringBuilder();
        // Header struk
        struk.append("Toko Jufri\n");
        struk.append("Jl. Dr. Setiabudi No.111\n");
        struk.append("081321267783\n");
        struk.append("--------------------------------\n");
        
        // Tanggal dan waktu
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        struk.append(sdf.format(new Date())).append("\n\n");
        struk.append("Data Pembeli : \n");
        struk.append("--------------------------------\n");
        struk.append("Nama: ").append(txtNama.getText()).append("\n");
        struk.append("Alamat: ").append(txtAlamat.getText()).append("\n");
        struk.append("Telp: ").append(txtTelp.getText()).append("\n");
        struk.append("--------------------------------\n\n");
        struk.append("Pesanan:\n");
        struk.append("--------------------------------\n");
        
        // Detail pesanan
        if (chkSteak.isSelected()) {
            int jumlah = (Integer)spinSteak.getValue();
            int subtotal = HARGA_STEAK * jumlah;
            struk.append(String.format("Steak\n%d x %,d\t\tRp %,d\n", 
                jumlah, HARGA_STEAK, subtotal));
        }
        if (chkSpagheti.isSelected()) {
            int jumlah = (Integer)spinSpagheti.getValue();
            int subtotal = HARGA_SPAGHETI * jumlah;
            struk.append(String.format("Spagheti\n%d x %,d\t\tRp %,d\n", 
                jumlah, HARGA_SPAGHETI, subtotal));
        }
        if (chkPizza.isSelected()) {
            int jumlah = (Integer)spinPizza.getValue();
            int subtotal = HARGA_PIZZA * jumlah;
            struk.append(String.format("Pizza\n%d x %,d\t\tRp %,d\n", 
                jumlah, HARGA_PIZZA, subtotal));
        }
        
        struk.append("--------------------------------\n");
        struk.append(String.format("Sub Total\t\tRp %s\n", lblTotalBayar.getText()));
        struk.append(String.format("Total\t\t\tRp %s\n", lblTotalBayar.getText()));
        struk.append(String.format("Bayar (Cash)\t\tRp %s\n", txtBayar.getText()));
        struk.append(String.format("Kembali\t\t\tRp %s\n", lblKembalian.getText()));
        struk.append("================================\n\n");
        
        txtAreaPesanan.append(struk.toString());
        
        // Reset form
        txtNama.setText("");
        txtAlamat.setText("");
        txtTelp.setText("");
        chkSteak.setSelected(false);
        chkSpagheti.setSelected(false);
        chkPizza.setSelected(false);
        spinSteak.setValue(1);
        spinSpagheti.setValue(1);
        spinPizza.setValue(1);
        lblTotalBayar.setText("0");
        txtBayar.setText("");
        lblKembalian.setText("0");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AplikasiPemesananMakanan().setVisible(true);
            }
        });
    }
}
