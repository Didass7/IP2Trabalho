package petroleum;

/**
 * Uma paragem do itinerário deve indicar
 * o posto onde se deve parar e quantos litros
 * devem ser transferidos para o posto
 */
public class Paragem {

    private Posto posto; // posto onde se deve parar
    private int litrosParaDepositar; // litros a transferir para o posto

    // construtor
    public Paragem(Posto posto, int litrosParaDepositar) {

        this.posto = posto;
        this.litrosParaDepositar = litrosParaDepositar;
    }

    // métodos de acesso
    public Posto getPosto() {
        return posto;
    }

    public int getLitrosParaDepositar() {
        return litrosParaDepositar;
    }
}