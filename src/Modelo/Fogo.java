package Modelo;

import Auxiliar.Desenho;

public class Fogo extends Personagem {
    public Fogo(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.bTransponivel = false; // pode passar por cima, mas...
        this.bMortal = true;       // morre ao encostar
    }

    @Override
    public void autoDesenho() {
        Desenho.desenhar(this.iImage, this.pPosicao.getColuna(), this.pPosicao.getLinha());
    }
}
