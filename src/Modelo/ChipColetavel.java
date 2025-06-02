package Modelo;

import Auxiliar.Desenho;
import java.io.Serializable;

/**
 * Item colecionável (chip).  
 * - Herdado de Personagem, que marca iImage como transient.
 * - Implementa recarregarSprite() para restaurar a imagem após desserializar.
 */
public class ChipColetavel extends Personagem implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Armazena o nome do arquivo de imagem (por ex. "chip.png") */
    private String nomeImagePNG;

    /**
     * Construtor que carrega o sprite pela primeira vez.
     * @param sNomeImagePNG nome do arquivo (ex.: "chip.png") dentro de Consts.PATH
     */
    public ChipColetavel(String sNomeImagePNG) {
        super(sNomeImagePNG);     // já chama carregarImageIcon(sNomeImagePNG)
        this.nomeImagePNG = sNomeImagePNG;
        this.bTransponivel = true;
        this.bMortal      = false;
    }

    /**
     * Desenha o chip na tela usando o iImage carregado.
     * (Não precisa de lógica de “vai e vem”; só desenha.)
     */
    @Override
    public void autoDesenho() {
        if (iImage != null) {
            Desenho.desenhar(this.iImage, this.pPosicao.getColuna(), this.pPosicao.getLinha());
        }
    }

    /**
     * Chamado logo após desserializar o objeto, para recarregar o campo transient iImage.
     * Sem isso, o chip ficaria sem imagem após carregar o savegame.
     */
    @Override
    public void recarregarSprite() {
        carregarImageIcon(nomeImagePNG);
    }
}
