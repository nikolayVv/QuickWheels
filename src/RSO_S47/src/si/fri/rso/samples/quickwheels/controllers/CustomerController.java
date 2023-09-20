package si.fri.rso.samples.quickwheels.controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import si.fri.rso.samples.quickwheels.models.DeliveryCustomer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CustomerController {
    private static final String uri = "http://67.207.72.211:8080/v1/deliveryCustomer";

    public CustomerController() {}

    public static List<DeliveryCustomer> getAllCustomers() throws IOException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();

        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body().toString();
            JSONArray jsonData = new JSONArray(body);
            List<DeliveryCustomer> customers = new ArrayList();

            for (int i = 0; i < jsonData.length(); i++) {
                JSONObject customerData = (JSONObject) jsonData.get(i);
                Iterator<String> customerKeys = customerData.keys();
                String name = "", surname = "", email = "", phone = "", dateOfBirth = "", note = "";
                long id = -1;

                while (customerKeys.hasNext()) {
                    String key = customerKeys.next();
                    if (key.equals("customerId")) {
                        id = customerData.getLong(key);
                    } else if (key.equals("name")) {
                        name = customerData.getString(key);
                    } else if (key.equals("surname")) {
                        surname = customerData.getString(key);
                    } else if (key.equals("emailAddress")) {
                        email = customerData.getString(key);
                    } else if (key.equals("phoneNumber")) {
                        phone = customerData.getString(key);
                    } else if (key.equals("dateOfBirth")) {
                        dateOfBirth = customerData.getString(key);
                    } else if (key.equals("note")) {
                        note = customerData.getString(key);
                    }
                }

                DeliveryCustomer customer = new DeliveryCustomer(id, name, surname, email, phone, dateOfBirth, note);
                customers.add(customer);
            }

            return customers;
        } catch (InterruptedException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
