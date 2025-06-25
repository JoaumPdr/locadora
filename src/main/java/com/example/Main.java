package com.example;
import locadora.dao.LocadoraDAO;
import locadora.model.Cliente;
import locadora.model.Item;
import locadora.model.Locacao;
import locadora.model.Tipo;
import locadora.util.ConexaoMySQL;

import java.sql.Connection; // Importado para verificação
import java.sql.SQLException; // Importado para verificação
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {

    private static final LocadoraDAO dao = new LocadoraDAO();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        verificarConexaoBanco(); // Nova chamada no início do programa
        carregarDadosIniciais();

        int opcao;
        do {
            exibirMenu();
            opcao = lerOpcao();

            switch (opcao) {
                case 1: cadastrarNovoCliente(); break;
                case 2: atualizarClienteExistente(); break;
                case 3: deletarClienteExistente(); break;
                case 4: listarTodosClientes(); break;
                case 5: cadastrarNovoItem(); break;
                case 6: atualizarItemExistente(); break;
                case 7: System.out.println("Funcionalidade de deletar item não implementada."); break;
                case 8: listarTodosItens(); break;
                case 9: realizarNovaLocacao(); break;
                case 10: registrarDevolucaoItem(); break;
                case 11: listarTodasLocacoes(); break;
                case 0: System.out.println("Saindo do sistema... Até logo!"); break;
                default: System.out.println("Opção inválida. Tente novamente.");
            }

            if (opcao != 0) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
            }
        } while (opcao != 0);

        scanner.close();
    }

    /**
     * NOVO MÉTODO: Tenta estabelecer uma conexão com o banco de dados.
     * Se falhar, exibe uma mensagem de erro e encerra o programa.
     */
    private static void verificarConexaoBanco() {
        System.out.println("--- Verificando conexão com o servidor MySQL ---");
        try (Connection conn = ConexaoMySQL.getConexao()) {
            if (conn != null) {
                System.out.println("======================================================");
                System.out.println("  CONEXÃO COM O BANCO DE DADOS ESTABELECIDA COM SUCESSO!  ");
                System.out.println("======================================================");
            }
        } catch (SQLException e) {
            System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.err.println("!! ERRO FATAL: Não foi possível conectar ao Banco de Dados.");
            System.err.println("!! Verifique suas credenciais ou se o servidor MySQL está ativo.");
            System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            // Encerra o programa se a conexão falhar
            System.exit(1);
        }
    }


    private static int lerOpcao() {
        try {
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consome a nova linha
            return opcao;
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            scanner.nextLine(); // Limpa o buffer
            return -1; // Retorna um valor inválido para repetir o loop
        }
    }

    private static void exibirMenu() {
        System.out.println("\n===== MENU DA LOCADORA =====");
        System.out.println("--- Clientes ---");
        System.out.println("1. Cadastrar Cliente");
        System.out.println("2. Editar Cliente");
        System.out.println("3. Deletar Cliente");
        System.out.println("4. Listar Clientes");
        System.out.println("--- Itens ---");
        System.out.println("5. Cadastrar Item");
        System.out.println("6. Editar Item");
        System.out.println("7. Deletar Item (Não implementado)");
        System.out.println("8. Listar Itens");
        System.out.println("--- Locações ---");
        System.out.println("9. Realizar Nova Locação");
        System.out.println("10. Registrar Devolução");
        System.out.println("11. Listar Locações Ativas");
        System.out.println("--- Sistema ---");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    // --- MÉTODOS DE CLIENTE ---
    private static void cadastrarNovoCliente() {
        System.out.println("\n--- Cadastro de Novo Cliente ---");
        System.out.print("ID: "); int id = lerOpcao();
        System.out.print("Nome: "); String nome = scanner.nextLine();
        System.out.print("Telefone: "); String telefone = scanner.nextLine();
        System.out.print("E-mail: "); String email = scanner.nextLine();
        Cliente novoCliente = new Cliente();
        novoCliente.setId(id);
        novoCliente.setNome(nome);
        novoCliente.setTelefone(telefone);
        novoCliente.setEmail(email);
        dao.cadastrarCliente(novoCliente);
    }

    private static void atualizarClienteExistente() {
        System.out.println("\n--- Atualização de Cliente ---");
        listarTodosClientes();
        System.out.print("\nDigite o ID do cliente a ser atualizado: ");
        int id = lerOpcao();
        System.out.print("Novo Nome: "); String nome = scanner.nextLine();
        System.out.print("Novo Telefone: "); String telefone = scanner.nextLine();
        System.out.print("Novo E-mail: "); String email = scanner.nextLine();
        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setId(id);
        clienteAtualizado.setNome(nome);
        clienteAtualizado.setTelefone(telefone);
        clienteAtualizado.setEmail(email);
        dao.atualizarCliente(clienteAtualizado);
    }

    private static void deletarClienteExistente() {
        System.out.println("\n--- Exclusão de Cliente ---");
        listarTodosClientes();
        System.out.print("\nDigite o ID do cliente a ser deletado: ");
        int id = lerOpcao();
        System.out.print("Tem certeza (S/N)? "); String conf = scanner.nextLine();
        if (conf.equalsIgnoreCase("S")) dao.deletarCliente(id);
        else System.out.println("Operação cancelada.");
    }

    private static void listarTodosClientes() {
        System.out.println("\n--- Lista de Clientes ---");
        List<Cliente> clientes = dao.listarClientes();
        if (clientes.isEmpty()) System.out.println("Nenhum cliente cadastrado.");
        else clientes.forEach(System.out::println);
    }

    // --- MÉTODOS DE ITEM ---
    private static void cadastrarNovoItem() {
        System.out.println("\n--- Cadastro de Novo Item ---");
        System.out.print("ID do Item: "); int id = lerOpcao();
        System.out.print("Título: "); String titulo = scanner.nextLine();
        System.out.print("Ano: "); int ano = lerOpcao();
        System.out.print("Preço (ex: 15,50): "); double preco = scanner.nextDouble(); scanner.nextLine();
        System.out.print("ID do Tipo (1=Filme, 2=Jogo): "); int idTipo = lerOpcao();

        Item item = new Item();
        item.setId(id);
        item.setTitulo(titulo);
        item.setAno(ano);
        item.setPrecoLocacao(preco);
        item.setDisponivel(true);
        item.setTipo(new Tipo(idTipo, ""));
        dao.cadastrarItem(item);
    }

    private static void atualizarItemExistente() {
        System.out.println("\n--- Atualização de Item ---");
        listarTodosItens();
        System.out.print("\nDigite o ID do item a ser atualizado: ");
        int id = lerOpcao();
        System.out.print("Novo Título: "); String titulo = scanner.nextLine();
        System.out.print("Novo Ano: "); int ano = lerOpcao();
        System.out.print("Novo Preço (ex: 15,50): "); double preco = scanner.nextDouble(); scanner.nextLine();
        System.out.print("Novo ID do Tipo (1=Filme, 2=Jogo): "); int idTipo = lerOpcao();

        Item item = new Item();
        item.setId(id);
        item.setTitulo(titulo);
        item.setAno(ano);
        item.setPrecoLocacao(preco);
        item.setTipo(new Tipo(idTipo, ""));
        dao.atualizarItem(item);
    }

    private static void listarTodosItens() {
        System.out.println("\n--- Lista de Itens ---");
        List<Item> itens = dao.listarItens();
        if (itens.isEmpty()) System.out.println("Nenhum item cadastrado.");
        else itens.forEach(System.out::println);
    }

    // --- MÉTODOS DE LOCAÇÃO ---
    private static void realizarNovaLocacao() {
        System.out.println("\n--- Realizar Nova Locação ---");
        listarTodosClientes();
        System.out.print("\nDigite o ID do Cliente: "); int idCliente = lerOpcao();

        System.out.println("\n--- Itens Disponíveis ---");
        dao.listarItens().stream().filter(Item::isDisponivel).forEach(System.out::println);
        System.out.print("\nDigite o ID do Item: "); int idItem = lerOpcao();

        System.out.print("Digite o ID da Locação (deve ser único): "); int idLocacao = lerOpcao();

        Locacao locacao = new Locacao();
        locacao.setId(idLocacao);
        Cliente cliente = new Cliente();
        cliente.setId(idCliente);
        locacao.setCliente(cliente);
        Item item = new Item();
        item.setId(idItem);
        locacao.setItem(item);
        locacao.setDataLocacao(Date.valueOf(LocalDate.now()));
        locacao.setDataDevolucaoPrevista(Date.valueOf(LocalDate.now().plusDays(7)));

        dao.realizarLocacao(locacao);
    }

    private static void registrarDevolucaoItem() {
        System.out.println("\n--- Registrar Devolução ---");
        listarTodasLocacoes();
        System.out.print("\nDigite o ID da Locação a ser finalizada: ");
        int idLocacao = lerOpcao();
        dao.registrarDevolucao(idLocacao);
    }

    private static void listarTodasLocacoes() {
        System.out.println("\n--- Locações Ativas ---");
        List<Locacao> locacoes = dao.listarLocacoes();
        if (locacoes.isEmpty()) System.out.println("Nenhuma locação ativa no momento.");
        else locacoes.forEach(System.out::println);
    }

    // --- DADOS INICIAIS ---
    private static void carregarDadosIniciais() {
        System.out.println("--- Carregando dados iniciais no sistema ---");

        Tipo tipoFilme = new Tipo(1, "Filme");
        Tipo tipoJogo = new Tipo(2, "Jogo");
        dao.cadastrarTipo(tipoFilme);
        dao.cadastrarTipo(tipoJogo);

        Cliente cliente1 = new Cliente();
        cliente1.setId(101);
        cliente1.setNome("Maria Oliveira");
        cliente1.setTelefone("21912345678");
        cliente1.setEmail("maria.o@email.com");
        dao.cadastrarCliente(cliente1);

        Cliente cliente2 = new Cliente();
        cliente2.setId(102);
        cliente2.setNome("João Pereira");
        cliente2.setTelefone("35988776655");
        cliente2.setEmail("joao.p@email.com");
        dao.cadastrarCliente(cliente2);

        Item item1 = new Item();
        item1.setId(201);
        item1.setTitulo("Interestelar");
        item1.setAno(2014);
        item1.setPrecoLocacao(12.0);
        item1.setDisponivel(true);
        item1.setTipo(new Tipo(1, ""));
        dao.cadastrarItem(item1);

        Item item2 = new Item();
        item2.setId(301);
        item2.setTitulo("The Witcher 3");
        item2.setAno(2015);
        item2.setPrecoLocacao(25.0);
        item2.setDisponivel(true);
        item2.setTipo(new Tipo(2, ""));
        dao.cadastrarItem(item2);
        System.out.println("--- Dados iniciais carregados ---\n");
    }
}