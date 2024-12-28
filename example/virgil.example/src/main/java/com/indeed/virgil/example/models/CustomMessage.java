package com.indeed.virgil.example.models;

public record CustomMessage(long id, String content) {
    public CustomMessage(
        final long id,
        final String content
    ) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
