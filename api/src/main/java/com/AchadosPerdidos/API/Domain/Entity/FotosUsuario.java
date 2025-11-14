package com.AchadosPerdidos.API.Domain.Entity;


public class FotosUsuario {
    private Integer usuarioId;
    private Integer fotoId;

    public FotosUsuario() {}

    public FotosUsuario(Integer usuarioId, Integer fotoId) {
        this.usuarioId = usuarioId;
        this.fotoId = fotoId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getFotoId() {
        return fotoId;
    }

    public void setFotoId(Integer fotoId) {
        this.fotoId = fotoId;
    }

}

