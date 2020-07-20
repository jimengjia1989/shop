package com.example.shopkill.mapper;

import com.example.shopkill.entity.ItemKill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface ItemKillMapper extends tk.mybatis.mapper.common.Mapper<ItemKill> {
    public  int updateKillItemById(@Param("id") Integer id);
}
