package si.fri.rso.samples.quickwheels.frames;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Frame extends JFrame implements ItemListener, ActionListener {

    private final ImageIcon logo = new ImageIcon("");

    private JCheckBox checkbox1, checkbox2, checkbox3, checkbox4;

    private JLabel label;

    private JButton btn;

    private JComboBox combobox;

    private JMenuBar menubar;

    private JMenu file, edit, window, help;

    private JMenuItem copy, cut, paste, selectAll, newFile;

    private JTextArea textarea;

    Frame() {
//        //frame.setSize(800, 600);
//        this.setTitle("Frame Title");
//        // Appears on the center
//        this.setLocationRelativeTo(null);
//        // Can't be resized
//        this.setResizable(false);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setIconImage(logo.getImage());
//        // Content pane => Inner area
//        this.getContentPane().setBackground(new Color(0xd95868));
//        this.setLayout(new FlowLayout());
//
//        JButton btn = new JButton("This is a button");
//        JButton btn2 = new JButton("This is a button 2");
//
//        // Size of pane resizing according to item
//        this.add(btn);
//        this.add(btn2);
//
//        this.pack();
//        this.setVisible(true);
//        Font font1 = new Font("Consolas", Font.ITALIC, 68);
//        Font font2 = new Font("Calibri", Font.BOLD, 70);
//
//        Border label_border = BorderFactory.createLineBorder(Color.RED, 2);
//
//        this.setTitle("Frame Title");
//        this.setSize(900, 500);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setIconImage(logo.getImage());
//        this.setLocationRelativeTo(null);
//        this.setLayout(null);
//
//        JLabel label = new JLabel();
//        JLabel label2 = new JLabel("Second label");
//
//        label.setText("This is my label");
//        label.setIcon(logo);
//        label.setVerticalAlignment(JLabel.CENTER);
//        label.setHorizontalAlignment(JLabel.CENTER);
//        label.setForeground(Color.red);
//        label.setFont(font1);
//        label.setBackground(Color.black);
//        label.setOpaque(true);
//
//        label.setBorder(label_border);
//        //label.setBounds(25,25,500,300);
//
//        this.add(label);
//        this.add(label2);
//        this.pack();
//        this.setVisible(true);

        this.setTitle("Quick Wheels");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(logo.getImage());
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        String fruits[] = {"Orange", "Apple", "Banana", "Mango"};

//        JButton btn = new JButton();
//        btn.setText("Button");
//        btn.setBounds(200, 200, 200, 50);
//        btn.setFocusable(false);
//        btn.setFont(new Font("Consolas", Font.BOLD, 28));
//        btn.setForeground(Color.red);
//        btn.setBackground(Color.black);
//        btn.setBorder(BorderFactory.createEtchedBorder());
//        btn.setEnabled(false);
//

        textarea = new JTextArea();
        textarea.setBounds(25,25,300,30);

        menubar = new JMenuBar();

        file = new JMenu("File");
        edit = new JMenu("Edit");
        help = new JMenu("Help");
        window = new JMenu("Window");

        menubar.add(file);
        menubar.add(edit);
        menubar.add(help);

        copy = new JMenuItem("Copy");
        cut = new JMenuItem("Cut");
        paste = new JMenuItem("Paste");
        selectAll = new JMenuItem("Select All");
        newFile = new JMenuItem("New File");

        file.add(newFile);

        edit.add(copy);
        edit.add(cut);
        edit.add(paste);
        edit.add(selectAll);

        help.add(window);

        label = new JLabel("Ordering system");
        label.setBounds(250,100,250,50);
        label.setFont(new Font("Arial", Font.BOLD, 22));
        label.setBackground(Color.red);
        label.setOpaque(true);
        label.setForeground(Color.white);
        label.setBorder(new EmptyBorder(0,10,0,0));

        combobox = new JComboBox(fruits);
        combobox.setBounds(100,200,200,40);
        combobox.addItem("Watermelon");
        combobox.removeItem("Banana");
        //combobox.removeAllItems();

        checkbox1 = new JCheckBox("Burger @ 15$", true);
        checkbox1.setBounds(300,150,300,50);
        checkbox1.addItemListener(this);
        checkbox1.setFont(new Font("Arial", Font.BOLD, 18));
        checkbox1.setForeground(Color.orange);

        checkbox2 = new JCheckBox("Pizza @ 10$");
        checkbox2.setBounds(300,200,300,50);
        checkbox2.addItemListener(this);
        checkbox2.setFont(new Font("Arial", Font.BOLD, 18));
        checkbox2.setForeground(Color.orange);

        checkbox3 = new JCheckBox("Coca cola @ 5$");
        checkbox3.setBounds(300,250,300,50);
        checkbox3.addItemListener(this);
        checkbox3.setFont(new Font("Arial", Font.BOLD, 18));
        checkbox3.setForeground(Color.orange);

        checkbox4 = new JCheckBox("Coffee @ 5$");
        checkbox4.setBounds(300,300,300,50);
        checkbox4.addItemListener(this);
        checkbox4.setFont(new Font("Arial", Font.BOLD, 18));
        checkbox4.setForeground(Color.orange);

        btn = new JButton("Place your order");
        btn.setBounds(290, 380, 170, 40);
        btn.addActionListener(this);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBackground(Color.blue);
        btn.setForeground(Color.white);

        this.add(textarea);
        this.add(menubar);
        this.setJMenuBar(menubar);

        this.add(label);
        this.add(checkbox1);
        this.add(checkbox2);
        this.add(checkbox3);
        this.add(checkbox4);
        this.add(btn);
        this.add(combobox);

        this.setVisible(true);
    }

    @Override
    public void itemStateChanged(ItemEvent evt) {
        if (evt.getSource() == checkbox1) {
            label.setText("Burger checkbox has been " + (evt.getStateChange()== ItemEvent.SELECTED ? "checked" : "unchecked"));
        } else if (evt.getSource() == checkbox2) {
            label.setText("Pizza checkbox has been " + (evt.getStateChange()== ItemEvent.SELECTED ? "checked" : "unchecked"));
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // new Second_frame()
        // this.dispose()
        float amount = 0;
        String msg = "Your order summary \n";

        if (checkbox1.isSelected()) {
            amount += 15;
            msg += "Burger : 15$\n";
        }
        if(checkbox2.isSelected()) {
            amount += 10;
            msg += "Pizza : 10$\n";
        }
        if (checkbox3.isSelected()) {
            amount += 5;
            msg += "Cola cola : 5$\n";
        }
        if(checkbox4.isSelected()) {
            amount += 5;
            msg += "Coffee : 5$\n";
        }

        msg += "-----------------------------\n";

        JOptionPane.showMessageDialog(this, msg + "Total: " + amount + "$\n");
    }

}
