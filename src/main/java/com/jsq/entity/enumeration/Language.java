package com.jsq.entity.enumeration;

public enum Language implements BusinessEnum {
    ZH_CN("zh_CN");


    private String key;

    Language(String key) {
        this.key = key;
    }

    public static Language parse(String key) {
        for (Language fieldType : Language.values()) {
            if (fieldType.getKey().equals(key)) {
                return fieldType;
            }
        }
        return null;
    }

    @Override
    public String getKey() {
        return this.key;
    }
}
