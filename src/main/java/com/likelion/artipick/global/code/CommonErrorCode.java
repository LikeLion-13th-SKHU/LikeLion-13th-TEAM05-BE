package com.likelion.artipick.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommonErrorCode implements BaseErrorCode {

    INVALID_INPUT(false, "C001", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    POST_NOT_FOUND(false, "P001", "게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR(false, "C999", "서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final boolean success;
    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    @Override public boolean isSuccess() { return success; }
    @Override public String getCode() { return code; }
    @Override public String getMessage() { return message; }
    @Override public HttpStatus getHttpStatus() { return httpStatus; }
}
