package com.example.blog.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomExceptionStatus {

    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),

    // [POST] /board
    POST_BOARD_EMPTY_SUBJECT(false, 2001, "제목을 입력해주세요."),
    POST_BOARD_EMPTY_SHORTDESCRIPTION(false, 2002, "블로그 문구를 입력해주세요."),
    POST_BOARD_EMPTY_TEXT(false, 2003, "내용을 입력해주세요"),
    POST_BOARD_EMPTY_CATEGORY(false, 2004, "카테고리를 입력해주세요."),

    // board_id
    BOARD_ID_NOT_FOUND(false,2005,"블로그를 찾을 수 없습니다."),

    // [POST] /comments
    COMMENTS_ID_NOT_FOUND(false,2006,"해당 블로그를 찾을 수 없습니다."),
    COMMENTS_EMPTY_COMMENT(false,2007,"댓글을 입력해주세요."),

    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다.");


    private final boolean isSuccess;
    private final int code;
    private final String message;
}
