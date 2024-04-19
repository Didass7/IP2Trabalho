package petroleum;

/**
 * Uma paragem do itinerário deve indicar
 * o posto onde se deve parar e quantos litros
 * devem ser transferidos para o posto
 */
public class Paragem {


    private Posto posto;
    private int litrosParaDepositar;

    public Paragem(Posto posto, int litrosParaDepositar) {

        this.posto = posto;
        this.litrosParaDepositar = litrosParaDepositar;
    }

    public Posto getPosto() {
        return posto;
    }


    public int getLitrosParaDepositar() {
        return litrosParaDepositar;
    }
}