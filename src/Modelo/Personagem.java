package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import auxiliar.Posicao;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 * Classe base para todos os personagens do jogo (herói, inimigos, fogos, chips, paredes, etc.).
 * Contém sprite (iImage), posição (pPosicao) e flags de transponibilidade / mortalidade.
 */
public abstract class Personagem implements Serializable {

    protected ImageIcon iImage;
    protected Posicao pPosicao;
    protected boolean bTransponivel; /* Pode passar por cima? */
    protected boolean bMortal;       /* Se encostar, morre */

    /**
     * Construtor que carrega o sprite PNG dentro de Consts.PATH + sNomeImagePNG
     *
     */
    protected Personagem(String sNomeImagePNG) {
        this.pPosicao = new Posicao(1, 1);
        this.bTransponivel = true;
        this.bMortal = false;

        try {
            // Carrega a imagem a partir de Consts.PATH + nomeArquivo
            String caminho = new java.io.File(".").getCanonicalPath() + Consts.PATH + sNomeImagePNG;
            iImage = new ImageIcon(caminho);
            Image img = iImage.getImage();
            BufferedImage bi = new BufferedImage(Consts.CELL_SIDE, Consts.CELL_SIDE, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, Consts.CELL_SIDE, Consts.CELL_SIDE, null);
            g.dispose();
            iImage = new ImageIcon(bi);
        } catch (IOException ex) {
            System.out.println("Erro ao carregar imagem: " + ex.getMessage());
        }
    }

    /** Retorna a posição atual (linha, coluna) do Personagem. */
    public Posicao getPosicao() {
        return pPosicao;
    }

    public boolean isbTransponivel() {
        return bTransponivel;
    }

    public void setbTransponivel(boolean bTransponivel) {
        this.bTransponivel = bTransponivel;
    }

    public boolean isbMortal() {
        return bMortal;
    }
    
    public void setMortal(boolean m) {
    this.bMortal = m;
}

    /**
     * Desenha o sprite na tela, usando o método Desenho.desenhar().
     * Deve ser chamado de dentro do paint() de Tela.
     */
    public void autoDesenho() {
        Desenho.desenhar(this.iImage, this.pPosicao.getColuna(), this.pPosicao.getLinha());
    }

    /**
     * Tenta definir a nova posição (linha, coluna).  
     * Retorna true se a operação foi bem‐sucedida.
     */
    public boolean setPosicao(int linha, int coluna) {
        return pPosicao.setPosicao(linha, coluna);
    }

    /** Move para cima (linha‐1). Retorna true se moveu. */
    public boolean moveUp() {
        return this.pPosicao.moveUp();
    }

    /** Move para baixo (linha+1). Retorna true se moveu. */
    public boolean moveDown() {
        return this.pPosicao.moveDown();
    }

    /** Move para a esquerda (coluna‐1). Retorna true se moveu. */
    public boolean moveLeft() {
        return this.pPosicao.moveLeft();
    }

    /** Move para a direita (coluna+1). Retorna true se moveu. */
    public boolean moveRight() {
        return this.pPosicao.moveRight();
    }
}
