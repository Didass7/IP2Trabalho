package petroleum;

import java.util.ArrayList;
import java.awt.Point;
import java.util.List;

/** Um itinerário é um conjunto de paragens.<br>
 * O itinerário assume que o ponto de início é sempre o mesmo e,
 * no final, deve retornar sempre ao local de início.
 */
public class Itinerario {

	private List<Posto> paragens;
	private Point pontoPartida;
	private Itinerario itinerarioAtual;

	public Itinerario(Itinerario itinerarioAtual) {
		this.itinerarioAtual = itinerarioAtual;
	}
//private int ou string pontoPartida;

	public int tamanho(){
		return paragens.size();
	}

	public Itinerario (Point pontoPartida){

		this.paragens=new ArrayList<>();
		this.pontoPartida=pontoPartida;
	}

	public void adicionaParagem(Posto posto,int litrosParaDepositar){

		Paragem paragem= new Paragem(posto,litrosParaDepositar);
		paragens.add(paragem.getPosto());
	}



	public Point getPontoPartida() {
		return pontoPartida;
	}

	public void setPontoPartida(Point pontoPartida) {
		this.pontoPartida = pontoPartida;
	}


	public List<Posto> getParagens() {
		return paragens;
	}

	public void setParagens(List<Posto> paragens) {
		this.paragens = paragens;
	}

	/** retorna o ponto de inicio do itenerário
	 * @return o ponto de inicio do itenerário
	 */
	public Point getInicio() {
		// DONE? fazer este método (não usar este valor assim)
		return this.pontoPartida;
	}

	/** limpa o itinerário, isto é, remove todas
	 * as paragens do mesmo
	 */
	public void limpar() {
		// DONE fazer este método
		paragens.clear();
	}

	public Posto getUltimaParagem() {
		return paragens.size() == 0 ? null : paragens.get(paragens.size() - 1);
	}
}