package Controler;

import Modelo.Personagem;
import Modelo.LaserBarrier;
import Modelo.Hero;
import Modelo.Chaser;
import Modelo.BichinhoVaiVemHorizontal;
import Auxiliar.Consts;
import Auxiliar.Desenho;
import Modelo.BichinhoVaiVemVertical;
import auxiliar.Posicao;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import Modelo.Fase;
import Modelo.Fase1;        
import Modelo.Fase2;
import Modelo.Fase3;
import Modelo.Fase4;
import Modelo.Fase5;

public class Tela extends javax.swing.JFrame implements MouseListener, KeyListener {

    private boolean showTitleScreen = true; // indica se estamos exibindo a tela de título/início
    private Image titleImage; //guarda a imagem da tela de título/início
    private Hero hero;
    private Fase faseAtual;
    private ControleDeJogo cj = new ControleDeJogo();
    private Graphics g2; //armazena o contexto gráfico
    private int cameraLinha = 0; //definem o canto superior esquerdo dentro do mundo do jogo
    private int cameraColuna = 0; //logica do scrolling, pois se o heroi anda para baixo talvez camera linha acompanha
    private int vidas = 3;
    private int fase = 1;
    private boolean gameOver = false;
    private boolean gameWin = false;
    private ArrayList<Fase> fases = new ArrayList<>(); //lista de todas as fases


    public Tela() {
        // Carrega a imagem de título
        try {
            String caminho = new java.io.File(".").getCanonicalPath() 
                            + Consts.PATH 
                            + "telaInicial.png";
            Image raw = Toolkit.getDefaultToolkit().getImage(caminho);
            // Redimensiona EXATAMENTE para RES × CELL_SIDE pixels (ou seja, área de jogo)
            int larguraJanela = Consts.RES * Consts.CELL_SIDE;
            int alturaJanela  = Consts.RES * Consts.CELL_SIDE;
            titleImage = raw.getScaledInstance(
                larguraJanela, 
                alturaJanela, 
                Image.SCALE_SMOOTH
            );
        } catch (IOException ex) {
            System.err.println("Não conseguiu carregar title.png: " + ex.getMessage());
            titleImage = null;
        }
     
        Desenho.setCenario(this);
        initComponents();
        this.addMouseListener(this);
        this.addKeyListener(this);
        
        /*Cria a janela do tamanho do tabuleiro + insets (bordas) da janela*/
        this.setSize(
        Consts.RES * Consts.CELL_SIDE + getInsets().left + getInsets().right,
        Consts.RES * Consts.CELL_SIDE + getInsets().top + getInsets().bottom 
      + Desenho.HUD_HEIGHT
);

     
        this.setIgnoreRepaint(true);
        this.createBufferStrategy(2);
        
        //inicialização do Hero
        hero = new Hero ("Hero.png"); //unica instancia do personagem
        
        Fase1 fase1 = new Fase1("imgs//mapa1.txt", 0, 0);
        fase1.setHero(hero);
        fase1.inicializar();

        Fase2 fase2 = new Fase2("imgs//mapa2.txt", 0, 0);
        fase2.setHero(hero);
        fase2.inicializar();
        
        Fase3 fase3 = new Fase3("imgs//mapa3.txt", 0, 0);
        fase3.setHero(hero);
        fase3.inicializar();
        
        Fase4 fase4 = new Fase4("imgs//mapa4.txt", 0, 0);
        fase4.setHero(hero);
        fase4.inicializar();
      
        Fase5 fase5 = new Fase5("imgs//mapa5.txt", 0, 0);
        fase5.setHero(hero);
        fase5.inicializar();
        
    // ADICIONAR TODAS AS FASES NA LISTA 'fases'
    fases.add(fase1);
    fases.add(fase2);
    fases.add(fase3);
    fases.add(fase4);
    fases.add(fase5);
    
    // DEFINIR A FASE ATUAL como fase1
    this.faseAtual = fase1;

    // Posiciona o herói (inicialmente) em (0,0) dentro do tabuleiro da fase1
    hero.setPosicao(fase1.getInicioLinha(), fase1.getInicioColuna());


    // Atualiza a câmera para centralizar no herói
    atualizaCamera();
}
      
  
    public int getCameraLinha() {
        return cameraLinha;
    }

    public int getCameraColuna() {
        return cameraColuna;
    }

    public boolean ehPosicaoValida(Personagem p) {
        return cj.ehPosicaoValida(this.faseAtual, p);
    }

   /* public void addPersonagem(Personagem umPersonagem) {
        faseAtual.add(umPersonagem);
    }

    public void removePersonagem(Personagem umPersonagem) {
        faseAtual.remove(umPersonagem);
    }
*/
    public Graphics getGraphicsBuffer() {
        return g2;
    }

    public void verificarGameOver() {
    //if (hero.isGameOver()) {
        gameOver = true;
        //repaint();
        System.out.println("GAME OVER – fechando o jogo.");
        //System.exit(0);
    }

