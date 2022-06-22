package com.quanta.archetype.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 数据接收主类
 * <T>填入要自动注入的DTO或者
 * */
@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class JsonRequest<T> {
    @NotNull
    @Valid
    T data;
}
