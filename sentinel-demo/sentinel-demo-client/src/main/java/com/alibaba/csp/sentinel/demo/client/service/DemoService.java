package com.alibaba.csp.sentinel.demo.client.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Service;

/**
 * @author Eric Zhao
 */
@Service
public class DemoService {

    @SentinelResource(value = "hello", blockHandler = "sayHelloBlockHandler")
    public String sayHello(String name) {
        return "Hello, " + name;
    }

    public String sayHelloBlockHandler(String name, BlockException ex) {
        // This is the block handler.
        ex.printStackTrace();
        return String.format("Oops, <%s> blocked by Sentinel", name);
    }
}