    private void verificarVitoria() {
    //if (faseAtual instanceof Fase5 && hero.getGameWin()) {
        gameWin = true;
   // }
}
      @Override
    public void paint(Graphics gOld) {
    // 1) Obtém o Graphics do BufferStrategy e desenha a tela de inicio/titulo
    Graphics g = this.getBufferStrategy().getDrawGraphics();
    g2 = g.create(
        getInsets().left,
        getInsets().top,
        getWidth() - getInsets().right,
        getHeight() - getInsets().top
    );

    // =====================================================
    // 2) Se ainda estivermos em modo TELA DE TÍTULO, desenhamos só a imagem e retornamos
    // =====================================================
    if (showTitleScreen) {
        // 2.1) Preencha o fundo de branco (ou qualquer outra cor, pois a imagem já cobre tudo):
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // 2.2) Desenhe titleImage exatamente na área do jogo (RES*CELL_SIDE × RES*CELL_SIDE):
        if (titleImage != null) {
            g2.drawImage(
                titleImage,
                0,                  // X inicial
                Desenho.HUD_HEIGHT, // Y inicial  
                                    
                Consts.RES * Consts.CELL_SIDE,
                Consts.RES * Consts.CELL_SIDE,
                null
            );
        }

        // 2.3) Fecha o buffer e retorna; não desenhe mais nada enquanto estiver em título:
        g.dispose();
        g2.dispose();
        if (!getBufferStrategy().contentsLost()) {
            getBufferStrategy().show();
        }
        return;
    }
         if (hero.isGameOver()) {
        gameOver = true;
           }
         
        g2 = g.create(
            getInsets().left, getInsets().top,
            getWidth() - getInsets().right, getHeight() - getInsets().top
        );

        // 1) Se gameOver ou gameWin, desenha tela final e retorna
        if (gameOver || gameWin) {
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 48));
            String mensagem = gameOver ? "GAME OVER!" : "YOU WON!";
            int stringWidth  = g2.getFontMetrics().stringWidth(mensagem);
            int stringHeight = g2.getFontMetrics().getHeight();
            g2.drawString(
                mensagem,
                (getWidth() - stringWidth) / 2,
                (getHeight() + stringHeight) / 2
            );

