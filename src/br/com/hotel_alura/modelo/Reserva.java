package br.com.hotel_alura.modelo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Reserva {
	private int id;
	private String dataEntrada;
	private String dataSaida;
	private float valor;
	private String formaPagamento;
	
	public Reserva(){
	}
	
	public Reserva(Integer id, String dataEntrada, String dataSaida, float valor, String formaPagamento){
		this.id = id;
		this.dataEntrada = dataEntrada;
		this.dataSaida = dataSaida;
		this.valor = valor;
		this.formaPagamento = formaPagamento;
	}
	
	public Reserva(String dataEntrada, String dataSaida, float valor, String formaPagamento){
		this.dataEntrada = dataEntrada;
		this.dataSaida = dataSaida;
		this.valor = valor;
		this.formaPagamento = formaPagamento;
	}
	
	public Reserva(String dataEntrada, String dataSaida, String formaPagamento){
		this.dataEntrada = dataEntrada;
		this.dataSaida = dataSaida;
		this.formaPagamento = formaPagamento;
	}
	
	public Float calcularValor(Date dataE, Date dataS) throws ParseException{
		
		long diferencaMS = dataS.getTime() - dataE.getTime();
	    long diferencaSegundos = diferencaMS / 1000;
	    long diferencaMinutos = diferencaSegundos / 60;
	    long diferencaHoras = diferencaMinutos / 60;
	    long diferencaDias = (diferencaHoras / 24);
	    
	    float valorCalculado = 0;
	    
	    if(diferencaDias > 0){
	    	valorCalculado = diferencaDias * 35;
	    }
		
		return valorCalculado;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDataEntrada() {
		return dataEntrada;
	}
	public void setDataEntrada(String dataEntrada) {
		this.dataEntrada = dataEntrada;
	}
	public String getDataSaida() {
		return dataSaida;
	}
	public void setDataSaida(String dataSaida) {
		this.dataSaida = dataSaida;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public String getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	
//	public String formatarData(String data){
//		Date dt = null;
//		try {
//			dt = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy" ,Locale.ENGLISH).parse(data);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String dataFormatada = new SimpleDateFormat("yyyy-MM-dd").format(dt);
//		return dataFormatada;
//	}

	@Override
	public String toString() {
		return "Reserva [id=" + id + ", dataEntrada=" + dataEntrada + ", dataSaida=" + dataSaida + ", valor=" + valor
				+ ", formaPagamento=" + formaPagamento + "]";
	}
}
