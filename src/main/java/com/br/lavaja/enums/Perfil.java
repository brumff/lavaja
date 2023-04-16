package com.br.lavaja.enums;

public enum Perfil {
    LAVACAR(1, "ROLE_LAVACAR"),
    DONOCARRO(2, "ROLE_DONOCARRO");

    private int cod;
    private String descricao;

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    private Perfil(int cod, String descricao){
        this.cod = cod;
        this.descricao = descricao;
    }

    public static Perfil toEnum(Integer cod){

        if (cod == null){
            return null;
        }

        for (Perfil x : Perfil.values()){
            if (cod.equals(x.getCod())){
                return x;
            }
        }
        throw new IllegalArgumentException("Id inválido" + cod);
    }
}
