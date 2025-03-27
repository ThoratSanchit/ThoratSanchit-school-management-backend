package com.jwt.implementation.responces;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GenerateResponces {


        public static <T> ResponseEntity<Map<String, Object>> generateResponse(String message, HttpStatus status,Object id) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", message);
            map.put("status", status.value());
            map.put("id",id);


            return new ResponseEntity<>(map, status);
        }


}
