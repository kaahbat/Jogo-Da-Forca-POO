//controla a lógica do jogo, como escolha de palavra, controle de tentativas, etc.

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Jogo {

    private int tentativasRestantes;
    private List<Palavras> palavras;
    private Forca forca;
    private int indicePalavraEscolhida;
    private int pontuacao;
    private int contagemDeVitorias;
    private int contagemDeDerrotas;
    private Historico historico;
    private Boolean modo2Jogadores;
    private String dificuldade;
    private String dificuldadePadrao;

    public Jogo() {

        this.tentativasRestantes = 6; // número de tentativas permitidas
        this.palavras = new ArrayList<>();
        this.indicePalavraEscolhida = -1;
        this.forca = new Forca();
        this.pontuacao = 0;
        this.contagemDeVitorias = 0;
        this.contagemDeDerrotas = 0;
        this.historico = new Historico(palavras);
        this.modo2Jogadores = false;
        this.dificuldade = "Facil";
        this.dificuldadePadrao = "Facil";
        lerListaPalavras();

    }

    public Historico getHistorico() {
        return historico;
    }

    public void setModo2Jogadores(Boolean modo2Jogadores) {
        this.modo2Jogadores = modo2Jogadores;
    }

    public void setDificuldade(String dificuldade) {

        this.dificuldade = dificuldade;
    }

    public void iniciarJogo() {
        // reseta score e tentativas
        if (modo2Jogadores) {
            pontuacao = 0;
            contagemDeVitorias = 0;
            contagemDeDerrotas = 0;
            historico.limparHistorico();
        }
        if (dificuldadePadrao != dificuldade) {
            palavras.clear();
            dificuldadePadrao = dificuldade;
            lerListaPalavras();
            System.out.println("aaaa qui  " + dificuldade);
        }
        modo2Jogadores = false;

        // escolhe uma palavra aleatória

        indicePalavraEscolhida = escolherPalavra();

        // exibe a dica
        System.out.println(palavras.get(indicePalavraEscolhida).getPalavraSecreta() + ",");

        exibirDica();
        /*
         * // exibe historico de partidas
         * if (historico != null) {
         * historico.exibirHistorico();
         * 
         * }
         */

        // imprime a posição da palavra escolhida
        for (int i = 0; i < palavras.size(); i++) {
            if (palavras.get(i).getPalavraSecreta().equals(palavras.get(indicePalavraEscolhida).getPalavraSecreta())) {
                System.out.println("indice " + i);
            }
        }

        System.out.println("indice aaaaaa " + indicePalavraEscolhida);

    }

    public void iniciarJogoModo2Jogadores(Palavras palavra) {
        // limpa a lista de palavras iniciada automaticamente ao iniciar o jogo para o
        // modo 2 jogadores
        if (palavras.size() > 30) {
            palavras.clear();
        }
        palavras.add(palavra);
        indicePalavraEscolhida = palavras.size() - 1;

        // exibe a dica
        System.out.println(palavras.get(indicePalavraEscolhida).getPalavraSecreta() + ",");

        exibirDica();

        // exibe historico de partidas
        if (historico != null) {
            historico.exibirHistorico();

        }

    }

    public void salvaHistoricoNasListas() {

        historico.adicionarIndicePalavraEscolhida(indicePalavraEscolhida);
        historico.adicionarPontuacao(pontuacao);
        historico.adicionarContagemDeVitorias(contagemDeVitorias);
        historico.adicionarContagemDeDerrotas(contagemDeDerrotas);

    }

    public Boolean jogoIniciado() {
        return (indicePalavraEscolhida != -1);
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacaoNova) {
        if (dificuldade.equals("Facil")) {
            this.pontuacao = pontuacaoNova;
        } else if (dificuldade.equals("Media")) {
            this.pontuacao = pontuacaoNova + 50;
        } else if (dificuldade.equals("Dificil")) {
            this.pontuacao = pontuacaoNova + 100;
        }

    }

    public int getContagemDeVitorias() {
        return contagemDeVitorias;
    }

    public void setContagemDeVitorias(int contagemDeVitorias) {
        this.contagemDeVitorias = contagemDeVitorias;
    }

    public int getContagemDeDerrotas() {
        return contagemDeDerrotas;
    }

    public void setContagemDeDerrotas(int contagemDeDerrotas) {
        this.contagemDeDerrotas = contagemDeDerrotas;
    }

    public void adicionarVitoria() {
        contagemDeVitorias++;
        salvaHistoricoNasListas();
    }

    public void adicionarDerrota() {
        contagemDeDerrotas++;
        salvaHistoricoNasListas();
    }

    public Palavras getPalavra(int indicePalavraEscolhida) {
        return palavras.get(indicePalavraEscolhida);
    }

    public void adicionarPalavras(String palavraSecreta, String dica) {
        palavras.add(new Palavras(palavraSecreta, dica));
    }

    public void lerListaPalavras() {
        // lê a lista de palavras do arquivo
        // adiciona as palavras à lista de palavras

        String caminho = "src\\palavras" + dificuldade + ".txt";
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {// abre o arquivo para leitura
            String linha;// variável para armazenar cada linha do arquivo
            while ((linha = br.readLine()) != null) {// enquanto houver linhas no arquivo
                String[] partes = linha.split(",");// separa a linha em duas partes
                if (partes.length == 2) {// se a linha tiver duas partes
                    adicionarPalavras(partes[0].trim(), partes[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Boolean carregaSave(String NomeDoSave) {
        // carrega a pontuação, a contagem de vitórias e a contagem de derrotas de um
        // arquivo

        historico.carregaGame(NomeDoSave);
        // atualiza os valores de pontuacao, contagem de vitorias e contagem de derrotas
        pontuacao = historico.getPontuacoes().get(historico.getPontuacoes().size() - 1);
        contagemDeVitorias = historico.getContagemDeVitoriasLista()
                .get(historico.getContagemDeVitoriasLista().size() - 1);
        contagemDeDerrotas = historico.getContagemDeDerrotasLista()
                .get(historico.getContagemDeDerrotasLista().size() - 1);
        // atualiza o indice da palavra escolhida
        indicePalavraEscolhida = historico.getIndicesPalavrasEscolhidas()
                .get(historico.getIndicesPalavrasEscolhidas().size() - 1);
        return true;

    }

    // funçao que revela uma letra aleatoria da palavra secreta verificando se a
    // letra nao foi revelada
    public void revelarLetra() {
        Random random = new Random();
        int indice = random.nextInt(palavras.get(indicePalavraEscolhida).getPalavraSecreta().length());
        char letra = palavras.get(indicePalavraEscolhida).getPalavraSecreta().charAt(indice);
        while (palavras.get(indicePalavraEscolhida).getPalavraAtual().contains(letra + "")) {
            indice = random.nextInt(palavras.get(indicePalavraEscolhida).getPalavraSecreta().length());
            letra = palavras.get(indicePalavraEscolhida).getPalavraSecreta().charAt(indice);
        }
        tentativa(letra, indicePalavraEscolhida);
    }

    public int getTentativasRestantes() {
        return tentativasRestantes;
    }

    public void setTentativasRestantes(int tentativasRestantes) {
        this.tentativasRestantes = tentativasRestantes;
    }

    public int escolherPalavra() {
        // escolhe uma palavra aleatória da lista de palavras
        Random random = new Random();
        indicePalavraEscolhida = random.nextInt(palavras.size());
        return indicePalavraEscolhida;

    }

    public int getIndicePalavraEscolhida() {
        return indicePalavraEscolhida;
    }

    public void exibirDica() {
        System.out.println(palavras.get(getIndicePalavraEscolhida()).getDica());
    }

    public void tentativa(char letra, int indicePalavraEscolhida) {
        // verifica se a letra está na palavra secreta
        // se estiver, atualiza a palavra atual
        // se não estiver, decrementa o número de tentativas restantes
        if (palavras.get(indicePalavraEscolhida).possuiLetra(letra)) {
            System.out.println("Acertou!");
            palavras.get(indicePalavraEscolhida).atualizarPalavraAtual(letra);
        } else {
            forca.diminuirDesenho(tentativasRestantes);
            tentativasRestantes--;
            System.out.println("Errou! Restam " + tentativasRestantes + " tentativas.");
        }
    }

    public Forca getForca() {
        return forca;
    }

    public boolean venceu(int indicePalavraEscolhida) {
        // setPontuacao(10 * tentativasRestantes);// pontuação é igual a 10 vezes o
        // número de tentativas restantes
        if (palavras.get(indicePalavraEscolhida).verificarPalavraIgualPalavraAtual()) {
            setPontuacao(getPontuacao() + 10 * tentativasRestantes);
            return true;

        }
        return false;

    }

    public boolean perdeu() {

        return forca.isEnforcado(tentativasRestantes);
    }

}
