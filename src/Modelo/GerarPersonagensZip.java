package util;

import Modelo.ChipColetavel;
import Modelo.Fogo;
import Modelo.LaserBarrier;
import Modelo.BichinhoVaiVemHorizontal;
import Modelo.BichinhoVaiVemVertical;
import Modelo.Chaser;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

/**
 * Classe de utilitário que, ao ser executada, cria vários arquivos ZIP
 * (GZIP + ObjectOutputStream) em personagens_zips/, cada um contendo
 * um objeto Personagem diferente, pronto para drag&drop no jogo.
 */
public class GerarPersonagensZip {

    public static void main(String[] args) {
        try {
            // 1) ZIP para ChipColetavel
            ChipColetavel chip = new ChipColetavel("moeda.png");
            // Define alguma posição arbitrária (será reposicionada pelo jogo)
            chip.setPosicao(0, 0);
            serializarEmZip(chip, "personagens_zips/chip.zip");

            // 2) ZIP para Fogo
            Fogo fogo = new Fogo("fogo.png");
            fogo.setPosicao(0, 0);
            serializarEmZip(fogo, "personagens_zips/fogo.zip");

            // 3) ZIP para LaserBarrier
            LaserBarrier laser = new LaserBarrier("laser.png");
            laser.setPosicao(0, 0);
            serializarEmZip(laser, "personagens_zips/laser.zip");

            // 4) ZIP para BichinhoVaiVemHorizontal
            BichinhoVaiVemHorizontal bichH = new BichinhoVaiVemHorizontal("RoboPink.png");
            bichH.setPosicao(0, 0);
            serializarEmZip(bichH, "personagens_zips/bichinho_h.zip");

            // 5) ZIP para BichinhoVaiVemVertical
            BichinhoVaiVemVertical bichV = new BichinhoVaiVemVertical("Robo.png");
            bichV.setPosicao(0, 0);
            serializarEmZip(bichV, "personagens_zips/bichinho_v.zip");

            // 6) ZIP para Chaser
            Chaser chaser = new Chaser("Chaser.png");
            chaser.setPosicao(0, 0);
            serializarEmZip(chaser, "personagens_zips/chaser.zip");

            System.out.println("Todos os ZIPs foram gerados em personagens_zips/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Serializa o objeto Personagem dentro de um GZIP + ObjectOutputStream
     * e salva em nomeArquivo (por ex. "personagens_zips/chip.zip").
     */
    private static void serializarEmZip(Object obj, String nomeArquivo) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(nomeArquivo);
             GZIPOutputStream gzip = new GZIPOutputStream(fos);
             ObjectOutputStream out = new ObjectOutputStream(gzip)) {
            out.writeObject(obj);
        }
    }
}
