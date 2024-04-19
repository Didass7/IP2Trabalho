package petroleum;

import java.util.ArrayList;
import java.awt.Point;
import java.util.Collections;
import java.util.List;

/** Um itinerário é um conjunto de paragens.<br>
 * O itinerário assume que o ponto de início é sempre o mesmo e,
 * no final, deve retornar sempre ao local de início.
 */
public class Itinerario {

	private List<Paragem> paragens; //variável de instância que guarda as paragens
	private Point pontoPartida; //variável de instância que guarda o ponto de partida

	//Construtor do itinerário
	public Itinerario (Point pontoPartida){

		this.paragens=new ArrayList<>();
		this.pontoPartida=pontoPartida;
	}

	//Método que adiciona uma paragem ao itinerário
	public void adicionaParagem(Posto posto,int litrosParaDepositar){

		Paragem paragem= new Paragem(posto,litrosParaDepositar);
		paragens.add(paragem);
	}

	public int tamanho(){
		return paragens.size();
	}


	public Point getPontoPartida() {
		return pontoPartida;
	}

	public void setPontoPartida(Point pontoPartida) {
		this.pontoPartida = pontoPartida;
	}


	public void addParagem(Paragem paragem) {
		this.paragens.add(paragem);
	}

	public void removeParagem(Paragem paragem) {
		this.paragens.remove(paragem);
	}

	public List<Paragem> getParagens() {
		return Collections.unmodifiableList( paragens );
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
		return paragens.size() == 0 ? null : paragens.get(paragens.size() - 1).getPosto();
	}

	public int contarParagens(){
		return paragens.size();
	}

	public Point paragem (int i){
		if (i>=0 && i<paragens.size())
			return paragens.get(i).getPosto().getLocalizacao();
		else
			return null;
	}




}