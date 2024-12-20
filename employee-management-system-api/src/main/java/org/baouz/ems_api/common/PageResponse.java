package org.baouz.ems_api.common;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PageResponse<T> {
    private Integer page;
    private Integer size;
    private Integer totalPages;
    private Long totalElements;
    private List<T> content;
    private Boolean isFirst;
    private Boolean isLast;
}
