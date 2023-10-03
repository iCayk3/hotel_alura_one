package br.com.hotel_alura.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.hotel_alura.modelo.Hospede;
import br.com.hotel_alura.modelo.Reserva;

public class HospedeDAO {

	private Connection connection;

	public HospedeDAO(Connection connection) {
		this.connection = connection;
	}

	public void salvar(Hospede hospede) {

		try {
			String sql = "INSERT INTO HOSPEDE (NOME, SOBRENOME, DATA_NASCIMENTO, NACIONALIDADE, TELEFONE, ID_RESERVA) VALUES (?, ?, ?, ?, ?, ?)";

			try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

				pstm.setString(1, hospede.getNome());
				pstm.setString(2, hospede.getSobrenome());
				pstm.setString(3, hospede.getDataNascimento());
				pstm.setString(4, hospede.getNacionalidade());
				pstm.setString(5, hospede.getTelefone());
				pstm.setInt(6, hospede.getReserva().getId());

				pstm.execute();

				try (ResultSet rst = pstm.getGeneratedKeys()) {
					while (rst.next()) {
						hospede.setId(rst.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			new RuntimeException(e);
		}
	}
	
	public List<Hospede> recuperaTodosHospedesComReservas(){
		try{
			List<Hospede> hospedes = new ArrayList<Hospede>();
			String sql = "SELECT H.ID, H.NOME, H.SOBRENOME, H.DATA_NASCIMENTO, H.NACIONALIDADE, H.TELEFONE, R.ID, R.DATA_ENTRADA, R.DATA_SAIDA,"
					+ "R.VALOR, R.FORMA_PAGAMENTO FROM HOSPEDE H INNER JOIN RESERVA R ON H.ID_RESERVA = R.ID;";
			try(PreparedStatement pstm = connection.prepareStatement(sql)){
				pstm.execute();
				
				trasformarResultSetEmHospedeComReservas(hospedes, pstm);
				
				return hospedes;
			}
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
		
	public List<Hospede> recuperaComFiltros(String filtro){
		List<Hospede> hospedes = new ArrayList<Hospede>();
		String sql = " SELECT H.ID, H.NOME, H.SOBRENOME, H.DATA_NASCIMENTO, H.NACIONALIDADE, H.TELEFONE, R.ID, R.DATA_ENTRADA, R.DATA_SAIDA,"
					+ "R.VALOR, R.FORMA_PAGAMENTO FROM HOSPEDE H INNER JOIN RESERVA R ON H.ID_RESERVA = R.ID WHERE (H.NOME LIKE ?)" 
				+ "OR (H.SOBRENOME LIKE ?)"
				+ "OR (H.DATA_NASCIMENTO LIKE ?)" 
				+ "OR (H.NACIONALIDADE LIKE ?)"
				+ "OR (H.TELEFONE LIKE ?)" 
				+ "OR (H.ID_RESERVA LIKE ?)"
				+ "OR (H.ID LIKE ?);";
		try(PreparedStatement pstm = connection.prepareStatement(sql)){
			pstm.setString(1, "%"+filtro+"%");
			pstm.setString(2, "%"+filtro+"%");
			pstm.setString(3, "%"+filtro+"%");
			pstm.setString(4, "%"+filtro+"%");
			pstm.setString(5, "%"+filtro+"%");
			pstm.setString(6, "%"+filtro+"%");
			pstm.setString(7, "%"+filtro+"%");
			pstm.execute();
			trasformarResultSetEmHospedeComReservas(hospedes, pstm);
			return hospedes;
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	
	private void trasformarResultSetEmHospedeComReservas(List<Hospede> produtos, PreparedStatement pstm){
		
		try{
			try (ResultSet rst = pstm.getResultSet()) {
				while (rst.next()) {
					
					Reserva reserva = new Reserva(rst.getInt(7), rst.getString(8), rst.getString(9), rst.getFloat(10), rst.getString(11));
					
					Hospede hospede = new Hospede(rst.getInt(1), rst.getString(2), rst.getString(3),
							rst.getString(4), rst.getString(5), rst.getString(6), reserva);

					produtos.add(hospede);
				}
			}
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}

	public void alterar(String nome, String sobrenome, String dataNascimento, String nacionalidade,
			String telefone, Integer id) {
		try{
			try(PreparedStatement stm = connection.prepareStatement("UPDATE HOSPEDE H SET H.NOME = ?, "
					+ "H.SOBRENOME = ?,"
					+ "H.DATA_NASCIMENTO = ?,"
					+ "H.NACIONALIDADE = ?,"
					+ "H.TELEFONE = ? WHERE ID = ?")){
				
				stm.setString(1,nome);
				stm.setString(2, sobrenome);
				stm.setString(3, dataNascimento);
				stm.setString(4, nacionalidade);
				stm.setString(5, telefone);
				stm.setInt(6, id);
				stm.execute();
			}
		} catch(SQLException e){
			new RuntimeException(e);
		}
		
	}
	public void deletar(Integer id){
		try{
			try (PreparedStatement stm = connection.prepareStatement("DELETE FROM HOSPEDE WHERE ID = ?")) {
				stm.setInt(1, id);
				stm.execute();
			}
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}

	public Hospede buscarPorId(int id) {
		Hospede hospede = null;
		try(PreparedStatement stm = connection.prepareStatement("SELECT H.ID, H.NOME, H.SOBRENOME, H.DATA_NASCIMENTO, H.NACIONALIDADE, H.TELEFONE, R.ID, R.DATA_ENTRADA, R.DATA_SAIDA,"
				+ "R.VALOR, R.FORMA_PAGAMENTO FROM HOSPEDE H INNER JOIN RESERVA R ON H.ID_RESERVA = R.ID WHERE H.ID = ?")){
			stm.setInt(1, id);
			stm.execute();
			
			try (ResultSet rst = stm.getResultSet()) {
				while (rst.next()) {
					
					Reserva reserva = new Reserva(rst.getInt(7), rst.getString(8), rst.getString(9), rst.getFloat(10), rst.getString(11));
					
					hospede = new Hospede(rst.getInt(1), rst.getString(2), rst.getString(3),
							rst.getString(4), rst.getString(5), rst.getString(6), reserva);
					System.out.println("cheguei aqui");
				}
			}
			
			
		} catch(SQLException e){
			new RuntimeException(e);
		}
		return hospede;
	}
	
	private void deletarPorIdReserva(Integer id){
		try{
			try (PreparedStatement stm = connection.prepareStatement("DELETE FROM HOSPEDE WHERE ID_RESERVA = ?")) {
				stm.setInt(1, id);
				stm.execute();
			}
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	
	public void deletarReserva(int id){
		deletarPorIdReserva(id);
		try(PreparedStatement stm = connection.prepareStatement("DELETE FROM RESERVA WHERE ID = ?))")){
			stm.setInt(1, id);
			stm.execute();
		}catch(SQLException e){
			new RuntimeException(e);
		}
	}
}
