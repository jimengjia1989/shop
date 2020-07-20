package com.example.shopkill.mapper;

import com.example.shopkill.entity.ItemKillSuccess;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface ItemKillSuccessMapper extends tk.mybatis.mapper.common.Mapper<ItemKillSuccess> {
    public int itemKillSuccessCountByKillUserId(@Param("killId") Integer killId,@Param("userId") int userId);
    public List<ItemKillSuccess>selectExpireOrders();
    public  void updateExpireOrder (@Param("code") String code);
}
