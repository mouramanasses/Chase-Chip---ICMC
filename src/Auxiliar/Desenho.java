package Auxiliar;

import java.awt.Graphics;
import java.io.Serializable;
import javax.swing.ImageIcon;
import Controler.Tela;
import auxiliar.Posicao;
import Modelo.Fase;
import Modelo.Hero;

public class Desenho implements Serializable {

    public static final int HUD_HEIGHT = 30;
    static Tela jCenario;

    public static void setCenario(Tela umJCenario) {
        jCenario = umJCenario;
    }

    public static Tela acessoATelaDoJogo() {
        return jCenario;
    }

    public static Graphics getGraphicsDaTela() {
        return jCenario.getGraphicsBuffer();
    }

    public static void desenhar(ImageIcon iImage, int iColuna, int iLinha) {
        int telaX = (iColuna - jCenario.getCameraColuna()) * Consts.CELL_SIDE;
        int telaY = (iLinha - jCenario.getCameraLinha()) * Consts.CELL_SIDE + HUD_HEIGHT;
        


        if (telaX >= 0 && telaX < Consts.RES * Consts.CELL_SIDE
                && telaY >= 0 && telaY < Consts.RES * Consts.CELL_SIDE) {
            iImage.paintIcon(jCenario, getGraphicsDaTela(), telaX, telaY);
        }
    }
        public static Posicao getHeroPosicao() {
        if (jCenario == null) return null;
        Fase fase = jCenario.getFaseAtual();
        Hero  h   = fase.getHero();
        return h.getPosicao();
    }
    
    
}

