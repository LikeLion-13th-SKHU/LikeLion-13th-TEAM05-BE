package com.likelion.artipick.global.exception;

import com.likelion.artipick.global.code.BaseErrorCode;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final BaseErrorCode errorCode;

    public BaseException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BaseException(BaseErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }
}
