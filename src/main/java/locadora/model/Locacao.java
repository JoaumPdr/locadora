package locadora.model;

import java.sql.Date;

public class Locacao {
    private int id;
    private Date dataLocacao;
    private Date dataDevolucaoPrevista;
    private Date dataDevolucaoReal;
    private double multa;
    private Item item;
    private Cliente cliente;

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataLocacao() {
        return dataLocacao;
    }

    public void setDataLocacao(Date dataLocacao) {
        this.dataLocacao = dataLocacao;
    }

    public Date getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public void setDataDevolucaoPrevista(Date dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    public Date getDataDevolucaoReal() {
        return dataDevolucaoReal;
    }

    public void setDataDevolucaoReal(Date dataDevolucaoReal) {
        this.dataDevolucaoReal = dataDevolucaoReal;
    }

    public double getMulta() {
        return multa;
    }

    public void setMulta(double multa) {
        this.multa = multa;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        String tituloItem = (item != null) ? item.getTitulo() : "N/A";
        String nomeCliente = (cliente != null) ? cliente.getNome() : "N/A";
        return "Locacao{" +
                "id=" + id +
                ", dataLocacao=" + dataLocacao +
                ", item=" + tituloItem +
                ", cliente=" + nomeCliente +
                '}';
    }
}