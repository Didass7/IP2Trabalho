package petroleum;

import java.awt.Point;
import java.util.List;

import menu.Mapa;
import petroleum.Camiao;

/**
 * Esta classe representa um camião, que, neste contexto, se refere à capacidade de transportar
 * combústivel. Tem para isso um limite máximo de combústivel que pode transportar e a
 * quantidade de combústvel que transporta num dado momento.
 * Cada camião desloca-se a uma velocidade, que para simplificar, iremos contabilizar como a velocidade média.
 * Um camiºão segue um itinerário, sendo que deve terminar o itinerário dentro
 * do limite de tempo de um turno de 14 horas )2 condutores com turnos de 7 horas cada).
 */
public class Camiao {
    /** o tempo máximo de um turno, que são as 14 horas
     * (2 condutores), dadas em segundos */

    private int capacidadeLitros; // capacidade máxima de litros que o camião pode transportar
    private int quantidadeCombusAtual; // quantidade de litros que o camião transporta
    private int velocidadeMediaKm; // velocidade média do camião
    private int debitoLs; // débito de litros por segundo
    private String matricula; // matrícula do camião
    private Itinerario itinerario; // itinerário do camião

    // Construtor
    public Camiao(String matricula, int capacidadeLitros, int velocidadeMediaKm, int debitoLs, Central central) {

        this.matricula = matricula;
        this.capacidadeLitros = capacidadeLitros;
        this.velocidadeMediaKm = velocidadeMediaKm;
        this.debitoLs = debitoLs;
        this.quantidadeCombusAtual = 0;
        this.itinerario = new Itinerario(central.getPosicao());

    }

    // Getters
    public int getQuantidadeCombusAtual() {
        return quantidadeCombusAtual;
    }

    public int getVelocidadeMediaKm() {
        return velocidadeMediaKm;
    }

    public String getMatricula() {
        return matricula;
    }

    public Itinerario getItinerario() {
        return itinerario;
    }

    /**
     * o tempo máximo de um turno, que são as 14 horas
     * (2 condutores), dadas em segundos
     */
    public static final int TEMPO_TURNO = 14 * 3600;
    public static final int SEGUNDOS_POR_HORA = 3600;

    /**
     * indica se o Camião pode acrescentar o seguinte pedido ao seu itinerário
     *
     * @param posto  posto que pede o abastecimento
     * @param litros litros que o posto pretende
     * @return ACEITE, se aceitar o pedido <br>
     * EXCEDE_CAPACIDADE_CAMIAO, se o número de litros for superior
     * ao que o camião tem disponível<br>
     * EXCEDE_TEMPO_TURNO, se o pedido implicar um tempo maior que um turno
     */
    public int podeFazerPedido(Posto posto, int litros) {
        if (litros > this.capacidadeLivre()) {
            // se o número de litros for superior ao que o camião tem disponível, retorna EXCEDE_CAPACIDADE_CAMIAO
            return Central.EXCEDE_CAPACIDADE_CAMIAO;
        }

        // se o pedido implicar um tempo maior que um turno, retorna EXCEDE_TEMPO_TURNO
        double tempoParaPedido = tempoDespejar(litros) + tempoPercorrer(getItinerario().getInicio(), posto.getLocalizacao());

        // se o tempo para o pedido for maior que o tempo de um turno, retorna EXCEDE_TEMPO_TURNO
        if (tempoParaPedido > TEMPO_TURNO) {
            return Central.EXCEDE_TEMPO_TURNO;
        }
        return Central.ACEITE;
    }

    /**
     * adiciona um posto ao itinerário do camião, se for possível.
     *
     * @param p      posto que pede o abastecimento
     * @param litros litros que o posto pretende
     * @return ACEITE, se aceitar o pedido <br>
     * EXCEDE_CAPACIDADE_CAMIAO, se o número de litros for superior
     * ao que o camião tem disponível<br>
     * EXCEDE_TEMPO_TURNO, se o pedido implicar um tempo maior que um turno
     */
    public int addPosto(Posto p, int litros) {
        int pedido = podeFazerPedido(p, litros);
        if (pedido != Central.ACEITE) {
            return pedido;
        }
        itinerario.addParagem(new Paragem(p, litros));
        quantidadeCombusAtual += litros;
        return Central.ACEITE;
    }

