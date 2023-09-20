package si.fri.rso.samples.quickwheels.frames;

import si.fri.rso.samples.quickwheels.controllers.*;
import si.fri.rso.samples.quickwheels.models.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class NewDeliveryFrame extends JFrame implements ActionListener, ItemListener {
    private final ImageIcon logo = new ImageIcon(System.getProperty("user.dir") + "/images/logo.PNG");

    private JMenuBar menuBar;

    private JMenu deliveries, orders;

    private JMenuItem newDelivery, deliveryDetails, newOrder, orderDetails;

    private DeliveryTransport currTransport;

    private List<DeliveryTransport> transportsList;

    private DeliveryCustomer currCustomer;

    private List<DeliveryCustomer> customersList;

    private DeliveryType currType;

    private List<DeliveryType> typesList;

    private Order currOrder;

    private List<Order> ordersList;

    private JLabel orderIds, customers, destinationLon, destinationLat, sourceLon, sourceLat, types, typeDescription, transports, transportDescription;

    private JTextField destinationLonField, destinationLatField, sourceLonField, sourceLatField, typeDescriptionField, transportDescriptionField;

    private JComboBox ordersComboBox, customersComboBox, transportsComboBox, typesComboBox;

    private JButton createDelivery;

    NewDeliveryFrame() throws IOException {
        this.setTitle("Quick Wheels - New Delivery");
        this.setSize(1200, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(logo.getImage());
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);

        this.setupMenu();
        this.setupLabels();
        this.loadOrders();
        this.loadCustomers();
        this.loadTransports();
        this.loadTypes();

        this.add(menuBar);
        this.setJMenuBar(menuBar);
        this.add(ordersComboBox);
        this.add(customersComboBox);
        this.add(transportsComboBox);
        this.add(typesComboBox);
        this.add(createDelivery);
        this.add(orderIds);
        this.add(destinationLon);
        this.add(destinationLonField);
        this.add(destinationLat);
        this.add(destinationLatField);
        this.add(sourceLon);
        this.add(sourceLonField);
        this.add(sourceLat);
        this.add(sourceLatField);
        this.add(customers);
        this.add(createDelivery);
        this.add(types);
        this.add(typeDescription);
        this.add(typeDescriptionField);
        this.add(transports);
        this.add(transportDescription);
        this.add(transportDescriptionField);
        this.setVisible(true);
    }

    private void setupLabels() {
        orderIds = new JLabel("Order id: ");
        orderIds.setFont(new Font("Arial", Font.BOLD, 22));
        orderIds.setBackground(new Color(0xfefeda));
        orderIds.setOpaque(true);
        orderIds.setForeground(Color.black);
        orderIds.setBorder(new EmptyBorder(0,10,0,0));
        orderIds.setBounds(0,30,orderIds.getPreferredSize().width + 15, 50);

        destinationLon = new JLabel("Destination longitude: ");
        destinationLon.setFont(new Font("Arial", Font.BOLD, 22));
        destinationLon.setBackground(new Color(0xfefeda));
        destinationLon.setOpaque(true);
        destinationLon.setForeground(Color.black);
        destinationLon.setBorder(new EmptyBorder(0,10,0,0));
        destinationLon.setBounds(0,110,destinationLon.getPreferredSize().width + 15, 50);

        destinationLonField = new JTextField();
        destinationLonField.setForeground(Color.black);
        destinationLonField.setBackground(new Color(0xfefeda));
        destinationLonField.setBounds(30 + destinationLon.getPreferredSize().width + 15,110, destinationLon.getPreferredSize().width + 15, 50);

        destinationLat = new JLabel("Destination latitude: ");
        destinationLat.setFont(new Font("Arial", Font.BOLD, 22));
        destinationLat.setBackground(new Color(0xfefeda));
        destinationLat.setOpaque(true);
        destinationLat.setForeground(Color.black);
        destinationLat.setBorder(new EmptyBorder(0,10,0,0));
        destinationLat.setBounds(30 + 2*destinationLon.getPreferredSize().width + 125,110,destinationLon.getPreferredSize().width + 15, 50);

        destinationLatField = new JTextField();
        destinationLatField.setForeground(Color.black);
        destinationLatField.setBackground(new Color(0xfefeda));
        destinationLatField.setBounds(30 + 125 + 3*destinationLon.getPreferredSize().width + 45,110, destinationLon.getPreferredSize().width + 15, 50);

        sourceLon = new JLabel("Source longitude: ");
        sourceLon.setFont(new Font("Arial", Font.BOLD, 22));
        sourceLon.setBackground(new Color(0xfefeda));
        sourceLon.setOpaque(true);
        sourceLon.setForeground(Color.black);
        sourceLon.setBorder(new EmptyBorder(0,10,0,0));
        sourceLon.setBounds(0,190,destinationLon.getPreferredSize().width + 15, 50);

        sourceLonField = new JTextField();
        sourceLonField.setForeground(Color.black);
        sourceLonField.setBackground(new Color(0xfefeda));
        sourceLonField.setBounds(30 + destinationLon.getPreferredSize().width + 15,190, destinationLon.getPreferredSize().width + 15, 50);

        sourceLat = new JLabel("Source latitude: ");
        sourceLat.setFont(new Font("Arial", Font.BOLD, 22));
        sourceLat.setBackground(new Color(0xfefeda));
        sourceLat.setOpaque(true);
        sourceLat.setForeground(Color.black);
        sourceLat.setBorder(new EmptyBorder(0,10,0,0));
        sourceLat.setBounds(30 + 2*destinationLon.getPreferredSize().width + 125,190,destinationLon.getPreferredSize().width + 15, 50);

        sourceLatField = new JTextField();
        sourceLatField.setForeground(Color.black);
        sourceLatField.setBackground(new Color(0xfefeda));
        sourceLatField.setBounds(30 + 125 + 3*destinationLon.getPreferredSize().width + 45,190, destinationLon.getPreferredSize().width + 15, 50);

        customers = new JLabel("Customer name: ");
        customers.setFont(new Font("Arial", Font.BOLD, 22));
        customers.setBackground(new Color(0xfefeda));
        customers.setOpaque(true);
        customers.setForeground(Color.black);
        customers.setBorder(new EmptyBorder(0,10,0,0));
        customers.setBounds(0,270,customers.getPreferredSize().width + 15, 50);

        types = new JLabel("Type name: ");
        types.setFont(new Font("Arial", Font.BOLD, 22));
        types.setBackground(new Color(0xfefeda));
        types.setOpaque(true);
        types.setForeground(Color.black);
        types.setBorder(new EmptyBorder(0,10,0,0));
        types.setBounds(0,350,types.getPreferredSize().width + 15, 50);

        typeDescription = new JLabel("Type description: ");
        typeDescription.setFont(new Font("Arial", Font.BOLD, 22));
        typeDescription.setBackground(new Color(0xfefeda));
        typeDescription.setOpaque(true);
        typeDescription.setForeground(Color.black);
        typeDescription.setBorder(new EmptyBorder(0,10,0,0));
        typeDescription.setBounds(0,430,typeDescription.getPreferredSize().width + 15, 50);

        typeDescriptionField = new JTextField();
        typeDescriptionField.setForeground(Color.black);
        typeDescriptionField.setEditable(false);
        typeDescriptionField.setBounds(30 + typeDescription.getPreferredSize().width + 15,430, 1170 - (30 + typeDescription.getPreferredSize().width + 15), 50);

        transports = new JLabel("Transport name: ");
        transports.setFont(new Font("Arial", Font.BOLD, 22));
        transports.setBackground(new Color(0xfefeda));
        transports.setOpaque(true);
        transports.setForeground(Color.black);
        transports.setBorder(new EmptyBorder(0,10,0,0));
        transports.setBounds(0,510,transports.getPreferredSize().width + 15, 50);

        transportDescription = new JLabel("Transport description: ");
        transportDescription.setFont(new Font("Arial", Font.BOLD, 22));
        transportDescription.setBackground(new Color(0xfefeda));
        transportDescription.setOpaque(true);
        transportDescription.setForeground(Color.black);
        transportDescription.setBorder(new EmptyBorder(0,10,0,0));
        transportDescription.setBounds(0,590,transportDescription.getPreferredSize().width + 15, 50);

        transportDescriptionField = new JTextField();
        transportDescriptionField.setForeground(Color.black);
        transportDescriptionField.setEditable(false);
        transportDescriptionField.setBounds(30 + transportDescription.getPreferredSize().width + 15,590, 1170 - (30 + transportDescription.getPreferredSize().width + 15), 50);


        createDelivery = new JButton("Create delivery");
        createDelivery.addActionListener(this);
        createDelivery.setBounds(1015, 670, createDelivery.getPreferredSize().width + 15, 50);
    }

    private void loadOrders() throws IOException {
        ordersList = OrderController.getAllOrders();
        ordersComboBox = new JComboBox();
        ordersComboBox.addItem("--Choose order--");

        for(Order order : ordersList) {
            ordersComboBox.addItem(Long.toString(order.getId()));
        }
        ordersComboBox.addItemListener(this);
        ordersComboBox.setBounds(30 + orderIds.getPreferredSize().width + 15, 30,1170 - (30 + orderIds.getPreferredSize().width + 15),50);
    }

    private void loadCustomers() throws IOException {
        customersList = CustomerController.getAllCustomers();
        customersComboBox = new JComboBox();
        customersComboBox.addItem("--Choose customer--");

        for(DeliveryCustomer customer : customersList) {
            customersComboBox.addItem(customer.getFullName() + " (" + customer.getEmail() + ")");
        }
        customersComboBox.addItemListener(this);
        customersComboBox.setBounds(30 + customers.getPreferredSize().width + 15, 270,1170 - (30 + customers.getPreferredSize().width + 15),50);
    }

    private void loadTypes() throws IOException {
        typesList = TypeController.getAllTypes();
        typesComboBox = new JComboBox();
        typesComboBox.addItem("--Choose type--");

        for(DeliveryType type : typesList) {
            typesComboBox.addItem(type.getName());
        }
        typesComboBox.addItemListener(this);
        typesComboBox.setBounds(30 + types.getPreferredSize().width + 15, 350,1170 - (30 + types.getPreferredSize().width + 15),50);
    }

    private void loadTransports() throws IOException {
        transportsList = TransportController.getAllTransports();
        transportsComboBox = new JComboBox();
        transportsComboBox.addItem("--Choose transport--");

        for(DeliveryTransport transport : transportsList) {
            transportsComboBox.addItem(transport.getName());
        }
        transportsComboBox.addItemListener(this);
        transportsComboBox.setBounds(30 + transports.getPreferredSize().width + 15, 510,1170 - (30 + transports.getPreferredSize().width + 15),50);
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
            if (evt.getSource().equals(ordersComboBox)) {
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
            } else if (evt.getSource().equals(customersComboBox)) {
                if (evt.getItem().equals("--Choose customer--")) {
                    currCustomer = null;
                } else {
                    for (DeliveryCustomer customer : customersList) {
                        if (evt.getItem().equals(customer.getFullName() + " (" + customer.getEmail() + ")")) {
                            currCustomer = customer;
                            break;
                        }
                    }
                }
            } else if (evt.getSource().equals(typesComboBox)) {
                if (evt.getItem().equals("--Choose type--")) {
                    currType = null;
                    typeDescriptionField.setText("");
                } else {
                    for (DeliveryType type : typesList) {
                        if (evt.getItem().equals(type.getName())) {
                            currType = type;
                            break;
                        }
                    }
                    typeDescriptionField.setText(currType.getDescription());
                }

            } else if (evt.getSource().equals(transportsComboBox)) {
                if (evt.getItem().equals("--Choose transport--")) {
                    currTransport = null;
                    transportDescriptionField.setText("");
                } else {
                    for (DeliveryTransport transport : transportsList) {
                        if (evt.getItem().equals(transport.getName())) {
                            currTransport = transport;
                            break;
                        }
                    }
                    transportDescriptionField.setText(currTransport.getDescription());
                }
            }
//            if (evt.getItem().equals("--Choose order--")) {
//                currOrder = null;
//            } else {
//                for (Order order : ordersList) {
//                    if (evt.getItem().equals(Long.toString(order.getId()))) {
//                        currOrder = order;
//                        break;
//                    }
//                }
//            }
//
//            setCurrOrder();
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
        } else if (e.getActionCommand().equals("Create delivery")) {
            if (currOrder != null && currCustomer != null && currType != null && currTransport != null) {
                try {
                    double fromLat = Double.parseDouble(sourceLatField.getText());
                    double fromLon = Double.parseDouble(sourceLonField.getText());
                    double toLat = Double.parseDouble(destinationLatField.getText());
                    double toLon = Double.parseDouble(destinationLonField.getText());

                    DeliveryController.createDelivery(fromLat, fromLon, toLat, toLon, currCustomer.getId(), currType.getId(), currTransport.getId(), currOrder);

                    currOrder = null;
                    currTransport = null;
                    currType = null;
                    currCustomer = null;
                    ordersComboBox.setSelectedItem("--Choose order--");
                    transportsComboBox.setSelectedItem("--Choose transport--");
                    typesComboBox.setSelectedItem("--Choose type--");
                    customersComboBox.setSelectedItem("--Choose customer--");
                    destinationLonField.setText("");
                    destinationLatField.setText("");
                    sourceLonField.setText("");
                    sourceLatField.setText("");
                    typeDescriptionField.setText("");
                    transportDescriptionField.setText("");
                } catch (NumberFormatException foe) {
                    System.out.println("Invalid format");
                }
            }

        }
    }
}