import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

#set($symbol_pound='#')
        #set($symbol_dollar='$')
        #set($symbol_escape='\' ) package ${package}.test.api;
        {package}.core.service.UserInfoService;
        {package}.test.AbstractApplicationTest;

/**
 * @author bins
 */
@Slf4j
public class MemeberManagerTest extends AbstractApplicationTest {

    @Autowired
    private UserInfoService userInfoService;

    //TODO 此处可以注意需要测试的服务接口

    /**
     * 测试单元
     */
    @Test
    public void contextLoads() {
        System.out.println("测试类出来了");
        //TODO 此处调用接口方法
    }

}
