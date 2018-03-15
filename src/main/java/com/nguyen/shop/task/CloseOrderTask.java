package com.nguyen.shop.task;

import com.nguyen.shop.service.IOrderService;
import com.nguyen.shop.utils.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author RWM
 * @date 2018/3/15
 */
@Component
@Slf4j
public class CloseOrderTask {

    @Autowired
    private IOrderService iOrderService;

    @Scheduled(cron = "0 0 */2 * * ?")//每两个小时
    public void closeOrderTaskV1(){
        log.info("关闭订单定时任务启动");
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time","2"));
        iOrderService.closeOrder(hour);
        log.info("关闭订单定时任务结束");
    }

}
