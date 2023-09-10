package com.github.doublebin.springfox.bridge.demo.service;

import io.springfox.bridge.core.builder.annotations.BridgeApi;
import io.springfox.bridge.core.builder.annotations.BridgeGroup;
import io.springfox.bridge.core.builder.annotations.BridgeModelProperty;
import io.springfox.bridge.core.builder.annotations.BridgeOperation;
import org.springframework.stereotype.Service;

@Service
@BridgeApi(value = "TestService Apis", description = "测试服务3",tags = "66666666")
@BridgeGroup("test-group1")
public class TestService {
    @BridgeOperation(value = "测试查询", notes = "测试查询方法说明")
    public String testQuery(@BridgeModelProperty(value = "用户id", required = true) long id, @BridgeModelProperty(value = "请求2", required = false) String request){
        return "Test query success, id is " + id;
    }
}
