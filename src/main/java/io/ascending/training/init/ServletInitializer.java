package io.ascending.training.init;

import com.amazonaws.services.amplify.model.App;
import com.amazonaws.services.autoscaling.model.LoadBalancerTargetGroupState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;


//WebSocket
// if extends SpringBootServletInitializer, if using external tomcat, the embedded tomcat won't be used, if not use the embedded one.
@Configuration
public class ServletInitializer extends SpringBootServletInitializer {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        logger.info("SpringBootServletInitializer check point");
        return builder.sources(ApplicationBoot.class);
    }
}
