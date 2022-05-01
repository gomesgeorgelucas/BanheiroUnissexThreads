package org.banheirounissex.model;

import lombok.*;
import org.banheirounissex.model.enums.SexoEnum;

@AllArgsConstructor
@Getter
public class FuncionarioModel {
    private SexoEnum sexoFuncionario;
    private long tempoNoBanheiro;

    public synchronized void usarBanheiro() throws InterruptedException {
        Thread.sleep(tempoNoBanheiro);
    }

    @Override
    public String toString() {
        return (sexoFuncionario.getSexo());
    }
}
