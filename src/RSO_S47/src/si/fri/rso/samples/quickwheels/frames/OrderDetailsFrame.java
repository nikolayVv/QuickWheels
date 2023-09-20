package si.fri.rso.samples.quickwheels.frames;

import si.fri.rso.samples.quickwheels.controllers.OrderController;
import si.fri.rso.samples.quickwheels.controllers.StatusController;
import si.fri.rso.samples.quickwheels.models.Order;
import si.fri.rso.samples.quickwheels.models.OrderProduct;
import si.fri.rso.samples.quickwheels.models.OrderStatus;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.io.IOException;

public class OrderDetailsFrame extends JFrame implements ActionListener, ItemListener, KeyListener {
    private final ImageIcon logo = new ImageIcon(System.getProperty("user.dir") + "/images/logo.PNG");

    private JMenuBar menuBar;

    private JMenu deliveries, orders;

    private JMenuItem newDelivery, deliveryDetails, newOrder, orderDetails;

    private JLabel id, createdAt, lastModified, productName, productDescription, productQuantity, productPrice;
    private JTextField idField, createdAtField, lastModifiedField, productNameField, productDescriptionField, productQuantityField, productPriceField;

    private List<Order> ordersList;

    private Order currOrder;

    private JComboBox ordersComboBox;

    private JButton editSubmit, activateDeactivateCancel;

    OrderDetailsFrame() throws IOException {
        this.setTitle("Quick Wheels - Order Details");
        this.setSize(1200, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(logo.getImage());
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);

        this.setupMenu();
        this.setupLabels();
        this.loadOrders();

        this.add(menuBar);
        this.setJMenuBar(menuBar);
        this.add(ordersComboBox);
        this.add(id);
        this.add(idField);
        this.add(createdAt);
        this.add(createdAtField);
        this.add(lastModified);
        this.add(lastModifiedField);
        this.add(productName);
        this.add(productNameField);
        this.add(productDescription);
        this.add(productDescriptionField);
        this.add(productQuantity);
        this.add(productQuantityField);
        this.add(productPrice);
        this.add(productPriceField);
        this.add(editSubmit);
        this.add(activateDeactivateCancel);
        this.setVisible(true);
    }

    private void setEditing(String action) {
        if (action.equals("Start editing")) {
            editSubmit.setText("Submit");
            activateDeactivateCancel.setText("Cancel");

            productQuantityField.setEditable(true);
            productQuantityField.setBackground(new Color(0xfefeda));
        } else if (action.equals("Stop editing")) {
            editSubmit.setText("Edit order");
            if (currOrder.getStatus().getName().equals("Activated")) {
                activateDeactivateCancel.setText("Deactivate order");
            } else {
                activateDeactivateCancel.setText("Activate order");
            }

            productQuantityField.setEditable(false);
            productQuantityField.setBackground(null);
        }
    }

