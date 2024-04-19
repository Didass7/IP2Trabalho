package menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.TitledBorder;
import petroleum.Camiao;


/** Renderer usado para apresentar as informações de um camião na lista de camiões */
@SuppressWarnings("serial")
public class RendererCamiao implements ListCellRenderer<Camiao> {

    /** quantos pixeis tem o tanque no icon */
    private static final int PIXEIS_TANQUE = 30;
    /** a imagem a apresentar na lista */
    private static final ImageIcon camiaoIcon = new ImageIcon("icones/camiao_lbl.png");

    private Camiao camiao; // qual o camião a apresentar
    private JPanel painel; // painel onde desenhar as infos

    /** cor a usar para indicar que o camião está selecionado */
    private static final Color selColor = Color.CYAN;
    // cor a usar para indicar que o camião NÃO está selecionado (usar a do sistema)
    private Color naoSelColor;


    public RendererCamiao() {
        Dimension size = new Dimension(camiaoIcon.getIconWidth(), camiaoIcon.getIconHeight());
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                desenharInfo(g);
            }
        };
        naoSelColor = panel.getBackground();
        panel.setPreferredSize(size);
        panel.setMinimumSize(size);
        panel.setSize(size);
        panel.setOpaque(false);
        painel = new JPanel();
        painel.add(panel);
        painel.setBorder(new TitledBorder("121212"));
    }

    /** define qual o caimão a usar
     * @param camiao o camião a usar */
    public void setCamiao(Camiao camiao) {
        this.camiao = camiao;
    }

    /** desenha a informação no painel de desenho
     * @param g ambiente onde desenhar */
    private void desenharInfo(Graphics g) {
        // obter as informações
        int quantidade = camiao.getQuantidadeCombusAtual(); // quantidade de combustível
        int capacidadeLivre = camiao.capacidadeLivre(); // capacidade livre
        int velocidadeMedia = camiao.getVelocidadeMediaKm(); // velocidade média
        float percentOcupacao = camiao.percentagemOcupacao(); // percentagem de ocupação
        //duração do turno em horas e minutos
        int duracaoHoras = (int) (camiao.duracaoTurno() / Camiao.SEGUNDOS_POR_HORA);
        int duracaoMinutos = (int) ((camiao.duracaoTurno() % Camiao.SEGUNDOS_POR_HORA / 60));

        // apresentar as infos
        int numPixeis = (int) (percentOcupacao * PIXEIS_TANQUE);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(21, 7, PIXEIS_TANQUE, 14);
        g.setColor(Color.GRAY);
        g.fillRect(21, 7, numPixeis, 14);
        camiaoIcon.paintIcon(painel, g, 0, 0);
        g.setColor(Color.BLACK);
        g.drawString("" + quantidade, 60, 12);
        g.drawString("" + capacidadeLivre, 60, 25);
        g.drawString("" + velocidadeMedia, 30, 47);
        g.drawString(duracaoHoras + ":" + duracaoMinutos, 83, 47);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Camiao> list, Camiao value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        camiao = value;

        // obter a matrícula do camião
        String matricula = camiao.getMatricula();


        ((TitledBorder) painel.getBorder()).setTitle(matricula);
        ((TitledBorder) painel.getBorder()).setTitleColor(isSelected ? Color.BLUE : Color.BLACK);
        painel.setBackground(isSelected ? selColor : naoSelColor);
        return painel;
    }
}