package si.fri.rso.samples.quickwheels.controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import si.fri.rso.samples.quickwheels.models.DeliveryStatus;
import si.fri.rso.samples.quickwheels.models.OrderStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StatusController {
    private static final String deliveryUri = "http://67.207.72.211:8080/v1/deliveryStatus";
    private static final String orderUri = "http://146.190.206.59:8080/v1/orderStatus";

    public StatusController() {}

    public static List<DeliveryStatus> getAllDeliveryStatuses() throws IOException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(deliveryUri))
                .build();

        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body().toString();
            JSONArray jsonData = new JSONArray(body);
            List<DeliveryStatus> statuses = new ArrayList();

            for (int i = 0; i < jsonData.length(); i++) {
                JSONObject statusData = (JSONObject) jsonData.get(i);
                Iterator<String> statusKeys = statusData.keys();
                String description = "", name = "";
                int id = -1;

                while (statusKeys.hasNext()) {
                    String key = statusKeys.next();
                    if (key.equals("productId")) {
                        id = statusData.getInt(key);
                    } else if (key.equals("name")) {
                        name = statusData.getString(key);
                    } else if (key.equals("description")) {
                        description = statusData.getString(key);
                    }
                }

                DeliveryStatus status = new DeliveryStatus(id, name, description);
                statuses.add(status);
            }

            return statuses;
        } catch (InterruptedException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
