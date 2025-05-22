package Modelo;

import Auxiliar.Consts;
import auxiliar.Posicao;
import java.io.Serializable;

public class Chaser extends Personagem implements Serializable {

    private boolean iDirectionV;
    private boolean iDirectionH;

    public Chaser(String sNomeImagePNG) {
        super(sNomeImagePNG);
        iDirectionV = true;
        iDirectionH = true;
        this.bTransponivel = true;
    }

    public void computeDirection(Posicao heroPos) {
        if (heroPos.getColuna() < this.getPosicao().getColuna()) {
            iDirectionH = true; // esquerda
        } else if (heroPos.getColuna() > this.getPosicao().getColuna()) {
            iDirectionH = false; // direita
        }

        if (heroPos.getLinha() < this.getPosicao().getLinha()) {
            iDirectionV = true; // cima
        } else if (heroPos.getLinha() > this.getPosicao().getLinha()) {
            iDirectionV = false; // baixo
        }
    }

    public void autoDesenho(Posicao heroPos) {
        super.autoDesenho();

        // Decide se vai mover vertical ou horizontal primeiro com base na distância
        if (Math.abs(this.getPosicao().getLinha() - heroPos.getLinha()) >
            Math.abs(this.getPosicao().getColuna() - heroPos.getColuna())) {
            
            // mover verticalmente primeiro
            if (iDirectionV) {
                this.moveUp();
            } else {
                this.moveDown();
            }

        } else {
            // mover horizontalmente primeiro
            if (iDirectionH) {
                this.moveLeft();
            } else {
                this.moveRight();
            }
        }
    }
}
