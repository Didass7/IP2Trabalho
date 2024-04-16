package petroleum;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/** Classe que representa a central de distribuição de combústivel.
 * Deve conter todas as informações, como os camiões, os postos, etc.
 * É ainda responsável por controlar todos os elemenots e operações
 */

public class Central {

		public static List<Posto> postos;
		public static List<Camiao>camioes;
		public Point armazenarCentral;
		private Point posicao;



	public Point getPosicao() {
		return this.posicao;
	}

	public void setPosicao(Point posicao) {
		this.posicao = posicao;
	}

	public static List<Posto> getPostos() {
		return postos;
	}

	public void setPostos(List<Posto> postos) {
		Central.postos = postos;
	}

	public Point getArmazenarCentral() {
		return armazenarCentral;
	}

	public void setArmazenarCentral(Point armazenarCentral) {
		this.armazenarCentral = armazenarCentral;
	}

	public static List<Camiao> getCamioes() {
		return camioes;
	}

	public void setCamioes(List<Camiao> camioes) {
		this.camioes = camioes;
	}

	public Central(List<Posto> postos, List<Camiao> camioes) {
		this.postos  = postos;
		this.camioes = camioes;
	}


// constantes para os erros que podem surgir udurante a realização das operações
	/** Correu tudo bem com a operação */
	public static final int ACEITE = 0;
	/** Usada quando se pretende adicionar um pedido a um posto, mas este não precisa */
	public static final int POSTO_NAO_PRECISA = ACEITE + 1;
	/** Indica que uma quantidade de litros excede a capacidade do posto de a armazenar */
	public static final int EXCEDE_CAPACIDADE_POSTO = POSTO_NAO_PRECISA + 1;
	/** Indica que uma quantidade de litros excede a capacidade do camião de a armazenar */
	public static final int EXCEDE_CAPACIDADE_CAMIAO = EXCEDE_CAPACIDADE_POSTO + 1;
	/** Indica que o camião não pode satisfazer um pedido, pois este iria exceder o tempo de turno */ 
	public static final int EXCEDE_TEMPO_TURNO = EXCEDE_CAPACIDADE_CAMIAO + 1;

	/** retorna o camião associado a uma matricula
	 * @param matricula matrícula a pesquisar matricula
	 * @return o camião com a matrícula indicada, ou null se não existir
	 */
	public Camiao getCamiao(String matricula ) {
		// FEITO! fazer este método
		if(camioes!=null) {
			for (Camiao camiao : camioes) {
				if (camiao.getMatricula().equals(matricula)) {
					return camiao;
				}
			}
		}
		return null;
	}


	/** retorna o posto que tem um dado id
	 * @param id id a pesquisar
	 * @return o posto com o id, ou null se não existir
	 */
	public Posto getPosto( int id ) {
		// FEITO! fazer este método
		if (postos!=null){
			for (Posto posto: postos){
				if (posto.getCodigoNumerico()==id){
					return posto;
				}
			}
		}
		return null;
	}


	/** processa uma entrega, isto é, associa o pedido ao camião respetivo
	 * @param posto posto onde entregar o combústivel
	 * @param litros litros a entregar
	 * @param camiao camião que irá fazer a entrega
	 * @return ACEITE, o pedido foi adicionado ao camião<br>
	 *         EXCEDE_CAPACIDADE_CAMIAO, se o número de litros for superior
	 *         ao que o camião tem disponível<br>
	 *         EXCEDE_TEMPO_TURNO, se o pedido implicar um tempo maior que um turno
	 *         POSTO_NAO_PRECISA, se o posto não necessita de ser abastecido
	 *         EXCEDE_CAPACIDADE_POSTO, se o posto não tem capacidade de armazenar os litros indicados      
	 */

	public int processarEntrega(Posto posto, int litros, Camiao camiao) {
		if (!posto.temPedidoPendente()){
			return POSTO_NAO_PRECISA;
		}

		if (litros > camiao.capacidadeLivre()){
			return EXCEDE_CAPACIDADE_CAMIAO;
		}

		// Verificar se a entrega excede o tempo de turno do camião
		if (camiao.tempoParaEntrega(posto) > Camiao.TEMPO_TURNO) {
			return EXCEDE_TEMPO_TURNO;
		}

		if (litros + posto.getQuantidadeAtual() > posto.getCapacidadeMaximaCombus()) {
			return EXCEDE_CAPACIDADE_POSTO;
		}

		// Adicionar o pedido ao camião
		camiao.podeFazerPedido(posto, litros);
		return ACEITE;
	}


	/* finaliza um turno, isto é, realiza os itinerários e
	 * processa os gastos dos postos 
	 */
	public void finalizarTurno() {
		realizarItinerarios();
		processarGastosPostos();
	}
	
	/** realiza os itinerários, isto é, faz os camiões
	 * transportar o combústivel para os postos adjudicados 
	 */
	private void realizarItinerarios() {
		for(Camiao camiao: camioes){
			camiao.transporta();
		}
		// DONE fazer este método
	}
	
	/** processa os gastos dos postos
	 */
	private void processarGastosPostos() {
		// DONE? fazer este método
		for (Posto posto : postos) {
			// Calcular o gasto com base na quantidade de combustível entregue
			double gasto = posto.getQuantidadeAtual() * posto.getGastoDiarioMedioCombus();

			// Processar o gasto
			posto.setGastoDiarioMedioCombus((int) gasto);
		}
	}
}

