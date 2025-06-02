package Modelo;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Fase3:
 *  - Lê o arquivo mapa3.txt (15 linhas × 20 colunas), onde:
 *      0 = chão
 *      1 = parede
 *      2 = chip
 *      3 = fogo
 *      4 = portal (saída)
 *      5 = laser
 *  - Herói começa na posição (xInicial, yInicial) passada no construtor
 *  - Compatível com serialização: recarrega sprites do mapa após desserializar.
 */
public class Fase3 extends Fase implements Serializable {
    private static final long serialVersionUID = 1L;

    public Fase3(String arquivoMapa, int xInicial, int yInicial) {
        super(arquivoMapa, xInicial, yInicial);

        // 1) Mapear imagens dos tiles fixos:
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
        // 1) Posiciona o herói na posição inicial da fase
        if (getHero() != null) {
            getHero().setPosicao(getInicioLinha(), getInicioColuna());
        }

        int[][] mapa = getMapa();
        int inicioLinha = getInicioLinha();
        int inicioColuna = getInicioColuna();

        // 2) Varre o mapa procurando por "2" (chips)
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[0].length; j++) {
                if (mapa[i][j] == 2) {
                    // (a) registra mais um chip no total desta fase
                    registrarChip();
                    // (b) cria objeto ChipColetavel na posição global
                    ChipColetavel chip = new ChipColetavel("moeda.png");
                    chip.setPosicao(inicioLinha + i, inicioColuna + j);
                    adicionarPersonagem(chip);
                    // (c) troca esse tile para '0', para não desenhar o "2" por baixo:
                    mapa[i][j] = 0;
                }
            }
        }

        System.out.println("Chips registrados em Fase3: " + getTotalChips());

        // 3) Varre o mapa procurando por “3” (Fogo)
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[0].length; j++) {
                if (mapa[i][j] == 3) {
                    Fogo fogo = new Fogo("fogo.png");
                    fogo.setPosicao(inicioLinha + i, inicioColuna + j);
                    fogo.setbTransponivel(false);
                    fogo.setMortal(true);
                    adicionarPersonagem(fogo);
                    // Zera o tile para não criar fogo duplicado em redraw
                    mapa[i][j] = 0;
                }
            }
        }

        // 4) Varre o mapa procurando por “5” (LaserBarrier) e cria lasers
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[0].length; j++) {
                if (mapa[i][j] == 5) {
                    LaserBarrier lb = new LaserBarrier("laser.png");
                    lb.setPosicao(inicioLinha + i, inicioColuna + j);
                    lb.setbTransponivel(false);
                    lb.setMortal(true);
                    adicionarPersonagem(lb);
                    // Zera o tile para não criar laser duplicado
                    mapa[i][j] = 0;
                }
            }
        }

        // 5) Criando os inimigos vai‐vem horizontal para aumentar a dificuldade desta fase
        BichinhoVaiVemHorizontal inimigoH = new BichinhoVaiVemHorizontal("RoboPink.png");
        inimigoH.setPosicao(inicioLinha + 7, inicioColuna + 8);
        adicionarPersonagem(inimigoH);

        BichinhoVaiVemHorizontal inimigoI = new BichinhoVaiVemHorizontal("RoboPink.png");
        inimigoI.setPosicao(inicioLinha + 12, inicioColuna + 8);
        adicionarPersonagem(inimigoI);

        BichinhoVaiVemHorizontal inimigoJ = new BichinhoVaiVemHorizontal("RoboPink.png");
        inimigoJ.setPosicao(inicioLinha + 8, inicioColuna + 14);
        adicionarPersonagem(inimigoJ);

        BichinhoVaiVemHorizontal inimigoK = new BichinhoVaiVemHorizontal("RoboPink.png");
        inimigoK.setPosicao(inicioLinha + 10, inicioColuna + 3);
        adicionarPersonagem(inimigoK);
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
