package trafficlight.example.phuctnh;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;

public class mqtt {
    private static final String BROKER_HOST = "iot.neosoft.vn";
    private static final int BROKER_PORT = 1883;
    private static final String USERNAME = "neosoftmqtt";
    private static final String PASSWORD = "NeosoftMQTT20#!";

    private Mqtt5AsyncClient client;

    // Hàm kết nối MQTT
    public void connectToMqtt() {
        client = MqttClient.builder()
                .useMqttVersion5()
                .serverHost(BROKER_HOST)
                .serverPort(BROKER_PORT)
                .sslWithDefaultConfig()
                .buildAsync();

        client.connectWith()
                .simpleAuth()
                .username(USERNAME)
                .password(PASSWORD.getBytes())
                .applySimpleAuth()
                .send()
                .whenComplete((connAck, throwable) -> {
                    if (throwable != null) {
                        System.out.println("Kết nối thất bại: " + throwable.getMessage());
                    } else {
                        System.out.println("Kết nối thành công!");
                        // Gọi hàm gửi tin nhắn sau khi kết nối thành công
                       sendMessage("update", "16,3,20", 0);
                    }
                });
    }

    // Hàm gửi tin nhắn đến chủ đề master/script


    // Hàm gửi tin nhắn đến một chủ đề cụ thể
    public void sendMessage(String topic, String payload, int qos) {
        if (client != null) {
            client.publishWith()
                    .topic(topic)
                    .payload(payload.getBytes())
                    .qos(MqttQos.fromCode(qos))
                    .send()
                    .whenComplete((publish, throwable) -> {
                        if (throwable != null) {
                            System.out.println("Gửi tin nhắn thất bại: " + throwable.getMessage());
                        } else {
                            System.out.println("Tin nhắn đã được gửi thành công!");
                        }
                    });
        } else {
            System.out.println("Client chưa được kết nối.");
        }
    }
}
