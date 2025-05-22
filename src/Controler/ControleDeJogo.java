package Controler;

import Modelo.Chaser;
import Modelo.Personagem;
import Modelo.Hero;
import auxiliar.Posicao;
import java.util.ArrayList;
import Modelo.ChipColetavel;
import Modelo.Fogo;

public class ControleDeJogo {
    
    public void desenhaTudo(ArrayList<Personagem> umaFase, Posicao heroPos) {
    for (Personagem p : umaFase) {
        if (p instanceof Chaser) {
            ((Chaser)p).autoDesenho(heroPos); // usa a versão correta
        } else {
            p.autoDesenho(); // normal para os demais
        }
    }
}

    
    public void processaTudo(ArrayList<Personagem> umaFase, Tela tela) {
        Hero hero = (Hero) umaFase.get(0);
        Personagem pIesimoPersonagem;
        for (int i = 1; i < umaFase.size(); i++) {
            pIesimoPersonagem = umaFase.get(i);
              if (hero.getPosicao().igual(pIesimoPersonagem.getPosicao())) { //quando encosta no fogo
                    if (pIesimoPersonagem instanceof Fogo){
                        tela.perderVida(); // chama o método de perder vida
                        tela.resetarPosição(); 
                        break;
    }
}
            if (hero.getPosicao().igual(pIesimoPersonagem.getPosicao())) {
    if (pIesimoPersonagem instanceof ChipColetavel) {
        umaFase.remove(pIesimoPersonagem);
        break; // importante para evitar erro de concorrência
    }

    if (pIesimoPersonagem.isbTransponivel()) {
        if (pIesimoPersonagem.isbMortal()) {
            umaFase.remove(pIesimoPersonagem);
            break;
        }
    }

}

        }
        for (int i = 1; i < umaFase.size(); i++) {
            pIesimoPersonagem = umaFase.get(i);
            if (pIesimoPersonagem instanceof Chaser) {
                ((Chaser) pIesimoPersonagem).computeDirection(hero.getPosicao());
            }
        }
    }

    /*Retorna true se a posicao p é válida para Hero com relacao a todos os personagens no array*/
    public boolean ehPosicaoValida(ArrayList<Personagem> umaFase, Posicao p) {
        Personagem pIesimoPersonagem;
        for (int i = 1; i < umaFase.size(); i++) {
            pIesimoPersonagem = umaFase.get(i);
            if (!pIesimoPersonagem.isbTransponivel()) {
                if (pIesimoPersonagem.getPosicao().igual(p)) {
                    return false;
                }
            }
        }
        return true;
    }
}
