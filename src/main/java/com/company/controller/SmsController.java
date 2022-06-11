package com.company.controller;

import com.company.dto.ProfileDetailDTO;
import com.company.dto.response.LoginResponse;
import com.company.service.SmsService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("api/v1/sms")
@RequiredArgsConstructor
public class SmsController {
    private final SmsService smsService;

    @ApiOperation(value = "Send Sms", notes = "Method used for send sms")
    @PostMapping("/send")
    public ResponseEntity<LoginResponse> sendSms(@RequestBody @Valid ProfileDetailDTO dto) {
        log.info("Send Sms {}", dto);
        return smsService.sendSms(dto);
    }
}
