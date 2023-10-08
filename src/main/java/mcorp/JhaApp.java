package mcorp;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class JhaApp {
//    private static final Logger log = LoggerFactory.getLogger(JhaApp.class);

    private final String MOSQUITTO_IP = "192.168.50.124";
    private final String MOSQUITTO_PORT = "1883";
    private final String KITCHEN_RADIATOR_TOPIC = "zigbee2mqtt/0x84fd27fffea65ddd";
    private final String SERVER_URI = String.format("tcp://%s:%s", MOSQUITTO_IP, MOSQUITTO_PORT);//.formatted(MOSQUITTO_IP, MOSQUITTO_PORT);
    private final AppConfig appConfig = new AppConfig();
    public void Run() {
        String publisherId = UUID.randomUUID().toString();
        try {
            IMqttClient publisher = new MqttClient(SERVER_URI, publisherId);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            publisher.connect(options);

            CountDownLatch receivedSignal = new CountDownLatch(10);

            String subscriberId = UUID.randomUUID().toString();

            IMqttClient subscriber = new MqttClient(SERVER_URI, subscriberId);
            subscriber.connect(options);

            subscriber.subscribe(KITCHEN_RADIATOR_TOPIC, (topic, msg) -> {
                byte[] payload = msg.getPayload();
                RadiatorDto response = appConfig.getObjectMapper().readValue(new String(payload), RadiatorDto.class);

                // ... payload handling omitted
                log.info("paylod log = " + response.toString());
                receivedSignal.countDown();
            });
            receivedSignal.await(1, TimeUnit.MINUTES);
        } catch (MqttException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
