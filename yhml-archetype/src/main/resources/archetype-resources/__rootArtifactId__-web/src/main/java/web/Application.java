#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\' )
package ${package}.web;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@EnableAsync
@EnableCaching
@SpringBootApplication
@ComponentScan(basePackages = {"${package}"})
@RestController
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping({"/", "/index"})
    public String index() {
        return "Hello ${package}.web.Application...";
    }
}
