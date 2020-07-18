package com.javatechie.aws.sns;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SubscribeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication (exclude = {ContextStackAutoConfiguration.class, ContextRegionProviderAutoConfiguration.class})
@RestController
public class SpringbootAwsSnsExampleApplication {

    @Autowired
    private AmazonSNSClient snsClient;

    String TOPIC_ARN="" ;

	@GetMapping("/addSubscription/{email}")
	public String addSubscription(@PathVariable String email) {
		SubscribeRequest request = new SubscribeRequest(TOPIC_ARN, "email", email);
		snsClient.subscribe(request);
		return "Subscription request is pending. To confirm the subscription, check your email : " + email;
	}

	 @GetMapping("/sendNotification")
	public String publishMessageToTopic(){
		 PublishRequest publishRequest=new PublishRequest(TOPIC_ARN,buildEmailBody(),"Notification: Network connectivity issue");
		 snsClient.publish(publishRequest);
		 return "Notification send successfully !!";
	}



	private String buildEmailBody(){
		return "Dear Employee ,\n" +
				"\n" +
				"\n" +
				"Connection down Bangalore."+"\n"+
				"All the servers in Bangalore Data center are not accessible. We are working on it ! \n" +
				"Notification will be sent out as soon as the issue is resolved. For any questions regarding this message please feel free to contact IT Service Support team";
	}

    public static void main(String[] args) {
        SpringApplication.run(SpringbootAwsSnsExampleApplication.class, args);
    }

}
