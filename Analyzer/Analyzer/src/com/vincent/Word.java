package com.vincent;

public class Word {
    private String element = "";
    private boolean isPunct = false;
    private String pos = "";
    private String info = "";
    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public Boolean getPunct() {
        return isPunct;
    }

    public void setPunct(boolean punct) {
        isPunct = punct;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
