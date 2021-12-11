package com.example.lavish;

public class LavishClass {
    private int id;
    private int uid;
    private String content;
    private String title;

    public LavishClass( int uid, String content, String title) {
        this.uid = uid;
        this.content = content;
        this.title = title;
    }

    public LavishClass(int id, int uid, String content, String title) {
        this.id = id;
        this.uid = uid;
        this.content = content;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
