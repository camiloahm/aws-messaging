package com.sample.msg.controller;


import com.sample.msg.service.QueueOneService;
import com.sample.msg.service.QueueTwoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @NotNull}))
public class SQSController {


    private final QueueOneService queueOneService;
    private final QueueTwoService queueTwoService;

    @GetMapping("/one")
    public ResponseEntity sendSqsOne() {
        queueOneService.send();

        return ResponseEntity.ok("Success");
    }

    @GetMapping("/two")
    public ResponseEntity sendSqstwo() {
        queueTwoService.send();
        return ResponseEntity.ok("Success");
    }
}