    /**
     * retorna o tempo, em segundos, que demora a fazer o itinerário
     *
     * @return o tempo, em segundos, que demora a fazer o itinerário
     */
    public double duracaoTurno() {
        double tempoTotalCentralPrimeiroPosto = 0;
        double tempoTotalUltimaParagemCentral = 0;
        double tempoTotal = 0;

        List<Paragem> paragens = itinerario.getParagens();
        if (paragens.isEmpty()) {
            return 0;
        }

        double tempoCentralPrimeiroPosto = tempoPercorrer(getItinerario().getInicio(), paragens.get(0).getPosto().getLocalizacao());
        tempoTotalCentralPrimeiroPosto = tempoCentralPrimeiroPosto + tempoDespejar(paragens.get(0).getLitrosParaDepositar());



        for (int i = 1; i < paragens.size(); i++) {
            tempoTotal += tempoPercorrer(paragens.get(i).getPosto().getLocalizacao(), paragens.get(i - 1).getPosto().getLocalizacao());
            tempoTotal += tempoDespejar(paragens.get(i).getLitrosParaDepositar());

        }


        double tempoUltimaParagemCentral = tempoPercorrer(paragens.get(paragens.size() - 1).getPosto().getLocalizacao(), getItinerario().getInicio());
        tempoTotalUltimaParagemCentral = tempoUltimaParagemCentral + tempoDespejar(paragens.get(paragens.size() - 1).getLitrosParaDepositar());

        return (tempoTotalCentralPrimeiroPosto + tempoTotal + tempoTotalUltimaParagemCentral);
    }


    /**
     * retorna o tempo, em segundos, que demora a fazer o itinerário
     * acrescentando um posto extra
     *
     * @param extra   o posto extra a processar
     * @param nLitros oslitros que o posto extra precisa
     * @return tempo, em segundos, que demora a fazer o itinerário mais o posto extra
     */

    public double duracaoTurnoExtra(Posto extra, int nLitros) {
        double duracao = 0;
        List<Paragem> paragens = itinerario.getParagens();
        if (paragens.isEmpty()) {
            duracao += 2 * (tempoPercorrer(itinerario.getInicio(), extra.getLocalizacao()));
            duracao += tempoDespejar(nLitros);

            return duracao;
        }

        duracao += tempoPercorrer(itinerario.getInicio(), paragens.get(0).getPosto().getLocalizacao());
        for (int i = 1; i < paragens.size(); i++) {
            duracao += tempoPercorrer(paragens.get(i).getPosto().getLocalizacao(), paragens.get(i - 1).getPosto().getLocalizacao());
            duracao += tempoDespejar(paragens.get(i).getLitrosParaDepositar());
        }

        duracao += tempoPercorrer(paragens.get(paragens.size() - 1).getPosto().getLocalizacao(), extra.getLocalizacao());
        duracao += tempoDespejar(nLitros);
        duracao += tempoPercorrer(extra.getLocalizacao(), itinerario.getInicio());

        return duracao;
    }

    /**
     * Efetua o transporte e transferência de combustível
     * para todos os postos no itinerário
     */
    public void transporta() {
        for (Paragem paragem : itinerario.getParagens()) {
            int litros = paragem.getLitrosParaDepositar();
            quantidadeCombusAtual -= litros;

            if (quantidadeCombusAtual < 0) {
                quantidadeCombusAtual = 0;
            }

            Posto posto = paragem.getPosto();
            posto.enche(litros);
        }
        itinerario.limpar();
    }

    /**
     * retorna o tempo, em segundos, que demora a percorrer o caminho entre
     * dois pontos.
     *
     * @param ini o ponto inical
     * @param fim o ponto final
     * @return o tempo que demora a ir de ini a fim.
     */
//
    private double tempoPercorrer(Point ini, Point fim) {
        return (Mapa.distancia(ini, fim) / velocidadeMediaKm) * SEGUNDOS_POR_HORA;
    }

    /**
     * retorna quanto tempo demora, em segundos, a transferir a quantidade de liquido
     *
     * @param nLitros a quantidade de liquido a transferir
     * @return o tempo que demora, em segundos, a transferir os nLitros
     */
    private double tempoDespejar(int nLitros) {
        if (nLitros <= 0) {
            return -1;
        } else {
            return (double) nLitros / debitoLs;
        }

    }

    /**
     * retorna a percentagem de ocupação do camião, entre 0 (0%) e 1 (100%)
     *
     * @return a percentagem de ocupação
     */
    public float percentagemOcupacao() {
        // FEITO! fazer este método
        return (float) quantidadeCombusAtual / capacidadeLitros;
    }

    /**
     * retorna a capacidade livre, isto é, quantos litros ainda pode
     * adicionar à carga
     *
     * @return a capacidade livre, em litros
     */
    public int capacidadeLivre() {
        return capacidadeLitros - quantidadeCombusAtual;
    }

    /**
     * retorna o tempo que demora a entregar a carga a um posto
     *
     * @param //posto o posto a entregar a carga
     * @return o tempo que demora a entregar a carga a um posto
     */
}