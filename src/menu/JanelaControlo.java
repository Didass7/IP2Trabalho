package menu;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import petroleum.Camiao;
import petroleum.Central;
import petroleum.Itinerario;
import petroleum.Posto;

/** Representa a janela principal da aplicação onde serão
 * desenhados o mapa, com os postos, e a lista dos camiões
 */
@SuppressWarnings("serial")
public class JanelaControlo extends JFrame {

	private Central central;       // central do sistema
	private Camiao camiaoSel;      // camião atualmente selecionado


	// a lista dos marcadores
	private ArrayList<MarcadorPosto> marcadores = new ArrayList<MarcadorPosto>();
	// o marcador selecionado atualmente (null se não houver nenhum selecionado)
	private MarcadorPosto marcadorAtual;
	// a lista de camiões
	private JList<Camiao> listaCamioes;

	/**
	 * imagem com o mapa
	 */
	private static ImageIcon mapa = new ImageIcon("icones/mapa.jpg");
	private JPanel painelDesenho;  // onde se vai desenhar o mapa
	private Point antes;           // variável onde se clicou antes (para efeitos de mover o mapa)

	/**
	 * imagem para a refinaria
	 */
	private static ImageIcon refinaria = new ImageIcon("icones/refinaria.png");

	// cores e estilos de linha e efeito alfa para desenhar o itinerário
	private static final Color corItinerarioExt = new Color(255, 130, 130);
	private static final Color corItinerarioInt = new Color(255, 30, 30);
	private static final Stroke estiloLinhaExterior = new BasicStroke(5.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
	private static final Stroke estiloLinhaInterior = new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
	private static final Composite alphaMeio = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
	private static final Composite alphaFull = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);

	/**
	 * Cria a janela para uma dada central
	 *
	 * @param c a contral a ser gerida por esta janela
	 */
	public JanelaControlo(Central c) {
		central = c;

		// DONE criar os marcadores de posto, um para cada posto (ciclo?)
		for (Posto p : Central.getPostos()) {
			MarcadorPosto mp = new MarcadorPosto(p);
			marcadores.add(mp);
		}

		// DONE adicionar o marcador à lista e descomentar a linha seguinte
		//      (está em comentário para não dar NullPointerException) 


		inicializarInterface();
	}

	/**
	 * desenha os marcadores de postos
	 *
	 * @param g onde desenhar
	 */
	private void desenharPostos( Graphics2D g ) {
		// desenhar o mapa
		mapa.paintIcon(painelDesenho, g, 0, 0);

		// TODO ver a posição da central (não usar o valor direto, como está)
		Point pos = central.getPosicao();


		// desenhar a central
		refinaria.paintIcon(null, g, pos.x - refinaria.getIconWidth() / 2, pos.y - refinaria.getIconHeight());
		g.setColor(Color.red);
		g.fillOval(pos.x - 6, pos.y - 6, 12, 12);

		// desenhar os postos
		for (MarcadorPosto mp : marcadores)
			mp.desenhar(g);

		// desenhar o itinerário num ambiente especial (por causa das linhas)
		Graphics2D ge = (Graphics2D) g.create();

		// TODO ver o itinerário (não criar um novo como aqui)
		if (camiaoSel != null) {
			Itinerario iti = camiaoSel.getItinerarioAtual();

			if (iti != null) {
				// TODO para cada ponto desenhar uma linha entre esse e o anterior
				/// p1 é sempre o anterior, p2 é sempre o atual
				Point p1 = iti.getInicio();
				for (int i = 0; i < iti.contarParagens() - 1; i++) {

					//Posto posto = iti.getParagens().get(i); // TODO usar o for correto
					Point p2 = iti.paragem(i);        // TODO próximo ponto no itinerário
					desenhaLinha(ge, p1, p2);
					p1 = p2;
				}
				Point p2 = iti.getInicio(); // e acaba no início
				desenhaLinha(ge, p1, p2);
			}
		}

		ge.dispose();

	}

	/** desenha uma linha entre dois pontos
	 * @param ge onde desenhar
	 * @param p1 ponto inicial
	 * @param p2 ponto final
	 */
	private void desenhaLinha(Graphics2D ge, Point p1, Point p2) {
		// cria a linha e desenha em duas fases, uma mais grossa mas meio transparente
		// e outra mais fina mas opaca, para dar um efeito "bonito"
		Line2D.Double line = new Line2D.Double( p1, p2 );
		ge.setColor( corItinerarioExt );
		ge.setComposite( alphaMeio );
		ge.setStroke( estiloLinhaExterior );											
		ge.draw( line );
		ge.setColor( corItinerarioInt );
		ge.setComposite( alphaFull );
		ge.setStroke( estiloLinhaInterior );										
		ge.draw( line );
	}
	
	/** método chamado quando o utilziador seleciona outro camião
	 * @param c o camião selecionado
	 */
	private void camiaoSelecionado(Camiao c) {
		camiaoSel = c;
		painelDesenho.repaint();
	}
	
	/** método chamado quado o utilizador preeeiona o botão de mudar o turno
	 */
	private void proximoTurno() {
		central.finalizarTurno();
		for( MarcadorPosto mp : marcadores )
			mp.setEscolhido( false );
		repaint();
	}

