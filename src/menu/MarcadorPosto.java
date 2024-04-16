package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import javax.swing.ImageIcon;

import petroleum.Posto;

/** Representa um marcador no mapa que representa um posto.
 * Foenece indicações visuais de como está o posto, se tem algum pedido,
 * se já está inserido num itinerário, entre outros
 */
public class MarcadorPosto {
	/* icones para os vários estados do posto */
	/** imagem para o posto com muito combustivel */
	private static ImageIcon postoVerde = new ImageIcon( "icones/posto_verde.png" );
	/** imagem para o posto com um nível aceitável de combústivel */
	private static ImageIcon postoAmarelo = new ImageIcon( "icones/posto_amarelo.png" );
	/** imagem para o posto com um nível baixo de combústivel */
	private static ImageIcon postoVermelho = new ImageIcon( "icones/posto_vermelho.png" );
	/** imagem para o fundo da etiqueta com o estdo do posto */
	private static ImageIcon etiqueta = new ImageIcon( "icones/etiqueta.png" );
	/** imagem que indica que o posto já é um destino num itinerário */
	private static ImageIcon destino = new ImageIcon( "icones/camiao.png" );
	/** imagem que indica que o posto tem um pedido pendente */
	private static ImageIcon pedindo = new ImageIcon( "icones/pedindo.png" );
	/** fonte de texto a usar nas informações */
	private static Font fonte = new Font( "Arial", Font.BOLD, 12 );

	private Posto posto;     // qual o posto associado a este marcador 
	private Rectangle area;  // área ocupada pelo marcador
	private boolean escolhido = false; // se o amrcador já foi escolhido
	private boolean expandido = false; // se deve apresentar a etiqueta ou só o marcador

	/** Cria um marcador visual para um dado posto 
	 * @param posto o posto a ser visualizado pelo marcador
	 */
	public MarcadorPosto(Posto posto) {
		this.posto = posto;
		Point pos = posto.getLocalizacao();

		// DONE ver a posição do posto (não criar um novo como aqui)

		Point p1=new Point(1400,990);
		Point p2=new Point(925,710);
		Point p3=new Point(1235,510);
		Point p4=new Point(890,310);
		Point p5=new Point(455,335);
		Point p6=new Point(1035,800);
		Point p7=new Point(1690,915);
		Point p8=new Point(735,310 );
		Point p9=new Point(1500,180);
		Point p10=new Point(860,500);
		Point p11=new Point(1540,980);
		Point p12=new Point(1380,700);
		Point p13=new Point(1110,1180);
		Point p14=new Point(1090,240);
		Point p15=new Point(175,400);
		Point p16=new Point(160,123);
		Point p17=new Point(214,124);
		Point p18=new Point(241,1200);
		Point p19=new Point(512,1294);
		Point p20=new Point(873,2394);




		int offsetX = postoVerde.getIconWidth()/2;
		int offsetY = postoVerde.getIconHeight();
		area = new Rectangle( pos.x-offsetX, pos.y-offsetY, postoVerde.getIconWidth(), postoVerde.getIconHeight());
	}

	/** desenha o marcador no ambiente gráfico
	 * @param g ambiente onde desenhar o posto
	 */
	public void desenhar( Graphics2D g ) {
		// se for preciso mostrar a etiqueta
		if (expandido) {
			// Feito colocar a informação correta nas seguintes variáveis

			String nome = posto.getNomeDoPosto();
			int id = posto.getCodigoNumerico();
			int capacidade = posto.getCapacidadeMaximaCombus();
			int quantidade = posto.getQuantidadeAtual();
			int gastoMedio = posto.getGastoDiarioMedioCombus();

			// desenhar a informação na etiqueta
			etiqueta.paintIcon(null, g, (int) area.getCenterX(), area.y);
			int statusX = area.x + area.width + 3;
			g.setColor(Color.BLACK);
			g.setFont(fonte);
			g.drawString(nome + " (" + id + ")", statusX, area.y + 13);
			statusX += 32;
			g.drawString(capacidade + "", statusX, area.y + 28);
			g.drawString(quantidade + "", statusX, area.y + 41);
			statusX += 72;
			g.drawString(gastoMedio + "", statusX, area.y + 28);
		}
		// se já foi escolhido desenhar o icone respetivo
		if (escolhido)
			destino.paintIcon(null, g, area.x, area.y - destino.getIconHeight());

		// DONE! colocar a informação na variável
		float percentagemOcupacao = posto.percentagemOcupacao();
		ImageIcon icon;
		// ver qual o icon a usar, de acordo com a percentagem de ocupação
		if (percentagemOcupacao > 0.45f) {
			icon = postoVerde;
		} else if (percentagemOcupacao > 0.25f) {
			icon = postoAmarelo;
		} else {
			icon = postoVermelho;
		}

		icon.paintIcon(null, g, area.x, area.y);
		
		// FEITO! ver se posto tem pedido pendente
		if(posto.temPedidoPendente() )
			pedindo.paintIcon(null, g, area.x, area.y);
	}
	
	/** define se o marcador deve ser visto expandido ou compacto 
	 * @param expandido true para expandir o marcador
	 */
	public void setExpandido(boolean expandido) {
		this.expandido = expandido;
	}
	
	/** indica se o marcador está expandido
	 * @return true, se estiver expandido
	 */
	public boolean estaExpandido() {
		return expandido;
	}
	
	/** retorna a área ocupada pelo marcador
	 * @return a área ocupada pelo marcador
	 */
	public Rectangle getArea() {
		return area;
	}
	
	/** indca qual o posto associado ao marcador
	 * @return o posto associado ao marcador
	 */
	public Posto getPosto() {
		return posto;
	}

	/** Escolhe o marcador
	 * @param escolhido true, se é para escolher, false para não escolher
	 */
	public void setEscolhido(boolean escolhido) {
		this.escolhido = escolhido;
	}
	
	/** Indica se este marcador já foi escolhido neste turno
	 * @return true se já foi escolhido, false caso contrário
	 */
	public boolean jaFoiEscolhido() {
		return escolhido;
	}	
}
