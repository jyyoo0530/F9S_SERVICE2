//package com.f9s.service.broker;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class RestApiController {
//
//    @Autowired
//    ArtemisProducer producer;
//
//    @RequestMapping(value="/produce")
//    public String produce(@RequestParam("msg")String msg){
//        producer.send(msg);
//        return "Done";
//    }
//}
