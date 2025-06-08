package com.chatapp.ChatAppV2.Models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BackendResponse {

    private boolean success;
    private String message;

}
