package kafka_demo.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.concurrent.CompletableFuture;

@Controller
public class HomeController {

  private final KafkaTemplate<String, String> template;

  public HomeController(KafkaTemplate<String, String> template) {
    this.template = template;
  }

  @GetMapping("/home")
  public void getHome() {
  }

  @PostMapping("/send")
  public void sendMessages() {
    sendToKafka();
  }

  private void sendToKafka() {
    for (int i = 0; i < 10000; i++) {
      CompletableFuture<SendResult<String, String>> future = template.send("order", "data" + i);
      int finalI = i;
      future.whenComplete((result, ex) -> {
        if (ex == null) {
          System.out.println("✅ Sent message " + finalI + " with offset " + result.getRecordMetadata().offset());
        } else {
          System.err.println("❌ Failed to send message " + finalI + ": " + ex.getMessage());
        }
      });
    }
  }
}
