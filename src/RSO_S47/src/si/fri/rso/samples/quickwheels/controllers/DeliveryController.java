package si.fri.rso.samples.quickwheels.controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import si.fri.rso.samples.quickwheels.models.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DeliveryController {
    private static final String uri = "http://67.207.72.211:8080/v1/delivery";

    public DeliveryController() {}

    public static void createDelivery(double fromLat, double fromLon, double toLat, double toLon, long currCustomerId, int currTypeId, int currTransportId, Order currOrder) {
        HttpClient client = HttpClient.newHttpClient();
        Long fromAddressId = -1L, toAddressId = -1L;

        String body = "{\"city\": \"\", \"country\": \"\", \"street\": \"\", \"zipCode\": \"\", \"geoLat\": \"" + fromLat + "\", \"geoLon\": \"" +  fromLon + "\"}";
        System.out.println(body);

        HttpRequest request = HttpRequest.newBuilder()
                .setHeader("Content-type", "application/json")
                .uri(URI.create(uri + "Address"))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String fromAddress = response.body().toString();
            JSONArray jsonData = new JSONArray( "[" + fromAddress + "]");
            JSONObject fromAddressObj = (JSONObject) jsonData.get(0);
            Iterator<String> fromAddressKeys = fromAddressObj.keys();
            while (fromAddressKeys.hasNext()) {
                String key = fromAddressKeys.next();
                if (key.equals("addressId")) {
                    fromAddressId = fromAddressObj.getLong(key);
                    break;
                }
            }

            body = "{\"city\": \"\", \"country\": \"\", \"street\": \"\", \"zipCode\": \"\", \"geoLat\": \"" + toLat + "\", \"geoLon\": \"" +  toLon + "\"}";
            System.out.println(body);

            request = HttpRequest.newBuilder()
                    .setHeader("Content-type", "application/json")
                    .uri(URI.create(uri + "Address"))
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String toAddress = response.body().toString();
            jsonData = new JSONArray( "[" + toAddress + "]");
            JSONObject toAddressObj = (JSONObject) jsonData.get(0);
            Iterator<String> toAddressKeys = toAddressObj.keys();
            while (toAddressKeys.hasNext()) {
                String key = toAddressKeys.next();
                if (key.equals("addressId")) {
                    toAddressId = toAddressObj.getLong(key);
                    break;
                }
            }

            System.out.println(fromAddressId);
            System.out.println(toAddressId);
            LocalDateTime date = LocalDateTime.now();
            String today = date.toString();

            body = "{\"customerId\": " + currCustomerId + ", \"fromAddressId\": " + fromAddressId + ", \"toAddressId\": " + toAddressId + ", \"typeId\": " + currTypeId + ", \"transportId\": " + currTransportId + ", \"geoLon\": " +  (-12.12) + ", \"geoLat\": " + (23.31) + ", \"createdAt\": \"" + today + "\", \"lastModified\": \"" + today + "\", \"toAddress\": {}}";

            System.out.println(body);
            request = HttpRequest.newBuilder()
                    .setHeader("Content-type", "application/json")
                    .uri(URI.create(uri))
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String newDelivery = response.body().toString();
            jsonData = new JSONArray( "[" + newDelivery + "]");
            JSONObject deliveryObj = (JSONObject) jsonData.get(0);
            Iterator<String> deliveryKeys = deliveryObj.keys();
            while (deliveryKeys.hasNext()) {
                String key = deliveryKeys.next();
                if (key.equals("deliveryId")) {
                    currOrder.setDeliveryId(deliveryObj.getLong(key));
                    break;
                }
            }

            OrderController.editOrder(currOrder);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Delivery> getAllDeliveries() throws IOException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();

        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body().toString();
            JSONArray jsonData = new JSONArray(body);
            List<Delivery> deliveries = new ArrayList();
            for (int i=0; i<jsonData.length(); i++) {
                JSONObject deliveryData = (JSONObject) jsonData.get(i);
                Iterator<String> deliveryKeys = deliveryData.keys();
                String createdAt = "", lastModified = "", customerLink = "", customerName = "", customerSurname = "", customerEmail = "", customerPhone = "", customerNote = "", fromLat = "", fromLon = "", toLat = "", toLon = "", currLat = "", currLon = "", note = "", statusLink = "", statusName = "", statusDescription = "", transportLink = "", transportName = "", transportDescription = "", typeLink = "", typeName = "", typeDescription = "";
                long deliveryId = -1, customerId = -1;
                int statusId = -1, transportId = -1, typeId = -1;
                JSONObject deliveryStatus = null, deliveryCustomer = null, deliveryType = null, deliveryTransport = null, fromAddress = null, toAddress = null;

                while (deliveryKeys.hasNext()) {
                    String key = deliveryKeys.next();
                    if (key.equals("customer")) {
                        deliveryCustomer = (JSONObject) deliveryData.get(key);
                    }  else if (key.equals("transport")) {
                        deliveryTransport = (JSONObject) deliveryData.get(key);
                    } else if (key.equals("type")) {
                        deliveryType = (JSONObject) deliveryData.get(key);
                    } else if (key.equals("fromAddress")) {
                        fromAddress = (JSONObject) deliveryData.get(key);
                    } else if (key.equals("toAddress")) {
                        toAddress = (JSONObject) deliveryData.get(key);
                    } else if (key.equals("status")) {
                        deliveryStatus = (JSONObject) deliveryData.get(key);
                    } else if (key.equals("geoLat")) {
                        currLat = deliveryData.getString(key);
                    } else if (key.equals("geoLon")) {
                        currLon = deliveryData.getString(key);
                    } else if (key.equals("note")) {
                        note = deliveryData.getString(key);
                    } else if (key.equals("lastModified")) {
                        lastModified = deliveryData.getString(key);
                    } else if (key.equals("createdAt")) {
                        createdAt = deliveryData.getString(key);
                    } else if (key.equals("deliveryId")) {
                        deliveryId = deliveryData.getLong(key);
                    }
                }

//                Iterator<String> statusKeys = deliveryStatus.keys();
//                while (statusKeys.hasNext()) {
//                    String key = statusKeys.next();
//                    if (key.equals("link")) {
//                        statusLink = deliveryStatus.getString(key);
//                    }
//                }
//                request = HttpRequest.newBuilder()
//                        .uri(URI.create(statusLink))
//                        .build();
//                response = client.send(request, HttpResponse.BodyHandlers.ofString());
//                JSONArray array = new JSONArray("[" + response.body().toString()+"]");
//                deliveryStatus = (JSONObject) array.get(0);
//                statusKeys = deliveryStatus.keys();
//                while (statusKeys.hasNext()) {
//                    String key = statusKeys.next();
//                    if (key.equals("name")) {
//                        statusName = deliveryStatus.getString(key);
//                    } else if (key.equals("description")) {
//                        statusDescription = deliveryStatus.getString(key);
//                    } else if (key.equals("statusId")) {
//                        statusId = deliveryStatus.getInt(key);
//                    }
//                }

                Iterator<String> transportKeys = deliveryTransport.keys();
                while (transportKeys.hasNext()) {
                    String key = transportKeys.next();
                    if (key.equals("link")) {
                        transportLink = deliveryTransport.getString(key);
                    }
                }
                request = HttpRequest.newBuilder()
                        .uri(URI.create(transportLink))
                        .build();
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                JSONArray array = new JSONArray("[" + response.body().toString()+"]");
                deliveryTransport = (JSONObject) array.get(0);
                transportKeys = deliveryTransport.keys();
                while (transportKeys.hasNext()) {
                    String key = transportKeys.next();
                    if (key.equals("name")) {
                        transportName = deliveryTransport.getString(key);
                    } else if (key.equals("description")) {
                        transportDescription = deliveryTransport.getString(key);
                    } else if (key.equals("transportId")) {
                        transportId = deliveryTransport.getInt(key);
                    }
                }

                Iterator<String> typeKeys = deliveryType.keys();
                while (typeKeys.hasNext()) {
                    String key = typeKeys.next();
                    if (key.equals("link")) {
                        typeLink = deliveryType.getString(key);
                    }
                }
                request = HttpRequest.newBuilder()
                        .uri(URI.create(typeLink))
                        .build();
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                array = new JSONArray("[" + response.body().toString()+"]");
                deliveryType = (JSONObject) array.get(0);
                typeKeys = deliveryType.keys();
                while (typeKeys.hasNext()) {
                    String key = typeKeys.next();
                    if (key.equals("name")) {
                        typeName = deliveryType.getString(key);
                    } else if (key.equals("description")) {
                        typeDescription = deliveryType.getString(key);
                    } else if (key.equals("typeId")) {
                        typeId = deliveryType.getInt(key);
                    }
                }

                Iterator<String> customerKeys = deliveryCustomer.keys();
                while (customerKeys.hasNext()) {
                    String key = customerKeys.next();
                    if (key.equals("link")) {
                        customerLink = deliveryCustomer.getString(key);
                    }
                }
                request = HttpRequest.newBuilder()
                        .uri(URI.create(customerLink))
                        .build();
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                array = new JSONArray("[" + response.body().toString()+"]");
                deliveryCustomer = (JSONObject) array.get(0);
                customerKeys = deliveryCustomer.keys();
                while (customerKeys.hasNext()) {
                    String key = customerKeys.next();
                    if (key.equals("name")) {
                        customerName = deliveryCustomer.getString(key);
                    } else if (key.equals("surname")) {
                        customerSurname = deliveryCustomer.getString(key);
                    } else if (key.equals("phoneNumber")) {
                        customerPhone = deliveryCustomer.getString(key);
                    } else if (key.equals("emailAddress")) {
                        customerEmail = deliveryCustomer.getString(key);
                    } else if (key.equals("customerId")) {
                        customerId = deliveryCustomer.getInt(key);
                    }
                }

                Iterator<String> fromAddressKeys = fromAddress.keys();
                while (fromAddressKeys.hasNext()) {
                    String key = fromAddressKeys.next();
                    if (key.equals("geoLon")) {
                        fromLon = fromAddress.getString(key);
                    } else if (key.equals("geoLat")) {
                        fromLat = fromAddress.getString(key);
                    }
                }

                Iterator<String> toAddressKeys = toAddress.keys();
                while (toAddressKeys.hasNext()) {
                    String key = toAddressKeys.next();
                    if (key.equals("geoLon")) {
                        toLon = toAddress.getString(key);
                    } else if (key.equals("geoLat")) {
                        toLat = toAddress.getString(key);
                    }
                }

                List<String> orders = OrderController.getOrdersFiltered(Long.toString(deliveryId));
                DeliveryCustomer customer = new DeliveryCustomer(customerId, customerName, customerSurname, customerEmail, customerPhone, "", customerNote);
                //DeliveryStatus status = new DeliveryStatus(statusId, statusName, statusDescription);
                DeliveryType type = new DeliveryType(typeId, typeName, typeDescription);
                DeliveryTransport transport = new DeliveryTransport(transportId, transportName, transportDescription);
                Delivery delivery = new Delivery(deliveryId, createdAt, lastModified, customer, fromLon, fromLat, toLon, toLat, currLon, currLat, null, type, transport, note, orders);
                deliveries.add(delivery);
            }

            return deliveries;
        } catch (InterruptedException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
