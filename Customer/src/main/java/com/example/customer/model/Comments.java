package com.example.customer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comments {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String comment;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "Student_id")
	private Tutorial tut;

	public Comments() {

	}
	
	public Comments(String comment,Tutorial tut) {

		this.comment = comment;
		this.tut = tut;
	}


	public Comments(long id, String comment,Tutorial tut) {

		this.id = id;
		this.comment = comment;
		this.tut = tut;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}


	public Tutorial getTut() {
		return tut;
	}

	public void setTut(Tutorial tut) {
		this.tut = tut;
	}

	@Override
	public String toString() {
		return "Comments [id=" + id + ", comment=" + comment + ", tut=" + tut + "]";
	}

}
