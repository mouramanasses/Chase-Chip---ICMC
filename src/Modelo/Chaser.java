package Modelo;

import Auxiliar.Consts;
import auxiliar.Posicao;
import java.io.Serializable;

/**
 * Chaser simples: persegue o herói, mas só se mexe a cada N chamadas de autoDesenho().
 */
public class Chaser extends Personagem implements Serializable {

    private boolean iDirectionV;
    private boolean iDirectionH;

    // Contador que controla a “velocidade” do Chaser.
    private int frameCounter = 0;
    
     //Quanto maior este valor, mais lento o Chaser anda. 
     //Por exemplo, se for 6, ele só se move a cada 6 quadros de pintura. 
    private static final int MOVE_DELAY = 6;

    public Chaser(String sNomeImagePNG) {
        super(sNomeImagePNG);
        iDirectionV = true;
        iDirectionH = true;
        this.bTransponivel = true;
    }

    /**
     * Calcula a direção (vertical/horizontal) para que ele persiga o herói,
     * mas NÃO chega a mover – apenas define iDirectionV e iDirectionH.
     */
    public void computeDirection(Posicao heroPos) {
        if (heroPos.getColuna() < this.getPosicao().getColuna()) {
            iDirectionH = true; // esquerdo
        } else if (heroPos.getColuna() > this.getPosicao().getColuna()) {
            iDirectionH = false; // direito
        }

        if (heroPos.getLinha() < this.getPosicao().getLinha()) {
            iDirectionV = true; // cima
        } else if (heroPos.getLinha() > this.getPosicao().getLinha()) {
            iDirectionV = false; // baixo
        }
    }

    /**
     * Aqui é onde desenhamos o Chaser e, eventualmente, movemos ele.
     * A cada chamada, incrementamos um contador “frameCounter”.
     * Somente quando (frameCounter % MOVE_DELAY == 0) é que o Chaser
     * realmente executa o moveUp() / moveDown() / moveLeft() / moveRight().
     */
    @Override
    public void autoDesenho() {
        // 1) sempre desenhe o sprite na tela (posição atual)
        super.autoDesenho();

        // 2) incrementa o contador de quadros
        frameCounter++;

        // 3) só movimenta de fato a cada MOVE_DELAY quadros
        if (frameCounter % MOVE_DELAY != 0) {
            return; 
        }

        // 4) decide se anda vertical ou horizontal primeiro, conforme o cálculo já feito
        Posicao heroPos = Auxiliar.Desenho.getHeroPosicao();
        if (heroPos == null) {
            // se por algum motivo não temos a posição do herói, não faz nada
            return;
        }

        // se a distância vertical para o herói for maior que a horizontal,
        // move verticalmente primeiro; senão, move horizontal.
        int diffV = Math.abs(this.getPosicao().getLinha() - heroPos.getLinha());
        int diffH = Math.abs(this.getPosicao().getColuna() - heroPos.getColuna());

        if (diffV > diffH) {
            // mover vertical
            if (iDirectionV) {
                this.moveUp();
            } else {
                this.moveDown();
            }
        } else {
            // mover horizontal
            if (iDirectionH) {
                this.moveLeft();
            } else {
                this.moveRight();
            }
        }
    }
}
