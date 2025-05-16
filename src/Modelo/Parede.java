package Modelo;

import auxiliar.Posicao;
import Auxiliar.Desenho;

public class Parede extends Personagem {
    public Parede(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.bTransponivel = false; // o jogador NÃO pode passar por cima
        this.bMortal = false;       // não causa morte
    }

    @Override
    public void autoDesenho() {
        Desenho.desenhar(this.iImage, this.pPosicao.getColuna(), this.pPosicao.getLinha());
    }
}
