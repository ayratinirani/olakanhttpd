package com.olakandayi.olakanhttpd.sample;

import java.util.ArrayList;
import java.util.List;

public class HtmlElement {
    String tagtype;

    public String getTagtype() {
        return tagtype;
    }

    public void setTagtype(String tagtype) {
        this.tagtype = tagtype;
    }

    public List<String> getAtribs() {
        return atribs;
    }

    public void setAtribs(List<String> atribs) {
        this.atribs = atribs;
    }

    public String getInnerText() {
        return innerText;
    }

    public void setInnerText(String innerText) {
        this.innerText = innerText;
    }

    public List<HtmlElement> getChildren() {
        return children;
    }

    public void setChildren(List<HtmlElement> children) {
        this.children = children;
    }

    List<String> atribs=new ArrayList<>();
    List<String> internalStyle=new ArrayList<>();
    List<String>classnames=new ArrayList<>();
    String innerText;
    List<HtmlElement>children=new ArrayList<>();
    public String toString(){
        String out="<"+tagtype;
        for (String at:atribs){
            out.concat(" "+at);
        }
        out.concat(" class=\"");
        for (String classname:classnames){
            out.concat(" "+classname);
        }
        out.concat(" \" ");
        out.concat(" class=\"");
        out.concat(" style=\" ");
        for (String style:internalStyle){
            out.concat(" "+style);
        }
        out.concat(" >");
        if(innerText!=null){
            out.concat(innerText);
        }
        if(!children.isEmpty()){
            for (HtmlElement element:children){
                out.concat(" "+element);
            }
        }
        out.concat("</"+tagtype+">");
        return null;
    }
}
