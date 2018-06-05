package com.jsq.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StudentImportDTO {

    private String userNumber;

    private String nickName;

    private String gender;

    private String phone;

    private String deptName;

    private String className;

    private Integer rowNum;

    private String errorDetail;
}
