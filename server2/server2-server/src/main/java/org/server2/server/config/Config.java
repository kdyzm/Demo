package org.server2.server.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = { "org.server2.server" })
@Import({ com.server2.service.config.Config.class})
public class Config {

}
