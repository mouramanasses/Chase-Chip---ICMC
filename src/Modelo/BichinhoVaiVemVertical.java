package Modelo;

import java.io.Serializable;

/**
 * Personagem que se move verticalmente “vai e vem”.
 * Agora compatível com Serializable:
 * - Herda de Personagem (que já marca iImage como transient).
 * - Possui recarregarSprite() para restaurar o sprite após desserializar.
 */
public class BichinhoVaiVemVertical extends Personagem implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Direção atual: true == vai para cima; false == vai para baixo */
    private boolean bUp;

    /** Armazena o nome do arquivo de imagem (para recarregar no método recarregarSprite) */
    private String nomeImagePNG;

    /**
     * Construtor que carrega, pela primeira vez, o sprite a partir do disco.
     * @param sNomeImagePNG nome do arquivo (por ex. "bichinhoV.png") dentro de Consts.PATH
     */
    public BichinhoVaiVemVertical(String sNomeImagePNG) {
        super(sNomeImagePNG);   // isso chama carregarImageIcon(sNomeImagePNG)
        this.nomeImagePNG = sNomeImagePNG;
        this.bUp = true;
        // pPosicao herdado de Personagem (inicializa em (1,1) ou conforme Personagem)
    }

    /**
     * Método chamado a cada repaint, que desenha e imediatamente move para cima ou para baixo,
     * invertendo a direção a cada chamada.
     */
    @Override
    public void autoDesenho() {
        // 1) Move uma célula para cima ou para baixo antes de desenhar
        if (bUp) {
            this.setPosicao(pPosicao.getLinha() - 1, pPosicao.getColuna());
        } else {
            this.setPosicao(pPosicao.getLinha() + 1, pPosicao.getColuna());
        }

        // 2) Desenha o sprite na tela
        super.autoDesenho();

        // 3) Inverte a direção para o próximo quadro
        bUp = !bUp;
    }

    /**
     * Chamado logo após desserializar o objeto para recarregar o campo transient iImage.
     * Sem isso, iImage ficaria null e nada apareceria na tela.
     */
    @Override
    public void recarregarSprite() {
        carregarImageIcon(nomeImagePNG);
    }
}
