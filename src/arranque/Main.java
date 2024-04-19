package arranque;

import java.awt.Point;
import menu.JanelaControlo;
import petroleum.Camiao;
import petroleum.Central;
import petroleum.Posto;


/** Responsável por cria o ambiente de execução e criar a janela */
public class Main {

    public static void main(String[] args) {

        // Criação dos postos
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
        Posto p16 = new Posto(16, "Olive from the Hospital", new Point(1600, 123), 5000, 10000, 40000);
        Posto p17 = new Posto(17, "Queen of the Caldas", new Point(700, 124), 3000, 12000, 39000);
        Posto p18 = new Posto(18, "Castle White", new Point(1753, 1200), 2100, 2300, 26000);
        Posto p19 = new Posto(19, "Alcães", new Point(1100, 1294), 2130, 25000, 15000);
        Posto p20 = new Posto(20, "Burba", new Point(873, 210), 2200, 23000, 35000);

        // Criação da central e definição das suas coordenadas
        Central c = new Central(new Point(505, 750));

        // Criação dos camiões
        Camiao camiao1 = new Camiao("11-FG-33", 20000, 65, 20, c);
        Camiao camiao2 = new Camiao("22-DV-22", 30000, 50, 30, c);
        Camiao camiao3 = new Camiao("AA-34-BB", 35000, 70, 30, c);
        Camiao camiao4 = new Camiao("CF-65-FC", 40000, 45, 40, c);
        Camiao camiao5 = new Camiao("AM-45-SO", 25000, 55, 50, c);

        // Adição dos postos e camiões à central
        c.addPosto(p1);
        c.addPosto(p2);
        c.addPosto(p3);
        c.addPosto(p4);
        c.addPosto(p5);
        c.addPosto(p6);
        c.addPosto(p7);
        c.addPosto(p8);
        c.addPosto(p9);
        c.addPosto(p10);
        c.addPosto(p11);
        c.addPosto(p12);
        c.addPosto(p13);
        c.addPosto(p14);
        c.addPosto(p15);
        c.addPosto(p16);
        c.addPosto(p17);
        c.addPosto(p18);
        c.addPosto(p19);
        c.addPosto(p20);
        c.addCamiao(camiao1);
        c.addCamiao(camiao2);
        c.addCamiao(camiao3);
        c.addCamiao(camiao4);
        c.addCamiao(camiao5);

        //criar a apresentar a janela principal
        JanelaControlo postosFrame = new JanelaControlo(c);
        postosFrame.setVisible(true);
    }

}
