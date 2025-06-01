package Modelo;

import Auxiliar.Desenho;

public class LaserBarrier extends Personagem {
    public LaserBarrier(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.bTransponivel = false; // não pode passar por cima
        this.bMortal     = true;   // toca → morre
    }

    @Override
    public void autoDesenho() {
        // desenha o sprite do laser na posição definida
        Desenho.desenhar(this.iImage, this.pPosicao.getColuna(), this.pPosicao.getLinha());
    }
}

