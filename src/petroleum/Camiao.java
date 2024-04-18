package petroleum;
import java.awt.Point;
import menu.Mapa;
import petroleum.Camiao;

/** Esta classe representa um camião, que, neste contexto, se refere à capacidade de transportar
 * combústivel. Tem para isso um limite máximo de combústivel que pode transportar e a
 * quantidade de combústvel que transporta num dado momento.
 * Cada camião desloca-se a uma velocidade, que para simplificar, iremos contabilizar como a velocidade média.
 * Um camiºão segue um itinerário, sendo que deve terminar o itinerário dentro 
 * do limite de tempo de um turno de 14 horas )2 condutores com turnos de 7 horas cada).
 */
public class Camiao {

	private int capacidadeLitros;
	private int quantidadeCombusAtual;
	private int velocidadeMediaKm;
	private int debitoLs;
	private String matricula;
	private Itinerario itinerario;
	private Point localizacao;


	public int getCapacidadeLitros() {
		return capacidadeLitros;
	}

	public int getQuantidadeCombusAtual() {
		return quantidadeCombusAtual;
	}

	public int getVelocidadeMediaKm() {
		return velocidadeMediaKm;
	}

	public int getDebitoLs() {
		return debitoLs;
	}

	public String getMatricula() {
		return matricula;
	}

	public Itinerario getItinerario() {
		return itinerario;
	}

	public void setItinerario(Itinerario itinerario) {
		this.itinerario = itinerario;
	}

	public void setCapacidadeLitros(int capacidadeLitros) {
		this.capacidadeLitros = capacidadeLitros;
	}

	public void setQuantidadeCombusAtual(int quantidadeCombusAtual) {
		this.quantidadeCombusAtual = quantidadeCombusAtual;
	}

	public void setVelocidadeMediaKm(int velocidadeMediaKm) {
		this.velocidadeMediaKm = velocidadeMediaKm;
	}

	public void setDebitoLs(int debitoLs) {
		this.debitoLs = debitoLs;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}



	public Camiao(String matricula, int capacidadeLitros, int velocidadeMediaKm, int debitoLs) {

		this.matricula = matricula;
		this.capacidadeLitros = capacidadeLitros;
		this.velocidadeMediaKm = velocidadeMediaKm;
		this.debitoLs = debitoLs;

		this.itinerario= new Itinerario( new Point(505,750) );

	}



	/** o tempo máximo de um turno, que são as 14 horas
	 * (2 condutores), dadas em segundos */
	public static final int TEMPO_TURNO = 14 * 3600;

	/** indica se o Camião pode acrescentar o seguinte pedido ao seu itinerário
	 * @param posto posto que pede o abastecimento
	 * @param litros litros que o posto pretende
	 * @return ACEITE, se aceitar o pedido <br>
	 *         EXCEDE_CAPACIDADE_CAMIAO, se o número de litros for superior
	 *         ao que o camião tem disponível<br>
	 *         EXCEDE_TEMPO_TURNO, se o pedido implicar um tempo maior que um turno
	 *
	 */
	public int podeFazerPedido(Posto posto, int litros) {
		if (litros > this.capacidadeLivre()){
			return Central.EXCEDE_CAPACIDADE_CAMIAO;
		}
		//variavel para calcular o tempo necessario para atender o pedido
		double tempoParaPedido= tempoDespejar(litros)+ tempoPercorrer(getItinerario().getInicio(), posto.getLocalizacao());
		//  implementar este método
		if (tempoParaPedido > TEMPO_TURNO) {
			return Central.EXCEDE_TEMPO_TURNO;
		}
		return Central.ACEITE;
	}

	/** adiciona um posto ao itinerário do camião, se for possível.
	 * @param p posto que pede o abastecimento
	 * @param litros litros que o posto pretende
	 * @return ACEITE, se aceitar o pedido <br>
	 *         EXCEDE_CAPACIDADE_CAMIAO, se o número de litros for superior
	 *         ao que o camião tem disponível<br>
	 *         EXCEDE_TEMPO_TURNO, se o pedido implicar um tempo maior que um turno
	 */
	public int addPosto( Posto p, int litros ) {
		// FEITO! fazer este método
		int pedido = podeFazerPedido(p, litros);
		if (pedido == Central.ACEITE) {
			itinerario.adicionaParagem(p, litros);
		}

		return pedido;
	}

