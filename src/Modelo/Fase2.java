package Modelo;

import java.io.Serializable;

/**
 * Fase2:
 *  - Lê o mapa2.txt com os valores:
 *      0 = chão
 *      1 = parede
 *      2 = chip
 *      3 = fogo
 *      4 = portal de saída
 *      5 = laser (outra barreira física igual o fogo)
 *  - Herói começa em (xInicial, yInicial) passados no construtor
 *  - Compatível com serialização: recarrega sprites do mapa após desserializar.
 */
public class Fase2 extends Fase implements Serializable {
    private static final long serialVersionUID = 1L;

    public Fase2(String arquivoMapa, int xInicial, int yInicial) {
        super(arquivoMapa, xInicial, yInicial);

        // Mapeia imagens dos elementos:
        spritesMapa.put(0, carregarImagem("chao.png"));
        spritesMapa.put(1, carregarImagem("parede.png"));
        spritesMapa.put(2, carregarImagem("moeda.png"));
        spritesMapa.put(3, carregarImagem("fogo.png"));
        spritesMapa.put(4, carregarImagem("portal.png"));
        spritesMapa.put(5, carregarImagem("laser.png"));

        // Carrega a matriz de inteiros do arquivo-texto (sem criar personagens aqui)
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

        // 2) Varre o mapa para “2” (chip), registra e cria o objeto ChipColetavel
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[0].length; j++) {
                if (mapa[i][j] == 2) {
                    // Registra mais um chip no total desta fase
                    registrarChip();

                    // Cria o ChipColetavel nessa posição global
                    ChipColetavel chip = new ChipColetavel("moeda.png");
                    chip.setPosicao(inicioLinha + i, inicioColuna + j);
                    adicionarPersonagem(chip);

                    // Para não redesenhar o “2” por baixo do sprite do ChipColetavel,
                    // mudamos esse tile para “0” (chão).
                    mapa[i][j] = 0;
                }
            }
        }

        System.out.println("Chips registrados em Fase2: " + getTotalChips());

        // 3) Varre o mapa procurando por valor 3 (fogo) ou 5 (laser)
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[0].length; j++) {
                if (mapa[i][j] == 3) {
                    Fogo f = new Fogo("fogo.png");
                    f.setPosicao(inicioLinha + i, inicioColuna + j);
                    f.setbTransponivel(false);
                    f.setMortal(true);
                    adicionarPersonagem(f);
                    mapa[i][j] = 0;
                } else if (mapa[i][j] == 5) {
                    LaserBarrier lb = new LaserBarrier("laser.png");
                    lb.setPosicao(inicioLinha + i, inicioColuna + j);
                    lb.setbTransponivel(false);
                    lb.setMortal(true);
                    adicionarPersonagem(lb);
                    mapa[i][j] = 0;
                }
            }
        }
    }

    /**
     * Ao desserializar, recarrega todas as imagens de tiles em spritesMapa.
     */
    @Override
    public void recarregarSpritesMapa() {
        spritesMapa = new java.util.HashMap<>();
        spritesMapa.put(0, carregarImagem("chao.png"));
        spritesMapa.put(1, carregarImagem("parede.png"));
        spritesMapa.put(2, carregarImagem("moeda.png"));
        spritesMapa.put(3, carregarImagem("fogo.png"));
        spritesMapa.put(4, carregarImagem("portal.png"));
        spritesMapa.put(5, carregarImagem("laser.png"));
    }
}
