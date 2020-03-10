package com.spring.reactive.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class Util {

    @Value("${pattern.deptPattern}")
    private  String deptPattern;

    @Value("${pattern.ailsePattern}")
    private  String ailsePattern;

    @Value("${pattern.shelfPattern}")
    private  String shelfPattern;


    public  Map<String,Pattern> getAllPatterns() {

        Map<String,Pattern> patternMap = new HashMap<>();
        patternMap.put("dept",Pattern.compile(deptPattern));
        patternMap.put("aisle",Pattern.compile(ailsePattern));
        patternMap.put("shelf",Pattern.compile(shelfPattern));

        return patternMap;
    }
}
