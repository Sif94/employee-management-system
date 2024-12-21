package org.baouz.ems_api.email;

import lombok.Getter;

@Getter
public enum EmailTemplateName {

    ASSIGNMENT_NOTIFICATION("assignment_notification"),
    ;


    private final String name;
    EmailTemplateName(String name) {
        this.name = name;
    }
}