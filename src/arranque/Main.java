package arranque;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import menu.JanelaControlo;
import petroleum.Camiao;
import petroleum.Central;
import petroleum.Posto;

/** Responsável por cria o ambiente de execução e criar a janela */
public class Main {


    public static void main(String[] args) {
        //DONE criar a central requerida
        //DONE criar os postos
        //DONE criar as listas postos


        Posto p1 = new Posto(1, "Minas Tirith", new Point(1400, 990), 2200, 10000, 40000);
        Posto p2 = new Posto(2, "Isengard", new Point(925, 710), 3200, 7000, 30000);
        Posto p3 = new Posto(3, "Dol Guldur", new Point(1235, 510), 2300, 17000, 30000);
        Posto p4 = new Posto(4, "Rivendell", new Point(890, 310), 800, 15000, 20000);
        Posto p5 = new Posto(5, "Hobbiton", new Point(455, 335), 1300, 25000, 30000);
        Posto p6 = new Posto(6, "Edoras", new Point(1035, 800), 1300, 20000, 25000);
        Posto p7 = new Posto(7, "Barad-dur", new Point(1690, 915), 2300, 5000, 35000);
        Posto p8 = new Posto(8, "Amon Sul", new Point(735, 310), 1800, 6000, 25000);
        Posto p9 = new Posto(9, "Erebor", new Point(1500, 180), 1750, 4000, 30000);
        Posto p10 = new Posto(10, "Moria", new Point(860, 500), 2100, 7000, 35000);
        Posto p11 = new Posto(11, "Cirith Ungol", new Point(1540, 980), 1200, 2000, 25000);
        Posto p12 = new Posto(12, "Emyn Muil", new Point(1380, 700), 1600, 18000, 25000);
        Posto p13 = new Posto(13, "Linhir", new Point(1110, 1180), 1900, 5000, 30000);
        Posto p14 = new Posto(14, "Dom Beornd", new Point(1090, 240), 1600, 5000, 30000);
        Posto p15 = new Posto(15, "Harlond", new Point(175, 400), 2000, 8000, 30000);
        Posto p16 = new Posto(16, "Oliveira do Hospital", new Point(160, 123), 1000, 10000, 30000);
        Posto p17 = new Posto(17, "Caldas da Rainha", new Point(214, 124), 1324, 12000, 25000);
        Posto p18 = new Posto(18, "Castelo Branco", new Point(241, 1200), 2100, 23000, 26000);
        Posto p19 = new Posto(19, "Alcains", new Point(512, 1294), 2130, 2134, 4212);
        Posto p20 = new Posto(20, "Idanha", new Point(873, 2394), 2312, 2345, 1234);

        //DONE criar os camiões
        //DONE criar lista camioes
        Camiao camiao1 = new Camiao("11-FG-33", 20000, 65, 20);
        Camiao camiao2 = new Camiao("22-DV-22", 30000, 50, 30);
        Camiao camiao3 = new Camiao("AA-34-BB", 35000, 70, 30);
        Camiao camiao4 = new Camiao("CF-65-FC", 40000, 45, 40);
        Camiao camiao5 = new Camiao("AZ-75-PO", 25000, 55, 50);


        List<Posto> postos = new ArrayList<>();
        postos.add(p1);
        postos.add(p2);
        postos.add(p3);
        postos.add(p4);
        postos.add(p5);
        postos.add(p6);
        postos.add(p7);
        postos.add(p8);
        postos.add(p9);
        postos.add(p10);
        postos.add(p11);
        postos.add(p12);
        postos.add(p13);
        postos.add(p14);
        postos.add(p15);
        postos.add(p16);
        postos.add(p17);
        postos.add(p18);
        postos.add(p19);
        postos.add(p20);

        List<Camiao> camioes = new ArrayList<>();

        camioes.add(camiao1);
        camioes.add(camiao2);
        camioes.add(camiao3);
        camioes.add(camiao4);
        camioes.add(camiao5);

        Central c= new Central( postos,camioes);
        c.setPosicao(new Point(505, 750));

        //DONE criar a apresentar a janela principal
        JanelaControlo postosFrame = new JanelaControlo(c);
        postosFrame.setVisible(true);
    }

}
