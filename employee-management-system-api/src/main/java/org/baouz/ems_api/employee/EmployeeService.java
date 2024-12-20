package org.baouz.ems_api.employee;

import lombok.RequiredArgsConstructor;
import org.baouz.ems_api.common.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository repository;
    private final EmployeeMapper mapper;

    public String save(EmployeeRequest request) {
        var employee = mapper.toEmployee(request);
        return repository.save(employee).getId();
    }

    public PageResponse<EmployeeResponse> findAll(Integer page, Integer size) {
        Page<Employee> employeePage = repository.findAll(PageRequest.of(page, size));
        List<EmployeeResponse> list = employeePage
                .getContent()
                .stream()
                .map(mapper::toEmployeeResponse)
                .toList();
        return PageResponse.<EmployeeResponse>builder()
                .page(page)
                .size(size)
                .totalPages(employeePage.getTotalPages())
                .totalElements(employeePage.getTotalElements())
                .content(list)
                .isFirst(employeePage.isFirst())
                .isLast(employeePage.isLast())
                .build();
    }
}
