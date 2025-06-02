package Modelo;

import Auxiliar.Desenho;
import java.io.Serializable;

/**
 * LaserBarrier: obstáculo de laser que mata o herói ao contato.
 * Compatível com Serializable:
 * - Herdado de Personagem, que já marca iImage como transient.
 * - Possui recarregarSprite() para restaurar o sprite após desserializar.
 */
public class LaserBarrier extends Personagem implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Guarda o nome do arquivo PNG (por ex. "laser.png") */
    private String nomeImagePNG;

    /**
     * Construtor que carrega, pela primeira vez, o sprite a partir do disco.
     * @param sNomeImagePNG nome do arquivo dentro de Consts.PATH
     */
    public LaserBarrier(String sNomeImagePNG) {
        super(sNomeImagePNG);    // carrega o ImageIcon via Personagem
        this.nomeImagePNG = sNomeImagePNG;
        this.bTransponivel = false; // não passa por cima
        this.bMortal       = true;  // mata ao encostar
    }

    /**
     * Desenha o laser na tela, usando o sprite carregado em iImage.
     */
    @Override
    public void autoDesenho() {
        if (iImage != null) {
            Desenho.desenhar(this.iImage, this.pPosicao.getColuna(), this.pPosicao.getLinha());
        }
    }

    /**
     * Após desserializar, recarrega o campo transient iImage do disco.
     */
    @Override
    public void recarregarSprite() {
        carregarImageIcon(nomeImagePNG);
    }
}
