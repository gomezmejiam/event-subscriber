package co.com.ingeniods.shared.infrastructure.subscribers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.stereotype.Service;

@Service
public class SubConsumerFactory {
  
  
  @Autowired
  private PubSubTemplate pubSubTemplate;

  public SubConsumerFactory(PubSubTemplate pubSubTemplate) {
    this.pubSubTemplate = pubSubTemplate;
  }

  public SubConsumerDlq getSubConsumerDlq(String queue, String dlq, String accepted) {
    return new SubConsumerDlq(pubSubTemplate, queue, dlq,accepted) {};
  }
  

}
