package si.fri.rso.samples.deliveries.services.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle("app-properties")
public class AppProperties {

    @ConfigValue(value = "external-services.enabled", watch = true)
    private boolean externalServicesEnabled;

    @ConfigValue(watch = true)
    private boolean healthy;

    @ConfigValue("amazon-location.access-key")
    private String amazonLocationAccessKey;

    @ConfigValue("amazon-location.secret-key")
    private String amazonLocationSecretKey;

    public boolean isExternalServicesEnabled() {
        return externalServicesEnabled;
    }

    public void setExternalServicesEnabled(boolean externalServicesEnabled) {
        this.externalServicesEnabled = externalServicesEnabled;
    }

    public boolean isHealthy() {
        return healthy;
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }

    public String getAmazonLocationSecretKey() {
        return amazonLocationSecretKey;
    }

    public void setAmazonLocationSecretKey(String amazonLocationSecretKey) {
        this.amazonLocationSecretKey = amazonLocationSecretKey;
    }

    public String getAmazonLocationAccessKey() {
        return amazonLocationAccessKey;
    }

    public void setAmazonLocationAccessKey(String amazonLocationAccessKey) {
        this.amazonLocationAccessKey = amazonLocationAccessKey;
    }
}
