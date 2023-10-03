package br.com.hotel_alura.controller;

import java.sql.Connection;
import java.util.List;

import br.com.hotel_alura.dao.ReservaDAO;
import br.com.hotel_alura.factory.ConnectionFactory;
import br.com.hotel_alura.modelo.Reserva;

public class ControllerReserva {
	ReservaDAO reservaDAO;
	
	public ControllerReserva(){
		Connection connection = new ConnectionFactory().recuperarConexao();
		this.reservaDAO = new ReservaDAO(connection);
	}
	
	public void salvar(Reserva reserva){
		this.reservaDAO.salvar(reserva);
	}
	
	public Reserva pegarUltimaReserva(){
		return this.reservaDAO.pegarUltimaReserva();
	}

	public void alterar(String dataE, String dataS, Float valor, String formaPagamento, Integer id) {
		this.reservaDAO.alterar(dataE, dataS, valor, formaPagamento, id);
		
	}
	
	public void deletar(int id){
		this.reservaDAO.deletar(id);
	}
}
