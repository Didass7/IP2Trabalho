package petroleum;

import java.awt.*;



/* Um posto do sistema. Um posto deve ter uma capacidade
 * máxima de combustível, assim como a quantidade de combustível que tem.
 * Para simular o uso do posto assume-se que ele tem num gasto médio diário,
 * o que significa que todos os dias, o posto vende esse combústivel. */
public class Posto {

    private int codigoNumerico; // identificador único do posto
    private String nomeDoPosto; // nome do posto
    private int gastoDiarioMedioCombus; // gasto médio diário de combustível
    private int quantidadeAtual; // quantidade de combustível atual
    private int capacidadeMaximaCombus; // capacidade máxima de combustível
    private Point localizacao; // localização do posto
    private boolean pedidoPendente; // indica se o posto tem um pedido pendente

    // construtor
    public Posto(int codigoNumerico, String nomeDoPosto, Point localizacao, int gastoDiarioMedioCombus, int quantidadeAtual, int capacidadeMaximaCombus) {
        this.codigoNumerico = codigoNumerico;
        this.nomeDoPosto = nomeDoPosto;
        this.localizacao = localizacao;
        this.gastoDiarioMedioCombus = gastoDiarioMedioCombus;
        this.quantidadeAtual = quantidadeAtual;
        this.capacidadeMaximaCombus = capacidadeMaximaCombus;
    }

    // métodos de acesso
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

    /* indica a capacidade (em percentagem) a partir da qual o posto não aceita
     * novos pedidos abastecimento */
    public static final double OCUPACAO_SUFICIENTE = 0.75;

    /* indica a capacidade (em percentagem) abaixo da qual o
     * posto precisa de fazer um pedido */
    public static final double OCUPACAO_MINIMA = 0.25;

    /* transferência de combústivel para o posto
     * @param nLitros litros a transferir
     * @return ACEITE, o pedido foi adicionado ao camião<br>
     *         POSTO_NAO_PRECISA, se o posto não necessita de ser abastecido
     *         EXCEDE_CAPACIDADE_POSTO, se o posto não tem capacidade de armazenar os litros indicados
     */
    public int enche(int nLitros) {
        if (percentagemOcupacao() >= OCUPACAO_SUFICIENTE) {
            //se o posto não necessita de ser abastecido, o pedido foi adicionado ao camião
            return Central.POSTO_NAO_PRECISA;
        } else if (capacidadeLivre() < nLitros) {
            //se o posto não tem capacidade para armazenar os litros indicados
            return Central.EXCEDE_CAPACIDADE_POSTO; // O posto não necessita de ser abastecido no momento
        }
        quantidadeAtual += nLitros;
        return Central.ACEITE; // O pedido foi adicionado ao camião
    }


    /** retorna a capacidade livre, isto é, quantos
     * litros ainda podem ser armazenados no posto
     * @return a capacidade livre */
    public int capacidadeLivre() {
        return getCapacidadeMaximaCombus() - getQuantidadeAtual();
        //subtrai a quantidade atual da capacidade máxima do posto pela quantidade de combustível atual
    }

    /* retorna a percentagem de ocupação do posto, entre 0 (0%) e 1 (100%)
     * @return a percentagem de ocupação do posto */
    public float percentagemOcupacao() {
        return (float) getQuantidadeAtual() / getCapacidadeMaximaCombus();
        //divide a quantidade de combustível atual pela capacidade máxima do posto
    }

    /* indica se o posto tem um pedido pendente
     * @return true, se tiver um pedido */
    public boolean temPedidoPendente() {
        //retorna se o posto tem um pedido pendente
        return pedidoPendente;
    }

    /* Laborar do posto. O posto processa os gastos e verifica
     * se precisa de realizar um pedido de abastecimento */
    public void laborar() {
        int debitoTotal = 0; // débito total do posto
        if (debitoTotal > capacidadeMaximaCombus * OCUPACAO_SUFICIENTE) {
            //se o débito total for superior a 75% da capacidade máxima do posto, o posto tem um pedido pendente
            pedidoPendente = true;
        }

        if (percentagemOcupacao() < OCUPACAO_MINIMA) {
            pedidoPendente = true;
            //se a percentagem de ocupação do posto for inferior a 25%, o posto tem um pedido pendente
        } else if (percentagemOcupacao() < OCUPACAO_SUFICIENTE) {
            //se a percentagem de ocupação do posto for inferior a 75%
            if (Math.random() < 0.1) {
                //se um número aleatório for inferior a 0.1, o posto tem um pedido pendente
                pedidoPendente = true;
            }
        }

        // realiza a verificação se precisa de fazer um pedido de abastecimento
        if (pedidoPendente) {
            // calcula a quantidade necessária para encher o posto
            int quantidadeNecessaria = (capacidadeMaximaCombus - (quantidadeAtual - gastoDiarioMedioCombus));
            // faz o pedido para encher o posto
            enche(quantidadeNecessaria);
        }
    }
}
