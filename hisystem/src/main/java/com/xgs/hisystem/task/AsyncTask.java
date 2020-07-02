package com.xgs.hisystem.task;

import com.xgs.hisystem.config.HisConstants;
import com.xgs.hisystem.pojo.entity.LoginInforEntity;
import com.xgs.hisystem.pojo.entity.UserEntity;
import com.xgs.hisystem.repository.ILoginInforRepository;
import com.xgs.hisystem.repository.IUserRepository;
import com.xgs.hisystem.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @author xgs
 * @Description:
 * @date 2019/3/25
 */
@Component
public class AsyncTask {

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private ILoginInforRepository iLoginInforRepository;

    private static final Logger logger = LoggerFactory.getLogger(AsyncTask.class);

    /**
     * 保存登录信息 异步线程
     *
     * @param ip
     * @param broswer
     * @return
     */
    @Async("myTaskAsyncPool")
    public void saveLoginInfor(String ip, String broswer, String email) {

        UserEntity user = iUserRepository.findByEmail(email);
        String userId = user.getId();
        LoginInforEntity userInfo= iLoginInforRepository.findByLoginIpAndLoginBroswerAndUserId(ip, broswer, userId);

        try {
            if (userInfo == null) {

                userInfo= new LoginInforEntity();
                userInfo.setLoginIp(ip);
                userInfo.setLoginBroswer(broswer);
                userInfo.setUser(user);
                userInfo.setDescription(email);

                RestTemplate restTemplate = new RestTemplate();
                //获取地理位置
                String url = HisConstants.URL.ADDRESS_URL ;
                String resultAddress = restTemplate.getForObject(url, String.class);

                logger.info("登录获取地址，url={},返回={}",url,resultAddress);

                if (!StringUtils.isEmpty(resultAddress)) {
                    resultAddress=resultAddress.replaceAll("[\r\n]" ,"");
                    userInfo.setLoginAddress(resultAddress);
                }
            } else {
                userInfo.setCreateDatetime(DateUtil.getCurrentDateToString());
            }
            iLoginInforRepository.saveAndFlush(userInfo);
        } catch (Exception e) {
            logger.error("userId={},保存登录记录失败！msg={}", userId, e.getStackTrace());
        }

    }

}
