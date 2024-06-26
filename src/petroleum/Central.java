package petroleum;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Classe que representa a central de distribuição de combústivel.
 * Deve conter todas as informações, como os camiões, os postos, etc.
 * É ainda responsável por controlar todos os elemenots e operações
 */

public class Central {

		private List<Posto> postos = new ArrayList<Posto>(); //lista de postos
		private List<Camiao>camioes = new ArrayList<Camiao>(); //lista de camiões
		private Point posicao; //posição da central

	// construtor
	public Central( Point posicao) {
		this.posicao = posicao;
	}

	// métodos de acesso
	public Point getPosicao() {
		return posicao;
	}

	public void addCamiao(Camiao camiao) {
		this.camioes.add(camiao);
	}

	public void addPosto(Posto posto) {
		this.postos.add(posto);
	}

	public  List<Posto> getPostos() {
		return Collections.unmodifiableList( postos );
	}

	public List<Camiao> getCamioes() {
		 return Collections.unmodifiableList( camioes );
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
		if(camioes!=null) {
			//procura o camião com a matrícula indicada e se encontrar retorna-o
			for (Camiao camiao : camioes) {
				if (camiao.getMatricula().equals(matricula)) {
					//
					return camiao;
				}
			}
		}
		return null;
	}


	/** retorna o posto que tem um dado id
	 * @param id id a pesquisar
	 * @return o posto com o id, ou null se não existir */
	public Posto getPosto( int id ) {
		//procura o posto com o id indicado e se encontrar retorna-o
			for (Posto posto: postos){
				if (posto.getCodigoNumerico()==id){
					return posto;
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
	 *         EXCEDE_CAPACIDADE_POSTO, se o posto não tem capacidade de armazenar os litros indicados */

	public int processarEntrega(Posto posto, int litros, Camiao camiao) {

		//verifica se o camião tem capacidade para armazenar os litros indicados
		if (litros > camiao.capacidadeLivre()) {
			return EXCEDE_CAPACIDADE_CAMIAO;
		}
		//verifica se o pedido implicar um tempo maior que um turno
		if (camiao.duracaoTurnoExtra(posto, litros) > Camiao.TEMPO_TURNO) {
			return EXCEDE_TEMPO_TURNO;
		}
		//verifica se o posto não necessita de ser abastecido
		if (posto.percentagemOcupacao() >= Posto.OCUPACAO_SUFICIENTE) {
			return POSTO_NAO_PRECISA;
		}
		//verifica se o posto tem capacidade para armazenar os litros indicados
		if ((posto.getQuantidadeAtual() + litros) > posto.getCapacidadeMaximaCombus()) {
			return EXCEDE_CAPACIDADE_POSTO;
		}
		//adiciona o pedido ao camião
		camiao.addPosto(posto, litros);
		return ACEITE;
	}

	/** finaliza um turno, isto é, realiza os itinerários e
	 * processa os gastos dos postos */
	public void finalizarTurno() {
		realizarItinerarios();
		processarGastosPostos();
	}
	
	/** realiza os itinerários, isto é, faz os camiões
	 * transportar o combústivel para os postos adjudicados */
	private void realizarItinerarios() {
		for(Camiao camiao: camioes){
			//faz o camião transportar o combústivel para os postos adjudicados
			camiao.transporta();
		}
	}
	
	/** processa os gastos dos postos */
	private void processarGastosPostos() {

		for (Posto posto : postos) {
			//para cada posto...
			int quantidadeCombustivelAtual = posto.getQuantidadeAtual();
			//variavel que guarda a quantidade de combustível atual do posto
			quantidadeCombustivelAtual -= posto.getGastoDiarioMedioCombus();
			//...diminui a quantidade de combustível atual do posto pelo gasto médio diário


			if (quantidadeCombustivelAtual < 0) {
				//se a quantidade de combustível for negativa, coloca-a a zero
				quantidadeCombustivelAtual = 0;
			}


			posto.setQuantidadeAtual(quantidadeCombustivelAtual);
			//atualiza a quantidade de combustível atual do posto
		}
	}
}

