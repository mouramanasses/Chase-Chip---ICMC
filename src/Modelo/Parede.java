package Modelo;

import Auxiliar.Desenho;
import java.io.Serializable;

/**
 * Parede estática: bloqueia movimento, não é mortal.
 * Compatível com Serializable:
 * - Herdado de Personagem, que mantém iImage como transient.
 * - Implementa recarregarSprite() para restaurar o sprite após desserializar.
 */
public class Parede extends Personagem implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Nome do arquivo PNG (por ex. "parede.png") */
    private String nomeImagePNG;

    /**
     * Construtor que carrega o sprite pela primeira vez.
     * @param sNomeImagePNG nome do arquivo dentro de Consts.PATH
     */
    public Parede(String sNomeImagePNG) {
        super(sNomeImagePNG);   // carrega iImage via Personagem
        this.nomeImagePNG = sNomeImagePNG;
        this.bTransponivel = false; // bloqueia movimento
        this.bMortal       = false; // não mata
    }

    /**
     * Desenha a parede na tela usando o iImage carregado.
     */
    @Override
    public void autoDesenho() {
        if (iImage != null) {
            Desenho.desenhar(this.iImage, this.pPosicao.getColuna(), this.pPosicao.getLinha());
        }
    }

    /**
     * Chamado após desserializar para restaurar iImage (campo transient).
     */
    @Override
    public void recarregarSprite() {
        carregarImageIcon(nomeImagePNG);
    }
}
