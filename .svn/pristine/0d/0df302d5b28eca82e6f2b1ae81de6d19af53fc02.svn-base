package com.iycharge.server.domain.entity.content;

import java.util.Arrays;
import java.util.List;

public enum ContentType {
     IMAGE("图片"),TEXT("文本"),URL("链接");
	private String title;
    
    private ContentType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static List<ContentType> asList() {
        return Arrays.asList(IMAGE, TEXT,URL);
    }
}
