package com.yhml.core.base;

import org.apache.commons.codec.CharEncoding;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MultiValueMap;

import com.yhml.core.util.BeanUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

// @formatter:off
/**
 * @author: Jianfeng.Hu
 * @date: 2017/9/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
// @ComponentScan(value="com.yhml")
@AutoConfigureMockMvc
public class AbstractApplicationTest {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected MockMvc mvc;

    public String httpGet(String url) {
        return httpGet(url, null);
    }

    public String httpGet(String url, Object params) {
        String content = null;
        try {
            content = getAction(url, params).andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(content);
        return content;
    }

    public String httpPost(String url, Object params) {
        String content = null;
        try {
            content = postAction(url, params).andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(content);
        return content;
    }

    public ResultActions getAction(String url, Object object) throws Exception {
        MultiValueMap<String, String> params = BeanUtil.toMultiValueMap(object);
        RequestBuilder request  = get(url).params(params).characterEncoding(CharEncoding.UTF_8);
        return mvc.perform(request);
    }

    public ResultActions postAction(String url, Object object) throws Exception {
        MultiValueMap<String, String> params = BeanUtil.toMultiValueMap(object);
        RequestBuilder request = post(url).params(params).characterEncoding(CharEncoding.UTF_8).contentType(MediaType.APPLICATION_JSON_UTF8);
        return mvc.perform(request);
    }

}
