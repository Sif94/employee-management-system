package org.baouz.ems_api.department;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DepartmentResponse {
    private String id;
    private String name;
    private String description;
    private String location;
}
