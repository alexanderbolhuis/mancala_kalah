package com.kalah.dto;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PlayerDTO {

    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String email;
}
