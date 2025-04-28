package com.reznikov.smartenergy.events.source;

import com.reznikov.smartenergy.dto.CustomerFullDto;

import com.reznikov.smartenergy.events.model.SupplierEnergyChangeModel;
import com.reznikov.smartenergy.utils.ModelMapper;
import com.reznikov.smartenergy.utils.MyBindings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SimpleSourceBean {
    @Autowired
    private MyBindings bindings;

    private static final Logger logger = LoggerFactory.getLogger(SimpleSourceBean.class);

    public void publishCustomerChange(CustomerFullDto customer){

        bindings.output2().send(MessageBuilder.withPayload(customer).build());
    }

    public void publishSupplierEnergyUpdate(SupplierEnergyChangeModel changeModel){

        //source.output().send(MessageBuilder.withPayload(changeModel).build());
        //source.output().send(MessageBuilder.withPayload(customer).build());
        bindings.output1().send(MessageBuilder.withPayload(changeModel).build());
    }




}
