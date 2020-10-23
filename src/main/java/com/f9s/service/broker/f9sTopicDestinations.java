package com.f9s.service.broker;

import lombok.Data;
import lombok.Synchronized;
import lombok.experimental.UtilityClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class f9sTopicDestinations {
    static HashMap<String, String> clientTopic = new HashMap<>();

    @Synchronized
    public void setDestination(String id, String dest) {
        clientTopic.putIfAbsent(id, dest);
    }

    @Synchronized
    public String getDestinations(String id) {
        return clientTopic.get(id);
    }

    @Synchronized
    public void removeDestinations(String id) {
        clientTopic.remove(id);
    }

    @Synchronized
    public List<String> getDistinctDestinations() {
        List<String> result = new ArrayList<>();

        clientTopic.forEach((k,v) -> result.add(v));

        return result.stream().distinct().collect(Collectors.toList()) ;
    }
}
