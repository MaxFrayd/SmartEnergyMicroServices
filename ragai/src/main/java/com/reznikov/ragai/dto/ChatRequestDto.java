package com.reznikov.ragai.dto;

public class ChatRequestDto {
    private String role;
    private String content;

    public ChatRequestDto() {
    }

    public ChatRequestDto(String role, String content) {
        this.content = content;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
