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

    public void setLocalizacao(Point localizacao) {
        this.localizacao = localizacao;
    }

    public void setCodigoNumerico(int codigoNumerico) {
        this.codigoNumerico = codigoNumerico;
    }

    public void setGastoDiarioMedioCombus(int gastoDiarioMedioCombus) {
        this.gastoDiarioMedioCombus = gastoDiarioMedioCombus;
    }

    public void setCapacidadeMaximaCombus(int capacidadeMaximaCombus) {
        this.capacidadeMaximaCombus = capacidadeMaximaCombus;
    }

    public void setNomeDoPosto(String nomeDoPosto) {
        this.nomeDoPosto = nomeDoPosto;
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

    /* transferência de combústivel para o posto
     * @param nLitros litros a transferir
     * @return ACEITE, o pedido foi adicionado ao camião<br>
     *         POSTO_NAO_PRECISA, se o posto não necessita de ser abastecido
     *         EXCEDE_CAPACIDADE_POSTO, se o posto não tem capacidade de armazenar os litros indicados
     */
    public int enche(int nLitros) {
        // FEITO! fazer este método
        //Teste do nosso melhor amigo

        // Verificar se o posto tem capacidade para armazenar os litros indicados
        if (quantidadeAtual + nLitros <= capacidadeMaximaCombus) {
            // Adicionar os litros ao depósito do posto
            quantidadeAtual += nLitros;

            // Verificar se a ocupação do posto é suficiente para aceitar novos pedidos de abastecimento
            if (percentagemOcupacao() >= OCUPACAO_SUFICIENTE) {
                return Central.ACEITE; // O pedido foi adicionado ao camião
            } else {
                return Central.POSTO_NAO_PRECISA; // O posto não necessita de ser abastecido no momento
            }
        } else {
            return Central.EXCEDE_CAPACIDADE_POSTO; // O posto não tem capacidade de armazenar os litros indicados
        }
    }

    /* retorna a capacidade livre, isto é, quantos
     * litros ainda podem ser armazenados no posto
     * @return a capacidade livre
     */
    public int capacidadeLivre() {
        // FEITO! fazer este método
        return getCapacidadeMaximaCombus() - getQuantidadeAtual();
    }

    /* retorna a percentagem de ocupação do posto, entre 0 (0%) e 1 (100%)
     * @return a percentagem de ocupação do posto
     */
    public float percentagemOcupacao() {
        // FEITO! fazer este método
        return (float) getQuantidadeAtual() / getCapacidadeMaximaCombus();
    }

    /* indica se o posto tem um pedido pendente
     * @return true, se tiver um pedido
     */
    public boolean temPedidoPendente() {
        // FEITO! fazer este método
        //Pode ser necessario alterar alguma cena para nao dar cana
        return pedidoPendente;
    }

    /* Laborar do posto. O posto processa os gastos e verifica
     * se precisa de realizar um pedido de abastecimento
     */
    public void laborar() {

        // FEITO! fazer este método

        int debitoTotal = 0;

        if (debitoTotal > capacidadeMaximaCombus * OCUPACAO_SUFICIENTE) {
            pedidoPendente = true;
        } else {
            pedidoPendente = false;
        }

        if (percentagemOcupacao()  < OCUPACAO_MINIMA ) {
            pedidoPendente = true;

            //Resolver este problema do Math.Random()
        } else if ((percentagemOcupacao()> OCUPACAO_MINIMA)&&(percentagemOcupacao() < OCUPACAO_SUFICIENTE)) {
            if (Math.random()<0.1) {
                pedidoPendente = true;
            }
            pedidoPendente = true;
        }
    }
}

