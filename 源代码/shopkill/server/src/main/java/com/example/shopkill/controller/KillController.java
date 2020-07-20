package com.example.shopkill.controller;

import com.example.shopkill.model.BaseResponse;
import com.example.shopkill.model.KillReq;
import com.example.shopkill.model.StatusCode;
import com.example.shopkill.service.KillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shopkill")
public class KillController {
    @Autowired
    private KillService killService;

    @PostMapping("/kill")
    @ResponseBody
    public BaseResponse kill(@RequestBody KillReq killReq){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            Boolean res= killService.killExecute(killReq.getKillId(),killReq.getUserId());
            if (!res){
                return new BaseResponse(StatusCode.Fail.getCode(),"商品已抢购完毕");
            }
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }


        return  response;
    }
}
