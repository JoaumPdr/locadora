package locadora.model;

public class Tipo {
    private int id;
    private String nome;

    // Construtores
    public Tipo() {}

    public Tipo(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Tipo{" + "id=" + id + ", nome='" + nome + '\'' + '}';
    }
}