package si.fri.rso.samples.quickwheels.frames;
import si.fri.rso.samples.quickwheels.controllers.OrderController;
import si.fri.rso.samples.quickwheels.controllers.ProductController;
import si.fri.rso.samples.quickwheels.models.Order;
import si.fri.rso.samples.quickwheels.models.OrderProduct;
import si.fri.rso.samples.quickwheels.models.OrderStatus;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

public class NewOrderFrame extends JFrame  implements ActionListener, ItemListener, KeyListener {
    private final ImageIcon logo = new ImageIcon(System.getProperty("user.dir") + "/images/logo.PNG");

    private JMenuBar menuBar;

    private JMenu deliveries, orders;

    private JMenuItem newDelivery, deliveryDetails, newOrder, orderDetails;

    private OrderProduct currProduct;

    private List<OrderProduct> productsList;

    private JLabel name, description, quantityLeft, price, quantity, total;

    private JTextField descriptionField, quantityLeftField, priceField, quantityField, totalField;

    private JComboBox productsComboBox;

    private JButton createOrder;

    NewOrderFrame() throws IOException {
        this.setTitle("Quick Wheels - New Order");
        this.setSize(1200, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(logo.getImage());
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);

        this.setupMenu();
        this.setupLabels();
        this.loadProducts();

        this.add(menuBar);
        this.setJMenuBar(menuBar);
        this.add(productsComboBox);
        this.add(name);
        this.add(description);
        this.add(descriptionField);
        this.add(quantityLeft);
        this.add(quantityLeftField);
        this.add(price);
        this.add(priceField);
        this.add(quantity);
        this.add(quantityField);
        this.add(total);
        this.add(totalField);
        this.add(createOrder);
        this.setVisible(true);
    }

    private void setCurrProduct() {
        if (currProduct == null) {
            descriptionField.setText("");
            quantityField.setText("");
            quantityField.setEditable(false);
            quantityLeftField.setText("");
            priceField.setText("");
            totalField.setText("");

            createOrder.setEnabled(false);
        } else {
            descriptionField.setText(currProduct.getDescription());
            quantityField.setText("1");
            quantityField.setEditable(true);
            quantityField.setBackground(new Color(0xfefeda));
            quantityLeftField.setText(Integer.toString(currProduct.getQuantityLeft()));
            priceField.setText(Double.toString(currProduct.getPrice()) + " $");
            totalField.setText(Double.toString(currProduct.getPrice()) + " $");

            createOrder.setEnabled(true);
        }
    }

    private void setupLabels() {
        name = new JLabel("Product name: ");
        name.setFont(new Font("Arial", Font.BOLD, 22));
        name.setBackground(new Color(0xfefeda));
        name.setOpaque(true);
        name.setForeground(Color.black);
        name.setBorder(new EmptyBorder(0,10,0,0));
        name.setBounds(0,50,name.getPreferredSize().width + 15, 50);

        description = new JLabel("Product description: ");
        description.setFont(new Font("Arial", Font.BOLD, 22));
        description.setBackground(new Color(0xfefeda));
        description.setOpaque(true);
        description.setForeground(Color.black);
        description.setBorder(new EmptyBorder(0,10,0,0));
        description.setBounds(0,140,description.getPreferredSize().width + 15, 50);

        descriptionField = new JTextField();
        descriptionField.setForeground(Color.black);
        descriptionField.setEditable(false);
        descriptionField.setBounds(30 + description.getPreferredSize().width + 15,140, 1170 - (30 + description.getPreferredSize().width + 15), 50);

        price = new JLabel("Product price: ");
        price.setFont(new Font("Arial", Font.BOLD, 22));
        price.setBackground(new Color(0xfefeda));
        price.setOpaque(true);
        price.setForeground(Color.black);
        price.setBorder(new EmptyBorder(0,10,0,0));
        price.setBounds(0,230,price.getPreferredSize().width + 15, 50);

        priceField = new JTextField();
        priceField.setForeground(Color.black);
        priceField.setEditable(false);
        priceField.setBounds(30 + price.getPreferredSize().width + 15,230, 1170 - (30 + price.getPreferredSize().width + 15), 50);

        quantityLeft = new JLabel("Quantity left: ");
        quantityLeft.setFont(new Font("Arial", Font.BOLD, 22));
        quantityLeft.setBackground(new Color(0xfefeda));
        quantityLeft.setOpaque(true);
        quantityLeft.setForeground(Color.black);
        quantityLeft.setBorder(new EmptyBorder(0,10,0,0));
        quantityLeft.setBounds(0,320,quantityLeft.getPreferredSize().width + 15, 50);

        quantityLeftField = new JTextField();
        quantityLeftField.setForeground(Color.black);
        quantityLeftField.setEditable(false);
        quantityLeftField.setBounds(30 + quantityLeft.getPreferredSize().width + 15,320, 1170 - (30 + quantityLeft.getPreferredSize().width + 15), 50);

        quantity = new JLabel("Quantity: ");
        quantity.setFont(new Font("Arial", Font.BOLD, 22));
        quantity.setBackground(new Color(0xfefeda));
        quantity.setOpaque(true);
        quantity.setForeground(Color.black);
        quantity.setBorder(new EmptyBorder(0,10,0,0));
        quantity.setBounds(0,410,quantity.getPreferredSize().width + 15, 50);

        quantityField = new JTextField();
        quantityField.setForeground(Color.black);
        quantityField.setEditable(false);
        quantityField.addKeyListener(this);
        quantityField.setBounds(30 + quantity.getPreferredSize().width + 15,410, 1170 - (30 + quantity.getPreferredSize().width + 15), 50);

        total = new JLabel("Total: ");
        total.setFont(new Font("Arial", Font.BOLD, 22));
        total.setBackground(new Color(0xfefeda));
        total.setOpaque(true);
        total.setForeground(Color.black);
        total.setBorder(new EmptyBorder(0,10,0,0));
        total.setBounds(0,650,total.getPreferredSize().width + 15, 50);

        totalField = new JTextField();
        totalField.setForeground(Color.black);
        totalField.setEditable(false);
        totalField.setBounds(30 + total.getPreferredSize().width + 15,650, (1170 - (30 + total.getPreferredSize().width + 15)) / 2, 50);
    }

