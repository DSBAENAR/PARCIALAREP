package com.edu.mathproxy.core;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/math")
public class MathService {
    
    @GetMapping("/pellseq")
    public ResponseEntity<?> calculatePellSequence(@RequestParam Integer value) {
        String result = "";
        int[] secuence = pellSeq(value);
        for (int i = 0; i < secuence.length; i++) {
            result += secuence[i] + " ";

        }

        return ResponseEntity.ok(Map.of(
            "operation","Secuencia de Pell",
            "input", value,
            "output", result
        ));

    }
    

    private int[] pellSeq(Integer value){
        int[] result = new int[value + 1];
        result[0] = 0;
        result[1] = 1;
        
        for (int i = 2; i <= value; i++) {
            result[i] = (2 * result[i - 1]) + result[i - 2];
        }

        return result;
    }

    @GetMapping("/health")
    public ResponseEntity<?> checkHealth() {
        return ResponseEntity.ok(Map.of("status","up"));
    }



    
}
