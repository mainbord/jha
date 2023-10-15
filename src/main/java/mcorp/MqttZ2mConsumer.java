package mcorp;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class MqttZ2mConsumer {

    @Inject
    AppConfig appConfig;

    @Incoming("radiator-gost")
    public void consume(byte[] raw) {
        RadiatorDto response = null;
        try {
            response = appConfig.getObjectMapper().readValue(new String(raw), RadiatorDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Log.info(response);
    }

}
