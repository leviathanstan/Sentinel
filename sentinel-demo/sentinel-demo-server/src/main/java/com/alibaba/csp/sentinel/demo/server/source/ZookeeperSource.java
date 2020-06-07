package com.alibaba.csp.sentinel.demo.server.source;

import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterFlowRuleManager;
import com.alibaba.csp.sentinel.datasource.zookeeper.ZookeeperDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

public class ZookeeperSource implements InitFunc {

    private String remoteAddress = "10.227.30.159:2181,10.227.30.159:2182,10.227.30.159:2183";
    private String group = "/sentinel/dynamic/";
    private String flow = "sentinel-client";

    @Override
    public void init() {
        ClusterFlowRuleManager.setPropertySupplier(namespace -> {
            return new ZookeeperDataSource<>(remoteAddress, group + flow,
                    source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {})
            ).getProperty();
        });
    }
}
