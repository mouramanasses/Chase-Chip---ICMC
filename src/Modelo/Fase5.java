package Modelo;

import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Fase5 extends Fase {

    public Fase5(String arquivoMapa, int xInicial, int yInicial) {
        super(arquivoMapa, xInicial, yInicial);

        spritesMapa.put(0, carregarImagem("chao.png"));
        spritesMapa.put(1, carregarImagem("parede.png"));
        spritesMapa.put(2, carregarImagem("moeda.png"));
        spritesMapa.put(3, carregarImagem("fogo.png"));
        spritesMapa.put(4, carregarImagem("portal.png"));
        spritesMapa.put(5, carregarImagem("laser.png")); 

        // 2) Carrega o mapa de inteiros (preenche this.mapa):
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

        // 2) Varre o mapa procurando por “2” (chips)
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[0].length; j++) {
                int valor = mapa[i][j];

                // 2.a) Se for “2”, registra chip e cria ChipColetavel
                if (valor == 2) {
                    registrarChip();
                    ChipColetavel chip = new ChipColetavel("moeda.png");
                    chip.setPosicao(inicioLinha + i, inicioColuna + j);
                    adicionarPersonagem(chip);
                    // Para não desenhar “2” por baixo do sprite, transformamos em chão:
                    mapa[i][j] = 0;
                }
                // 2.b) Se for “4” (portal), deixamos apenas como tile: 
                //     o desenho do portal será feito automaticamente via spritesMapa.
            }
        }

        System.out.println("Chips registrados em Fase5: " + getTotalChips());

        // 3) Agora varre o mapa procurando por “3” (fogos) e por "5" (lasers) e instancia:
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[0].length; j++) {
                if (mapa[i][j] == 3) {
                    Fogo f = new Fogo("fogo.png");
                    f.setPosicao(inicioLinha + i, inicioColuna + j);
                    f.setbTransponivel(false);
                    f.setMortal(true);
                    adicionarPersonagem(f);
                    mapa[i][j] = 0;
                }
                else if (mapa[i][j] == 5) {
                    LaserBarrier lf = new LaserBarrier("laser.png");
                    lf.setPosicao(inicioLinha + i, inicioColuna + j);
                    lf.setbTransponivel(false);
                    lf.setMortal(true);
                    adicionarPersonagem(lf);
                    mapa[i][j] = 0;
            }
        }
     }

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
        
        BichinhoVaiVemVertical v1 = new BichinhoVaiVemVertical("Robo.png");
        v1.setPosicao(inicioLinha + 4,  inicioColuna + 15);
        adicionarPersonagem(v1);

        BichinhoVaiVemVertical v2 = new BichinhoVaiVemVertical("Robo.png");
        v2.setPosicao(inicioLinha + 9,  inicioColuna +  4);
        adicionarPersonagem(v2);

        BichinhoVaiVemVertical v3 = new BichinhoVaiVemVertical("Robo.png");
        v3.setPosicao(inicioLinha + 13, inicioColuna + 10);
        adicionarPersonagem(v3);

        //personagem novo adicionado nessa fase: inimido (chaser) que persegue o Hero
        Chaser c1 = new Chaser("Chaser.png");
        c1.setPosicao(inicioLinha + 9,  inicioColuna + 18);
        adicionarPersonagem(c1);
    }
}
