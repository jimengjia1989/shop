package com.example.shopkill.service.impl;

import com.example.shopkill.entity.ItemKill;
import com.example.shopkill.entity.ItemKillSuccess;
import com.example.shopkill.enums.SysConstant;
import com.example.shopkill.mapper.ItemKillMapper;
import com.example.shopkill.mapper.ItemKillSuccessMapper;
import com.example.shopkill.service.KillService;
import com.example.shopkill.utils.RandomUtil;
import org.joda.time.DateTime;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class KillServiceImpl implements KillService {
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private ItemKillMapper itemKillMapper;
    @Autowired
    private ItemKillSuccessMapper itemKillSuccessMapper;
    /**
     * 商品秒杀核心业务逻辑 运用redisson分布式锁
     * @param killId
     * @param userId
     * @return
     */
    @Override
    public Boolean killExecute(Integer killId, Integer userId) throws Exception{
        Boolean result=false;
        final String lockKey=new StringBuffer().append(killId).append("redissonLock").toString();
        RLock lock=redissonClient.getLock(lockKey);
        try {
            //超过10秒后释放锁
            Boolean lockFlag=lock.tryLock(5,10, TimeUnit.SECONDS);
            if(lockFlag){
                //核心业务逻辑处理
                //判段用户是否秒杀过该商品还未付款
                if(itemKillSuccessMapper.itemKillSuccessCountByKillUserId(killId,userId)<=0){
                    ItemKill itemKill=itemKillMapper.selectByPrimaryKey(killId);
                    //判断秒杀商品是否还有库存
                    if(itemKill!=null&&itemKill.getTotal()>0){
                        int res=itemKillMapper.updateKillItemById(killId);
                        if (res>0){
                            //保存订单
                            saveKillSuccessInfo(itemKill,userId);
                            result=true;
                        }
                    }
                }else {
                    throw new Exception("您已经抢购过该商品了!");
                }
            }
        }catch (Exception e){
            throw new Exception("抢购失败");
        }finally {
            lock.unlock();
        }
        return result;
    }

    private void saveKillSuccessInfo(ItemKill itemKill,Integer userId){
        ItemKillSuccess entity=new ItemKillSuccess();
        entity.setCode(RandomUtil.generateOrderCode());
        entity.setCreateTime(DateTime.now().toDate());
        entity.setKillId(itemKill.getId());
        entity.setUserId(userId.toString());
        entity.setStatus(SysConstant.OrderStatus.SuccessNotPayed.getCode().byteValue());
        itemKillSuccessMapper.insertSelective(entity);
    }
}
