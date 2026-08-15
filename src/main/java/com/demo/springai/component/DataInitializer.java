package com.demo.springai.component;

import org.springframework.ai.reader.TextReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    public void initData(){
        TextReader textReader = new TextReader(new ClassPathResource("product_details.txt"));
    }
}
