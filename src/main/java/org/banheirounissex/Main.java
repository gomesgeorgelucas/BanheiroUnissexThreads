package org.banheirounissex;

import org.banheirounissex.controllers.BanheiroUnissexController;
import org.banheirounissex.controllers.FuncionarioThread;
import org.banheirounissex.model.FuncionarioModel;
import org.banheirounissex.model.enums.SexoEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final List<FuncionarioModel> FUNCIONARIOS = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException{


        System.out.println("Program starting...\n");

        for (int i = 0; i < 10; i++) {
            FUNCIONARIOS.add(geraFuncionarioAleatorio());
        }

        horaDeIrAoBanheiro();

        System.out.println("\nProgram ended!\n");
    }

    private static void horaDeIrAoBanheiro() throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        BanheiroUnissexController banheiro = new BanheiroUnissexController();

        for (FuncionarioModel funcionario : FUNCIONARIOS) {
            executor.submit(new FuncionarioThread(funcionario, banheiro));
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (FuncionarioModel funcionario : FUNCIONARIOS) {
            executor.submit(new FuncionarioThread(funcionario, banheiro));
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);
    }

    public static FuncionarioModel geraFuncionarioAleatorio() {
        SexoEnum sexoFuncionario = new Random().nextBoolean() ? SexoEnum.HOMEM : SexoEnum.MULHER;
        long tempoNoBanheiro = (long) (new Random().nextDouble() * 10000 + 2000);
        return new FuncionarioModel(sexoFuncionario, tempoNoBanheiro);

    }
}