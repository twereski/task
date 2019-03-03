package com.twereski.task.app.exception;

import lombok.Getter;

public class RepositoryException extends RuntimeException {

    @Getter
    private Code code;

    public RepositoryException(Code code, String message, Throwable e) {
        super(message, e);
        this.code = code;
    }


    public enum Code {
        SERVER_FAILED,
        CLIENT_FAILED
    }
}
