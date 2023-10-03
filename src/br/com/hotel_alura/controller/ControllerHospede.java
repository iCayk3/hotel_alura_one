package br.com.hotel_alura.controller;

import java.sql.Connection;
import java.util.List;

import br.com.hotel_alura.dao.HospedeDAO;
import br.com.hotel_alura.factory.ConnectionFactory;
import br.com.hotel_alura.modelo.Hospede;

public class ControllerHospede {
	HospedeDAO hospedeDAO;
	
	public ControllerHospede(){
		Connection connection = new ConnectionFactory().recuperarConexao();
		this.hospedeDAO = new HospedeDAO(connection);
	}
	
	public void salvar(Hospede hospede){
		this.hospedeDAO.salvar(hospede);
	}
	public List<Hospede> listarTodos(){
		return this.hospedeDAO.recuperaTodosHospedesComReservas();
	}
	public List<Hospede> recuperaComFiltros(String filtro){
		return this.hospedeDAO.recuperaComFiltros(filtro);
	}

	public void alterar(String nome, String sobrenome, String dataNascimento, String nacionalidade, String telefone, Integer id) {	
		this.hospedeDAO.alterar(nome, sobrenome, dataNascimento, nacionalidade, telefone, id);
	}
	
	public void deletar(int id){
		this.hospedeDAO.deletar(id);
	}
	public Hospede buscarPorId(int id){
		return this.hospedeDAO.buscarPorId(id);
	}

	public void deletarReserva(int id) {
		this.hospedeDAO.deletarReserva(id);
		
	}
}
