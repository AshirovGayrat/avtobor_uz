package com.company.service;

import com.company.dto.ProfileDetailDTO;
import com.company.dto.response.LoginResponse;
import com.company.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmsService {

    private final ProfileDetailService profileDetailService;

    @Value("${message.access.key}")
    private String ACCESS_KEY;

    public ResponseEntity<LoginResponse> sendSms(ProfileDetailDTO dto) {
        String code = RandomUtil.getRandomSmsCode();
        System.out.println(code);

        dto.setSmsCode(code);
        dto.setSms("Your verification code is " + code);

        try {
            sendMessage(dto);
        } catch (RuntimeException e) {
            log.warn("Bad request {}", dto);
            return response(dto, new LoginResponse());
        }
        profileDetailService.create(dto);

        return ResponseEntity.ok().body(new LoginResponse(true, String.valueOf(code)));
    }

    private void sendMessage(ProfileDetailDTO dto) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("recipients", dto.getPhoneNumber());
        jsonObject.put("originator", "Caravan");
        jsonObject.put("body", dto.getSms());

        RequestBody formBody = RequestBody.create(
                jsonObject.toString(),
                MediaType.parse("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url("https://rest.messagebird.com/messages")
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .header("Authorization", "AccessKey " + ACCESS_KEY)
                .post(formBody)
                .build();

        OkHttpClient client = new OkHttpClient();

        Thread thread = new Thread(() -> {
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    System.out.println(response);
                } else {
                    throw new RuntimeException();
                }
            } catch (IOException e) {
                throw new RuntimeException();
            }
        });

        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = (th, ex) -> {

        };

        thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
        thread.start();
    }

    public ResponseEntity<LoginResponse> response(ProfileDetailDTO dto, LoginResponse response) {

        switch (dto.getAppLanguage()) {
            case en -> {
                response.setTitle("Sms code wrong");
                response.setMessage("You entered wrong code. Please try again.");
            }
            case uz -> {
                response.setTitle("Sms kod xato");
                response.setMessage("Siz xato kod kiritdingiz. Qaytadan urinib ko'ring.");
            }
            case ru -> {
                response.setTitle("Не правильный смс код");
                response.setMessage("Вы ввели не правильный смс код. Пожалуйста повторите ещё раз.");
            }
        }
        return ResponseEntity.badRequest().body(response);
    }
}
