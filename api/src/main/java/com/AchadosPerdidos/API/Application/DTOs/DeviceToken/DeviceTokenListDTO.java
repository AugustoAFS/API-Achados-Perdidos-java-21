package com.AchadosPerdidos.API.Application.DTOs.DeviceToken;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO para lista de tokens de dispositivos")
public class DeviceTokenListDTO {
    @Schema(description = "Lista de tokens de dispositivos")
    private List<DeviceTokenDTO> deviceTokens;

    @Schema(description = "Total de tokens", example = "10")
    private Integer total;

    public DeviceTokenListDTO() {
        // Construtor padrão para frameworks de serialização
    }

    public DeviceTokenListDTO(List<DeviceTokenDTO> deviceTokens) {
        this.deviceTokens = deviceTokens;
        this.total = deviceTokens != null ? deviceTokens.size() : 0;
    }

    public List<DeviceTokenDTO> getDeviceTokens() {
        return deviceTokens;
    }

    public void setDeviceTokens(List<DeviceTokenDTO> deviceTokens) {
        this.deviceTokens = deviceTokens;
        this.total = deviceTokens != null ? deviceTokens.size() : 0;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}

