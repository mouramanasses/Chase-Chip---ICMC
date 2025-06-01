package Modelo;

import java.util.ArrayList;

public class Fase4 extends Fase {

    public Fase4(String arquivoMapa, int xInicial, int yInicial) {
        super(arquivoMapa, xInicial, yInicial);

        // 1) Mapa de sprites fixos:
        spritesMapa.put(0, carregarImagem("chao.png"));
        spritesMapa.put(1, carregarImagem("parede.png"));
        spritesMapa.put(2, carregarImagem("moeda.png"));
        spritesMapa.put(3, carregarImagem("fogo.png"));
        spritesMapa.put(4, carregarImagem("portal.png"));
        spritesMapa.put(5, carregarImagem("laser.png")); 

        // 2) Carrega a matriz de inteiros do arquivo
        carregarMapa();
    }

    @Override
    public void inicializar() {
        // 1) Posiciona o herói em (xInicial, yInicial):
        if (getHero() != null) {
            getHero().setPosicao(getInicioLinha(), getInicioColuna());
        }

        int[][] mapa = getMapa();
        int inicioLinha  = getInicioLinha();
        int inicioColuna = getInicioColuna();

        // 2) Varre o mapa em busca de “2” (chips) e “3” (fogo):
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[0].length; j++) {
                int valor = mapa[i][j];

                if (valor == 2) {
                    // (a) registra chip (incrementa totalChips)
                    registrarChip();

                    // (b) cria o ChipColetavel na posição global (linha, coluna)
                    ChipColetavel chip = new ChipColetavel("moeda.png");
                    chip.setPosicao(inicioLinha + i, inicioColuna + j);
                    adicionarPersonagem(chip);

                    // (c) converte tile “2” para chão (“0”) para não desenhar debaixo:
                    mapa[i][j] = 0;
                }
                else if (valor == 3) {
                    // (a) cria fogueira na posição global
                    Fogo fogo = new Fogo("fogo.png");
                    fogo.setPosicao(inicioLinha + i, inicioColuna + j);
                    fogo.setbTransponivel(false);
                    fogo.setMortal(true);
                    adicionarPersonagem(fogo);
                     mapa[i][j] = 0;

                }
               
                else if (mapa[i][j] == 5) {
                    LaserBarrier lb = new LaserBarrier("laser.png");
                    lb.setPosicao(inicioLinha + i, inicioColuna + j);
                    lb.setbTransponivel(false);
                    lb.setMortal(true);
                    adicionarPersonagem(lb);
                    mapa[i][j] = 0; // evita recriar o laser várias vezes
                }

            }
        }

        System.out.println("Chips registrados em Fase4: " + getTotalChips());

        BichinhoVaiVemVertical inimigoV1 = new BichinhoVaiVemVertical("Robo.png");
        inimigoV1.setPosicao(inicioLinha +  2, inicioColuna +  9);
        adicionarPersonagem(inimigoV1);
        
        BichinhoVaiVemVertical inimigoV2 = new BichinhoVaiVemVertical("Robo.png");
        inimigoV2.setPosicao(inicioLinha + 13, inicioColuna + 7);
        adicionarPersonagem(inimigoV2);

        // 4.2) Bichinhos horizontais
        BichinhoVaiVemHorizontal inimigoH1 = new BichinhoVaiVemHorizontal("RoboPink.png");
        inimigoH1.setPosicao(inicioLinha +  6, inicioColuna +  6);
        adicionarPersonagem(inimigoH1);
        
        BichinhoVaiVemHorizontal inimigoH2 = new BichinhoVaiVemHorizontal("RoboPink.png");
        inimigoH2.setPosicao(inicioLinha + 4, inicioColuna +  12);
        adicionarPersonagem(inimigoH2);
        
        BichinhoVaiVemHorizontal inimigoH3 = new BichinhoVaiVemHorizontal("RoboPink.png");
        inimigoH3.setPosicao(inicioLinha + 6, inicioColuna +  17);
        adicionarPersonagem(inimigoH3);
        
        BichinhoVaiVemHorizontal inimigoH4 = new BichinhoVaiVemHorizontal("RoboPink.png");
        inimigoH4.setPosicao(inicioLinha + 2, inicioColuna +  6);
        adicionarPersonagem(inimigoH4);
    }
}
