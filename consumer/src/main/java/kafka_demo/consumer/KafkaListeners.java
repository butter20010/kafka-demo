package kafka_demo.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

  @KafkaListener(topics = "order", groupId = "group_id1")
  public void handle (String value) {
    System.out.println("✅ Message '" + value + "' was received");
  }
}
