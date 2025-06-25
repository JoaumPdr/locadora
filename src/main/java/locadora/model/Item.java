package locadora.model;

public class Item {
    private int id;
    private String titulo;
    private int ano;
    private double precoLocacao;
    private boolean disponivel;
    private Tipo tipo; // Usando composição para representar a relação

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public double getPrecoLocacao() {
        return precoLocacao;
    }

    public void setPrecoLocacao(double precoLocacao) {
        this.precoLocacao = precoLocacao;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        String nomeTipo = (tipo != null) ? tipo.getNome() : "N/A";
        return "Item{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", ano=" + ano +
                ", precoLocacao=" + precoLocacao +
                ", disponivel=" + disponivel +
                ", tipo=" + nomeTipo +
                '}';
    }
}
