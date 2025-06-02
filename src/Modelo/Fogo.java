package Modelo;

import Auxiliar.Desenho;
import java.io.Serializable;

/**
 * Obstáculo de fogo: se o herói encostar, morre.
 * Compatível com Serializable:
 * - Herda de Personagem, que já marca iImage como transient.
 * - Possui recarregarSprite() para restaurar iImage após desserializar.
 */
public class Fogo extends Personagem implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Guarda o nome do arquivo PNG (por ex. "fogo.png") */
    private String nomeImagePNG;

    /**
     * Construtor que carrega, inicialmente, o sprite do fogo.
     * @param sNomeImagePNG nome do arquivo de imagem dentro de Consts.PATH
     */
    public Fogo(String sNomeImagePNG) {
        super(sNomeImagePNG);    // chama carregarImageIcon(sNomeImagePNG)
        this.nomeImagePNG = sNomeImagePNG;
        this.bTransponivel = false; // não pode passar por cima sem morrer
        this.bMortal      = true;   // é mortal ao herói
    }

    /**
     * Desenha o fogo na tela, usando o iImage carregado.
     */
    @Override
    public void autoDesenho() {
        if (iImage != null) {
            Desenho.desenhar(this.iImage, this.pPosicao.getColuna(), this.pPosicao.getLinha());
        }
    }

    /**
     * Chamado após desserializar para recarregar o campo transient iImage.
     * Sem isso, iImage ficaria null e nada apareceria como fogo.
     */
    @Override
    public void recarregarSprite() {
        carregarImageIcon(nomeImagePNG);
    }
}
