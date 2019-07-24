#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\' )
package ${package}.web.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SysApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private AbstractApplicationContext ctx;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        doStatic();

        log.info("=======================================");
        log.info("=         Application Ready           =");
        log.info("=======================================");

        doCopyRight();
    }

    private void doStatic() {
        log.info("bean definition count = {}", ctx.getBeanDefinitionCount());
    }

    private void doCopyRight() {
        log.info("Power by yhml${symbol_escape}n");
    }
}
