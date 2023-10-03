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
import br.com.hotel_alura.util.FormatarData;

public class ReservaDAO {
	private Connection connection;
	public ReservaDAO(Connection connection){
		this.connection = connection;
	}
	
	public void salvar(Reserva reserva) {
		
		try {
			String sql = "INSERT INTO RESERVA(DATA_ENTRADA, DATA_SAIDA, VALOR, FORMA_PAGAMENTO) VALUES (?, ?, ?, ?)";

			try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

				pstm.setString(1, FormatarData.formatarData(reserva.getDataEntrada()));
				pstm.setString(2, FormatarData.formatarData(reserva.getDataSaida()));
				pstm.setFloat(3, reserva.getValor());
				pstm.setString(4, reserva.getFormaPagamento());
				pstm.execute();

				try (ResultSet rst = pstm.getGeneratedKeys()) {
					while (rst.next()) {
						reserva.setId(rst.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			new RuntimeException(e);
		}
	}
	
	public Reserva pegarUltimaReserva(){
		
		Reserva ultimaReserva = null;
		try{
			String sql = "SELECT * FROM RESERVA WHERE id = (SELECT max(id) FROM RESERVA);";
			try(PreparedStatement pstm = connection.prepareStatement(sql)){
				pstm.execute();
				try(ResultSet rst = pstm.getResultSet()){
					while(rst.next()){
						ultimaReserva = new Reserva(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getFloat(4), rst.getString(5));
					}
				}
			}		
		} catch (SQLException e){
			new RuntimeException(e);
		}		
		return ultimaReserva;
	}

	public void alterar(String dataE, String dataS, Float valor, String formaPagamento, Integer id) {
		try{
			try(PreparedStatement stm = connection.prepareStatement("UPDATE RESERVA R SET R.DATA_ENTRADA = ?, "
					+ "R.DATA_SAIDA = ?,"
					+ "R.VALOR = ?,"
					+ "R.FORMA_PAGAMENTO = ? WHERE ID = ?")){
				
				stm.setString(1,dataE);
				stm.setString(2, dataS);
				stm.setFloat(3, valor);
				stm.setString(4, formaPagamento);
				stm.setInt(5, id);
				stm.execute();
			}
		} catch(SQLException e){
			new RuntimeException(e);
		}
		
	}
	
	public void deletar(Integer id){
		try{
			try (PreparedStatement stm = connection.prepareStatement("DELETE FROM RESERVA WHERE ID = ?")) {
				stm.setInt(1, id);
				stm.execute();
			}
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	
}
