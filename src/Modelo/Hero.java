package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import auxiliar.Posicao;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Representa o personagem controlado pelo jogador. Extende Personagem.
 * Agora compatível com Serializable:
 * - Herdado de Personagem, que já marca o iImage como transient.
 * - Possui recarregarSprite() para restaurar o sprite após desserializar.
 */
public class Hero extends Personagem implements Serializable {
    private static final long serialVersionUID = 1L;

    private int vidas;
    private boolean gameOver;
    private boolean gameWin;

    /** Nome do arquivo de imagem (ex.: "Hero.png") para recarregar após load */
    private String nomeImagePNG;

    /**
     * Construtor do herói. Passa o nome do PNG para o Personagem carregar.
     * @param nomeArquivoPNG nome do arquivo dentro de Consts.PATH (ex.: "Hero.png")
     */
    public Hero(String nomeArquivoPNG) {
        super(nomeArquivoPNG);  // carrega o sprite pela primeira vez
        this.nomeImagePNG = nomeArquivoPNG;
        this.vidas    = 3;
        this.gameOver = false;
        this.gameWin  = false;
        this.bTransponivel = false; // Herói bloqueia, não passa por cima
    }

    /**
     * Chamado logo após desserializar para restaurar o campo transient iImage.
     * Sem isso, o iImage ficaria null e o herói não seria desenhado.
     */
    @Override
    public void recarregarSprite() {
        carregarImageIcon(nomeImagePNG);
    }

    /**
     * Desenha o herói na tela usando o sprite carregado.
     */
    @Override
    public void autoDesenho() {
        if (iImage != null) {
            Desenho.desenhar(iImage, pPosicao.getColuna(), pPosicao.getLinha());
        }
    }

    // MOVIMENTAÇÃO

    /** Move o herói para cima, decrementando a linha. */
    @Override
    public boolean moveUp() {
        return this.pPosicao.moveUp();
    }

    /** Move o herói para baixo, incrementando a linha. */
    @Override
    public boolean moveDown() {
        return this.pPosicao.moveDown();
    }

    /** Move o herói para a esquerda, decrementando a coluna. */
    @Override
    public boolean moveLeft() {
        return this.pPosicao.moveLeft();
    }

    /** Move o herói para a direita, incrementando a coluna. */
    @Override
    public boolean moveRight() {
        return this.pPosicao.moveRight();
    }

    // POSIÇÃO

    /** Define a posição em (linha, coluna). */
    @Override
    public boolean setPosicao(int linha, int coluna) {
        return pPosicao.setPosicao(linha, coluna);
    }

    /** Retorna a posição atual do herói. */
    @Override
    public Posicao getPosicao() {
        return this.pPosicao;
    }

    // VIDA E ESTADO DE JOGO

    /** Diminui uma vida; se chegar a zero, marca gameOver. */
    public void perderVida() {
        this.vidas--;
        if (this.vidas <= 0) {
            this.gameOver = true;
        }
    }

    /** Retorna quantas vidas o herói ainda tem. */
    public int getVidas() {
        return this.vidas;
    }

    /** Retorna true se o herói está em game over. */
    public boolean isGameOver() {
        return this.gameOver;
    }

    /** Marca que o herói venceu (pode ser usado ao coletar todos os chips). */
    public void setGameWin(boolean vencedor) {
        this.gameWin = vencedor;
    }

    /** Retorna true se o herói já venceu. */
    public boolean isGameWin() {
        return this.gameWin;
    }
}
