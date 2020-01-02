package com.mustr.common.entity;

import java.io.Serializable;

public class Mu implements Serializable {
    private static final long serialVersionUID = 5885982473921192842L;
    protected Long id;
    protected String name;

    public Mu() {
		super();
	}

	public Mu(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
