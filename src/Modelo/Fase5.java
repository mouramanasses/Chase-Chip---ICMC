package Modelo;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Fase5:
 *  - Lê o arquivo mapa5.txt, onde:
 *      0 = chão
 *      1 = parede
 *      2 = chip
 *      3 = fogo
 *      4 = portal (saída)
 *      5 = laser
 *  - Herói começa na posição (xInicial, yInicial) passada no construtor
 *  - Compatível com serialização: recarrega sprites do mapa após desserializar.
 */
public class Fase5 extends Fase implements Serializable {
    private static final long serialVersionUID = 1L;

    public Fase5(String arquivoMapa, int xInicial, int yInicial) {
        super(arquivoMapa, xInicial, yInicial);

        // 1) Mapear sprites fixos:
        spritesMapa.put(0, carregarImagem("chao.png"));
        spritesMapa.put(1, carregarImagem("parede.png"));
        spritesMapa.put(2, carregarImagem("moeda.png"));
        spritesMapa.put(3, carregarImagem("fogo.png"));
        spritesMapa.put(4, carregarImagem("portal.png"));
        spritesMapa.put(5, carregarImagem("laser.png"));

        // 2) Carrega a matriz de inteiros do arquivo-texto:
        carregarMapa();
    }

    @Override
    public void inicializar() {
        // 1) Posiciona o herói na posição inicial (xInicial, yInicial)
        if (getHero() != null) {
            getHero().setPosicao(getInicioLinha(), getInicioColuna());
        }

        int[][] mapa = getMapa();
        int inicioLinha = getInicioLinha();
        int inicioColuna = getInicioColuna();

        // 2) Varre o mapa procurando por “2” (chips) e cria ChipColetavel
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[0].length; j++) {
                if (mapa[i][j] == 2) {
                    // (a) registra chip
                    registrarChip();
                    // (b) cria ChipColetavel na posição global
                    ChipColetavel chip = new ChipColetavel("moeda.png");
                    chip.setPosicao(inicioLinha + i, inicioColuna + j);
                    adicionarPersonagem(chip);
                    // (c) zera o tile para não desenhar “2” por baixo
                    mapa[i][j] = 0;
                }
            }
        }

        System.out.println("Chips registrados em Fase5: " + getTotalChips());

        // 3) Varre o mapa procurando por “3” (fogo) ou “5” (laser)
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[0].length; j++) {
                if (mapa[i][j] == 3) {
                    Fogo fogo = new Fogo("fogo.png");
                    fogo.setPosicao(inicioLinha + i, inicioColuna + j);
                    fogo.setbTransponivel(false);
                    fogo.setMortal(true);
                    adicionarPersonagem(fogo);
                    mapa[i][j] = 0;
                } else if (mapa[i][j] == 5) {
                    LaserBarrier lf = new LaserBarrier("laser.png");
                    lf.setPosicao(inicioLinha + i, inicioColuna + j);
                    lf.setbTransponivel(false);
                    lf.setMortal(true);
                    adicionarPersonagem(lf);
                    mapa[i][j] = 0;
                }
            }
        }

        // 4) Adiciona bichinhos horizontais
        BichinhoVaiVemHorizontal h1 = new BichinhoVaiVemHorizontal("RoboPink.png");
        h1.setPosicao(inicioLinha + 2,  inicioColuna +  5);
        adicionarPersonagem(h1);

        BichinhoVaiVemHorizontal h2 = new BichinhoVaiVemHorizontal("RoboPink.png");
        h2.setPosicao(inicioLinha + 7,  inicioColuna + 8);
        adicionarPersonagem(h2);

        BichinhoVaiVemHorizontal h3 = new BichinhoVaiVemHorizontal("RoboPink.png");
        h3.setPosicao(inicioLinha + 10, inicioColuna +  8);
        adicionarPersonagem(h3);

        BichinhoVaiVemHorizontal h4 = new BichinhoVaiVemHorizontal("RoboPink.png");
        h4.setPosicao(inicioLinha + 4, inicioColuna +  8);
        adicionarPersonagem(h4);

        BichinhoVaiVemHorizontal h5 = new BichinhoVaiVemHorizontal("RoboPink.png");
        h5.setPosicao(inicioLinha + 11, inicioColuna +  1);
        adicionarPersonagem(h5);

        BichinhoVaiVemHorizontal h6 = new BichinhoVaiVemHorizontal("RoboPink.png");
        h6.setPosicao(inicioLinha + 7, inicioColuna +  12);
        adicionarPersonagem(h6);

        // 5) Adiciona bichinhos verticais
        BichinhoVaiVemVertical v1 = new BichinhoVaiVemVertical("Robo.png");
        v1.setPosicao(inicioLinha + 4,  inicioColuna + 15);
        adicionarPersonagem(v1);

        BichinhoVaiVemVertical v2 = new BichinhoVaiVemVertical("Robo.png");
        v2.setPosicao(inicioLinha + 9,  inicioColuna +  4);
        adicionarPersonagem(v2);

        BichinhoVaiVemVertical v3 = new BichinhoVaiVemVertical("Robo.png");
        v3.setPosicao(inicioLinha + 13, inicioColuna + 10);
        adicionarPersonagem(v3);

        // 6) Adiciona Chaser que persegue o herói
        Chaser c1 = new Chaser("Chaser.png");
        c1.setPosicao(inicioLinha + 9,  inicioColuna + 18);
        adicionarPersonagem(c1);
    }

    /**
     * Ao desserializar, recarrega todas as imagens de tiles em spritesMapa.
     */
    @Override
    public void recarregarSpritesMapa() {
        spritesMapa = new HashMap<>();
        spritesMapa.put(0, carregarImagem("chao.png"));
        spritesMapa.put(1, carregarImagem("parede.png"));
        spritesMapa.put(2, carregarImagem("moeda.png"));
        spritesMapa.put(3, carregarImagem("fogo.png"));
        spritesMapa.put(4, carregarImagem("portal.png"));
        spritesMapa.put(5, carregarImagem("laser.png"));
    }
}
