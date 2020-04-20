package com.example.micro_pago.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="transaction")
public class Transaction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2091032447980895878L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	@NotNull
	private int nroprestamo;
	

	@NotNull
	@Column(name="importe_cuota")
	private double importeCuota ;
	
	
	@NotNull
	private int cuota;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public int getNroprestamo() {
		return nroprestamo;
	}


	public void setNroprestamo(int nroprestamo) {
		this.nroprestamo = nroprestamo;
	}


	public double getImporteCuota() {
		return importeCuota;
	}


	public void setImporteCuota(double importeCuota) {
		this.importeCuota = importeCuota;
	}


	public int getCuota() {
		return cuota;
	}


	public void setCuota(int cuota) {
		this.cuota = cuota;
	}

	
	

}
