package org.baouz.ems_api.email;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EmailDTO {
    private String from;
    private String to;
    private String subject;
    private EmailTemplateName emailTemplate;
    private Map<String, Object> properties;
}
