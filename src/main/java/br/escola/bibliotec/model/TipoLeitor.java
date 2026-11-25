
package br.escola.bibliotec.model;

public enum TipoLeitor {

    ALUNO("Aluno"),
    PROFESSOR("Professor");

    private final String rotulo;

    TipoLeitor(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getRotulo() {
        return rotulo;
    }
}