	/** retorna o tempo, em segundos, que demora a fazer o itinerário
	 * @return o tempo, em segundos, que demora a fazer o itinerário
	 */
	public double duracaoTurno( ) {
		double tempoTotal=0;
		Point localizacaoAtual= getItinerario().getPontoPartida();

		for (Paragem p: getItinerario().getParagens() ){
			tempoTotal+=tempoPercorrer(localizacaoAtual,p.getPosto().getLocalizacao())+tempoDespejar(p.getLitrosParaDepositar());
			localizacaoAtual=p.getPosto().getLocalizacao();
		}
		// DONE fazer este método
		return tempoTotal;
	}

	/** retorna o tempo, em segundos, que demora a fazer o itinerário
	 * acrescentando um posto extra
	 * @param extra o posto extra a processar
	 * @param nLitros oslitros que o posto extra precisa
	 * @return tempo, em segundos, que demora a fazer o itinerário mais o posto extra
	 */

	//Refazer o metodo do turno extra
	public double duracaoTurnoExtra( Posto extra, int nLitros ) {

		return 0;
	}

	/** Efetua o transporte e transferência de combustível
	 * para todos os postos no itinerário
	 */
	public void transporta( ){
		for (Paragem paragem : itinerario.getParagens()) {
			// Verificar se o camião tem combustível suficiente para abastecer o posto
			if (quantidadeCombusAtual >= paragem.getLitrosParaDepositar()) {
				// Transferir combustível do camião para o posto
				quantidadeCombusAtual -= paragem.getLitrosParaDepositar();
				paragem.setLitrosParaDepositar(paragem.getLitrosParaDepositar());
			} else {
				// Se o camião não tem combustível suficiente, transferir
				// odo o combustível disponível
				paragem.setLitrosParaDepositar(paragem.getLitrosParaDepositar() - quantidadeCombusAtual);

			}

			// Atualizar a localização do camião para a localização do posto
			localizacao = paragem.getPosto().getLocalizacao();


			// Se o camião está vazio, não há necessidade de continuar o itinerário
			if (quantidadeCombusAtual == 0) {
				break;
			}
		}
		// DONE fazer este método
	}

	/** retorna o tempo, em segundos, que demora a percorrer o caminho entre
	 * dois pontos.
	 * @param ini o ponto inical
	 * @param fim o ponto final
	 * @return o tempo que demora a ir de ini a fim.
	 */
	private double tempoPercorrer( Point ini, Point fim ){
		// FEITO! terminar este método (distância / velocidade)

		double distancia = Mapa.distancia(ini, fim); // Calcula a distância entre a localização atual e o destino
		double velocidadeMediaKmPorSegundo = getVelocidadeMediaKm() / 3600.0;
		double tempoViagem = distancia / velocidadeMediaKmPorSegundo; // Calcula o tempo de viagem com base na distância e na velocidade média
		return tempoViagem;

	}

	/** retorna quanto tempo demora, em segundos, a transferir a quantidade de liquido
	 * @param nLitros a quantidade de liquido a transferir
	 * @return o tempo que demora, em segundos, a transferir os nLitros
	 */
	private double tempoDespejar( int nLitros ){
		// FEITO! fazer este método

		if (nLitros<=0){
			return -1;
		}else {
			return (double) nLitros / debitoLs;
		}
		//tempo que demmora a despejar em segundos;
	}

	/** retorna a percentagem de ocupação do camião, entre 0 (0%) e 1 (100%)
	 * @return a percentagem de ocupação
	 */
	public float percentagemOcupacao() {
		// FEITO! fazer este método
		return (float) quantidadeCombusAtual / capacidadeLitros;
	}

	/** retorna a capacidade livre, isto é, quantos litros ainda pode
	 * adicionar à carga
	 * @return a capacidade livre, em litros
	 */
	public  int capacidadeLivre() {
		// FEITO! fazer este método
		return capacidadeLitros - quantidadeCombusAtual;
	}

	/** retorna o tempo que demora a entregar a carga a um posto
	 * @param //posto o posto a entregar a carga
	 * @return o tempo que demora a entregar a carga a um posto
	 */

	public Point getLocalizacao() {
		return this.localizacao;
	}
}