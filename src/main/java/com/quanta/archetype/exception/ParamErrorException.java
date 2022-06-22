package com.quanta.archetype.exception;

/**
 * @description
 * @author Leslie Leung
 * @date 2021/12/12
 */
public class ParamErrorException extends RuntimeException{

    public ParamErrorException() {
        super("参数错误");
    }

    public ParamErrorException(String message) {
        super(message);
    }
}
