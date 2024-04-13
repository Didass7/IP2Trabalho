package arranque;
import java.awt.Point;

import menu.Mapa;
import menu.JanelaControlo;
import petroleum.Camiao;
import petroleum.Central;
import petroleum.Posto;

/** Responsável por cria o ambiente de execução e criar a janela */
public class Main {

	public static void main(String[] args) {
		// TODO criar a central requerida
		Central c = null;
		
		// criar os postos

		Posto p1= new Posto(1,"Minas Tirith", new Point(1400,990),2200, 10000 ,40000);
		Posto p2= new Posto(2, "Isengard", "925, 710", 3200, 7000, 30000);
		Posto p3= new Posto(3 ,"Dol Guldur","1235,510" ,2300, 17000, 30000);
		Posto p4= new Posto(4 ,"Rivendell" ,"890,310" ,800, 15000, 20000);
		Posto p5= new Posto(5 ,"Hobbiton", "455,335" ,1300, 25000, 30000);
		Posto p6= new Posto(6 ,"Edoras" ,"1035,800" ,1300, 20000, 25000);
		Posto p7= new Posto(7, "Barad-dur", "1690,915", 2300, 5000 ,35000);
		Posto p8= new Posto(8 ,"Amon Sul", "735,310" ,1800 ,6000, 25000);
		Posto p9= new Posto(9 ,"Erebor", "1500,180" ,1750, 4000, 30000);
		Posto p10= new Posto(10, "Moria" ,"860,500", 2100, 7000, 35000);
		Posto p11= new Posto(11 ,"Cirith Ungol" ,"1540,980", 1200, 2000, 25000);
		Posto p12= new Posto(12 ,"Emyn Muil", "1380,700", 1600, 18000, 25000);
		Posto p13= new Posto(13 ,"Linhir" ,"1110,1180" ,1900, 5000, 30000);
		Posto p14= new Posto(14 ,"Dom Beornd", "1090,240", 1600, 5000, 30000);
		Posto p15= new Posto(15 ,"Harlond" ,"175,400", 2000, 8000, 30000);
		Posto p16= new Posto(16,"Oliveira do Hospital","154,241",1000,10000,30000);
		Posto p17= new Posto(17,"Caldas da Rainha", "162,312",1324, 12000, 25000);
		Posto p18= new Posto(18,"Castelo Branco", "302,210", 2100, 23000, 26000);
		Posto p19= new Posto(19,"Benfica", "402,213", 2130, 2134,4212);
		Posto p20= new Posto(20,"zbording", "312,412", 2312, 2345, 1234);

		//  criar os camiões

		Camiao camiao1=new Camiao("11-FG-33" ,20000 ,65 ,20);
		Camiao camiao2=new Camiao("22-DV-22" ,30000, 50, 30);
		Camiao camiao3=new Camiao("AA-34-BB", 35000, 70, 30);
		Camiao camiao4=new Camiao("CF-65-FC" ,40000 ,45 ,40);
		Camiao camiao5=new Camiao("AZ-75-PO",25000,55,50);

		
		//TODO criar a apresentar a janela principal
		JanelaControlo postosFrame = new JanelaControlo( c );
		postosFrame.setVisible( true );
	}

}
