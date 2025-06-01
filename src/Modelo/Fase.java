 package Modelo;

import Auxiliar.Consts;
import auxiliar.Posicao;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import java.io.Serializable;


 // Classe abstrata que carrega um mapa textual e instancia Personagens
 //(Herói, Parede, ChipColetável, Fogo, inimigos, etc.) automaticamente.
 // Cada fase “concreta” só precisa chamar super.inicializar() para que
 //o mapa seja carregado e todos os Personagens sejam criados
 
public abstract class Fase implements Serializable {
    // Lista de todos os Personagem desta fase (inclui herói, inimigos, chips, paredes, fogo, etc.)
    protected ArrayList<Personagem> personagens = new ArrayList<>();

    // Referência direta ao herói da fase
    protected Hero hero;

    // Matriz interna que armazena o código de cada tile
    protected int[][] mapa;

    // Caminho do arquivo-texto do mapa (ex: "imgs/mapa1.txt")
    private String arquivoMapa;

    // Posições iniciais do herói (também usadas como posição inicial da "câmera", se necessário)
    private int heroLinha, heroColuna;

    // Dimensões do mapa
    protected int largura, altura;

    // Posição inicial da "câmera" (linha e coluna) — começando onde o herói nasce
    protected int cameraLinha, cameraColuna;

    // Cor de fundo da fase (pode ser usada para preencher áreas sem tile)
    protected Color corDeFundo = Color.BLACK;

    // Mapa de sprites: código do tile → ImageIcon correspondente (reduz a necessidade de recarregar imagens repetidamente)
    protected Map<Integer, ImageIcon> spritesMapa = new HashMap<>();

    /**
     *Constrói uma instância de Fase: só guarda o nome do arquivo de mapa e
     * onde o herói vai nascer (e também define a posição inicial da câmera).
     *
     * @param arquivoMapa   caminho do arquivo-texto que contém o código de cada tile
     *                      (deve estar em algum subdiretório conhecido, ex.: "mapas/mapa1.txt")
     * @param posHeroLinha  linha inicial do herói (0-based) — também usada como cameraLinha
     * @param posHeroColuna coluna inicial do herói (0-based) — também usada como cameraColuna
     */
    public Fase(String arquivoMapa, int posHeroLinha, int posHeroColuna) {
        this.arquivoMapa = arquivoMapa;
        // Inicializa posição da "câmera" na mesma posição do herói
        this.cameraLinha = posHeroLinha;
        this.cameraColuna = posHeroColuna;

        this.largura = 0;
        this.altura = 0;
        this.personagens = new ArrayList<>();
    }

    
    // Define o herói da fase. Se ainda não estiver na lista de personagens, adiciona-o
    public void setHero(Hero hero) {
        this.hero = hero;
        if (!personagens.contains(hero)) {
            personagens.add(hero);
        }
    }
    
    //  Retorna a instância do herói desta fase
    public Hero getHero() {
        return hero;
    }

     // Atualiza a posição do herói (seja para movimentação durante o jogo).
    public void atualizarPosicaoHero(int x, int y) {
        if (hero != null) {
            hero.setPosicao(x, y);
        }
    }

    /**
     * Método principal para inicializar a fase:
     *   1) Carrega o mapa textual em int[][] usando carregarMapa().
     *   2) Cria o Herói e posiciona em (heroLinha, heroColuna).
     *   3) Para cada célula do mapa, instancia o Personagem correto
     *      (Parede, ChipColetável, Fogo, inimigos, etc.) usando as suas imagens.
     *
     * Subclasses concretas podem chamar super.inicializar() para
     * utilizar toda essa lógica padrão, e então adicionar outros elementos
     * ou comportamentos específicos.
     */
    public abstract void inicializar(); //vai ser desenvolvido dentro de cada fase
    /**
     * Retorna a lista de Personagens desta fase. A Tela pode usar este
     * método para desenhar e processar cada personagem.
     */
    
    public void adicionarPersonagem (Personagem p){
        personagens.add(p);
    }
    public ArrayList<Personagem> getPersonagens() {
        return personagens;
    }

