package com.example.blog.exception;

import org.springframework.validation.Errors;

public class ValidationExceptionProvider {

    public static void throwValidError(Errors errors) {
        String errorCode = errors.getFieldError().getCode();
        String errorTarget = errors.getFieldError().getField();
        throw new CustomException(ValidationExceptionProvider.getExceptionStatus(errorCode,errorTarget));
    }

    public static CustomExceptionStatus getExceptionStatus(String code, String target) {
        if (code.equals("NotBlank")){
            if (target.equals("subject")) return CustomExceptionStatus.POST_BOARD_EMPTY_SUBJECT;
            else if (target.equals("shortDescription")) return CustomExceptionStatus.POST_BOARD_EMPTY_SHORTDESCRIPTION;
            else if (target.equals("text")) return CustomExceptionStatus.POST_BOARD_EMPTY_TEXT;
            else if (target.equals("category")) return CustomExceptionStatus.POST_BOARD_EMPTY_CATEGORY;
            else if (target.equals("id")) return CustomExceptionStatus.BOARD_ID_NOT_FOUND;
            else if (target.equals("boardId")) return CustomExceptionStatus.COMMENTS_ID_NOT_FOUND;
            else if (target.equals("comment")) return CustomExceptionStatus.COMMENTS_EMPTY_COMMENT;
        }
        return CustomExceptionStatus.RESPONSE_ERROR;
    }
}