    private void setupLabels() {
        id = new JLabel("Order id: ");
        id.setFont(new Font("Arial", Font.BOLD, 22));
        id.setBackground(new Color(0xfefeda));
        id.setOpaque(true);
        id.setForeground(Color.black);
        id.setBorder(new EmptyBorder(0,10,0,0));
        id.setBounds(0,60,id.getPreferredSize().width + 15, 50);

        idField = new JTextField();
        idField.setForeground(Color.black);
        idField.setEditable(false);
        idField.setBounds(30 + id.getPreferredSize().width + 15,60, 1170 - (30 + id.getPreferredSize().width + 15) - 230, 50);

        createdAt = new JLabel("Created at: ");
        createdAt.setFont(new Font("Arial", Font.BOLD, 22));
        createdAt.setBackground(new Color(0xfefeda));
        createdAt.setOpaque(true);
        createdAt.setForeground(Color.black);
        createdAt.setBorder(new EmptyBorder(0,10,0,0));
        createdAt.setBounds(0,150,createdAt.getPreferredSize().width + 15, 50);

        createdAtField = new JTextField();
        createdAtField.setForeground(Color.black);
        createdAtField.setEditable(false);
        createdAtField.setBounds(30 + createdAt.getPreferredSize().width + 15,150, 1170 - (30 + createdAt.getPreferredSize().width + 15) - 230, 50);

        lastModified = new JLabel("Last modified: ");
        lastModified.setFont(new Font("Arial", Font.BOLD, 22));
        lastModified.setBackground(new Color(0xfefeda));
        lastModified.setOpaque(true);
        lastModified.setForeground(Color.black);
        lastModified.setBorder(new EmptyBorder(0,10,0,0));
        lastModified.setBounds(0,240,lastModified.getPreferredSize().width + 15, 50);

        lastModifiedField = new JTextField();
        lastModifiedField.setForeground(Color.black);
        lastModifiedField.setEditable(false);
        lastModifiedField.setBounds(30 + lastModified.getPreferredSize().width + 15,240, 1170 - (30 + lastModified.getPreferredSize().width + 15) - 230, 50);

        productName = new JLabel("Product name: ");
        productName.setFont(new Font("Arial", Font.BOLD, 22));
        productName.setBackground(new Color(0xfefeda));
        productName.setOpaque(true);
        productName.setForeground(Color.black);
        productName.setBorder(new EmptyBorder(0,10,0,0));
        productName.setBounds(0,330,productName.getPreferredSize().width + 15, 50);

        productNameField = new JTextField();
        productNameField.setForeground(Color.black);
        productNameField.setEditable(false);
        productNameField.setBounds(30 + productName.getPreferredSize().width + 15,330, 1170 - (30 + productName.getPreferredSize().width + 15) - 230, 50);

        productDescription = new JLabel("Product description: ");
        productDescription.setFont(new Font("Arial", Font.BOLD, 22));
        productDescription.setBackground(new Color(0xfefeda));
        productDescription.setOpaque(true);
        productDescription.setForeground(Color.black);
        productDescription.setBorder(new EmptyBorder(0,10,0,0));
        productDescription.setBounds(0,420,productDescription.getPreferredSize().width + 15, 50);

        productDescriptionField = new JTextField();
        productDescriptionField.setForeground(Color.black);
        productDescriptionField.setEditable(false);
        productDescriptionField.setBounds(30 + productDescription.getPreferredSize().width + 15,420, 1170 - (30 + productDescription.getPreferredSize().width + 15) - 230, 50);

        productQuantity = new JLabel("Product quantity: ");
        productQuantity.setFont(new Font("Arial", Font.BOLD, 22));
        productQuantity.setBackground(new Color(0xfefeda));
        productQuantity.setOpaque(true);
        productQuantity.setForeground(Color.black);
        productQuantity.setBorder(new EmptyBorder(0,10,0,0));
        productQuantity.setBounds(0,510,productQuantity.getPreferredSize().width + 15, 50);

        productQuantityField = new JTextField();
        productQuantityField.setForeground(Color.black);
        productQuantityField.setEditable(false);
        productQuantityField.addKeyListener(this);
        productQuantityField.setBounds(30 + productQuantity.getPreferredSize().width + 15,510, 1170 - (30 + productQuantity.getPreferredSize().width + 15) - 230, 50);

        productPrice = new JLabel("Product price: ");
        productPrice.setFont(new Font("Arial", Font.BOLD, 22));
        productPrice.setBackground(new Color(0xfefeda));
        productPrice.setOpaque(true);
        productPrice.setForeground(Color.black);
        productPrice.setBorder(new EmptyBorder(0,10,0,0));
        productPrice.setBounds(0,600,productPrice.getPreferredSize().width + 15, 50);

        productPriceField = new JTextField();
        productPriceField.setForeground(Color.black);
        productPriceField.setEditable(false);
        productPriceField.setBounds(30 + productPrice.getPreferredSize().width + 15,600, 1170 - (30 + productPrice.getPreferredSize().width + 15) - 230, 50);
    }

