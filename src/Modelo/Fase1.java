package Modelo;

import java.util.ArrayList;

/**
 * Fase1:
 *  - Carrega mapa1.txt (com 0=chão, 1=parede, 2=moeda, 3=fogo, 4=portal),
 *  - Posiciona o herói em (inicioLinha, inicioColuna),
 *  - Cria automaticamente todos os objetos “ChipColetavel” e “Fogo” a partir do mapa,
 */
public class Fase1 extends Fase {

    public Fase1(String arquivoMapa, int xInicial, int yInicial) {
        super(arquivoMapa, xInicial, yInicial);

        // 1) Mapeia quais imagens serão usadas para cada valor do mapa:
        spritesMapa.put(0, carregarImagem("chao.png"));
        spritesMapa.put(1, carregarImagem("parede.png"));
        spritesMapa.put(2, carregarImagem("moeda.png"));
        spritesMapa.put(3, carregarImagem("fogo.png"));    // fogo virá do mapa (3)
        spritesMapa.put(4, carregarImagem("portal.png"));  //portal de saída para próxima fase

        // 2) Carrega a matriz de inteiros do arquivo-texto (sem criar objetos aqui)
        carregarMapa();
    }

    @Override
    public void inicializar() {
        // 1) Posiciona o herói na posição inicial da fase
        if (getHero() != null) {
            getHero().setPosicao(getInicioLinha(), getInicioColuna());
        }

        int[][] mapa = getMapa();
        int inicioLinha  = getInicioLinha();
        int inicioColuna = getInicioColuna();

        // 2) Varre o mapa para “2” (moeda), registra e cria o objeto ChipColetavel
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[0].length; j++) {
                if (mapa[i][j] == 2) {
                    // Registra mais um chip no total desta fase
                    registrarChip();

                    // Cria o ChipColetavel nessa posição global
                    ChipColetavel chip = new ChipColetavel("moeda.png");
                    chip.setPosicao(inicioLinha + i, inicioColuna + j);
                    adicionarPersonagem(chip);

                    // Para não desenhar o “2” por baixo do sprite do ChipColetavel,
                    // mudamos esse tile para “0” (chão).
                    mapa[i][j] = 0;
                }
            }
        }

        System.out.println("Chips registrados em Fase1: " + getTotalChips());

        // 3) Varre o mapa procurando por valor “3” (fogo)
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[0].length; j++) {
                if (mapa[i][j] == 3) {
                    Fogo fogo = new Fogo("fogo.png");
                    fogo.setPosicao(inicioLinha + i, inicioColuna + j);
                    fogo.setbTransponivel(false);
                    fogo.setMortal(true);
                    adicionarPersonagem(fogo);

                    // Marca como chão para não recriar na próxima leitura
                    mapa[i][j] = 0;
                }
            }
        }

        // 4) Não instanciamos nada para “4” (portal); se o seu código de desenho
        //    usa spritesMapa.get(4), o tile continuará exibindo o sprite do portal,
        //    mas não haverá Personagem associado em runtime.
    }
}