     // Retorna a matriz de inteiros que representa o mapa (códigos de tile).
    public int[][] getMapa() {
        return mapa;
    }

    
     // Retorna o nome do arquivo de mapa que foi usado para carregar esta fase.
    public String getArquivoMapa() {
        return arquivoMapa;
    }

    
     // Se, em algum momento, você quiser obter a ImageIcon de um dos tiles
     // diretamente a partir do mapa de sprites, pode usar este método:
     //  getImagemParaCelula(codigoDoTile);
    public ImageIcon getImagemParaCelula(int valorCelula) {
        return spritesMapa.getOrDefault(valorCelula, null);
    }
    
     // Retorna a cor de fundo desta fase.
    public Color getCorDeFundo() {
        return corDeFundo;
    }

    
     //Retorna a largura (número de colunas) do mapa, em tiles.
    public int getLargura() {
        return largura;
    }

    /**
     * Retorna a altura (número de linhas) do mapa, em tiles.
     */
    public int getAltura() {
        return altura;
    }

    /**
     * Retorna a linha inicial da "câmera" (geralmente a linha onde o herói começa).
     */
    public int getInicioLinha() {
        return cameraLinha;
    }

    /**
     * Retorna a coluna inicial da "câmera" (geralmente a coluna onde o herói começa).
     */
    public int getInicioColuna() {
        return cameraColuna;
    }

    /**
     * Retorna o mapa de sprites (código do tile → ImageIcon).
     * Útil caso você queira pré-carregar imagens de tiles específicos.
     */
    public Map<Integer, ImageIcon> getSpritesMapa() {
        return spritesMapa;
    }

    /**
     * Lê o arquivo-texto (arquivoMapa), converte cada linha em um array de ints
     * e preenche this.mapa, this.altura e this.largura.
     *
     * Formato esperado do arquivo-texto:
     *   Cada linha: números inteiros separados por espaços (ex.: "1 0 0 2 3 1").
     *   Linhas vazias (ou só espaços) são ignoradas.
     *
     * Ao final, this.mapa conterá a matriz [altura][largura] dos códigos.
     */
    protected void carregarMapa() {
        try{
        List<String> linhas = Files.readAllLines(Paths.get(this.getArquivoMapa()));

        linhas.removeIf(linha -> linha.trim().isEmpty());
        
        altura = linhas.size();
        largura = linhas.get(0).length();
        
        mapa = new int[altura][largura];
        
        for (int i = 0; i < altura; i++){
            String linha = linhas.get(i).trim();
            if (linha.length() < largura){
                throw new RuntimeException ("Linha " + (i+1) + "tem menos coluna do que o esperado");
            }
            
            for (int j = 0; j < largura; j++){
                mapa [i][j] = Character.getNumericValue(linha.charAt(j));   
            
            }
        } 
           
        } catch (IOException e) {
            e.printStackTrace();
        }
}
    /*
     * Carrega uma imagem do disco (baseada em Consts.PATH) e redimensiona
     * para o tamanho de célula (Consts.CELL_SIDE × Consts.CELL_SIDE).
     *
     * Uso típico: ImageIcon ic = carregarImagem("nomeDoArquivo.png");
     */
protected ImageIcon carregarImagem(String nomeArquivo) {
        try {
            String caminho = new java.io.File(".").getCanonicalPath() + Consts.PATH + nomeArquivo;
            ImageIcon original = new ImageIcon(caminho);

            // Redimensiona a imagem para o tamanho da célula
            Image img = original.getImage();
            BufferedImage bi = new BufferedImage(Consts.CELL_SIDE, Consts.CELL_SIDE, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, Consts.CELL_SIDE, Consts.CELL_SIDE, null);
            g.dispose(); // Libera recursos gráficos

            return new ImageIcon(bi);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
}

protected int totalChips = 0;
protected int chipsColetados = 0;

public void registrarChip() {
    totalChips++;
}

public void coletarChip() {
    chipsColetados++;
}

public boolean todosChipsColetados() {
    return chipsColetados >= totalChips;
}

public int getTotalChips() {
    return totalChips;
}

public int getChipsColetados() {
    return chipsColetados;
}
}