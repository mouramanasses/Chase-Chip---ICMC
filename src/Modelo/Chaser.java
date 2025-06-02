package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import auxiliar.Posicao;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Chaser simples: persegue o herói, mas só se mexe a cada N chamadas de autoDesenho().
 * lógica de recarregar sprite para serialização.
 */
public class Chaser extends Personagem {
    private static final long serialVersionUID = 1L;

    // Estas flags dizem em qual direção o Chaser está apontando
    private boolean iDirectionV; // true == sobe, false == desce
    private boolean iDirectionH; // true == esquerda, false == direita

    // Contador que controla a “velocidade” do Chaser.
    private int frameCounter = 0;

    // Quanto maior este valor, mais lento o Chaser anda. Ex: 6 → anda a cada 6 quadros
    private static final int MOVE_DELAY = 6;

    /**
     * Construtor que delega ao Personagem o carregamento inicial do PNG.
     * 
     * @param sNomeImagePNG nome exato do arquivo de imagem (por ex. "chaser.png"),
     *                      que deve estar dentro da pasta apontada por Consts.PATH.
     */
    public Chaser(String sNomeImagePNG) {
        super(sNomeImagePNG); // isso já chama carregarImageIcon(sNomeImagePNG)
        this.iDirectionV   = true;
        this.iDirectionH   = true;
        this.bTransponivel = true;
    }

    /**
     * Após desserializar o objeto, o campo transient iImage fica null.
     * Chamamos este método para recarregar (do disco) o mesmo PNG que passamos
     * no construtor. Assim, o Chaser volta a desenhar corretamente.
     */
    @Override
    public void recarregarSprite() {
        // Usa a mesma função utilitária de Personagem para carregar e redimensionar
        carregarImageIcon("chaser.png");
    }

    /**
     * Aqui desenhamos o Chaser e, eventualmente, movemos ele.
     * A cada chamada, incrementamos frameCounter. Só quando (frameCounter % MOVE_DELAY == 0)
     * é que o Chaser de fato altera posição (moveUp, moveDown, moveLeft ou moveRight).
     */
    @Override
    public void autoDesenho() {
        // 1) Desenha o sprite na tela (posição atual)
        super.autoDesenho();

        // 2) Incrementa o contador de quadros
        frameCounter++;

        // 3) Se não chegou no momento de mover, sai sem mexer.
        if (frameCounter % MOVE_DELAY != 0) {
            return;
        }
        

        // 4) Obtém a posição atual do herói (armazenada em Auxiliar.Desenho)
        Posicao heroPos = Desenho.getHeroPosicao();
        if (heroPos == null) {
            // se não tivermos a posição do herói, não movemos
            return;
        }

        // 5) Atualiza iDirectionV e iDirectionH conforme o herói:
        //    se hero estiver à esquerda, iDirectionH=true (ir para esquerda);
        //    se hero estiver acima, iDirectionV=true (ir para cima).
        if (heroPos.getColuna() < this.getPosicao().getColuna()) {
            iDirectionH = true;  // esquerda
        } else if (heroPos.getColuna() > this.getPosicao().getColuna()) {
            iDirectionH = false; // direita
        }
        if (heroPos.getLinha() < this.getPosicao().getLinha()) {
            iDirectionV = true;  // cima
        } else if (heroPos.getLinha() > this.getPosicao().getLinha()) {
            iDirectionV = false; // baixo
        }

        // 6) Decide se anda vertical ou horizontal primeiro, de acordo com a distância
        int diffV = Math.abs(this.getPosicao().getLinha() - heroPos.getLinha());
        int diffH = Math.abs(this.getPosicao().getColuna() - heroPos.getColuna());

        if (diffV > diffH) {
            // Se a diferença vertical for maior, mexe na vertical primeiro
            if (iDirectionV) {
                this.moveUp();
            } else {
                this.moveDown();
            }
        } else {
            // Senão, mexe na horizontal primeiro
            if (iDirectionH) {
                this.moveLeft();
            } else {
                this.moveRight();
            }
        }
    }
    
        /**
     * Atualiza iDirectionV e iDirectionH para apontar em direção ao herói.
     * Chamado de ControleDeJogo.processaTudo(...).
     */
    public void computeDirection(Posicao heroPos) {
        if (heroPos.getColuna() < this.getPosicao().getColuna()) {
            iDirectionH = true;  // apontar para a esquerda
        } else if (heroPos.getColuna() > this.getPosicao().getColuna()) {
            iDirectionH = false; // apontar para a direita
        }

        if (heroPos.getLinha() < this.getPosicao().getLinha()) {
            iDirectionV = true;  // apontar para cima
        } else if (heroPos.getLinha() > this.getPosicao().getLinha()) {
            iDirectionV = false; // apontar para baixo
        }
    }

}
