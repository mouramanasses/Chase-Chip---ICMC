package Controler;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Contém dois métodos estáticos: salvar e carregar objetos Serializable,
 * usando compressão GZIP para reduzir o tamanho do arquivo.
 */
public class Serializador {

    /**
     * Grava qualquer objeto implementando Serializable dentro de um arquivo comprimido por GZIP.
     * @param objeto      Qualquer instância de uma classe que implemente Serializable.
     * @param nomeArquivo Nome do arquivo (ex.: "savegame.dat").
     */
    public static void salvarEstado(Object objeto, String nomeArquivo) {
        try ( FileOutputStream      fos = new FileOutputStream(nomeArquivo);
              GZIPOutputStream      gzip = new GZIPOutputStream(fos);
              ObjectOutputStream    oos = new ObjectOutputStream(gzip) )
        {
            oos.writeObject(objeto);
            System.out.println("Serializador: salvou em '" + nomeArquivo + "'");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Serializador: erro ao salvar em '" + nomeArquivo + "' → " + ex.getMessage());
        }
    }

    /**
     * Lê (desserializa) um objeto previamente gravado em um arquivo GZIP+ObjectStream.
     * @param nomeArquivo Nome do arquivo (ex.: "savegame.dat").
     * @return O objeto desserializado, ou null se ocorrer erro.
     */
    public static Object carregarEstado(String nomeArquivo) {
        try ( FileInputStream      fis = new FileInputStream(nomeArquivo);
              GZIPInputStream      gzip = new GZIPInputStream(fis);
              ObjectInputStream    ois = new ObjectInputStream(gzip) )
        {
            Object obj = ois.readObject();
            System.out.println("Serializador: carregou de '" + nomeArquivo + "'");
            return obj;
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            System.err.println("Serializador: erro ao carregar de '" + nomeArquivo + "' → " + ex.getMessage());
            return null;
        }
    }
}
