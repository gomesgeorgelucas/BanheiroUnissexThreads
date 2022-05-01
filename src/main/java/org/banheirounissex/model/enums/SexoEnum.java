package org.banheirounissex.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SexoEnum {
    HOMEM ("Homem"),
    MULHER ("Mulher");

    private final String sexo;
}
