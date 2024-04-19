package petroleum;

import java.awt.*;


/* Um posto do sistema. Um posto deve ter uma capacidade
 * máxima de combustível, assim como a quantidade de combustível que tem.
 * Para simular o uso do posto assume-se que ele tem num gasto médio diário,
 * o que significa que todos os dias, o posto vende esse combústivel.
 */
public class Posto {

    private int codigoNumerico;
    private String nomeDoPosto;
    private int gastoDiarioMedioCombus;
    private int quantidadeAtual;
    private int capacidadeMaximaCombus;
    private Point localizacao;
    private boolean pedidoPendente;


    public int getCodigoNumerico() {
        return codigoNumerico;
    }

    public int getGastoDiarioMedioCombus() {
        return gastoDiarioMedioCombus;
    }

    public int getCapacidadeMaximaCombus() {
        return capacidadeMaximaCombus;
    }

    public String getNomeDoPosto() {
        return nomeDoPosto;
    }

    public Point getLocalizacao() {
        return localizacao;
    }

    public int getQuantidadeAtual() {
        return quantidadeAtual;
    }

    public void setQuantidadeAtual(int quantidadeAtual) {
        this.quantidadeAtual = quantidadeAtual;
    }

    public Posto(int codigoNumerico, String nomeDoPosto, Point localizacao, int gastoDiarioMedioCombus, int quantidadeAtual, int capacidadeMaximaCombus) {
        this.codigoNumerico = codigoNumerico;
        this.nomeDoPosto = nomeDoPosto;
        this.localizacao = localizacao;
        this.gastoDiarioMedioCombus = gastoDiarioMedioCombus;
        this.quantidadeAtual = quantidadeAtual;
        this.capacidadeMaximaCombus = capacidadeMaximaCombus;


    }


    /* indica a capacidade (em percentagem) a partir da qual o posto não aceita
     * novos pedidos abastecimento */
    public static final double OCUPACAO_SUFICIENTE = 0.75;

    /* indica a capacidade (em percentagem) abaixo da qual o
     * posto precisa de fazer um pedido */
    public static final double OCUPACAO_MINIMA = 0.25;

    /**
     * Transferência de combústivel para o posto
     *
     * @param nLitros litros a transferir
     * @return ACEITE, o pedido foi adicionado ao camião<br>
     * POSTO_NAO_PRECISA, se o posto não necessita de ser abastecido
     * EXCEDE_CAPACIDADE_POSTO, se o posto não tem capacidade de armazenar os litros indicados
     */
    public int enche(int nLitros) {

        if (percentagemOcupacao() >= OCUPACAO_SUFICIENTE) {
            return Central.POSTO_NAO_PRECISA;
        } else if (capacidadeLivre() < nLitros) {

            return Central.EXCEDE_CAPACIDADE_POSTO;
        }
        quantidadeAtual += nLitros;
        return Central.ACEITE;

    }

    /* retorna a capacidade livre, isto é, quantos
     * litros ainda podem ser armazenados no posto
     * @return a capacidade livre
     */
    public int capacidadeLivre() {
        return getCapacidadeMaximaCombus() - getQuantidadeAtual();
    }

    /* retorna a percentagem de ocupação do posto, entre 0 (0%) e 1 (100%)
     * @return a percentagem de ocupação do posto
     */
    public float percentagemOcupacao() {
        return (float) getQuantidadeAtual() / getCapacidadeMaximaCombus();
    }


    /* indica se o posto tem um pedido pendente
     * @return true, se tiver um pedido
     */
    public boolean temPedidoPendente() {
        return pedidoPendente;
    }

    /* Laborar do posto. O posto processa os gastos e verifica
     * se precisa de realizar um pedido de abastecimento
     */
    public void laborar() {
        int debitoTotal = 0;
        if (debitoTotal > capacidadeMaximaCombus * OCUPACAO_SUFICIENTE) {
            pedidoPendente = true;
        }

        if (percentagemOcupacao() < OCUPACAO_MINIMA) {
            pedidoPendente = true;
        } else if (percentagemOcupacao() < OCUPACAO_SUFICIENTE) {
            if (Math.random() < 0.1) {
                pedidoPendente = true;
            }
        }
        if (pedidoPendente) {
            int quantidadeNecessaria = (capacidadeMaximaCombus - (quantidadeAtual - gastoDiarioMedioCombus));
            enche(quantidadeNecessaria);
        }
    }
}
