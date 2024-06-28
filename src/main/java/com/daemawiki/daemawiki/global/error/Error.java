package com.daemawiki.daemawiki.global.error;

import lombok.Getter;

@Getter
public enum Error {
    //400: BAD REQUEST
    INVALID_DATA(400, "잘못된 데이터입니다.", ""),
    INVALID_TOKEN(401, "유효하지 않은 토큰입니다.", ""),
    VERSION_MISMATCH(400, "문서의 버전이 일치하지 않습니다.", ""),

    //401: AUTHENTICATION
    PASSWORD_MISMATCH(401, "비밀번호가 일치하지 않습니다.", ""),

    //403: Forbidden
    DOCUMENT_DELETE_FAILED(403, "학생 문서는 삭제하지 못합니다.", ""),
    UNVERIFIED_EMAIL(403, "이메일 인증을 하지 않은 사용자입니다.", ""),
    NO_EDIT_PERMISSION_USER(403, "수정 권한이 없는 유저입니다.", ""),
    NO_PERMISSION_USER(403, "권한이 없는 유저입니다.", ""),

    //404: NOT FOUND
    USER_NOT_FOUND(404, "해당 이메일로 가입된 유저를 찾지 못했습니다.", ""),
    DOCUMENT_NOT_FOUND(404, "해당 문서를 찾지 못했습니다.", ""),
    DOCUMENT_GROUP_NOT_FOUND(404, "해당 분류 그룹을 찾지 못했습니다.", ""),
    STUDENT_INFO_NOT_FOUND(404, "해당 학생 정보를 찾지 못했습니다.", ""),
    FILE_NOT_FOUND(404, "해당 id로 파일을 찾지 못했습니다.", ""),
    CONTENT_NOT_FOUND(404, "해당 인덱스의 목차를 찾지 못했습니다.", ""),

    //409: CONFLICT
    ALREADY_EXISTS_EMAIL(409, "이 이메일을 사용 중인 유저가 이미 존재합니다.", ""),

    //500: SERVER ERROR
    MAIL_CONFIRM_FAILED(500, "메일을 전송하는데 문제가 발생했습니다.", ""),
    TOKEN_REISSUE_FAILED(500, "토큰을 재발급하는데 문제가 발생했습니다.", ""),
    FILE_UPLOAD_FAILED(500, "파일을 업로드하는데 문제가 발생했습니다.", ""),
    REDIS_CONNECT_FAILED(500, "레디스 서버에 연결하는데 문제가 발생했습니다.", ""),

    TEST(400, "TEST", "");

    private final int status;
    private final String message;
    private final String viewMessage;
    private final String detail = this.name().toLowerCase();

    Error(int status, String message, String viewMessage) {
        this.status = status;
        this.message = message;
        this.viewMessage = viewMessage;
    }
}
