package Modelo;

import java.util.ArrayList;

public class Fase2 {
    public ArrayList<Personagem> criarFase() {
        ArrayList<Personagem> fase = new ArrayList<>();

        // Hero (posição inicial segura)
        Hero hero = new Hero("Robbo.png");
        hero.setPosicao(1, 1);
        fase.add(hero);

        // Mapa visual
        // 0 = chão
        // 1 = parede
        // 2 = chip
        int[][] mapa = {
            {1,1,1,1,1,1,1,1,1,1},
            {1,0,0,1,0,2,0,0,2,1},
            {1,2,1,1,0,1,1,0,1,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,1,1,0,1,1,1,1,0,1},
            {1,0,2,0,1,2,0,1,0,1},
            {1,0,1,0,0,0,0,1,0,1},
            {1,0,1,1,1,1,0,1,2,1},
            {1,2,0,0,0,0,0,0,0,1},
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

        // Fogos fixos em áreas de risco
      int[][] fogos = {
           {3, 5}, // no meio do corredor, mas contornável
           {4, 7}, // trava caminho, mas com volta por baixo
           {6, 2}, // ponto estreito com rota segura
           {6, 6}, // curva perigosa
           {8, 7}  // perto do fim, exige planejamento
};

        for (int[] pos : fogos) {
            Fogo fogo = new Fogo("fogo.png");
            fogo.setPosicao(pos[0], pos[1]);
            fase.add(fogo);
        }

        return fase;
    }
}
