package locadora.dao;

import locadora.model.Cliente;
import locadora.model.Item;
import locadora.model.Locacao;
import locadora.model.Tipo;
import locadora.util.ConexaoMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LocadoraDAO {

    // --- MÉTODOS DE TIPO ---
    public void cadastrarTipo(Tipo tipo) {
        String sql = "INSERT INTO Tipos (idTipos, nome) VALUES (?, ?) ON DUPLICATE KEY UPDATE nome=nome;";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, tipo.getId());
            pstmt.setString(2, tipo.getNome());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao inicializar tipo: " + e.getMessage());
        }
    }

    // --- MÉTODOS CRUD DE CLIENTE ---
    public void cadastrarCliente(Cliente cliente) {
        String sql = "INSERT INTO Clientes (idClientes, nome, telefone, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cliente.getId());
            pstmt.setString(2, cliente.getNome());
            pstmt.setString(3, cliente.getTelefone());
            pstmt.setString(4, cliente.getEmail());
            pstmt.executeUpdate();
            System.out.println("Cliente '" + cliente.getNome() + "' cadastrado com sucesso!");
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                System.out.println("Aviso: O cliente com ID " + cliente.getId() + " já existe no banco de dados.");
            } else {
                System.err.println("Erro ao cadastrar cliente: " + e.getMessage());
            }
        }
    }

    public void atualizarCliente(Cliente cliente) {
        String sql = "UPDATE Clientes SET nome = ?, telefone = ?, email = ? WHERE idClientes = ?";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getTelefone());
            pstmt.setString(3, cliente.getEmail());
            pstmt.setInt(4, cliente.getId());
            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("Cliente atualizado com sucesso!");
            else System.out.println("Cliente com ID " + cliente.getId() + " não encontrado.");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    public void deletarCliente(int idCliente) {
        String sql = "DELETE FROM Clientes WHERE idClientes = ?";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCliente);
            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("Cliente deletado com sucesso!");
            else System.out.println("Cliente com ID " + idCliente + " não encontrado.");
        } catch (SQLException e) {
            if (e.getErrorCode() == 1451) {
                System.err.println("Erro: Cliente não pode ser deletado pois possui locações ativas.");
            } else {
                System.err.println("Erro ao deletar cliente: " + e.getMessage());
            }
        }
    }

    public List<Cliente> listarClientes() {
        String sql = "SELECT * FROM Clientes ORDER BY nome";
        List<Cliente> clientes = new ArrayList<>();
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while(rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("idClientes"));
                c.setNome(rs.getString("nome"));
                c.setTelefone(rs.getString("telefone"));
                c.setEmail(rs.getString("email"));
                clientes.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar clientes: " + e.getMessage());
        }
        return clientes;
    }

    // --- MÉTODOS CRUD DE ITEM ---

    public void cadastrarItem(Item item) {
        String sql = "INSERT INTO Itens (iditens, titulo, ano, preco_locacao, disponivel, Tipos_idTipos) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, item.getId());
            pstmt.setString(2, item.getTitulo());
            pstmt.setInt(3, item.getAno());
            pstmt.setDouble(4, item.getPrecoLocacao());
            pstmt.setBoolean(5, item.isDisponivel());
            pstmt.setInt(6, item.getTipo().getId());
            pstmt.executeUpdate();
            System.out.println("Item '" + item.getTitulo() + "' cadastrado com sucesso!");
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Chave duplicada
                System.out.println("Aviso: O item com ID " + item.getId() + " já existe.");
            } else if (e.getErrorCode() == 1452) { // Chave estrangeira não encontrada
                System.err.println("Erro: O Tipo com ID " + item.getTipo().getId() + " não existe. Cadastre o tipo primeiro.");
            } else {
                System.err.println("Erro ao cadastrar item: " + e.getMessage());
            }
        }
    }

    public void atualizarItem(Item item) {
        String sql = "UPDATE Itens SET titulo = ?, ano = ?, preco_locacao = ?, Tipos_idTipos = ? WHERE iditens = ?";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, item.getTitulo());
            pstmt.setInt(2, item.getAno());
            pstmt.setDouble(3, item.getPrecoLocacao());
            pstmt.setInt(4, item.getTipo().getId());
            pstmt.setInt(5, item.getId());
            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("Item atualizado com sucesso!");
            else System.out.println("Item com ID " + item.getId() + " não encontrado.");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar item: " + e.getMessage());
        }
    }

    public List<Item> listarItens() {
        String sql = "SELECT i.*, t.nome AS nome_tipo FROM Itens i JOIN Tipos t ON i.Tipos_idTipos = t.idTipos ORDER BY i.titulo";
        List<Item> itens = new ArrayList<>();
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while(rs.next()) {
                Item i = new Item();
                i.setId(rs.getInt("iditens"));
                i.setTitulo(rs.getString("titulo"));
                i.setAno(rs.getInt("ano"));
                i.setPrecoLocacao(rs.getDouble("preco_locacao"));
                i.setDisponivel(rs.getBoolean("disponivel"));
                i.setTipo(new Tipo(rs.getInt("Tipos_idTipos"), rs.getString("nome_tipo")));
                itens.add(i);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar itens: " + e.getMessage());
        }
        return itens;
    }

    // --- MÉTODOS DE LOCAÇÃO ---

    public void realizarLocacao(Locacao locacao) {
        // Para garantir a consistência dos dados (atomicidade), o ideal é usar uma transação.
        // Se a atualização do item falhar, a inserção da locação deve ser desfeita (rollback).
        // Para simplificar, faremos em duas etapas, mas em um sistema real, use transações.
        String sqlUpdateItem = "UPDATE Itens SET disponivel = false WHERE iditens = ? AND disponivel = true";
        String sqlInsertLocacao = "INSERT INTO Locações (idLocações, data_locacao, data_devolução_prevista, Itens_iditens, Clientes_idClientes) VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = ConexaoMySQL.getConexao();
            conn.setAutoCommit(false); // Inicia a transação

            // 1. Tenta tornar o item indisponível
            try(PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdateItem)) {
                pstmtUpdate.setInt(1, locacao.getItem().getId());
                int rows = pstmtUpdate.executeUpdate();
                if (rows == 0) {
                    throw new SQLException("Item não está disponível para locação ou não existe.");
                }
            }

            // 2. Se o item foi atualizado, insere o registro de locação
            try(PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsertLocacao)) {
                pstmtInsert.setInt(1, locacao.getId());
                pstmtInsert.setDate(2, locacao.getDataLocacao());
                pstmtInsert.setDate(3, locacao.getDataDevolucaoPrevista());
                pstmtInsert.setInt(4, locacao.getItem().getId());
                pstmtInsert.setInt(5, locacao.getCliente().getId());
                pstmtInsert.executeUpdate();
            }

            conn.commit(); // Efetiva a transação
            System.out.println("Locação realizada com sucesso!");

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Desfaz a transação em caso de erro
                    System.err.println("Transação desfeita devido a erro.");
                } catch (SQLException ex) {
                    System.err.println("Erro ao tentar reverter a transação: " + ex.getMessage());
                }
            }
            if (e.getErrorCode() == 1062) {
                System.err.println("Erro: ID de locação já existe.");
            } else {
                System.err.println("Erro ao realizar locação: " + e.getMessage());
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Retorna ao modo padrão
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Locacao> listarLocacoes() {
        String sql = "SELECT l.*, i.titulo as item_titulo, c.nome as cliente_nome " +
                "FROM Locações l " +
                "JOIN Itens i ON l.Itens_iditens = i.iditens " +
                "JOIN Clientes c ON l.Clientes_idClientes = c.idClientes " +
                "WHERE l.data_devolução_real IS NULL " +
                "ORDER BY l.data_devolução_prevista";

        List<Locacao> locacoes = new ArrayList<>();
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while(rs.next()) {
                Locacao l = new Locacao();
                l.setId(rs.getInt("idLocações"));
                l.setDataLocacao(rs.getDate("data_locacao"));
                l.setDataDevolucaoPrevista(rs.getDate("data_devolução_prevista"));

                Item item = new Item();
                item.setId(rs.getInt("Itens_iditens"));
                item.setTitulo(rs.getString("item_titulo"));
                l.setItem(item);

                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("Clientes_idClientes"));
                cliente.setNome(rs.getString("cliente_nome"));
                l.setCliente(cliente);

                locacoes.add(l);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar locações: " + e.getMessage());
        }
        return locacoes;
    }

    public void registrarDevolucao(int idLocacao) {
        String sqlFindItem = "SELECT Itens_iditens FROM Locações WHERE idLocações = ? AND data_devolução_real IS NULL";
        String sqlUpdateLocacao = "UPDATE Locações SET data_devolução_real = ? WHERE idLocações = ?";
        String sqlUpdateItem = "UPDATE Itens SET disponivel = true WHERE iditens = ?";

        Connection conn = null;
        try {
            conn = ConexaoMySQL.getConexao();
            conn.setAutoCommit(false);

            // 1. Encontra o item associado à locação
            int idItem = -1;
            try(PreparedStatement pstmtFind = conn.prepareStatement(sqlFindItem)) {
                pstmtFind.setInt(1, idLocacao);
                try(ResultSet rs = pstmtFind.executeQuery()) {
                    if (rs.next()) {
                        idItem = rs.getInt("Itens_iditens");
                    } else {
                        throw new SQLException("Locação não encontrada ou já finalizada.");
                    }
                }
            }

            // 2. Atualiza a data de devolução na locação
            try(PreparedStatement pstmtUpdateLocacao = conn.prepareStatement(sqlUpdateLocacao)) {
                pstmtUpdateLocacao.setDate(1, Date.valueOf(LocalDate.now()));
                pstmtUpdateLocacao.setInt(2, idLocacao);
                pstmtUpdateLocacao.executeUpdate();
            }

            // 3. Torna o item disponível novamente
            try(PreparedStatement pstmtUpdateItem = conn.prepareStatement(sqlUpdateItem)) {
                pstmtUpdateItem.setInt(1, idItem);
                pstmtUpdateItem.executeUpdate();
            }

            conn.commit();
            System.out.println("Devolução registrada com sucesso!");

        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            System.err.println("Erro ao registrar devolução: " + e.getMessage());
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }
}