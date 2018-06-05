package com.jsq.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
public class TitleLine implements Serializable {

    private UUID uuid;

    private String type;

    private String title;

    private double rate;

    private Set options;

    private String answer;

    /*@Override
    public String toString(){
        return this.uuid+"|%|"+this.type+"|%|"+this.title+"|%|"+this.options+"|%|"+this.answer;
    }*/

    private TitleLine getTitleLine (String str){
        String s[] = str.split("|%|");
        System.out.print(s);
        return TitleLine.builder().build();
    }
}
