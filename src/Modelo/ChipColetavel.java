package Modelo;

import Auxiliar.Desenho;

public class ChipColetavel extends Personagem {

    public ChipColetavel(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.bTransponivel = true;
        this.bMortal = false;
    }

    @Override
    public void autoDesenho() {
        Desenho.desenhar(this.iImage, this.pPosicao.getColuna(), this.pPosicao.getLinha());
    }
}
