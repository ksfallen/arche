import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.yhml.core.base.AbstractApplicationTest;

#set($symbol_pound='#')
        #set($symbol_dollar='$')
        #set($symbol_escape='\' ) package test;

@ComponentScan(value="${package}")
public abstract class ApplicationTest extends AbstractApplicationTest {

    @Autowired
    protected ApplicationContext ctx;

    @Before
    public void before() {

    }
}
