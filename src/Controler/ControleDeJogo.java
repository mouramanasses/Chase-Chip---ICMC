package Controler;

import Modelo.Chaser;
import Modelo.Personagem;
import Modelo.Hero;
import Modelo.ChipColetavel;
import Modelo.Fogo;
import Modelo.Fase;
import auxiliar.Posicao;
import java.util.ArrayList;
import Modelo.LaserBarrier;
import Modelo.BichinhoVaiVemHorizontal;
import Modelo.BichinhoVaiVemVertical;
import Modelo.Chaser;

public class ControleDeJogo {

    /** Desenha todos os personagens da fase. */
    public void desenhaTudo(Fase e) {
        for (int i = 0; i < e.getPersonagens().size(); i++) {
            e.getPersonagens().get(i).autoDesenho();
        }
    }

    /**
     * Processa colisões e movimentações:
     *  - Colisão com Fogo (retorna vida e respawna)
     *  - Colisão com ChipColetavel (incrementa contador, remove o objeto)
     *  - Atualiza direção de Chaser
     */
    public void processaTudo(Fase umaFase) {
        Hero hero = (Hero) umaFase.getHero();

        // ── Percorre lista de personagens para detectar colisões ──
        for (int i = 0; i < umaFase.getPersonagens().size(); i++) {
            Personagem pIesimoPersonagem = umaFase.getPersonagens().get(i);

            if (hero.getPosicao().igual(pIesimoPersonagem.getPosicao())) {
                //  COLISÃO COM FOGO
                if (pIesimoPersonagem instanceof Fogo) {
                    hero.perderVida();
                    System.out.println(" O herói tocou no fogo! Vidas restantes: " + hero.getVidas());
                    // Volta o herói para a posição inicial da fase
                    hero.setPosicao(umaFase.getInicioLinha(), umaFase.getInicioColuna());
                    return; // Termina processaTudo para esta iteração
                }
                // COLIDIU COM CHIP (ChipColetavel)
                else if (pIesimoPersonagem instanceof ChipColetavel) {
                    // (a) Incrementa contador de chips coletados
                    umaFase.coletarChip();
                    // (b) Remove o objeto da lista, para que não desenhe nem colida mais
                    umaFase.getPersonagens().remove(i);
                    i--; // Corrige índice após remoção

                    System.out.println(" Chip coletado! ("
                        + umaFase.getChipsColetados() + "/"
                        + umaFase.getTotalChips() + ")");
                }
                //  COLISÃO COM LASER
                if (pIesimoPersonagem instanceof LaserBarrier) {
                    hero.perderVida();
                    System.out.println(" O herói tocou no Laser! Vidas restantes: " + hero.getVidas());
                    // Volta o herói para a posição inicial da fase
                    hero.setPosicao(umaFase.getInicioLinha(), umaFase.getInicioColuna());
                    return; // Termina processaTudo para esta iteração
                }
                
                // COLISÃO COM INIMIGO VERTICAL
                else if (pIesimoPersonagem instanceof BichinhoVaiVemVertical) {
                hero.perderVida();
                System.out.println(" O herói foi atingido pelo inimigo vertical! Vidas restantes: " + hero.getVidas());
                hero.setPosicao(umaFase.getInicioLinha(), umaFase.getInicioColuna());
                return;
            }
                // COLISÃO COM INIMIGO HORIZONTAL
                else if (pIesimoPersonagem instanceof BichinhoVaiVemHorizontal) {
                hero.perderVida();
                System.out.println(" O herói foi atingido pelo inimigo horizontal! Vidas restantes: " + hero.getVidas());
                hero.setPosicao(umaFase.getInicioLinha(), umaFase.getInicioColuna());
                return;
            }
                // COLISÃO COM CHASER
                else if (pIesimoPersonagem instanceof Chaser) {
                hero.perderVida();
                System.out.println(" O herói foi capturado pelo chaser! Vidas restantes: " + hero.getVidas());
                hero.setPosicao(umaFase.getInicioLinha(), umaFase.getInicioColuna());
                return;
            }
            }
        }

        // ── Atualiza movimentação de Chasers ──
        for (Personagem p : umaFase.getPersonagens()) {
            if (p instanceof Chaser) {
                ((Chaser) p).computeDirection(hero.getPosicao());
            }
        }
    }

