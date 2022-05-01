package org.banheirounissex.controllers;

import org.banheirounissex.model.FuncionarioModel;
import org.banheirounissex.model.enums.SexoEnum;

import java.util.concurrent.Semaphore;

public class BanheiroUnissexController {
    private final Semaphore banheiroVazio = new Semaphore(1);
    private final Semaphore mutexHomem = new Semaphore(1);
    private final Semaphore homem = new Semaphore(3);
    private final Semaphore mutexMulher = new Semaphore(1);
    private final Semaphore mulher = new Semaphore(3);
    private final Semaphore ordemFila = new Semaphore(1, true);

    public int numeroHomens = 0;
    public int numeroMulheres = 0;

    public void entrarNoBanheiro(FuncionarioModel funcionario) {
        try {
            ordemFila.acquire();

            if (funcionario.getSexoFuncionario() == SexoEnum.HOMEM) {
                mutexHomem.acquire();
                if (numeroHomens == 0) {
                    banheiroVazio.acquire();
                }
                numeroHomens++;
                mutexHomem.release();
                homem.acquire();
            } else if (funcionario.getSexoFuncionario() == SexoEnum.MULHER) {
                mutexMulher.acquire();
                if (numeroMulheres == 0) {
                    banheiroVazio.acquire();
                }
                numeroMulheres++;
                mutexMulher.release();
                mulher.acquire();
            }

            ordemFila.release();
        } catch(InterruptedException ignored) {
            System.out.println("Thread interrompida!");
        }
    }

    public void sairDoBanheiro(FuncionarioModel funcionario) {
        try {
            if (funcionario.getSexoFuncionario() == SexoEnum.HOMEM) {
                homem.release();

                mutexHomem.acquire();
                numeroHomens--;

                if (numeroHomens == 0) {
                    banheiroVazio.release();
                }
                mutexHomem.release();
            } else if (funcionario.getSexoFuncionario() == SexoEnum.MULHER) {
                mulher.release();

                mutexMulher.acquire();
                numeroMulheres--;

                if (numeroMulheres == 0) {
                    banheiroVazio.release();
                }
                mutexMulher.release();
            }
        } catch (InterruptedException e) {
            System.out.println("Thread interrompida!");
        }
    }
}
