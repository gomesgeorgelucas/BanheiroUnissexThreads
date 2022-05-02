package org.banheirounissex.controllers;

import lombok.AllArgsConstructor;
import org.banheirounissex.model.FuncionarioModel;

import java.util.concurrent.Callable;

@AllArgsConstructor
public class FuncionarioThread implements Callable {
    private FuncionarioModel funcionario;
    private BanheiroUnissexController banheiro;
    @Override
    public Object call(){
        String threadName = String.valueOf(Thread.currentThread().getId());
        try {
            System.out.println("("+threadName+") Funcionario " + funcionario + " entrou na fila.");

            banheiro.entrarNoBanheiro(funcionario);

            System.out.println(threadName + " " + funcionario + " usando o banheiro: " + funcionario.getTempoNoBanheiro()/1000 + "s");
            funcionario.usarBanheiro();

            System.out.println(threadName + " " + funcionario + " saiu do banheiro: " + funcionario.getTempoNoBanheiro()/1000 + "s");
            banheiro.sairDoBanheiro(funcionario);
        } catch (InterruptedException e) {
            System.out.println("Thread interrompida!");
        }

        return this;
    }
}
