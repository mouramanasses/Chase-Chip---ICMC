package Modelo;

import java.util.ArrayList;

public class Fase3 {
    public ArrayList<Personagem> criarFase() {
        ArrayList<Personagem> fase = new ArrayList<>();

        // Hero
        Hero hero = new Hero("Robbo.png");
        hero.setPosicao(1, 1);
        fase.add(hero);

        // Mapa: 0 = chão, 1 = parede, 2 = chip
        int[][] mapa = {
            {1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,2,0,0,1},
            {1,0,1,1,0,1,1,1,0,1},
            {1,0,0,0,0,0,0,1,0,1},
            {1,1,0,1,1,1,0,1,0,1},
            {1,0,2,0,0,2,0,0,0,1},
            {1,0,1,1,1,1,1,1,0,1},
            {1,0,0,0,0,0,0,2,0,1},
            {1,0,1,1,1,0,1,1,0,1},
            {1,1,1,1,1,1,1,1,1,1}
        };

        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[0].length; j++) {
                switch (mapa[i][j]) {
                    case 1 -> {
                        Parede parede = new Parede("parede.png");
                        parede.setPosicao(i, j);
                        fase.add(parede);
                    }
                    case 2 -> {
                        ChipColetavel chip = new ChipColetavel("moeda.png");
                        chip.setPosicao(i, j);
                        fase.add(chip);
                    }
                }
            }
        }

        // Fogos ajustados (difíceis, mas com caminhos de desvio)
        int[][] fogos = {
            {4, 6}, {6, 1}, {7, 5}
        };
        for (int[] pos : fogos) {
            Fogo fogo = new Fogo("fogo.png");
            fogo.setPosicao(pos[0], pos[1]);
            fase.add(fogo);
        }

        // Robô horizontal (roboPink)
        BichinhoVaiVemHorizontal inimigoH = new BichinhoVaiVemHorizontal("roboPink.png");
        inimigoH.setPosicao(2, 3);
        fase.add(inimigoH);

        // Robô vertical (robo)
        BichinhoVaiVemVertical inimigoV = new BichinhoVaiVemVertical("robo.png");
        inimigoV.setPosicao(5, 7);
        fase.add(inimigoV);

        return fase;
    }
}
