package com.likelion.artipick.global.exception.handler;

import com.likelion.artipick.global.code.BaseErrorCode;
import com.likelion.artipick.global.code.CommonErrorCode;
import com.likelion.artipick.global.code.dto.ApiResponse;
import com.likelion.artipick.global.exception.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    /** 우리 쪽에서 명시적으로 던진 예외 */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<Void>> handleBase(BaseException e) {
        BaseErrorCode code = e.getErrorCode();
        return ResponseEntity
                .status(code.getHttpStatus())
                .body(code.toResponse(null));
    }

    /** @Valid 실패 등 바인딩/검증 오류 */
    @ExceptionHandler({ MethodArgumentNotValidException.class, BindException.class, IllegalArgumentException.class })
    public ResponseEntity<ApiResponse<Void>> handleValidation(Exception e) {
        BaseErrorCode code = CommonErrorCode.INVALID_INPUT;
        return ResponseEntity
                .status(code.getHttpStatus())
                .body(code.toResponse(null));
    }

    /** 그 외 모든 예외(안전망) */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleEtc(Exception e) {
        BaseErrorCode code = CommonErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity
                .status(code.getHttpStatus())
                .body(code.toResponse(null));
    }
}
