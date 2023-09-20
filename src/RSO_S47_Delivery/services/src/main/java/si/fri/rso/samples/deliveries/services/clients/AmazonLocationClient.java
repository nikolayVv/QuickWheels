package si.fri.rso.samples.deliveries.services.clients;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.location.AmazonLocation;
import com.amazonaws.services.location.AmazonLocationClientBuilder;
import com.amazonaws.services.location.model.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.nio.ByteBuffer;
import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import si.fri.rso.samples.deliveries.lib.RouteRequest;
import si.fri.rso.samples.deliveries.services.config.AppProperties;

@ApplicationScoped
public class AmazonLocationClient {

    @Inject
    private AppProperties appProperties;

    private AmazonLocation locationClient;

    @PostConstruct
    private void init() {

        AWSCredentials credentials;
        try {
            credentials = new BasicAWSCredentials(
                    appProperties.getAmazonLocationAccessKey(),
                    appProperties.getAmazonLocationSecretKey());
        } catch (Exception e) {
            throw new AmazonClientException("Cannot initialise the credentials.", e);
        }


        locationClient = AmazonLocationClientBuilder
                .standard()
                .withRegion(Regions.EU_WEST_1)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    public byte[] getMap() {
        GetMapStyleDescriptorRequest req = new GetMapStyleDescriptorRequest().withMapName("quickWheelsMap");
        GetMapStyleDescriptorResult res = locationClient.getMapStyleDescriptor(req);

        ByteBuffer blobRes = res.getBlob();
        byte[] blobBytes = new byte[blobRes.array().length];
        blobRes.get(0, blobBytes);

        return blobBytes;
    }

    public List<Leg> calculateRoute(RouteRequest route) throws ParseException {
        Double[] departurePosition = {route.getDepartureLon(), route.getDepartureLat()};
        Double[] destinationPosition = {route.getDestinationLon(), route.getDestinationLat()};
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sssXXX");
        Date departureTime = sf.parse(route.getDepartureTime());

        CalculateRouteRequest req = new CalculateRouteRequest()
                .withCalculatorName("QuickWheelsCalculator")
                .withDeparturePosition(departurePosition)
                .withDestinationPosition(destinationPosition)
                .withDepartureTime(departureTime)
                .withDistanceUnit(route.getDistanceUnit())
                .withTravelMode(route.getTransport());
        CalculateRouteResult res = locationClient.calculateRoute(req);

        return res.getLegs();
    }
}