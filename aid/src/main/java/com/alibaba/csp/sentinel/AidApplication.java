package com.alibaba.csp.sentinel;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;

public class AidApplication {

    private static String resourceName = "abc";

    public static void main(String[] args) throws Exception{
        final AidApplication aid = new AidApplication();
        aid.initFlowQpsRule();
        for (int i = 0; i < 50; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    aid.foo();
                }
            }).start();
            Thread.sleep(100);
        }
        Thread.sleep(3000);
    }

    @SuppressWarnings("unused")
    public void foo() {
        try (Entry entry = SphU.entry(resourceName)){
            //do some thing
            System.out.println("Hello world");
        } catch (BlockException bockException) {
            bockException.printStackTrace();
        }
    }

    private void initFlowQpsRule() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule(resourceName);
        // set limit qps to 20
        rule.setCount(3);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setLimitApp("default");
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }
}
