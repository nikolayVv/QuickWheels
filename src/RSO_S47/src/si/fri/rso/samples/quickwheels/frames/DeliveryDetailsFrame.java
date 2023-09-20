package si.fri.rso.samples.quickwheels.frames;
import si.fri.rso.samples.quickwheels.controllers.DeliveryController;
import si.fri.rso.samples.quickwheels.controllers.MapController;
import si.fri.rso.samples.quickwheels.models.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class DeliveryDetailsFrame extends JFrame implements ActionListener, ItemListener {
    private final ImageIcon logo = new ImageIcon(System.getProperty("user.dir") + "/images/logo.PNG");

    private JMenuBar menuBar;

    private JMenu deliveries, orders;

    private JMenuItem newDelivery, deliveryDetails, newOrder, orderDetails;

    private JLabel id, createdAt, lastModified, customer, destination, source, currentLat, currentLon, type, typeDescription, transport, orderIds, calculatedTime, distanceUnits;

    private JTextField idField, createdAtField, lastModifiedField, customerField, destinationField, sourceField, currentLatField, currentLonField, typeField, typeDescriptionField, transportField, orderIdsField;

    private List<Delivery> deliveriesList;

    private Delivery currDelivery;

    private JComboBox deliveriesComboBox, distanceUnitsComboBox;

    private JButton calculateTime;

    DeliveryDetailsFrame() throws IOException {
        this.setTitle("Quick Wheels - Delivery Details");
        this.setSize(1200, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(logo.getImage());
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);

        this.setupMenu();
        this.setupLabels();
        this.loadDeliveries();

        this.add(menuBar);
        this.setJMenuBar(menuBar);
        this.add(deliveriesComboBox);
        this.add(id);
        this.add(idField);
        this.add(createdAt);
        this.add(createdAtField);
        this.add(lastModified);
        this.add(lastModifiedField);
        this.add(customer);
        this.add(customerField);
        this.add(destination);
        this.add(destinationField);
        this.add(source);
        this.add(sourceField);
        this.add(type);
        this.add(typeField);
        this.add(typeDescription);
        this.add(typeDescriptionField);
        this.add(transport);
        this.add(transportField);
        this.add(orderIds);
        this.add(orderIdsField);
        this.add(currentLon);
        this.add(currentLonField);
        this.add(currentLat);
        this.add(currentLatField);
        this.add(distanceUnits);
        this.add(distanceUnitsComboBox);
        this.add(calculateTime);
        this.add(calculatedTime);
        this.setVisible(true);
    }

    private void setupLabels() {
        id = new JLabel("Delivery id: ");
        id.setFont(new Font("Arial", Font.BOLD, 22));
        id.setBackground(new Color(0xfefeda));
        id.setOpaque(true);
        id.setForeground(Color.black);
        id.setBorder(new EmptyBorder(0,10,0,0));
        id.setBounds(0,30,id.getPreferredSize().width + 15, 50);

        idField = new JTextField();
        idField.setForeground(Color.black);
        idField.setEditable(false);
        idField.setBounds(30 + id.getPreferredSize().width + 15,30, 1170 - (30 + id.getPreferredSize().width + 15) - 330, 50);

        createdAt = new JLabel("Created at: ");
        createdAt.setFont(new Font("Arial", Font.BOLD, 22));
        createdAt.setBackground(new Color(0xfefeda));
        createdAt.setOpaque(true);
        createdAt.setForeground(Color.black);
        createdAt.setBorder(new EmptyBorder(0,10,0,0));
        createdAt.setBounds(0,100,createdAt.getPreferredSize().width + 15, 50);

        createdAtField = new JTextField();
        createdAtField.setForeground(Color.black);
        createdAtField.setEditable(false);
        createdAtField.setBounds(30 + createdAt.getPreferredSize().width + 15,100, 1170 - (30 + createdAt.getPreferredSize().width + 15) - 330, 50);

        lastModified = new JLabel("Last modified: ");
        lastModified.setFont(new Font("Arial", Font.BOLD, 22));
        lastModified.setBackground(new Color(0xfefeda));
        lastModified.setOpaque(true);
        lastModified.setForeground(Color.black);
        lastModified.setBorder(new EmptyBorder(0,10,0,0));
        lastModified.setBounds(0,170,lastModified.getPreferredSize().width + 15, 50);

        lastModifiedField = new JTextField();
        lastModifiedField.setForeground(Color.black);
        lastModifiedField.setEditable(false);
        lastModifiedField.setBounds(30 + lastModified.getPreferredSize().width + 15,170, 1170 - (30 + lastModified.getPreferredSize().width + 15) - 330, 50);

        customer = new JLabel("Customer: ");
        customer.setFont(new Font("Arial", Font.BOLD, 22));
        customer.setBackground(new Color(0xfefeda));
        customer.setOpaque(true);
        customer.setForeground(Color.black);
        customer.setBorder(new EmptyBorder(0,10,0,0));
        customer.setBounds(0,240,customer.getPreferredSize().width + 15, 50);

        customerField = new JTextField();
        customerField.setForeground(Color.black);
        customerField.setEditable(false);
        customerField.setBounds(30 + customer.getPreferredSize().width + 15,240, 1170 - (30 + customer.getPreferredSize().width + 15) - 330, 50);

        destination = new JLabel("Destination: ");
        destination.setFont(new Font("Arial", Font.BOLD, 22));
        destination.setBackground(new Color(0xfefeda));
        destination.setOpaque(true);
        destination.setForeground(Color.black);
        destination.setBorder(new EmptyBorder(0,10,0,0));
        destination.setBounds(0,310,destination.getPreferredSize().width + 15, 50);

        destinationField = new JTextField();
        destinationField.setForeground(Color.black);
        destinationField.setEditable(false);
        destinationField.setBounds(30 + destination.getPreferredSize().width + 15,310, 1170 - (30 + destination.getPreferredSize().width + 15) - 330, 50);

        source = new JLabel("Source: ");
        source.setFont(new Font("Arial", Font.BOLD, 22));
        source.setBackground(new Color(0xfefeda));
        source.setOpaque(true);
        source.setForeground(Color.black);
        source.setBorder(new EmptyBorder(0,10,0,0));
        source.setBounds(0,380,source.getPreferredSize().width + 15, 50);

        sourceField = new JTextField();
        sourceField.setForeground(Color.black);
        sourceField.setEditable(false);
        sourceField.setBounds(30 + source.getPreferredSize().width + 15,380, 1170 - (30 + source.getPreferredSize().width + 15) - 330, 50);

        type = new JLabel("Type: ");
        type.setFont(new Font("Arial", Font.BOLD, 22));
        type.setBackground(new Color(0xfefeda));
        type.setOpaque(true);
        type.setForeground(Color.black);
        type.setBorder(new EmptyBorder(0,10,0,0));
        type.setBounds(0,450,type.getPreferredSize().width + 15, 50);

        typeField = new JTextField();
        typeField.setForeground(Color.black);
        typeField.setEditable(false);
        typeField.setBounds(30 + type.getPreferredSize().width + 15,450, 1170 - (30 + type.getPreferredSize().width + 15) - 330, 50);

        typeDescription = new JLabel("Type description: ");
        typeDescription.setFont(new Font("Arial", Font.BOLD, 22));
        typeDescription.setBackground(new Color(0xfefeda));
        typeDescription.setOpaque(true);
        typeDescription.setForeground(Color.black);
        typeDescription.setBorder(new EmptyBorder(0,10,0,0));
        typeDescription.setBounds(0,520,typeDescription.getPreferredSize().width + 15, 50);

        typeDescriptionField = new JTextField();
        typeDescriptionField.setForeground(Color.black);
        typeDescriptionField.setEditable(false);
        typeDescriptionField.setBounds(30 + typeDescription.getPreferredSize().width + 15,520, 1170 - (30 + typeDescription.getPreferredSize().width + 15) - 330, 50);

        transport = new JLabel("Transport: ");
        transport.setFont(new Font("Arial", Font.BOLD, 22));
        transport.setBackground(new Color(0xfefeda));
        transport.setOpaque(true);
        transport.setForeground(Color.black);
        transport.setBorder(new EmptyBorder(0,10,0,0));
        transport.setBounds(0,590,transport.getPreferredSize().width + 15, 50);

        transportField = new JTextField();
        transportField.setForeground(Color.black);
        transportField.setEditable(false);
        transportField.setBounds(30 + transport.getPreferredSize().width + 15,590, 1170 - (30 + transport.getPreferredSize().width + 15) - 330, 50);

        orderIds = new JLabel("Order's id: ");
        orderIds.setFont(new Font("Arial", Font.BOLD, 22));
        orderIds.setBackground(new Color(0xfefeda));
        orderIds.setOpaque(true);
        orderIds.setForeground(Color.black);
        orderIds.setBorder(new EmptyBorder(0,10,0,0));
        orderIds.setBounds(0,660,orderIds.getPreferredSize().width + 15, 50);

        orderIdsField = new JTextField();
        orderIdsField.setForeground(Color.black);
        orderIdsField.setEditable(false);
        orderIdsField.setBounds(30 + orderIds.getPreferredSize().width + 15,660, 1170 - (30 + orderIds.getPreferredSize().width + 15) - 330, 50);
    }

    private void setCurrDelivery() {
        currentLonField.setText("");
        currentLatField.setText("");
        calculatedTime.setText("");

        if (currDelivery == null) {
            idField.setText("");
            createdAtField.setText("");
            lastModifiedField.setText("");
            customerField.setText("");
            destinationField.setText("");
            sourceField.setText("");
            typeField.setText("");
            typeDescriptionField.setText("");
            transportField.setText("");
            orderIdsField.setText("");

            calculateTime.setEnabled(false);
            currentLonField.setEditable(false);
            currentLonField.setBackground(null);
            currentLatField.setEditable(false);
            currentLatField.setBackground(null);
            distanceUnitsComboBox.setEnabled(false);
        } else {
            DeliveryType currType = currDelivery.getType();
            DeliveryTransport currTransport = currDelivery.getTransport();
            DeliveryCustomer currCustomer = currDelivery.getCustomer();

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMM d, yyyy HH:mm:ss a");
            LocalDateTime createdAtDateTime = LocalDateTime.parse(currDelivery.getCreatedAt());
            LocalDateTime lastModifiedDateTime = LocalDateTime.parse(currDelivery.getLastModified());

            idField.setText(Long.toString(currDelivery.getId()));
            createdAtField.setText(dateFormatter.format(createdAtDateTime));
            lastModifiedField.setText(dateFormatter.format(lastModifiedDateTime));
            customerField.setText(currCustomer.getFullName() + ", CONTACT: " + currCustomer.getEmail() + " (" + currCustomer.getPhone() + ")");
            destinationField.setText("LON: " + currDelivery.getToLon() + ", LAT: " + currDelivery.getToLat());
            sourceField.setText("LON: " + currDelivery.getFromLon() + ", LAT: " + currDelivery.getFromLat());
            typeField.setText(currType.getName());
            typeDescriptionField.setText(currType.getDescription());
            transportField.setText(currTransport.getName());
            orderIdsField.setText(currDelivery.getOrders());

            calculateTime.setEnabled(true);
            currentLonField.setEditable(true);
            currentLonField.setBackground(new Color(0xfefeda));
            currentLatField.setEditable(true);
            currentLatField.setBackground(new Color(0xfefeda));
            distanceUnitsComboBox.setEnabled(true);
        }
    }

    private void loadDeliveries() throws IOException {
        deliveriesList = DeliveryController.getAllDeliveries();
        deliveriesComboBox = new JComboBox();
        deliveriesComboBox.addItem("--Choose delivery--");

        for(Delivery delivery : deliveriesList) {
            deliveriesComboBox.addItem(Long.toString(delivery.getId()));
        }
        deliveriesComboBox.addItemListener(this);
        deliveriesComboBox.setBounds(870, 36,300,40);

        currentLon = new JLabel("Current longitude");
        currentLon.setFont(new Font("Arial", Font.BOLD, 20));
        currentLon.setBackground(new Color(0xfefeda));
        currentLon.setOpaque(true);
        currentLon.setForeground(Color.black);
        currentLon.setBorder(new EmptyBorder(0,10,0,0));
        currentLon.setBounds(915, 360,currentLon.getPreferredSize().width + 15, 30);

        currentLonField = new JTextField();
        currentLonField.setForeground(Color.black);
        currentLonField.setEditable(false);
        currentLonField.setHorizontalAlignment(SwingConstants.CENTER);
        currentLonField.setBounds(915, 390, currentLon.getPreferredSize().width + 15, 40);

        currentLat = new JLabel("Current latitude");
        currentLat.setFont(new Font("Arial", Font.BOLD, 20));
        currentLat.setBackground(new Color(0xfefeda));
        currentLat.setOpaque(true);
        currentLat.setForeground(Color.black);
        currentLat.setBorder(new EmptyBorder(0,10,0,0));
        currentLat.setBounds(915, 450,currentLon.getPreferredSize().width + 15, 30);

        currentLatField = new JTextField();
        currentLatField.setForeground(Color.black);
        currentLatField.setEditable(false);
        currentLatField.setHorizontalAlignment(SwingConstants.CENTER);
        currentLatField.setBounds(915, 480, currentLon.getPreferredSize().width + 15, 40);

        distanceUnits = new JLabel("Distance unit");
        distanceUnits.setFont(new Font("Arial", Font.BOLD, 20));
        distanceUnits.setBackground(new Color(0xfefeda));
        distanceUnits.setOpaque(true);
        distanceUnits.setForeground(Color.black);
        distanceUnits.setBorder(new EmptyBorder(0,10,0,0));
        distanceUnits.setBounds(915, 540,currentLon.getPreferredSize().width + 15, 30);

        distanceUnitsComboBox = new JComboBox();
        distanceUnitsComboBox.addItem("Kilometers");
        distanceUnitsComboBox.addItem("Miles");
        distanceUnitsComboBox.setEnabled(false);
        distanceUnitsComboBox.setBounds(915, 570, currentLon.getPreferredSize().width + 15, 40);

        calculateTime = new JButton("Calculate time");
        calculateTime.setEnabled(false);
        calculateTime.addActionListener(this);
        calculateTime.setBounds(955, 660, calculateTime.getPreferredSize().width + 15, 40);

        calculatedTime = new JLabel();
        calculatedTime.setFont(new Font("Arial", Font.BOLD, 15));
        calculatedTime.setOpaque(true);
        calculatedTime.setForeground(Color.black);
        calculatedTime.setHorizontalAlignment(SwingConstants.CENTER);
        calculatedTime.setBounds(870, 710,300, 30);
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

    private void calculateRoute() {
        if (currentLonField.getText().trim().isEmpty() || currentLatField.getText().trim().isEmpty()) {
            calculatedTime.setText("Invalid value");
        } else {
            try {
                double lon = Double.parseDouble(currentLonField.getText().trim());
                double lat = Double.parseDouble(currentLatField.getText().trim());

                currDelivery.setCurrLon(currentLonField.getText().trim());
                currDelivery.setCurrLat(currentLatField.getText().trim());

                String unit = "Kilometers";
                if (distanceUnitsComboBox.getSelectedIndex() == 1) {
                    unit = "Miles";
                }

                long[] response = MapController.calcRoute(currDelivery, unit);
                System.out.println(response[0]);
                System.out.println(response[1]);
                int day = (int) TimeUnit.SECONDS.toDays(response[1]);
                long hours = TimeUnit.SECONDS.toHours(response[1]) - (day * 24);
                long minutes = TimeUnit.SECONDS.toMinutes(response[1]) - (TimeUnit.SECONDS.toHours(response[1]) * 60);

                String unitLabel = "km";
                if (unit.equals("Miles")) {
                    unitLabel = "mi";
                }

                calculatedTime.setText((day != 0 ? day + " days, " : "") + (hours != 0 ? hours + " hours, " : (day != 0 ? "0 hours, " : "")) + (minutes != 0 ? minutes + " minutes" : (hours != 0 ? "0 minutes" : (day != 0 ? "0 minutes" : ""))) + (response[0] != 0 ? "(" + response[0] + unitLabel + ")" : ""));


            } catch (NumberFormatException foe) {
                calculatedTime.setText("Invalid value");
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent evt) {
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            if (evt.getItem().equals("--Choose delivery--")) {
                currDelivery = null;
            } else {
                for (Delivery delivery : deliveriesList) {
                    if (evt.getItem().equals(Long.toString(delivery.getId()))) {
                        currDelivery = delivery;
                        break;
                    }
                }
            }

            setCurrDelivery();
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
        } else if (e.getActionCommand().equals("Calculate time")) {
            calculateRoute();
        }
    }
}

