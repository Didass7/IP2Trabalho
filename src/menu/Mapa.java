package menu;

import java.awt.Point;

/** Representa o mapa do sistema. Neste caso, tem apenas a tarefa
 * de calcular distâncias. Num futuro (não para implementar neste trabalho)
 * podemos ter zoom e outras opções no mapa.
 */
public class Mapa {
	private static final double ESCALA = 0.38;

	/** calcula a distância (em quilómetros) entre dois pontos do mapa
	 * @param p1 ponto 1
	 * @param p2 ponto 2
	 * @return a distância entre os pontos p1 e p2
	 */
	public static double distancia( Point p1, Point p2 ) {
		// é a distância vezes o fator de escala do mapa
		return p1.distance( p2 ) * ESCALA;
	}
}