    private void loadOrders() throws IOException {
        ordersList = OrderController.getAllOrders();
        ordersComboBox = new JComboBox();
        ordersComboBox.addItem("--Choose order--");

        for(Order order : ordersList) {
            ordersComboBox.addItem(Long.toString(order.getId()));
        }
        ordersComboBox.addItemListener(this);
        ordersComboBox.setBounds(970, 66,200,40);

        editSubmit = new JButton("Edit order");
        editSubmit.setEnabled(false);
        editSubmit.addActionListener(this);
        editSubmit.setBounds(1015, 580, editSubmit.getPreferredSize().width + 15, 50);

        activateDeactivateCancel = new JButton("Deactivate order");
        activateDeactivateCancel.setEnabled(false);
        activateDeactivateCancel.addActionListener(this);
        activateDeactivateCancel.setBounds(995, 650, activateDeactivateCancel.getPreferredSize().width + 15, 50);
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

    private void setCurrOrder() {
        editSubmit.setText("Edit order");
        idField.setBackground(null);
        createdAtField.setBackground(null);
        lastModifiedField.setBackground(null);
        productNameField.setBackground(null);
        productDescriptionField.setBackground(null);
        productQuantityField.setBackground(null);
        productPriceField.setBackground(null);

        if (currOrder == null) {
            idField.setText("");
            createdAtField.setText("");
            lastModifiedField.setText("");
            productNameField.setText("");
            productDescriptionField.setText("");
            productQuantityField.setText("");
            productPriceField.setText("");

            editSubmit.setEnabled(false);
            activateDeactivateCancel.setText("Deactivate order");
            activateDeactivateCancel.setEnabled(false);
        } else {
            OrderProduct currProduct = currOrder.getProduct();
            OrderStatus currStatus = currOrder.getStatus();

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMM d, yyyy HH:mm:ss a");
            LocalDateTime createdAtDateTime = LocalDateTime.parse(currOrder.getCreatedAt());
            LocalDateTime lastModifiedDateTime = LocalDateTime.parse(currOrder.getLastModified());

            idField.setText(Long.toString(currOrder.getId()));
            createdAtField.setText(dateFormatter.format(createdAtDateTime));
            lastModifiedField.setText(dateFormatter.format(lastModifiedDateTime));
            productNameField.setText(currProduct.getName());
            productDescriptionField.setText(currProduct.getDescription());
            productQuantityField.setText(Integer.toString(currProduct.getQuantity()));
            productPriceField.setText(currProduct.getPrice() + " $, TOTAL: " + currProduct.getTotalPrice() + " $");

            if (currStatus.getName().equals("Activated")) {
                editSubmit.setEnabled(true);
                activateDeactivateCancel.setText("Deactivate order");
            } else if (currStatus.getName().equals("Deactivated")) {
                editSubmit.setEnabled(false);
                activateDeactivateCancel.setText("Activate order");
            }
            activateDeactivateCancel.setEnabled(true);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent evt) {
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            if (evt.getItem().equals("--Choose order--")) {
                currOrder = null;
            } else {
                for (Order order : ordersList) {
                    if (evt.getItem().equals(Long.toString(order.getId()))) {
                        currOrder = order;
                        break;
                    }
                }
            }

            setCurrOrder();
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
        } else if (e.getActionCommand().equals("Edit order")) {
            setEditing("Start editing");
        } else if (e.getActionCommand().equals("Activate order")) {
            OrderStatus newStatus = new OrderStatus(1, "Activated");
            currOrder.setStatus(newStatus);
            for (Order order : ordersList) {
                if (order.getId() == currOrder.getId()) {
                    order.setStatus(newStatus);
                    currOrder.setStatus(newStatus);
                    break;
                }
            }

            OrderController.editOrder(currOrder);
            editSubmit.setEnabled(true);
            activateDeactivateCancel.setText("Deactivate order");
        } else if (e.getActionCommand().equals("Deactivate order")) {
            OrderStatus newStatus = new OrderStatus(2, "Deactivated");
            for (Order order : ordersList) {
                if (order.getId() == currOrder.getId()) {
                    order.setStatus(newStatus);
                    currOrder.setStatus(newStatus);
                    break;
                }
            }

            OrderController.editOrder(currOrder);
            editSubmit.setEnabled(false);
            activateDeactivateCancel.setText("Activate order");
        } else if (e.getActionCommand().equals("Submit")) {
            if (!productQuantityField.getText().equals(Integer.toString(currOrder.getProduct().getQuantity()))) {
                for (Order order : ordersList) {
                    if (order.getId() == currOrder.getId()) {
                        order.getProduct().setQuantity(Integer.parseInt(productQuantityField.getText()));
                        currOrder.getProduct().setQuantity(Integer.parseInt(productQuantityField.getText()));
                        break;
                    }
                }

                OrderController.editOrder(currOrder);
            }
            this.setEditing("Stop editing");
        } else if (e.getActionCommand().equals("Cancel")) {
            this.setEditing("Stop editing");
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
        OrderProduct currProduct = currOrder.getProduct();

        try{
            int data = Integer.parseInt(productQuantityField.getText());

            if (data <= 0) {
                productQuantityField.setText("1");
                productPriceField.setText(currProduct.getPrice() + " $, TOTAL: " + currProduct.getPrice() + " $");
            } else if (data > currProduct.getQuantityLeft()) {
                productQuantityField.setText(Integer.toString(currProduct.getQuantityLeft()));
                productPriceField.setText(currProduct.getPrice() + " $, TOTAL: " + (currProduct.getPrice() * currProduct.getQuantityLeft()) + " $");
            } else {
                productPriceField.setText(currProduct.getPrice() + " $, TOTAL: " + (data * currProduct.getPrice()) + " $");
            }

        } catch (NumberFormatException ex) {
            productQuantityField.setText(Integer.toString(currProduct.getQuantity()));
            productPriceField.setText(currProduct.getPrice() + " $, TOTAL: " + currProduct.getTotalPrice() + " $");
        }
    }
}