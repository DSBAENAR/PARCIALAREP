package com.edu.mathproxy.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/proxy/math")
public class MathProxyService {
    
    private String ACTIVE_SERVER = System.getenv("MAIN_SERVER");
    private String PASSIVE_SERVER = System.getenv("PASSIVE_SERVER");

    private String active_server = ACTIVE_SERVER;

    private void switchServer(){
        if (active_server.equals(ACTIVE_SERVER)){
            active_server=PASSIVE_SERVER;
        }

        else{
            active_server= ACTIVE_SERVER;
        }
    }

    public String callServer(String urlServer){
        try {
            URL obj = new URL(urlServer);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(3000);
            
            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            
            return response.toString();
        } catch (Exception e) {
            e.getStackTrace();
            return null;
        }
    }


    @GetMapping("/pellSeq")
    public ResponseEntity<?> pellSeq(@RequestParam Integer value) {

        
        String path = "/api/math/pellseq?value=" + value;
        String result = callServer(active_server + path);
        System.out.println(result);

        if (result == null){
            switchServer();
            result = callServer(active_server + path);
        }

        if(result == null){
            return ResponseEntity.internalServerError().body(Map.of(
                "message", "Both servers are down"
            ));
        }

        return ResponseEntity.ok(Map.of(
            "result", result,
            "server", active_server
        ));

    }
    


    

}
