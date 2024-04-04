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
	//private int ou string pontoPartida;

	/** retorna o ponto de inicio do itenerário
	 * @return o ponto de inicio do itenerário
	 */
	public Point getInicio() {
		// TODO fazer este método (não usar este valor assim)
		return new Point(505,750);
	}

	/** limpa o itinerário, isto é, remove todas
	 * as paragens do mesmo
	 */
	public void limpar() {
		// TODO fazer este método
	}
}
