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


	//private int ou string pontoPartida;

	public Itinerario (Point pontoPartida){

		this.paragens=new ArrayList<>();
		this.pontoPartida=pontoPartida;
	}

	public void adicionaParagem(Posto posto,int litrosParaDepositar){

		Paragem paragem= new Paragem(posto,litrosParaDepositar);
		paragens.add(paragem.getPosto());
	}


	/** retorna o ponto de inicio do itenerário
	 * @return o ponto de inicio do itenerário
	 */
	public Point getInicio() {
		// TODO fazer este método (não usar este valor assim)
		return pontoPartida;
	}

	/** limpa o itinerário, isto é, remove todas
	 * as paragens do mesmo
	 */
	public void limpar() {
		// TODO fazer este método
		paragens.clear();
	}
}