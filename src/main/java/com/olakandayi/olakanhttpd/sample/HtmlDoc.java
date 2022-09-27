package com.olakandayi.olakanhttpd.sample;

import java.util.ArrayList;
import java.util.List;

public class HtmlDoc {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public List<HtmlElement> getDocBody() {
        return docBody;
    }

    public void setDocBody(List<HtmlElement> docBody) {
        this.docBody = docBody;
    }

    private String lang;
    private String charset="utf-8";
    private List<HtmlElement> docBody=new ArrayList<>();
    private String printBody(){
        String body="";
        for (HtmlElement element:docBody){
            body.concat(element.toString());
        }
        return body;
    }
    @Override
    public String toString() {
        return "<!DOCTYPE html>"+
                "<html lang=\""+getLang()+"\">"+
                "<head>"+
                "<meta charset=\""+getCharset()+"\">"+
                "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"+
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"+
                "<title>"+getTitle()+"</title>"+
                "</head>"+ "<body>" + printBody() +
                "</body>"+
                "</html>";
    }
}
