package Modelo;

import Auxiliar.Consts;
import auxiliar.Posicao;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import java.io.File;     

/**
 * Classe abstrata que carrega um mapa textual e instancia Personagens
 * (Herói, Parede, ChipColetável, Fogo, inimigos, etc.) automaticamente.
 * Cada fase “concreta” só precisa chamar super.inicializar() para que
 * o mapa seja carregado e todos os Personagens sejam criados.
 *
 * compatível com Serializable:
 *  - spritesMapa é transient (não serializamos ImageIcon).
 *  - Há um método recarregarSpritesMapa() para repopular spritesMapa após o load.
 */
public abstract class Fase implements Serializable {
    private static final long serialVersionUID = 1L;

    // Lista de todos os Personagem desta fase (inclui herói, inimigos, chips, paredes, fogo, etc.)
    protected ArrayList<Personagem> personagens = new ArrayList<>();

    // Referência direta ao herói da fase
    protected Hero hero;

    // Matriz interna que armazena o código de cada tile
    protected int[][] mapa;

    // Caminho do arquivo-texto do mapa (ex: "imgs/mapa1.txt")
    private String arquivoMapa;

    // Posições iniciais do herói (não são usadas diretamente, mas mantidas para referência)
    private int heroLinha, heroColuna;

    // Dimensões do mapa
    protected int largura, altura;

    // Posição inicial da "câmera" (linha e coluna) — começando onde o herói nasce
    protected int cameraLinha, cameraColuna;

    // Cor de fundo da fase (pode ser usada para preencher áreas sem tile)
    protected Color corDeFundo = Color.BLACK;

    // Mapa de sprites: código do tile → ImageIcon correspondente
    // Marcado como transient para não tentar serializar ImageIcon
    protected transient Map<Integer, ImageIcon> spritesMapa = new HashMap<>();

    // Contadores de chips nesta fase
    protected int totalChips = 0;
    protected int chipsColetados = 0;

    /**
     * Constrói uma instância de Fase: só guarda o nome do arquivo de mapa e
     * onde o herói vai nascer (e também define a posição inicial da câmera).
     *
     * @param arquivoMapa   caminho do arquivo-texto que contém o código de cada tile
     *                      (deve estar em algum subdiretório conhecido, ex.: "imgs//mapa1.txt")
     * @param posHeroLinha  linha inicial do herói (0-based) — também usada como cameraLinha
     * @param posHeroColuna coluna inicial do herói (0-based) — também usada como cameraColuna
     */
    public Fase(String arquivoMapa, int posHeroLinha, int posHeroColuna) {
        this.arquivoMapa = arquivoMapa;
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

    // Retorna a instância do herói desta fase
    public Hero getHero() {
        return hero;
    }

    // Retorna a lista de Personagens desta fase
    public ArrayList<Personagem> getPersonagens() {
        return personagens;
    }

    // Retorna a matriz de inteiros que representa o mapa (códigos de tile)
    public int[][] getMapa() {
        return mapa;
    }

    // Retorna o nome do arquivo de mapa que foi usado para carregar esta fase
    public String getArquivoMapa() {
        return arquivoMapa;
    }

    // Retorna a cor de fundo desta fase
    public Color getCorDeFundo() {
        return corDeFundo;
    }

    // Retorna a largura (número de colunas) do mapa, em tiles
    public int getLargura() {
        return largura;
    }

    // Retorna a altura (número de linhas) do mapa, em tiles
    public int getAltura() {
        return altura;
    }

    // Retorna a linha inicial da "câmera" (geralmente a linha onde o herói começa)
    public int getInicioLinha() {
        return cameraLinha;
    }

    // Retorna a coluna inicial da "câmera" (geralmente a coluna onde o herói começa)
    public int getInicioColuna() {
        return cameraColuna;
    }

    // Retorna o mapa de sprites (código do tile → ImageIcon)
    public Map<Integer, ImageIcon> getSpritesMapa() {
        return spritesMapa;
    }

    // Marca como coletado um chip nesta fase
    public void registrarChip() {
        totalChips++;
    }

    // Incrementa o contador de chips coletados
    public void coletarChip() {
        chipsColetados++;
    }

    // Retorna true se o jogador já coletou todos os chips
    public boolean todosChipsColetados() {
        return chipsColetados >= totalChips;
    }

    public int getTotalChips() {
        return totalChips;
    }

    public int getChipsColetados() {
        return chipsColetados;
    }

    /**
     * Carrega o arquivo-texto (arquivoMapa), converte cada linha em um array de ints
     * e preenche this.mapa, this.altura e this.largura.
     *
     * Formato esperado do arquivo-texto:
     *   Cada linha: números inteiros sem separação (ex.: "102031")
     *   Linhas vazias (ou só espaços) são ignoradas.
     */
    protected void carregarMapa() {
        try {
            List<String> linhas = Files.readAllLines(Paths.get(this.getArquivoMapa()));
            linhas.removeIf(linha -> linha.trim().isEmpty());

            altura = linhas.size();
            largura = linhas.get(0).length();
            mapa = new int[altura][largura];

            for (int i = 0; i < altura; i++) {
                String linha = linhas.get(i).trim();
                if (linha.length() < largura) {
                    throw new RuntimeException("Linha " + (i + 1) + " tem menos colunas do que o esperado");
                }
                for (int j = 0; j < largura; j++) {
                    mapa[i][j] = Character.getNumericValue(linha.charAt(j));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método auxiliar: carrega uma imagem do disco (baseada em Consts.PATH) e redimensiona
     * para o tamanho de célula (Consts.CELL_SIDE × Consts.CELL_SIDE).
     *
     * Uso típico: ImageIcon ic = carregarImagem("nomeDoArquivo.png");
     */
    protected ImageIcon carregarImagem(String nomeArquivo) {
        try {
            String caminho = new File(".").getCanonicalPath() + Consts.PATH + nomeArquivo;
            ImageIcon original = new ImageIcon(caminho);
            Image img = original.getImage();

            BufferedImage bi = new BufferedImage(Consts.CELL_SIDE, Consts.CELL_SIDE,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, Consts.CELL_SIDE, Consts.CELL_SIDE, null);
            g.dispose();

            return new ImageIcon(bi);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Inicializa personagens da fase. Cada subclasse concreta deve implementar
     * esta lógica, chamando super.inicializar() se quiser reutilizar carregarMapa()
     * ou lógica comum. (Em Fase1, chame super.inicializar() e depois adicione lógicas
     * específicas se necessário.)
     */
    public abstract void inicializar();

    /**
     * Recarrega os sprites do mapa (transient) após desserializar. Subclasses devem
     * sobrescrever para repopular spritesMapa exatamente como no construtor.
     */
    public void recarregarSpritesMapa() {
        // Classe base não sabe quais tiles exatamente a fase tem.
        // Cada FaseX deve sobrescrever este método para repovoar spritesMapa.
    }
    public void adicionarPersonagem (Personagem p){
        personagens.add(p);
    }
    
     public ImageIcon getImagemParaCelula(int valorCelula) {
        return spritesMapa.get(valorCelula);
    }
}
