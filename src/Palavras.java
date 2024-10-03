
public class Palavras {

    private String palavraSecreta;
    private String dica;
    private StringBuilder palavraAtual;

    public Palavras(String palavraSecreta, String dica) {
        this.palavraSecreta = palavraSecreta;
        this.dica = dica;
        this.palavraAtual = new StringBuilder("_".repeat(palavraSecreta.length()));// cria uma string com o mesmo
                                                                                   // tamanho da palavraSecreta, mas
                                                                                   // com
                                                                                   // "_" em todas as posições

    }

    public String getPalavraSecreta() {
        return palavraSecreta;
    }

    public String getDica() {
        return dica;
    }

    public String getPalavraAtual() {
        return palavraAtual.toString();
    }

    public void setPalavraAtual(StringBuilder palavraAtual) {
        this.palavraAtual = palavraAtual;
    }

    public boolean possuiLetra(char letra) {
        boolean acertou = false;
        for (int i = 0; i < palavraSecreta.length(); i++) {
            if (palavraSecreta.charAt(i) == letra) {
                System.out.println("A palavra secreta possui a letra " + letra);
                palavraAtual.setCharAt(i, letra);
                acertou = true;
            }
        }
        return acertou;
    }

    public boolean atualizarPalavraAtual(char letra) {
        boolean atualizou = false;
        for (int i = 0; i < palavraSecreta.length(); i++) {
            if (palavraSecreta.charAt(i) == letra) {
                palavraAtual.setCharAt(i, letra);
                atualizou = true;
            }
        }
        return atualizou;
    }

    public Boolean verificarPalavraIgualPalavraAtual() {
        if (palavraSecreta.equals(palavraAtual.toString())) {
            return true;
        } else {
            return false;
        }
    }
}
