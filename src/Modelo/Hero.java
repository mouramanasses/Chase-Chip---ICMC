package Modelo;

import auxiliar.Posicao;

/**
 * Representa o personagem controlado pelo jogador. Extende Personagem.
 */
public class Hero extends Personagem {
    private int vidas;
    private boolean gameOver;
    private boolean gameWin;

     //Construtor do herói. Passa o nome do PNG para o construtor de Personagem.     
    public Hero(String nomeArquivoPNG) {
        super(nomeArquivoPNG);
        this.vidas = 3;
        this.gameOver = false;
        this.gameWin = false;
        this.bTransponivel = false; // Herói bloqueia, não passa por cima
    }

    //MÉTODOS DE MOVIMENTO

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

    //MÉTODOS DE POSIÇÃO

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

    //MÉTODOS DE VIDA E ESTADO DE JOGO 

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

    /** Marca que o herói venceu (podemos usar isso em Fase5 ou quando coletar todos os chips). */
    public void setGameWin(boolean vencedor) {
        this.gameWin = vencedor;
    }

    /** Retorna true se o herói já venceu. */
    public boolean isGameWin() {
        return this.gameWin;
    }
}

