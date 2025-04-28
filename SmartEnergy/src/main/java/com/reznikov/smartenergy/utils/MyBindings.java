package com.reznikov.smartenergy.utils;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

public interface MyBindings {

    @Output("output1")
    SubscribableChannel output1();

    @Output("output2")
    SubscribableChannel output2();
}
