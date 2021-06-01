package com.example.desafiospring.dto;

public class ErrorDTO {
    private String name;
    private String descricao;

    public ErrorDTO(String name, String descricao) {
        this.name = name;
        this.descricao = descricao;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
