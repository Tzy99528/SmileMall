package com.smile.mall.security.common.controller;

// 验证码返回码枚举，定义各种错误码，比如坐标错误、验证失败、参数错误
import com.anji.captcha.model.common.RepCodeEnum;
// 验证码统一返回对象，包含状态码、提示信息、返回数据
import com.anji.captcha.model.common.ResponseModel;
// 前端传过来的请求实体，获取验证码、校验验证码都用这个对象封装参数
import com.anji.captcha.model.vo.CaptchaVO;
// anji‑captcha 核心业务接口，框架已经实现好，我们直接注入调用即可
import com.anji.captcha.service.CaptchaService;
// swagger3 注解，接口文档标签，在接口文档页面给这个控制器打上 “验证码” 分组标签。
import io.swagger.v3.oas.annotations.tags.Tag;
// 接口控制器，**所有方法返回值直接输出 JSON，不需要视图**
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.smile.mall.common.response.ServerResponseEntity;

@RestController
@RequestMapping("/captcha")
@Tag(name = "验证吗")
public class CaptchaController 
{
    
    private final CaptchaService captchaService;

    public CaptchaController(CaptchaService captchaService)
    {
        this.captchaService = captchaService;
    }

    @PostMapping("/get")
    public ServerResponseEntity<ResponseModel> getCaptcha(@RequestBody CaptchaVO captchaVO)
    {
        return ServerResponseEntity.success(captchaService.get(captchaVO));
    }

}
