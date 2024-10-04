public class Forca {
    // (controla a construção da imagem da forca com base nos erros
    // cometidos pelo jogador)

    private Boolean[] forca = new Boolean[6];

    public Forca() {
        for (int i = 0; i < forca.length; i++) {
            forca[i] = false;
        }
    }

    public Boolean isEnforcado(int tentativasRestantes) {
        return (tentativasRestantes == 0);
    }

    public Boolean getPernaDireita() {
        return forca[0];
    }

    public Boolean getPernaEsquerda() {
        return forca[1];
    }

    public Boolean getBracoDireito() {
        return forca[2];
    }

    public Boolean getBracoEsquerdo() {
        return forca[3];
    }

    public Boolean getTronco() {
        return forca[4];
    }

    public Boolean getCabeca() {
        return forca[5];
    }

    public void diminuirDesenho(int tentativasRestantes) {
        forca[tentativasRestantes - 1] = true;
    }

    public void resetarForca() {
        for (int i = 0; i < forca.length; i++) {
            forca[i] = false;
        }
    }

}
