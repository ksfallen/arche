import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

#set($symbol_pound='#')
        #set($symbol_dollar='$')
        #set($symbol_escape='\' ) package ${package}.web.listener;

/**
 * ApplicationRunner or CommandLineRunner
 * @author bins
 */
@Slf4j
@Component
public class SagaAppliactionRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.debug("run after app bootstarp");
    }
}
