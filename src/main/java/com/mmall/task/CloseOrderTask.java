package com.mmall.task;

import com.mmall.service.IOrderService;
import com.mmall.utils.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author:Chengkangxin
 * @Description:
 * @Date:Create in 17:46 2018/11/25
 */
@Component
@Slf4j
public class CloseOrderTask {
    @Autowired
    private IOrderService iOrderService;

    @Scheduled(cron="0 */1 * * * ?")//每个一分钟的整数倍
    public void closeOrderTaskV1(){
        log.info("关闭订单定时任务启动");
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.hour"));
        iOrderService.closeOrder(hour);
    }
}
