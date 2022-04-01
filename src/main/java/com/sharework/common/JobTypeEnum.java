package com.sharework.common;

public enum JobTypeEnum {
    OPEN,  // 공고 올림
    CLOSED, // 일 취소
    STARTED,  // 일 시작됨
    FAILED,  // 일 시작한 application이 없음
    COMPLETED;  // 일 완료
}
