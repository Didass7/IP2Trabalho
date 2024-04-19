package petroleum;

import java.util.ArrayList;
import java.awt.Point;
import java.util.Collections;
import java.util.List;

/**
 * Um itinerário é um conjunto de paragens.<br>
 * O itinerário assume que o ponto de início é sempre o mesmo e,
 * no final, deve retornar sempre ao local de início.
 */
public class Itinerario {

    private List<Paragem> paragens;
    private Point pontoPartida;


    public Itinerario(Point pontoPartida) {

        this.paragens = new ArrayList<>();
        this.pontoPartida = pontoPartida;
    }

    public void addParagem(Paragem paragem) {
        this.paragens.add(paragem);
    }

    public List<Paragem> getParagens() {
        return Collections.unmodifiableList(paragens);
    }

    /**
     * retorna o ponto de inicio do itenerário
     *
     * @return o ponto de inicio do itenerário
     */
    public Point getInicio() {
        return this.pontoPartida;
    }

    /**
     * limpa o itinerário, isto é, remove todas
     * as paragens do mesmo
     */
    public void limpar() {
        paragens.clear();
    }

    public int contarParagens() {
        return paragens.size();
    }

    public Point paragem(int i) {
        if (i >= 0 && i < paragens.size())
            return paragens.get(i).getPosto().getLocalizacao();
        else
            return null;
    }


}