package petroleum;

/** Classe que representa a central de distribuição de combústivel.
 * Deve conter todas as informações, como os camiões, os postos, etc.
 * É ainda responsável por controlar todos os elemenots e operações
 */
public class Central {


	private List<Camiao> listarCamioes;
	private List<Posto> listarPostos;
	private int posicaoX;
	private int posicaoY;

	public Central(int posicaoX,int posicaoY){

		this.listarCamioes = new ArrayList<>();
		this.listarPostos = new ArrayList<>();
		this.posicaoX=posicaoX;
		this.posicaoY=posicaoY;
	}

	public void adicionarCamiao(Camiao camiao) {

		// Verifica se a matrícula do camião já existe na lista de camiões
		for (Camiao c : listarCamioes) {
			// Se encontrarmos uma matrícula igual, não fazemos nada e retornamos
			if (c.getMatricula().equals(camiao.getMatricula())) {
				return;
			}
		}
		// Se a matrícula não existir na lista, adicionamos o camião à lista
		listarCamioes.add(camiao);

	}

	public void adicionarPosto(Posto posto){
		// Verifica se o posto já existe na lista
		if (!listarPostos.contains(posto)) {
			listarPostos.add(posto);
		} else {
			System.out.println("O posto já existe na lista.");
		}
	}

	public int getPosicaoX() {
		return posicaoX;
	}

	public void setPosicaoX(int posicaoX) {
		this.posicaoX = posicaoX;
	}

	public int getPosicaoY() {
		return posicaoY;
	}

	public void setPosicaoY(int posicaoY) {
		this.posicaoY = posicaoY;
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
		// TODO fazer este método
		for (Camiao camiao : listarCamioes) {
			if (camiao.getMatricula().equals(matricula)) {
				return camiao;
			}
		}
		return null; // Retorna null se não encontrar nenhum camião com a matrícula especificada
	}
	
	/**
	 * retorna o posto que tem um dado id
	 *
	 * @return o posto com o id, ou null se não existir
	 */
	public Posto getPosto(int id) {
		for (Posto posto : listarPostos) {
			if (posto.getCodigoNumerico() == id) {
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
	 *         EXCEDE_CAPACIDADE_POSTO, se o posto não tem capacidade de armazenar os litros indicados      
	 */
	public int processarEntrega(Posto posto, int litros, Camiao camiao) {
		// Verifica se o posto precisa ser abastecido
		if (!posto.()) {
			return POSTO_NAO_PRECISA;
		}

		// Verifica se o posto tem capacidade para armazenar os litros indicados
		if (litros > posto.getCapacidadeMaximaCombus()) {
			return EXCEDE_CAPACIDADE_POSTO;
		}

		// Verifica se o camião tem capacidade para armazenar os litros indicados
		if (litros > camiao.getCapacidadeLitros()) {
			return EXCEDE_CAPACIDADE_CAMIAO;
		}

		// Verifica se o pedido implica um tempo maior que um turno
		if (litros / camiao.duracaoTurnoExtra() > camiao.get()) {
			return EXCEDE_TEMPO_TURNO;
		}

		// Realiza a entrega, associando o pedido ao camião
		camiao.(new Pedido(posto, litros));

		return ACEITE;
	}

	
	/** finaliza um turno, isto é, realiza os itinerários e
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
		// TODO fazer este método
	}
	
	/** processa os gastos dos postos
	 */
	private void processarGastosPostos() {
		// TODO fazer este método
	}
}
