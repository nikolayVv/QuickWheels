package si.fri.rso.samples.quickwheels.controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import si.fri.rso.samples.quickwheels.models.DeliveryTransport;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TransportController {
    private static final String uri = "http://67.207.72.211:8080/v1/deliveryTransport";

    public TransportController() {}

    public static List<DeliveryTransport> getAllTransports() throws IOException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();

        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body().toString();
            JSONArray jsonData = new JSONArray(body);
            List<DeliveryTransport> transports = new ArrayList();

            for (int i=0; i<jsonData.length(); i++){
                JSONObject transportData = (JSONObject) jsonData.get(i);
                Iterator<String> transportKeys = transportData.keys();
                String name = "", description = "";
                int id = -1;

                while (transportKeys.hasNext()) {
                    String key = transportKeys.next();
                    if (key.equals("transportId")) {
                        id = transportData.getInt(key);
                    } else if (key.equals("name")) {
                        name = transportData.getString(key);
                    } else if (key.equals("description")) {
                        description = transportData.getString(key);
                    }
                }

                DeliveryTransport transport = new DeliveryTransport(id, name, description);
                transports.add(transport);
            }

            return transports;
        } catch (InterruptedException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
