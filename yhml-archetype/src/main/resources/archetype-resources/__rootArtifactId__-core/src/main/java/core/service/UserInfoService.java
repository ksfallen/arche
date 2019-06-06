import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;

#set($symbol_pound='#')
        #set($symbol_dollar='$')
        #set($symbol_escape='\' ) package ${package}.core.service;
        {package}.entity.UserInfo;
        {package}.mapper.UserInfoMapper;


@Slf4j
@Service
public class UserInfoService extends ServiceImpl<UserInfoMapper, UserInfo>{

}
