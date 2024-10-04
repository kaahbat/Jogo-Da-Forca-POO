import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfaceGrafica extends JFrame {
    private Jogo jogo = new Jogo();
    private JPanel painelPrincipal = new JPanel();
    private JPanel painelPalavra = new JPanel();
    private JPanel painelDica = new JPanel();
    private JLabel pernaDireita = new JLabel();
    private JLabel pernaEsquerda = new JLabel();
    private JLabel bracoDireito = new JLabel();
    private JLabel bracoEsquerdo = new JLabel();
    private JLabel tronco = new JLabel();
    private JLabel cabeca = new JLabel();
    private JPanel painelTentativas = new JPanel();
    private JPanel painelBotaoLetras = new JPanel();
    private JPanel painelScore = new JPanel();

    public InterfaceGrafica() {
        // Configurações da janela
        setTitle("Jogo da Forca"); // Título da janela

        setSize(1100, 900); // Tamanho da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fechar o aplicativo ao clicar
        setResizable(false); // Não permitir redimensionar a janela

        // JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(null);
        painelPrincipal.setBackground(new Color(255, 204, 229));

        JLabel titulo = new JLabel("Jogo da Forca");
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 60));
        titulo.setBounds(500, 40, 460, 80);
        painelPrincipal.add(titulo);
        JLabel imagemForca = new JLabel();
        ImageIcon img = new ImageIcon("img\\forca.png");
        imagemForca.setIcon(img);
        imagemForca.setBounds(5, 4, 400, 400);
        painelPrincipal.add(imagemForca);

        painelScore.setLayout(new GridLayout(1, 3));
        painelScore.setBounds(23, 440, 300, 50);

        painelScore.setBackground(new Color(255, 153, 204));

        JLabel score = new JLabel("  Score: " + jogo.getPontuacao());
        score.setFont(new Font("Arial", Font.PLAIN, 15));
        score.setForeground(Color.BLACK);

        JLabel vitorias = new JLabel(" Vitórias: " + jogo.getContagemDeVitorias());
        vitorias.setFont(new Font("Arial", Font.PLAIN, 15));
        vitorias.setForeground(Color.BLACK);

        JLabel derrotas = new JLabel(" Derrotas: " + jogo.getContagemDeDerrotas());
        derrotas.setFont(new Font("Arial", Font.PLAIN, 15));
        derrotas.setForeground(Color.BLACK);

        painelScore.add(score);
        painelScore.add(vitorias);
        painelScore.add(derrotas);

        painelPrincipal.add(painelScore);

        painelBotaoLetras.setLayout(new GridLayout(3, 9, 4, 5));
        painelBotaoLetras.setBackground(new Color(255, 204, 229));
        painelBotaoLetras.setBounds(390, 500, 650, 170); // x posiçao pra lateral, y posição pra cima, largura, altura
        // painelBotaoLetras.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
        // "Escolha uma letra", TitledBorder.CENTER, TitledBorder.TOP));

        // Array de botões
        JButton[] botoes = new JButton[26];
        char letra = 'A';
        for (int i = 0; i < 26; i++) {
            botoes[i] = new JButton(String.valueOf(letra));
            botoes[i].setFont(new Font("Arial", Font.PLAIN, 30));
            painelBotaoLetras.add(botoes[i]);
            letra++;
        }

        // Adiciona funcao de clique nos botoes
        for (int i = 0; i < 26; i++) {
            botoes[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!jogo.jogoIniciado()) {
                        JOptionPane.showMessageDialog(null, "Clique em Iniciar Jogo para começar.");
                        return;
                    } else if (jogo.perdeu() || jogo.venceu(jogo.getIndicePalavraEscolhida())) {
                        JOptionPane.showMessageDialog(null,
                                "O jogo acabou! Clique em Iniciar Jogo para jogar novamente.");
                        return;
                    } else {
                        JButton botao = (JButton) e.getSource();
                        char letra = Character.toLowerCase(botao.getText().charAt(0));
                        System.out.println("Letra escolhida: " + letra);

                        jogo.tentativa(letra, jogo.getIndicePalavraEscolhida());
                        criarDesenhoForca();
                        exibirPalavraAtual(jogo.getPalavra(jogo.getIndicePalavraEscolhida()));
                        setTentativasRestantes(jogo.getTentativasRestantes());
                        if (jogo.venceu(jogo.getIndicePalavraEscolhida())) {
                            // atuazlia o score e a quantidade de vitorias do jogador
                            jogo.adicionarVitoria();
                            score.setText("  Score: " + jogo.getPontuacao());
                            vitorias.setText(" Vitórias: " + jogo.getContagemDeVitorias());
                            derrotas.setText(" Derrotas: " + jogo.getContagemDeDerrotas());
                            painelScore.revalidate();
                            painelScore.repaint();
                            JOptionPane.showMessageDialog(null, "Você venceu!");

                        } else if (jogo.perdeu()) {

                            jogo.adicionarDerrota();
                            score.setText("  Score: " + jogo.getPontuacao());
                            vitorias.setText(" Vitórias: " + jogo.getContagemDeVitorias());
                            derrotas.setText(" Derrotas: " + jogo.getContagemDeDerrotas());
                            painelScore.revalidate();
                            painelScore.repaint();

                            JOptionPane.showMessageDialog(null, "Você perdeu!");

                        }
                        // desabilita o botao escolhido
                        botao.setEnabled(false);
                    }

                }
            });
        }

        // botao iniciar jogo
        JButton botaoIniciar = new JButton("Iniciar Jogo");
        botaoIniciar.setFont(new Font("Arial", Font.PLAIN, 20));
        botaoIniciar.setBounds(613, 700, 200, 50);
        painelPrincipal.add(botaoIniciar);

        // evento do botao iniciar jogo
        botaoIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // inicio do jogo

                iniciarJogo();
                score.setText("  Score: " + jogo.getPontuacao());
                vitorias.setText(" Vitórias: " + jogo.getContagemDeVitorias());
                derrotas.setText(" Derrotas: " + jogo.getContagemDeDerrotas());
                painelScore.revalidate();
                painelScore.repaint();
                painelPrincipal.revalidate();
                painelPrincipal.repaint();
                reiniciarBotoes();
            }
        });

        // botao dica do lado do botao iniciar
        JButton botaoDica = new JButton("Dica");
        botaoDica.setFont(new Font("Arial", Font.PLAIN, 20));
        botaoDica.setBounds(390, 700, 90, 50);
        painelPrincipal.add(botaoDica);

        // evento do botao dica
        botaoDica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!jogo.jogoIniciado()) {
                    JOptionPane.showMessageDialog(null, "Clique em Iniciar Jogo para começar.");
                    return;
                } else if (jogo.perdeu() || jogo.venceu(jogo.getIndicePalavraEscolhida())) {
                    JOptionPane.showMessageDialog(null,
                            "O jogo acabou! Clique em Iniciar Jogo para jogar novamente.");
                    return;
                } else {
                    // revela uma letra da palavra
                    jogo.revelarLetra();
                    exibirPalavraAtual(jogo.getPalavra(jogo.getIndicePalavraEscolhida()));
                    botaoDica.setEnabled(false);
                    if (jogo.venceu(jogo.getIndicePalavraEscolhida())) {
                        // atuazlia o score e a quantidade de vitorias do jogador
                        jogo.adicionarVitoria();
                        score.setText("  Score: " + jogo.getPontuacao());
                        vitorias.setText(" Vitórias: " + jogo.getContagemDeVitorias());
                        derrotas.setText(" Derrotas: " + jogo.getContagemDeDerrotas());
                        painelScore.revalidate();
                        painelScore.repaint();

                        JOptionPane.showMessageDialog(null, "Você venceu!");

                    } else if (jogo.perdeu()) {

                        jogo.adicionarDerrota();
                        score.setText("  Score: " + jogo.getPontuacao());
                        vitorias.setText(" Vitórias: " + jogo.getContagemDeVitorias());
                        derrotas.setText(" Derrotas: " + jogo.getContagemDeDerrotas());
                        painelScore.revalidate();
                        painelScore.repaint();
                        JOptionPane.showMessageDialog(null, "Você perdeu!");

                    }
                }
            }
        });

        // botao de informação
        JButton botaoInfo = new JButton("Info");
        botaoInfo.setFont(new Font("Arial", Font.PLAIN, 20));
        botaoInfo.setBounds(970, 700, 67, 50);

        painelPrincipal.add(botaoInfo);

        // evento do botao info
        botaoInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Jogo da Forca\n\n" + "O jogo consiste em adivinhar a palavra secreta.\n"
                                + "Cada letra correta revela a letra na palavra secreta.\n"
                                + "Cada letra errada adiciona uma parte do corpo do boneco na forca.\n"
                                + "O jogo acaba quando o boneco é completado ou a palavra é adivinhada.\n"
                                + "A pontuação é baseada na quantidade de letras corretas, voce perde 10 pontos por cada erro.\n"
                                + "Se nao errar pontuação é 60, onde palavras medias soma mais 50 e palavras dificil soma mais 100.\n"
                                + "Clique em Iniciar Jogo para começar.\n" + "Clique em Dica para revelar uma letra.\n"
                                + "Clique em Info para ver as instruções do jogo.\n"
                                + "Clique em Carregar Save para carregar o historico do jogo e pontuação.\n"
                                + "Clique em Salvar Jogo para salvar o historico do jogo e pontuação.\n"
                                + "Clique em Modo 2 Jogadores para jogar com um amigo, enquanto voce vai reiniciando o jogo com esse modo\n"
                                + " o historico é mantido, ao clicar em novo jogo sem salvar reinicia o historico.\n"
                                + "Clique em Configurar Forca para mudar a dificuldade das palavras.");
            }
        });

        JPanel painelMenu = new JPanel();// Cria um painel para o menu
        painelMenu.setLayout(null); // Layout nulo para definir posições manuais
        painelMenu.setBounds(20, 500, 350, 270); // Define tamanho do painel
        painelMenu.setBackground(new Color(255, 102, 178)); // Cor verde-claro do fundo

        // Define a borda arredondada com título (sem texto no título neste caso)
        painelMenu.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                "MENU", TitledBorder.CENTER, TitledBorder.TOP));

        // Botão "Carregar Palavras"
        JButton carregarPalavras = new JButton("Carregar Save");
        carregarPalavras.setFont(new Font("Arial", Font.PLAIN, 15));
        carregarPalavras.setBounds(15, 40, 200, 40); // Define posição e tamanho do botão
        painelMenu.add(carregarPalavras);

        // adiciona evento ao botao carregar palavras
        carregarPalavras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // exibe uma caixa de dialogo do nome do arquivo

                String save = JOptionPane.showInputDialog(null, "Digite o nome do save salvo:");
                if (!(save == null || save.isBlank())) {
                    Boolean carregou = jogo.carregaSave(save);
                    score.setText("  Score: " + jogo.getPontuacao());
                    vitorias.setText(" Vitórias: " + jogo.getContagemDeVitorias());
                    derrotas.setText(" Derrotas: " + jogo.getContagemDeDerrotas());
                    painelScore.revalidate();
                    painelScore.repaint();
                    if (carregou) {
                        JOptionPane.showMessageDialog(null, "Palavras carregadas com sucesso!");
                        iniciarJogo();
                        painelPrincipal.revalidate();
                        painelPrincipal.repaint();
                        reiniciarBotoes();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Você não inseriu um nome válido ou cancelou.");

                }

            }
        });

        // Botão "Salvar ""
        JButton salvarPalavrasButton = new JButton("Salvar Jogo");
        salvarPalavrasButton.setFont(new Font("Arial", Font.PLAIN, 15));
        salvarPalavrasButton.setBounds(15, 90, 200, 40); // Define posição e tamanho do botão
        painelMenu.add(salvarPalavrasButton);

        // adiciona evento ao botao salvar palavras
        salvarPalavrasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String save = JOptionPane.showInputDialog(null, "Digite o nome para o save:");
                if (!(save == null || save.isBlank())) {
                    jogo.getHistorico().salvarGame(save);
                    JOptionPane.showMessageDialog(null, "Palavras salvas com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Você não inseriu um nome válido ou cancelou.");
                }

            }
        });

        // Botão "Modo 2 Jogadores"
        JButton modo2JogadoresButton = new JButton("Modo 2 Jogadores");
        modo2JogadoresButton.setFont(new Font("Arial", Font.PLAIN, 15));
        modo2JogadoresButton.setBounds(15, 140, 200, 40); // Define posição e tamanho do botão
        painelMenu.add(modo2JogadoresButton);

        // adiciona evento ao botao modo 2 jogadores
        modo2JogadoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (iniciarJogoModo2Jogadores()) {
                    painelPrincipal.revalidate();
                    painelPrincipal.repaint();
                    reiniciarBotoes();
                    jogo.setModo2Jogadores(true);
                }

            }
        });

        // Botao "Configurar Forca"
        JButton configurarForcaButton = new JButton("Configurar Forca");
        configurarForcaButton.setFont(new Font("Arial", Font.PLAIN, 15));
        configurarForcaButton.setBounds(15, 190, 200, 40); // Define posição e tamanho do botão
        painelMenu.add(configurarForcaButton);

        // adiciona evento ao botao configurar forca
        configurarForcaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // um painel com um slider de seleção pra escolher a dificuldade das palavras
                // facil, medio ou dificil
                String[] dificuldades = { "Facil", "Media", "Dificil" };
                String dificuldadeEscolhida = (String) JOptionPane.showInputDialog(null, "Escolha a dificuldade:",
                        "Configurar Forca", JOptionPane.QUESTION_MESSAGE, null, dificuldades, dificuldades[0]);
                if (dificuldadeEscolhida != null) {
                    jogo.setDificuldade(dificuldadeEscolhida);
                    JOptionPane.showMessageDialog(null, "Dificuldade alterada para " + dificuldadeEscolhida);
                }
                System.out.println(dificuldadeEscolhida);

            }
        });

        // Adiciona o painelMenu ao painel principal
        painelPrincipal.add(painelMenu);

        painelPrincipal.add(painelBotaoLetras);
        painelPrincipal.revalidate();
        painelPrincipal.repaint();

        add(painelPrincipal);

    }

    public void reiniciarBotoes() {
        Component[] components = painelPrincipal.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton botao = (JButton) component;
                botao.setEnabled(true);
            }
        }
        components = painelBotaoLetras.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton botao = (JButton) component;
                botao.setEnabled(true);
            }
        }

    }

    // funcao pra iniciar o jogo
    public void iniciarJogo() {
        painelPalavra.setLayout(new GridLayout(1, 0));
        painelPalavra.setBounds(350, 300, 700, 100);
        painelPalavra
                .setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK),
                        "", TitledBorder.CENTER, TitledBorder.TOP));

        jogo.iniciarJogo();
        exibirPalavraAtual(jogo.getPalavra(jogo.getIndicePalavraEscolhida()));
        jogo.setTentativasRestantes(6);
        setTentativasRestantes(jogo.getTentativasRestantes());
        criarDesenhoForca();

        painelPrincipal.revalidate();
        painelPrincipal.repaint();

    }

    // funcao pra iniciar o jogo em modo 2 jogadores
    public Boolean iniciarJogoModo2Jogadores() {
        Boolean verificaCancelou = false;
        // Exibe uma caixa de diálogo para inserir a palavra e a dica
        String palavra = JOptionPane.showInputDialog(null, "Digite a palavra secreta:");
        String dica = "";
        if (palavra == null || palavra.isBlank()) {
            JOptionPane.showMessageDialog(null, "Você não inseriu uma palavra válida ou cancelou.");
            verificaCancelou = true;
            ; // Sai do método se a palavra for inválida ou se o usuário clicar em cancelar
        }
        if (!verificaCancelou) {
            dica = JOptionPane.showInputDialog(null, "Digite a dica:");
            if (dica == null || dica.isBlank()) {
                JOptionPane.showMessageDialog(null, "Você não inseriu uma dica válida ou cancelou.");
                verificaCancelou = true;
                ; // Sai do método se a dica for inválida ou se o usuário clicar em cancelar
            }
        }

        if (!verificaCancelou) {
            painelPalavra.setLayout(new GridLayout(1, 0));
            painelPalavra.setBounds(350, 300, 700, 100);
            painelPalavra
                    .setBorder(
                            BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK),
                                    "", TitledBorder.CENTER, TitledBorder.TOP));

            jogo.iniciarJogoModo2Jogadores(new Palavras(palavra, dica));
            exibirPalavraAtual(jogo.getPalavra(jogo.getIndicePalavraEscolhida()));
            jogo.setTentativasRestantes(6);
            setTentativasRestantes(jogo.getTentativasRestantes());
            criarDesenhoForca();
            return true;
        }
        return false;

    }

    // funcao pra exibir a palavra atual
    public void exibirPalavraAtual(Palavras palavra) {

        // Limpa o painel da palavra
        painelPalavra.removeAll();
        painelPalavra.setBackground(new Color(255, 204, 229));

        // Array de labels
        JLabel[] labels = new JLabel[palavra.getPalavraAtual().length()];
        for (int i = 0; i < palavra.getPalavraAtual().length(); i++) {
            // labels[i] = new JLabel("" + palavra.getPalavraAtual().charAt(i));
            if (palavra.getPalavraAtual().charAt(i) != '_') {
                // Se a letra foi descoberta, exibe a letra real
                labels[i] = new JLabel("" + palavra.getPalavraAtual().charAt(i) + " ");
            } else {
                // Se a letra ainda está oculta, exibe "___"
                labels[i] = new JLabel("___");
            }
            labels[i].setFont(new Font("Arial", Font.PLAIN, 30));
            painelPalavra.add(labels[i]);
        }

        mostrarDica(palavra.getDica());

        // Adiciona os painéis ao painel principal
        painelPrincipal.add(painelPalavra);
        painelPrincipal.revalidate();
        painelPrincipal.repaint();

    }

    public void setTentativasRestantes(int tentativasRestantes) {

        painelTentativas.setLayout(new GridLayout(1, 0));
        painelTentativas.setBounds(350, 400, 700, 100);
        painelTentativas.setBackground(new Color(255, 204, 229));
        painelTentativas
                .setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK),
                        "", TitledBorder.CENTER, TitledBorder.TOP));

        // limpa o painel de tentativas
        painelTentativas.removeAll();

        JLabel tentativas = new JLabel("Tentativas restantes: " + tentativasRestantes);
        tentativas.setFont(new Font("Arial", Font.PLAIN, 20));
        painelTentativas.add(tentativas);

        painelPrincipal.add(painelTentativas);
        painelPrincipal.revalidate();
        painelPrincipal.repaint();

    }

    public void mostrarDica(String dica) {

        painelDica.setLayout(new GridLayout(1, 0));
        painelDica.setBounds(350, 150, 700, 100);
        painelDica.setBackground(new Color(255, 204, 229));
        painelDica.setBorder(
                BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                        "", TitledBorder.CENTER, TitledBorder.TOP));

        // limpa o painel da dica
        painelDica.removeAll();

        JLabel labelDica = new JLabel("Dica: " + dica);
        labelDica.setFont(new Font("Arial", Font.PLAIN, 20));
        painelDica.add(labelDica);

        painelPrincipal.add(painelDica);
        painelPrincipal.revalidate();
        painelPrincipal.repaint();

    }

    public void criarDesenhoForca() {
        // Limpa a forca

        painelPrincipal.remove(pernaDireita);
        painelPrincipal.remove(pernaEsquerda);
        painelPrincipal.remove(bracoDireito);
        painelPrincipal.remove(bracoEsquerdo);
        painelPrincipal.remove(tronco);
        painelPrincipal.remove(cabeca);

        painelPrincipal.revalidate();
        painelPrincipal.repaint();

        // Cria a forca

        if (jogo.getForca().getPernaDireita()) {
            mostrarPernaDireita();

        }
        if (jogo.getForca().getPernaEsquerda()) {
            mostrarPernaEsquerda();
        }
        if (jogo.getForca().getBracoDireito()) {
            mostrarBracoDireito();
        }
        if (jogo.getForca().getBracoEsquerdo()) {
            mostrarBracoEsquerdo();
        }
        if (jogo.getForca().getTronco()) {
            mostrarTronco();

        }
        if (jogo.getForca().getCabeca()) {
            mostrarCabeca();

        }
    }

    private void mostrarPernaDireita() {
        ImageIcon img = new ImageIcon("img\\pd.png");
        pernaDireita.setIcon(img);
        pernaDireita.setBounds(258, 250, 150, 150);

        painelPrincipal.add(pernaDireita);

        painelPrincipal.revalidate();
        painelPrincipal.repaint();
    }

    private void mostrarPernaEsquerda() {
        ImageIcon img = new ImageIcon("img\\pe.png");
        pernaEsquerda.setIcon(img);
        pernaEsquerda.setBounds(205, 250, 150, 150);
        painelPrincipal.add(pernaEsquerda);
        painelPrincipal.revalidate();
        painelPrincipal.repaint();
    }

    private void mostrarBracoDireito() {
        ImageIcon img = new ImageIcon("img\\bd.png");
        bracoDireito.setIcon(img);
        bracoDireito.setBounds(260, 175, 150, 150);
        painelPrincipal.add(bracoDireito);
        painelPrincipal.revalidate();
        painelPrincipal.repaint();
    }

    private void mostrarBracoEsquerdo() {

        ImageIcon img = new ImageIcon("img\\be.png");
        bracoEsquerdo.setIcon(img);
        bracoEsquerdo.setBounds(180, 175, 150, 150);
        painelPrincipal.add(bracoEsquerdo);
        painelPrincipal.revalidate();
        painelPrincipal.repaint();
    }

    private void mostrarTronco() {

        ImageIcon img = new ImageIcon("img\\tronco.png");
        tronco.setIcon(img);
        tronco.setBounds(230, 182, 150, 150);
        // faz com que o tronco fique por cima dos braços
        painelPrincipal.add(tronco);

        painelPrincipal.revalidate();
        painelPrincipal.repaint();
    }

    private void mostrarCabeca() {

        ImageIcon img = new ImageIcon("img\\cabeca.png");
        cabeca.setIcon(img);
        cabeca.setBounds(195, 85, 150, 150);
        // faz com que a cabeça fique por cima do tronco

        painelPrincipal.add(cabeca);

        painelPrincipal.setComponentZOrder(cabeca, 0);

        painelPrincipal.revalidate();
        painelPrincipal.repaint();
    }

}