    private void loadProducts() throws IOException {
        productsList = ProductController.getAllProducts();
        productsComboBox = new JComboBox();
        productsComboBox.addItem("--Choose product--");

        for(OrderProduct product : productsList) {
            productsComboBox.addItem(product.getName());
        }
        productsComboBox.addItemListener(this);
        productsComboBox.setBounds(30 + name.getPreferredSize().width + 15, 50,1170 - (30 + name.getPreferredSize().width + 15),50);

        createOrder = new JButton("Create order");
        createOrder.setEnabled(false);
        createOrder.addActionListener(this);
        createOrder.setBounds(1015, 650, createOrder.getPreferredSize().width + 15, 50);
    }

    private void setupMenu() {
        menuBar = new JMenuBar();
        deliveries = new JMenu("Deliveries");
        orders = new JMenu("Orders");
        newDelivery = new JMenuItem("New Delivery");
        deliveryDetails = new JMenuItem("Delivery Details");
        newOrder = new JMenuItem("New Order");
        orderDetails = new JMenuItem("Order Details");

        newDelivery.addActionListener(this);
        deliveryDetails.addActionListener(this);
        newOrder.addActionListener(this);
        orderDetails.addActionListener(this);

        menuBar.add(deliveries);
        menuBar.add(orders);
        deliveries.add(newDelivery);
        deliveries.add(deliveryDetails);
        orders.add(newOrder);
        orders.add(orderDetails);
    }

    @Override
    public void itemStateChanged(ItemEvent evt) {
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            if (evt.getItem().equals("--Choose product--")) {
                currProduct = null;
            } else {
                for (OrderProduct product : productsList) {
                    if (evt.getItem().equals(product.getName())) {
                        currProduct = product;
                        break;
                    }
                }
            }

            setCurrProduct();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("New Delivery")) {
            try {
                new NewDeliveryFrame();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            this.dispose();
        } else if (e.getActionCommand().equals("Delivery Details")) {
            try {
                new DeliveryDetailsFrame();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            this.dispose();
        } else if (e.getActionCommand().equals("New Order")) {
            try {
                new NewOrderFrame();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            this.dispose();
        } else if (e.getActionCommand().equals("Order Details")) {
            try {
                new OrderDetailsFrame();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            this.dispose();
        } else if (e.getActionCommand().equals("Create order")) {
            currProduct.setQuantity(Integer.parseInt(quantityField.getText()));
            OrderController.createOrder(currProduct);

            currProduct = null;
            productsComboBox.setSelectedItem("--Choose product--");
            setCurrProduct();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        try{
            int data = Integer.parseInt(quantityField.getText());

            if (data <= 0) {
                quantityField.setText("1");
                totalField.setText(Double.toString(currProduct.getPrice()) + " $");
            } else if (data > currProduct.getQuantityLeft()) {
                quantityField.setText(Integer.toString(currProduct.getQuantityLeft()));
                totalField.setText(Double.toString(currProduct.getQuantityLeft() * currProduct.getPrice()) + " $");
            } else {
                totalField.setText(Double.toString(data * currProduct.getPrice()) + " $");
            }

        } catch (NumberFormatException ex) {
            quantityField.setText("1");
            totalField.setText(Double.toString(currProduct.getPrice()) + " $");
        }
    }
}