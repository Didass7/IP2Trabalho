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
		// TODO ver a posição do posto (não criar um novo como aqui)
		Point pos = new Point();



		int offsetX = postoVerde.getIconWidth()/2;
		int offsetY = postoVerde.getIconHeight();
		area = new Rectangle( pos.x-offsetX, pos.y-offsetY, postoVerde.getIconWidth(), postoVerde.getIconHeight() );
	}

	/** desenha o marcador no ambiente gráfico
	 * @param g ambiente onde desenhar o posto
	 */
	public void desenhar( Graphics2D g ) {
		// se for preciso mostrar a etiqueta
		if( expandido ) {
			// TODO colocar a informação correta nas seguintes variáveis
			String nome = "Minas Tirith";
			int id = 1;
			int capacidade = 40000;
			int quantidade = 10000;
			int gastoMedio = 2200;

			// desenhar a informação na etiqueta
			etiqueta.paintIcon(null, g, (int)area.getCenterX(), area.y);
			int statusX = area.x + area.width + 3;			
			g.setColor( Color.BLACK );
			g.setFont(fonte);
			g.drawString( nome + " (" + id + ")", statusX, area.y+13 );
			statusX += 32;
			g.drawString( capacidade + "", statusX, area.y+28 );
			g.drawString( quantidade + "", statusX, area.y+41 );
			statusX += 72;
			g.drawString( gastoMedio +"", statusX, area.y+28 );
		}
		// se já foi escolhido desenhar o icone respetivo
		if( escolhido )
			destino.paintIcon(null, g, area.x, area.y-destino.getIconHeight());
		
		// TODO colocar a informação na variável
		float percentagemOcupacao = 0.5f;
		ImageIcon icon;
		// ver qual o icon a usar, de acordo com a percentagem de ocupação
		if( percentagemOcupacao > 0.45f )
			icon = postoVerde;
		else if( percentagemOcupacao > 0.25f )
			icon = postoAmarelo;
		else
			icon = postoVermelho;
		icon.paintIcon(null, g, area.x, area.y);
		
		// TODO ver se posto tem pedido pendente
		if( false )
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
