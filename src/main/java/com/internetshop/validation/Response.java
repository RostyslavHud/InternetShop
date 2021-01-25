package com.internetshop.validation;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class Response {

    private int code;
    private Map<String, String> messages;
}
