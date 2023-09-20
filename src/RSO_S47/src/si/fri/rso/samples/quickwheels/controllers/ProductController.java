package si.fri.rso.samples.quickwheels.controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import si.fri.rso.samples.quickwheels.models.OrderProduct;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductController {
    private static final String uri = "http://146.190.206.59:8080/v1/orderProduct";

    public ProductController() {}

    public static List<OrderProduct> getAllProducts() throws IOException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();

        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body().toString();
            JSONArray jsonData = new JSONArray(body);
            List<OrderProduct> products = new ArrayList();

            for (int i = 0; i < jsonData.length(); i++) {
                JSONObject productData = (JSONObject) jsonData.get(i);
                Iterator<String> productKeys = productData.keys();
                String description = "", name = "";
                long id = -1;
                int quantityLeft = -1;
                double price = -1.0;

                while (productKeys.hasNext()) {
                    String key = productKeys.next();
                    if (key.equals("quantityLeft")) {
                        quantityLeft = productData.getInt(key);
                    } else if (key.equals("productId")) {
                        id = productData.getLong(key);
                    } else if (key.equals("price")) {
                        price = productData.getDouble(key);
                    } else if (key.equals("name")) {
                        name = productData.getString(key);
                    } else if (key.equals("description")) {
                        description = productData.getString(key);
                    }
                }

                OrderProduct orderProduct = new OrderProduct(id, description, name, price, 0, quantityLeft);
                products.add(orderProduct);
            }

            return products;
        } catch (InterruptedException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