	/** método chamado quando o utilizador pressiona o botão esquerdo no mapa
	 * @param e evento do rato
	 */
	private void botaoEsquerdoPremido( MouseEvent e ) {
		Point pm = e.getPoint(); // onde clicou
		MarcadorPosto mp = getMarcador( pm ); // qual o marcador
		
		antes = null;
		// se não ciclou num marcador é porque quer arrastar o mapa
		if( mp ==null ) {
			antes = e.getLocationOnScreen();
			return;
		}
		// se já escolheu o marcador não volta a processar
		if( mp.jaFoiEscolhido() )
			return;
		
		// escolheu um marcador válido, pedir a quantidade de litros
		JPanel p = new JPanel(); // para centrar a janela de pedir litros em cima do marcador
		painelDesenho.add( p );
		p.setLocation( new Point( (int)mp.getArea().getCenterX(), (int)mp.getArea().getCenterY() ) );
		String res = JOptionPane.showInputDialog( p , "Quantos litros?", 10000 );
		painelDesenho.remove( p );
		// se não escreveu nada ou cancelou o pedido
		if( res == null || res.isBlank() )
			return;
		
		// NOTA: NÃO é preciso verificar se escreveu alguma coisa sem ser números (dá erro, mas ...)
		int litros = Integer.parseInt( res );
		
		// pedir à central para processar o pedido
		int r = central.processarEntrega( mp.getPosto(), litros , camiaoSel );
		String msg = null;
		switch( r ){
		case Central.EXCEDE_CAPACIDADE_CAMIAO: msg = "Camião não tem capacidade"; break;
		case Central.EXCEDE_CAPACIDADE_POSTO: msg = "Posto não tem capacidade"; break;
		case Central.POSTO_NAO_PRECISA: msg = "Posto não tem necessidade"; break;
		case Central.EXCEDE_TEMPO_TURNO: msg = "Turno longo demais"; break;
		}
		// se deu erro apresentar o erro
		if( msg != null ) {
			JOptionPane.showMessageDialog(p, msg);
		}
		else {
			// se não seu erro é porque o marcador foi escolhido
			mp.setEscolhido( true );
			listaCamioes.repaint();
		}
	}

	/** retona o marcador que está numa dada posição do mapa
	 * @param p a posição
	 * @return o marcador que está nessa posição, ou null caso não haja
	 */
	public MarcadorPosto getMarcador( Point p ) {
		for( MarcadorPosto mp : marcadores )
			if( mp.getArea().contains(p) )
				return mp;
		return null;
	}

	/** Método que inicializa a janela */
	private void inicializarInterface() {
		setSize( 1000, 700 );
		setMaximizedBounds( null );
		setTitle( "ESTPetroleum" );
		setExtendedState( getExtendedState() | JFrame.MAXIMIZED_BOTH);
		add( getPainelDesenho(), BorderLayout.CENTER );
		add( getPainelComandos(), BorderLayout.WEST );
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
	}
	
	/** vai criar o painel de comandos (botão próximo turno e lista de camiões)*/
	private JPanel getPainelComandos() {
		JPanel panel = new JPanel( new BorderLayout() );
		DefaultListModel<Camiao> modelo = new DefaultListModel<Camiao>();
		
		// criar a lista de camiões
		// DONE! : Adicionar cada camião à lista (ciclo?)
		for(Camiao c: Central.camioes) {
			modelo.addElement(c);
		}
		
		listaCamioes = new JList<Camiao>( modelo);
		listaCamioes.setPreferredSize( new Dimension(140, 50) );
		listaCamioes.setCellRenderer( new RendererCamiao() );
		listaCamioes.addListSelectionListener( new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				camiaoSelecionado( listaCamioes.getSelectedValue() );
			}

		});
		JButton nextBt = new JButton("<html><center>Próximo<br>Turno</center></html>");
		nextBt.addActionListener( e -> proximoTurno() );
		panel.add( nextBt, BorderLayout.NORTH );

		panel.add( new JScrollPane( listaCamioes  ), BorderLayout.CENTER );
		listaCamioes.setSelectedIndex( 0 );
		
		return panel;
	}
	
	/** vai criar o painel de desenho (onde aparece o mapa) */
	private JScrollPane getPainelDesenho() {
		painelDesenho = new JPanel( ){
			protected void paintComponent(Graphics g) {
				super.paintComponent( g );
				desenharPostos( (Graphics2D)g );
			}
		};
		painelDesenho.setLayout( null );
		painelDesenho.setBackground( new Color( 0, 120, 0 ) );
		Dimension dim = new Dimension( mapa.getIconWidth(), mapa.getIconHeight() );
		painelDesenho.setPreferredSize( dim );
		final JScrollPane scroller = new JScrollPane( painelDesenho ); 
		MouseAdapter ma = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if( e.getButton() == MouseEvent.BUTTON1 ) {
					botaoEsquerdoPremido(e);
					painelDesenho.repaint();
					listaCamioes.repaint();
				}
			}
			
			@Override
			public void mouseMoved(MouseEvent e) {
				Point pm = e.getPoint();
				MarcadorPosto mp = getMarcador( pm );
				if( mp !=null ) {
					mp.setExpandido( true );
					marcadorAtual = mp;
					painelDesenho.repaint();
				}
				else if( marcadorAtual != null ) {
					marcadorAtual.setExpandido( false );
					marcadorAtual = null;
					painelDesenho.repaint();
				}
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if( antes == null) return;
				
				Point agora = e.getLocationOnScreen();
				int dx = agora.x - antes.x;
				int dy = agora.y - antes.y;
				int px = scroller.getHorizontalScrollBar().getValue() - dx;
				int py = scroller.getVerticalScrollBar().getValue() - dy;
				scroller.getHorizontalScrollBar().setValue( px );
				scroller.getVerticalScrollBar().setValue( py );				
				antes = agora;
			}
		};
		painelDesenho.addMouseMotionListener( ma );
		painelDesenho.addMouseListener( ma );
		return scroller;	}
}
