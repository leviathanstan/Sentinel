package com.alibaba.csp.sentinel.demo.client.source;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.zookeeper.ZookeeperDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

public class InitZookeeperSource implements InitFunc {

    private String remoteAddress = "10.227.30.159:2181,10.227.30.159:2182,10.227.30.159:2183";
    private String group = "/sentinel/dynamic/";
    private String data = "sentinel-client";

    @Override
    public void init() {
        initFlow();
    }

    private void initFlow() {
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new ZookeeperDataSource<>(remoteAddress, group + data,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {}));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
    }

    private <T> String encodeJson(T t) {
        return JSON.toJSONString(t);
    }
}
