package si.fri.rso.samples.quickwheels.controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import si.fri.rso.samples.quickwheels.models.Order;
import si.fri.rso.samples.quickwheels.models.OrderProduct;
import si.fri.rso.samples.quickwheels.models.OrderStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class OrderController {
    private static final String uri = "http://146.190.206.59:8080/v1/order";

    public OrderController() {}

    public static void createOrder(OrderProduct product) {
        HttpClient client = HttpClient.newHttpClient();

        String body = "{\"quantity\": " + product.getQuantity() + ", \"productId\": " + product.getId() + "}";

        HttpRequest request = HttpRequest.newBuilder()
                .setHeader("Content-type", "application/json")
                .uri(URI.create(uri))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void editOrder(Order order) {
        HttpClient client = HttpClient.newHttpClient();

        String body = "{\"orderDeliveryId\": " + order.getDeliveryId() + ", \"quantity\": " + order.getProduct().getQuantity() + ", \"orderStatusId\": " + order.getStatus().getId() + ", \"orderProductId\": " + order.getProduct().getId() + "}";
        HttpRequest request = HttpRequest.newBuilder()
                .setHeader("Content-type", "application/json")
                .uri(URI.create(uri + "/" + order.getId()))
                .PUT(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getOrdersFiltered(String deliveryId) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();

        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body().toString();
            JSONArray jsonData = new JSONArray(body);
            List<String> orders = new ArrayList();

            for (int i = 0; i < jsonData.length(); i++) {
                JSONObject orderData = (JSONObject) jsonData.get(i);
                Iterator<String> orderKeys = orderData.keys();
                Long orderDeliveryId = -1L, orderId = -1L;
                while (orderKeys.hasNext()) {
                    String key = orderKeys.next();
                    if (key.equals("orderDeliveryId")) {
                        orderDeliveryId = orderData.getLong(key);
                    } else if (key.equals("orderId")) {
                        orderId = orderData.getLong(key);
                    }
                }

                if (Long.toString(orderDeliveryId).equals(deliveryId)) {
                    orders.add(Long.toString(orderId));
                }
            }

            return orders;
        } catch (InterruptedException | JSONException | IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static List<Order> getAllOrders() throws IOException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();

        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body().toString();
            JSONArray jsonData = new JSONArray(body);
            List<Order> orders = new ArrayList();

            for (int i=0; i<jsonData.length(); i++){
                JSONObject orderData = (JSONObject) jsonData.get(i);
                Iterator<String> orderKeys = orderData.keys();
                String createdAt = "", lastModified = "", statusLink = "", nameStatus = "", productLink = "", descriptionProduct = "", nameProduct="";
                long orderId = -1, productId = -1, deliveryId = -1;
                int statusId = -1, quantityLeftProduct = -1, quantityProduct = -1;
                double priceProduct = -1.0;
                JSONObject orderStatus = null, orderProduct = null;

                while (orderKeys.hasNext()) {
                    String key = orderKeys.next();
                    if (key.equals("createdAt")) {
                        createdAt = orderData.getString(key);
                    } else if (key.equals("orderId")) {
                        orderId = orderData.getLong(key);
                    } else if (key.equals("orderStatus")) {
                        orderStatus = (JSONObject) orderData.get(key);
                    } else if (key.equals("lastModified")) {
                        lastModified = orderData.getString(key);
                    } else if (key.equals("orderProduct")) {
                        orderProduct = (JSONObject) orderData.get(key);
                    } else if (key.equals("orderDeliveryId")) {
                        deliveryId = orderData.getInt(key);
                    }
                }

                Iterator<String> statusKeys = orderStatus.keys();
                while (statusKeys.hasNext()) {
                    String key = statusKeys.next();
                    if (key.equals("link")) {
                        statusLink = orderStatus.getString(key);
                    }
                }

                request = HttpRequest.newBuilder()
                        .uri(URI.create(statusLink))
                        .build();
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                JSONArray array = new JSONArray("[" + response.body().toString()+"]");
                orderStatus = (JSONObject) array.get(0);
                statusKeys = orderStatus.keys();

                while (statusKeys.hasNext()) {
                    String key = statusKeys.next();
                    if (key.equals("name")) {
                        nameStatus = orderStatus.getString(key);
                    } else if (key.equals("statusId")) {
                        statusId = orderStatus.getInt(key);
                    }
                }

                Iterator<String> productKeys = orderProduct.keys();
                while (productKeys.hasNext()) {
                    String key = productKeys.next();
                    if (key.equals("link")) {
                        productLink = orderProduct.getString(key);
                    } else if (key.equals("quantity")) {
                        quantityProduct = orderProduct.getInt(key);
                    }
                }

                request = HttpRequest.newBuilder()
                        .uri(URI.create(productLink))
                        .build();
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                array = new JSONArray("[" + response.body().toString()+"]");
                orderProduct = (JSONObject) array.get(0);
                productKeys = orderProduct.keys();

                while (productKeys.hasNext()) {
                    String key = productKeys.next();
                    if (key.equals("description")) {
                        descriptionProduct = orderProduct.getString(key);
                    } else if (key.equals("name")) {
                        nameProduct = orderProduct.getString(key);
                    } else if (key.equals("price")) {
                        priceProduct = orderProduct.getDouble(key);
                    } else if (key.equals("quantityLeft")) {
                        quantityLeftProduct = orderProduct.getInt(key);
                    } else if (key.equals("productId")) {
                        productId = orderProduct.getInt(key);
                    }
                }

                OrderStatus status = new OrderStatus(statusId, nameStatus);
                OrderProduct product = new OrderProduct(productId, descriptionProduct, nameProduct, priceProduct, quantityProduct, quantityLeftProduct);
                Order order = new Order(orderId, createdAt, lastModified, product, status, deliveryId);
                orders.add(order);
            }

            return orders;
        } catch (InterruptedException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