            g.dispose();
            g2.dispose();
            if (!getBufferStrategy().contentsLost()) {
                getBufferStrategy().show();
            }
            return;
        }
        
        // 3)Desenho do RETÂNGULO de fundo do HUD 
    //     isso garante que nada do mapa cubra o texto.
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), Desenho.HUD_HEIGHT); 
        // Desenha um retângulo escuro nos primeiros 30 pixels (HUD_HEIGHT)
        
        // DESENHO DO HUD EM BRANCO
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Monospaced", Font.BOLD, 18));

        String textoFase  = "FASE: "  + fase;
        String textoVidas = "VIDAS: " + hero.getVidas();
        String textoChips = "CHIPS: " + faseAtual.getChipsColetados()
                      + "/" + faseAtual.getTotalChips();

        // Ajuste as posições X,Y conforme quiser que apareçam no topo:
        g2.drawString(textoFase,  10, 22);
        g2.drawString(textoVidas, 120, 22);
        g2.drawString(textoChips, 260, 22);
    
        // 3) Pega referência ao mapa e ao dicionário de sprites
        int[][] mapa = faseAtual.getMapa();
        Map<Integer, ImageIcon> sprites = faseAtual.getSpritesMapa();

        // 4) Desenha o fundo (tiles “0” = chão) com base na câmera
        for (int i = 0; i < Consts.RES; i++) {
            for (int j = 0; j < Consts.RES; j++) {
                int mapaLinha  = cameraLinha + i;
                int mapaColuna = cameraColuna + j;
                if (mapaLinha < Consts.MUNDO_ALTURA && mapaColuna < Consts.MUNDO_LARGURA) {
                    try {
                        Image blackTile = Toolkit.getDefaultToolkit().getImage(
                            new java.io.File(".").getCanonicalPath() + Consts.PATH + "chao.png"
                        );
                        g2.drawImage(
                            blackTile,
                            j * Consts.CELL_SIDE, i * Consts.CELL_SIDE + Desenho.HUD_HEIGHT,
                            Consts.CELL_SIDE, Consts.CELL_SIDE,
                            null
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }

        // 5) Desenha o mapa estático (paredes=1, moeda=2, fogo=3, portal=4, etc.)
        int inicioApartirLinha  = faseAtual.getInicioLinha();
        int inicioApartirColuna = faseAtual.getInicioColuna();
        for (int k = 0; k < mapa.length; k++) {
            for (int l = 0; l < mapa[0].length; l++) {
                int linhaGlobal  = inicioApartirLinha + k;
                int colunaGlobal = inicioApartirColuna + l;
                Desenho.desenhar(
                    faseAtual.getImagemParaCelula(mapa[k][l]),
                    colunaGlobal, linhaGlobal
                );
            }
        }

        // 6) Desenha e processa todos os personagens desta fase
        if (!this.faseAtual.getPersonagens().isEmpty()) {
            this.cj.desenhaTudo(faseAtual);
            this.cj.processaTudo(faseAtual);
        }

        // 7)Bloco único de troca de fase
        // Aqui verificamos se o herói pisou no portal (tile==4) E já coletou todos os chips.
        Hero h = (Hero) faseAtual.getHero();
        int heroiLinhaGlobal = h.getPosicao().getLinha();
        int heroiColunaGlobal = h.getPosicao().getColuna();
        int relLinha = heroiLinhaGlobal - inicioApartirLinha;
        int relColuna = heroiColunaGlobal - inicioApartirColuna;

        if (relLinha >= 0 && relLinha < mapa.length
         && relColuna >= 0 && relColuna < mapa[0].length) {

            int valorTile = mapa[relLinha][relColuna];
            if (valorTile == 4) {
                
                if (faseAtual.todosChipsColetados()) {
                    System.out.println("portal liberado! Trocando de fase...");
                    if (fase < fases.size()) {
                        // Avança para próxima fase na lista
                        fase++;
                        faseAtual = fases.get(fase - 1);
                        faseAtual.setHero(hero);
                        faseAtual.inicializar();
                        // Reposiciona o herói no ponto inicial da nova fase
                        hero.setPosicao(faseAtual.getInicioLinha(), faseAtual.getInicioColuna());
                        // Zera flag de vitória da fase anterior
                        hero.setGameWin(false);
                        // Atualiza câmera para nova fase
                        atualizaCamera();
                        System.out.println("Avançou para a Fase " + fase);
                    } else {
                        // Se não houver mais fases, marca vitória total
                        verificarVitoria();
                    }
                } else {
                    System.out.println(" Ainda faltam chips para passar de fase: ("
                        + faseAtual.getChipsColetados() + "/"
                        + faseAtual.getTotalChips() + ")");
                }
            }
        }

        // 8) Finaliza o bufferStrategy
        g.dispose();
        g2.dispose();
        if (!getBufferStrategy().contentsLost()) {
            getBufferStrategy().show();
        }
}
    private void atualizaCamera() {
    int linha = hero.getPosicao().getLinha();
    int coluna = hero.getPosicao().getColuna();

    cameraLinha = Math.max(
        0,
        Math.min(linha - Consts.RES / 2, Consts.MUNDO_ALTURA - Consts.RES)
    );
    cameraColuna = Math.max(
        0,
        Math.min(coluna - Consts.RES / 2, Consts.MUNDO_LARGURA - Consts.RES)
    );
}

public void go() {
    TimerTask task = new TimerTask() {
        public void run() {
            repaint();
        }
    };
    Timer timer = new Timer();
    timer.schedule(task, 0, Consts.PERIOD);
}

public void keyPressed(KeyEvent e) {
    
     // 1) Se estivermos ainda na tela de título, basta "sair" dela:
    if (showTitleScreen) {
        showTitleScreen = false;
        // Forçar um repaint para começar a desenhar o jogo:
        repaint();
        return;
    }
    
    int novaLinha = hero.getPosicao().getLinha();
    int novaColuna = hero.getPosicao().getColuna();

    switch (e.getKeyCode()) {
        case KeyEvent.VK_UP:
            novaLinha--;
            break;
        case KeyEvent.VK_DOWN:
            novaLinha++;
            break;
        case KeyEvent.VK_LEFT:
            novaColuna--;
            break;
        case KeyEvent.VK_RIGHT:
            novaColuna++;
            break;
        case KeyEvent.VK_C:
            this.faseAtual.getPersonagens().clear();
            break;
        default:
            return;
    }

    Hero tentativa = new Hero("hero.png");
    tentativa.setPosicao(novaLinha, novaColuna);

    if (cj.ehPosicaoValida(faseAtual, tentativa)) {
        hero.setPosicao(novaLinha, novaColuna);
    }

    this.atualizaCamera();
    this.setTitle("-> Cell: " + (hero.getPosicao().getColuna()) + ", " + (hero.getPosicao().getLinha()));
}


 public void mousePressed(MouseEvent e) {
    /* Clique do mouse desligado */
    int x = e.getX();
    int y = e.getY();

    this.setTitle("X: " + x + ", Y: " + y
            + " -> Cell: " + (y / Consts.CELL_SIDE) + ", " + (x / Consts.CELL_SIDE));

    this.hero.getPosicao().setPosicao(y / Consts.CELL_SIDE, x / Consts.CELL_SIDE);
    repaint();
}

    public Fase getFaseAtual() {
    return this.faseAtual;
}


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("POO2023-1 - Skooter");
        setAlwaysOnTop(true);
        setAutoRequestFocus(false);
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 561, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}

