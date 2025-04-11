package com.jwt.implementation.dto;

import lombok.Data;

@Data
public class SubjectAssignmentDto {
    private Long subjectId;
    private Long classRoomId;
    private Long teacherId;
}