    /**
     * Verifica se a posição alvo é válida para o personagem:
     *  - Bloqueia tile == 1 (parede)
     *  - Se tile == 4 (portal) e for herói:
     *      • Se todos os chips já coletados, marca vitória (setGameWin)
     *      • Senão, retorna false (bloqueia) e imprime mensagem “falta coletar”
     *  - Também impede movimentação se colidir com Personagem não-transponível (como inimigos/fogo)*
     */
    public boolean ehPosicaoValida(Fase umaFase, Personagem personagem) {
        if (umaFase == null) {
            // Situação inicial, antes de setar faseAtual
            return true;
        }

        Posicao p = personagem.getPosicao();
        if (p == null) return false;

        int linha = p.getLinha();
        int coluna = p.getColuna();

        int[][] mapa = umaFase.getMapa();
        if (mapa == null || mapa.length == 0 || mapa[0].length == 0) return false;

        int inicioLinha = umaFase.getInicioLinha();
        int inicioColuna = umaFase.getInicioColuna();

        // Cálculo de índice no mapa local:
        int mapaLinha = linha - inicioLinha;
        int mapaColuna = coluna - inicioColuna;

        // Verifica limites do mapa (linha/coluna dentro dos limites):
        if (linha >= inicioLinha && linha < mapa.length + inicioLinha
         && coluna >= inicioColuna && coluna < mapa[0].length + inicioColuna) {

            // 1) Bloqueia se parede (1)
            if (mapa[mapaLinha][mapaColuna] == 1) {
                return false;
            }

            // Heroi nao pode andar sobre o fogo (3)
            if ((personagem instanceof Hero) && mapa[mapaLinha][mapaColuna] == 3) {
                return false;
            }

            // 3) Se tile == 4 (portal) e personagem for Hero:
            if ((personagem instanceof Hero) && mapa[mapaLinha][mapaColuna] == 4) {
                if (umaFase.todosChipsColetados()) {
                    //se todos os chips forem coletados libera passagem e marca vitória
                    ((Hero) personagem).setGameWin(true);
                    // NOTA: não retornamos false; permitimos que o herói pise no portal (mas não vai passar de fase se não tiver coletado todos os chips)
                } else {
                    System.out.println("Ainda faltam chips! ("
                        + umaFase.getChipsColetados() + "/"
                        + umaFase.getTotalChips() + ")");
                    return false;
                }
            }
        }

        // 4) Limita que o herói não volte mais de 5 posições acima do inicioLinha)
        //impede que o Hero fique voltando demais e invada regiões que não deveria
        if (personagem instanceof Hero) {
            if (linha < umaFase.getInicioLinha() - 5) {
                return false;
            }
        }

        // 5) Verifica colisão com outros Personagem não-transponíveis
        for (Personagem pOutro : umaFase.getPersonagens()) {
            if (pOutro != personagem && !pOutro.isbTransponivel()) {
                if (pOutro.getPosicao().igual(p)) {
                    //o movimento é permitio mesmo com um inimigo ali
                    //deixamos o Hero passar para que possa colidir com esses objetos
                    if (pOutro instanceof Fogo
                        || pOutro instanceof LaserBarrier
                        || pOutro instanceof BichinhoVaiVemHorizontal
                        || pOutro instanceof BichinhoVaiVemVertical
                        || pOutro instanceof Chaser) {
                        return true;
                    }
                    
                    //inimigos não podem andar no tile que o Hero está
                    if (!(personagem instanceof Hero) && pOutro instanceof Hero) {
                        return false;
                    }
                    //Hero não pode pisar em personagens não-transponíveis
                    if (personagem instanceof Hero) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
