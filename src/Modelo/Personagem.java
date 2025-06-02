package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import auxiliar.Posicao;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

/**
 * Classe base para todos os personagens do jogo (herói, inimigos, fogos, chips, paredes, etc.).
 * Contém sprite (iImage), posição (pPosicao) e flags de transponibilidade / mortalidade.
 */
public abstract class Personagem implements Serializable {
    private static final long serialVersionUID = 1L;

    /** O ícone que será desenhado no mapa; marcado como transient para não serializar */
    protected transient ImageIcon iImage;

    protected Posicao pPosicao;
    protected boolean bTransponivel; /* Pode passar por cima? */
    protected boolean bMortal;       /* Se encostar, morre */

    /**
     * Construtor que carrega o sprite PNG dentro de Consts.PATH + sNomeImagePNG
     */
    protected Personagem(String sNomeImagePNG) {
        this.pPosicao = new Posicao(1, 1);
        this.bTransponivel = true;
        this.bMortal = false;
        carregarImageIcon(sNomeImagePNG);
    }

    /**
     * Utility: carrega um ImageIcon de "imgs/<nomeArquivoPNG>", redimensiona para CELL_SIDE×CELL_SIDE
     * e armazena em iImage (que é transient).
     */
    protected void carregarImageIcon(String nomeArquivoPNG) {
        try {
            String caminho = new java.io.File(".").getCanonicalPath() + Consts.PATH + nomeArquivoPNG;
            ImageIcon raw = new ImageIcon(caminho);
            Image img = raw.getImage();
            // Redimensiona para exatamente CELL_SIDE×CELL_SIDE
            BufferedImage bi = new BufferedImage(Consts.CELL_SIDE, Consts.CELL_SIDE, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, Consts.CELL_SIDE, Consts.CELL_SIDE, null);
            g.dispose();
            this.iImage = new ImageIcon(bi);
        } catch (IOException ex) {
            System.out.println("Erro ao carregar imagem de sprite: " + ex.getMessage());
            this.iImage = null;
        }
    }

    /**
     * Deve ser chamado logo após desserializar, para recarregar a imagem no campo transient.
     * Cada subclasse deve implementar este método chamando carregarImageIcon(...) com o nome exato do arquivo.
     */
    public abstract void recarregarSprite();

    /**
     * Desenha o sprite na tela, usando o método Desenho.desenhar().
     * Deve ser chamado de dentro do paint() de Tela.
     */
    public void autoDesenho() {
        if (iImage != null) {
            Desenho.desenhar(this.iImage, this.pPosicao.getColuna(), this.pPosicao.getLinha());
        }
    }

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

    /** Tenta definir a nova posição (linha, coluna). Retorna true se a operação foi bem-sucedida. */
    public boolean setPosicao(int linha, int coluna) {
        return pPosicao.setPosicao(linha, coluna);
    }

    /** Move para cima (linha-1). Retorna true se moveu. */
    public boolean moveUp() {
        return this.pPosicao.moveUp();
    }

    /** Move para baixo (linha+1). Retorna true se moveu. */
    public boolean moveDown() {
        return this.pPosicao.moveDown();
    }

    /** Move para a esquerda (coluna-1). Retorna true se moveu. */
    public boolean moveLeft() {
        return this.pPosicao.moveLeft();
    }

    /** Move para a direita (coluna+1). Retorna true se moveu. */
    public boolean moveRight() {
        return this.pPosicao.moveRight();
    }
}
