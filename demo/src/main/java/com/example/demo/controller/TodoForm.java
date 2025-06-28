package com.example.demo.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TodoForm {

    @NotEmpty(message = "내용은 필수입니다.")
    private String item;

    private String date;

    private boolean completed;

    private String time;
}
