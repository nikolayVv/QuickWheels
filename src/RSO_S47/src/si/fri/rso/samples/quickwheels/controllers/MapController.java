package si.fri.rso.samples.quickwheels.controllers;

import org.json.JSONArray;
import org.json.JSONObject;
import si.fri.rso.samples.quickwheels.models.Delivery;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Iterator;

public class MapController {
    private static final String uri = "http://67.207.72.211:8080/v1/deliveryMap";

    public static long[] calcRoute(Delivery delivery, String unit) {
        HttpClient client = HttpClient.newHttpClient();

        LocalDateTime date = LocalDateTime.now().plusHours(1);
        String departureDate = date.toString() + "Z+01:00";
        System.out.println(departureDate);

        String transport = delivery.getTransport().getName();
        //  Car | Truck | Walking | Bicycle | Motorcycle
        switch (transport) {
            case "Car":
            case "Walking":
            case "Truck":
            case "Bicycle":
            case "Motorcycle":
                break;
            case "Biking":
                transport = "Bicycle";
                break;
            case "Subway":
            case "Bus":
                transport = "Truck";
                break;
            case "Aerial Tramway":
                transport = "Motorcycle";
                break;
            default:
                transport = "Car";
        }

        String body = "{\"departureLat\": " + Double.parseDouble(delivery.getCurrLat()) + ", \"departureLon\": " + Double.parseDouble(delivery.getCurrLon()) + ", \"departureTime\": " + "\"" + departureDate + "\"" + ", \"destinationLat\": " + Double.parseDouble(delivery.getToLat()) + ", \"destinationLon\": " + Double.parseDouble(delivery.getToLon()) + ", \"distanceUnit\": " + "\"" + unit + "\"" + ", \"transport\": " + "\"" + transport + "\"" + "}";

        HttpRequest request = HttpRequest.newBuilder()
                .setHeader("Content-type", "application/json")
                .uri(URI.create(uri + "/calcRoute"))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        long distance = 0L, seconds = 0L;

        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body().toString());
            String data = response.body().toString();
            JSONArray jsonData = new JSONArray(data);
            JSONObject calcRoute = (JSONObject) jsonData.get(0);
            Iterator<String> calcRouteKeys = calcRoute.keys();
            while (calcRouteKeys.hasNext()) {
                String key = calcRouteKeys.next();
                if (key.equals("distance")) {
                    distance = calcRoute.getLong(key);
                } else if (key.equals("durationSeconds")) {
                    seconds = calcRoute.getLong(key);
                }
            }

            return new long[]{distance, seconds};
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new long[2];
    }

    public static byte[] getMap() throws IOException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();

        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body().toString().getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
