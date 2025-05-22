package Controler;

import Modelo.Fase1;
import Modelo.Personagem;
import java.util.ArrayList;
import javax.swing.JFrame;
import Modelo.Hero;

public class Main {
    public static void main(String[] args) {
        JFrame janela = new JFrame("Chip's Challenge - Fase 1");
        Tela tela = new Tela();
        tela.setVisible(true);

        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(1200, 1200); //antes tava 800x800

        // Cria a fase
        Fase1 fase1 = new Fase1();
        ArrayList<Personagem> personagens = fase1.criarFase();
        for (Personagem p : personagens) {
            tela.addPersonagem(p);
                 if (p instanceof Hero) {
                    tela.setHero((Hero) p);// necessário para o teclado e a câmera funcionarem
                              }
                                }
          tela.go(); // inicia o jogo (Timer de atualização)

    }
}
