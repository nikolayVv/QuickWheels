package si.fri.rso.samples.quickwheels.controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import si.fri.rso.samples.quickwheels.models.DeliveryType;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TypeController {
    private static final String uri = "http://67.207.72.211:8080/v1/deliveryType";

    public TypeController() {}

    public static List<DeliveryType> getAllTypes() throws IOException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();

        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body().toString();
            JSONArray jsonData = new JSONArray(body);
            List<DeliveryType> types = new ArrayList();

            for (int i=0; i<jsonData.length(); i++){
                JSONObject typeData = (JSONObject) jsonData.get(i);
                Iterator<String> typeKeys = typeData.keys();
                String name = "", description = "";
                int id = -1;

                while (typeKeys.hasNext()) {
                    String key = typeKeys.next();
                    if (key.equals("typeId")) {
                        id = typeData.getInt(key);
                    } else if (key.equals("name")) {
                        name = typeData.getString(key);
                    } else if (key.equals("description")) {
                        description = typeData.getString(key);
                    }
                }

                DeliveryType type = new DeliveryType(id, name, description);
                types.add(type);
            }

            return types;
        } catch (InterruptedException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
