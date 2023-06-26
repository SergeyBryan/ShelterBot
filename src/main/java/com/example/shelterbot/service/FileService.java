package com.example.shelterbot.service;

import com.pengrad.telegrambot.model.Message;

public interface FileService {
    String saveImage(Message message);

    byte[] getImage(String filePath);
}
