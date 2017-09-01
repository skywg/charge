package com.iycharge.server.domain.entity.operator;

import java.util.Arrays;
import java.util.List;

public enum OperatorType {
    ORGANIZATION("自营"), PERSONAL("个人"),OTHER("第三方");
	
    private String title;

    OperatorType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static List<OperatorType> asList() {
        return Arrays.asList(ORGANIZATION, PERSONAL,OTHER);
    }
}
