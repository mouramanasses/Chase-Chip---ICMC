package Modelo;

import java.util.ArrayList;

public class Fase5 {
    public ArrayList<Personagem> criarFase() {
        ArrayList<Personagem> fase = new ArrayList<>();

        // Hero (posição inicial segura)
        Hero hero = new Hero("Robbo.png");
        hero.setPosicao(1, 1);
        fase.add(hero);

        // Mapa: 0 = chão, 1 = parede, 2 = chip
        int[][] mapa = {
            {1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,1,0,0,2,0,1},
            {1,0,1,0,0,0,1,0,0,1},
            {1,2,0,0,0,0,0,0,2,1},
            {1,1,1,0,1,1,1,1,0,1},
            {1,0,2,0,0,2,0,1,0,1},
            {1,0,1,1,1,1,0,1,0,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,2,1,1,0,1,1,1,0,1},
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

        // 🔥 Fogos (removidos os centrais que trancavam tudo)
        int[][] fogos = {
            {2, 8}, {4, 8}, {6, 1}
        };
        for (int[] pos : fogos) {
            Fogo fogo = new Fogo("fogo.png");
            fogo.setPosicao(pos[0], pos[1]);
            fase.add(fogo);
        }

        // 🤖 Robô horizontal (roboPink)
        BichinhoVaiVemHorizontal roboH = new BichinhoVaiVemHorizontal("roboPink.png");
        roboH.setPosicao(3, 6); // patrulha perto de chip
        fase.add(roboH);

        // 🤖 Robô vertical (robo)
        BichinhoVaiVemVertical roboV = new BichinhoVaiVemVertical("robo.png");
        roboV.setPosicao(6, 4); // controla acesso ao centro
        fase.add(roboV);

        // 👣 Chaser (mais afastado do jogador)
        Chaser perseguidor = new Chaser("Chaser.png");
        perseguidor.setPosicao(7, 8); // entra em ação mais tarde
        fase.add(perseguidor);

        return fase;
    }
}
