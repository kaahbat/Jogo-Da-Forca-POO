import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Historico {
    private List<Palavras> palavras;
    private List<Integer> indicesPalavrasEscolhidas;
    private List<Integer> pontuacoes;
    private List<Integer> contagemDeVitoriasLista;
    private List<Integer> contagemDeDerrotasLista;

    public Historico(List<Palavras> palavras) {
        this.palavras = palavras;
        this.indicesPalavrasEscolhidas = new ArrayList<>();
        this.pontuacoes = new ArrayList<>();
        this.contagemDeVitoriasLista = new ArrayList<>();
        this.contagemDeDerrotasLista = new ArrayList<>();
    }

    public void setPalavras(List<Palavras> palavras) {
        this.palavras = palavras;
    }

    public List<Integer> getIndicesPalavrasEscolhidas() {
        return indicesPalavrasEscolhidas;
    }

    public List<Integer> getPontuacoes() {
        return pontuacoes;
    }

    public List<Integer> getContagemDeVitoriasLista() {
        return contagemDeVitoriasLista;
    }

    public List<Integer> getContagemDeDerrotasLista() {
        return contagemDeDerrotasLista;
    }

    public void setIndicesPalavrasEscolhidas(List<Integer> indicesPalavrasEscolhidas) {
        this.indicesPalavrasEscolhidas = indicesPalavrasEscolhidas;
    }

    public void setPontuacoes(List<Integer> pontuacoes) {
        this.pontuacoes = pontuacoes;
    }

    public void setContagemDeVitoriasLista(List<Integer> contagemDeVitoriasLista) {
        this.contagemDeVitoriasLista = contagemDeVitoriasLista;
    }

    public void setContagemDeDerrotasLista(List<Integer> contagemDeDerrotasLista) {
        this.contagemDeDerrotasLista = contagemDeDerrotasLista;
    }

    public void adicionarIndicePalavraEscolhida(int indicePalavraEscolhida) {
        this.indicesPalavrasEscolhidas.add(indicePalavraEscolhida);
    }

    public void adicionarPontuacao(int pontuacao) {
        this.pontuacoes.add(pontuacao);
    }

    public void adicionarContagemDeVitorias(int contagemDeVitorias) {
        this.contagemDeVitoriasLista.add(contagemDeVitorias);
    }

    public void adicionarContagemDeDerrotas(int contagemDeDerrotas) {
        this.contagemDeDerrotasLista.add(contagemDeDerrotas);
    }

    public void limparHistorico() {
        this.indicesPalavrasEscolhidas.clear();
        this.pontuacoes.clear();
        this.contagemDeVitoriasLista.clear();
        this.contagemDeDerrotasLista.clear();
    }

    public void exibirHistorico() {
        System.out.println("Histórico de partidas:");
        for (int i = 0; i < indicesPalavrasEscolhidas.size(); i++) {
            System.out.println("Partida " + (i + 1) + ":");
            System.out.println("Palavra escolhida: " + indicesPalavrasEscolhidas.get(i));
            System.out
                    .println("Palavra secreta: " + palavras.get(indicesPalavrasEscolhidas.get(i)).getPalavraSecreta());
            System.out.println("Pontuação: " + pontuacoes.get(i));
            System.out.println("Vitórias: " + contagemDeVitoriasLista.get(i));
            System.out.println("Derrotas: " + contagemDeDerrotasLista.get(i));
        }
    }

    public void salvarGame(String NomeDoSave) {

        String caminho = "src\\" + NomeDoSave + ".txt";

        if (indicesPalavrasEscolhidas.size() == 0) {
            System.out.println("Não há histórico para salvar");
            return;
        }
        if (new File(caminho).exists()) {
            System.out.println("Arquivo de save já existe: " + caminho);
            return;
        }

        try {
            FileWriter fw = new FileWriter(caminho);// cria um arquivo
            BufferedWriter bw = new BufferedWriter(fw);// cria um buffer para escrever no arquivo
            for (int i = 0; i < indicesPalavrasEscolhidas.size(); i++) {// escreve no arquivo
                bw.write(pontuacoes.get(i) + "," + contagemDeVitoriasLista.get(i) + "," + contagemDeDerrotasLista.get(i)
                        + "," + indicesPalavrasEscolhidas.get(i) + ","
                        + palavras.get(indicesPalavrasEscolhidas.get(i)).getPalavraSecreta());
                bw.newLine();// pula uma linha
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void carregaGame(String NomeDoSave) {
        // carrega a pontuação, a contagem de vitórias e a contagem de derrotas de um
        // arquivo
        // modelo de arquivo:
        // pontuação,contagemDeVitorias,contagemDeDerrotas,indicesPalavrasEscolhidas,palavra

        // exemplo de arquivo:
        // pontuação,contagemDeVitorias,contagemDeDerrotas,indicesPalavrasEscolhidas,palavra
        String caminho = "src\\" + NomeDoSave + ".txt";

        File arquivoSave = new File(caminho);
        if (!arquivoSave.exists()) {
            System.out.println("Arquivo de save não encontrado: " + caminho);
            return; // Interrompe a execução do método se o arquivo não existir
        }

        // Limpa o histórico atual
        limparHistorico();

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {// abre o arquivo para leitura
            String linha;// variável para armazenar cada linha do arquivo
            while ((linha = br.readLine()) != null) {// enquanto houver linhas no arquivo
                String[] partes = linha.split(",");// separa a linha em cinco partes
                if (partes.length == 5) {// se a linha tiver cinco partes
                    adicionarPontuacao(Integer.parseInt(partes[0].trim()));
                    adicionarContagemDeVitorias(Integer.parseInt(partes[1].trim()));
                    adicionarContagemDeDerrotas(Integer.parseInt(partes[2].trim()));
                    adicionarIndicePalavraEscolhida(Integer.parseInt(partes[3].trim()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
