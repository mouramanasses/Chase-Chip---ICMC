package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import auxiliar.Posicao;
import java.awt.Graphics;
import java.io.IOException;
import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 * Personagem que se move horizontalmente “vai e vem”.
 * 
 * compatível com Serializable: 
 * - Herdou de Personagem, que já marca iImage como transient.
 * - Possui recarregarSprite() para restaurar o sprite após desserializar.
 */
public class BichinhoVaiVemHorizontal extends Personagem implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Direção atual: true == vai para a direita; false == vai para a esquerda */
    private boolean bRight;

    /** Contador de quadros para controlar a velocidade */
    private int iContador;

    /** Armazena o nome do arquivo de imagem (para recarregar no método recarregarSprite) */
    private String nomeImagePNG;

    /**
     * Construtor que carrega, pela primeira vez, o sprite a partir do disco.
     * @param sNomeImagePNG nome do arquivo (por ex. "bichinhoH.png") dentro de Consts.PATH
     */
    public BichinhoVaiVemHorizontal(String sNomeImagePNG) {
        super(sNomeImagePNG);       // método do Personagem que carrega e redimensiona o ImageIcon
        this.nomeImagePNG = sNomeImagePNG;
        this.bRight = true;
        this.iContador = 0;
        // pPosicao herdado de Personagem
    }

    /**
     * Método chamado a cada repaint, que desenha e, a cada 5 quadros, inverte o movimento.
     */
    @Override
    public void autoDesenho() {
        // 1) Se já atingiu 5 quadros, move e inverte a direção
        if (iContador == 5) {
            iContador = 0;
            if (bRight) {
                // tenta mover uma célula para a direita
                this.setPosicao(pPosicao.getLinha(), pPosicao.getColuna() + 1);
            } else {
                // tenta mover uma célula para a esquerda
                this.setPosicao(pPosicao.getLinha(), pPosicao.getColuna() - 1);
            }
            bRight = !bRight; // inverte direção para o próximo movimento
        }

        // 2) Desenha sempre o sprite (super.autoDesenho() usa o ImageIcon em iImage)
        super.autoDesenho();

        // 3) Incrementa o contador de quadros
        iContador++;
    }

    /**
     * Chamado logo após desserializar o objeto para recarregar o campo transient iImage.
     * Sem isso, o iImage ficaria null e nada apareceria na tela.
     */
    @Override
    public void recarregarSprite() {
        // Usa o mesmo nome de arquivo passado ao construtor
        carregarImageIcon(nomeImagePNG);
    }
}
