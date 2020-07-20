package com.example.shopkill.service.impl;

import com.example.shopkill.entity.ItemKillSuccess;
import com.example.shopkill.mapper.ItemKillSuccessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;

import java.util.List;

@Service
public class TaskServiceImpl {

    @Autowired
    private ItemKillSuccessMapper itemKillSuccessMapper;

    @Autowired
    private Environment env;


    /**
     * 定时获取status=0的订单并判断是否超过设定时间，然后进行失效。每隔1分钟判断一次
     */
    @Scheduled(cron = "* 0/1 * * * ?")
    public void schedulerExpireOrders(){
        try {
            List<ItemKillSuccess> list=itemKillSuccessMapper.selectExpireOrders();
            if (list!=null && !list.isEmpty()){
                list.forEach(i -> {
                    if (i!=null && i.getDiffTime() > env.getProperty("task.expire.orders.time",Integer.class)){
                        itemKillSuccessMapper.updateExpireOrder(i.getCode());
                    }
                });
            }
        }catch (Exception e){

        }
    }
}
