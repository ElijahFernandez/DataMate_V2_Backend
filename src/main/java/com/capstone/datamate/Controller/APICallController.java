package com.capstone.datamate.Controller;

import com.capstone.datamate.Service.APICallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin("http://localhost:3000/")
@RestController
public class APICallController {
    @Autowired
    APICallService service;

    @GetMapping("/api")
    public String CallAPI(@RequestParam(name = "filepath") String filepath){
        try {
            return service.callAPI(filepath);
        } catch (IOException e) {
            return "An error occurred: " + e.getMessage();
        }
    }

    @PostMapping("/api/headers")
    public String callAPIWithHeaders(@RequestBody List<String> headers){
        return service.callAPIWithHeaders(headers);
    }
}
