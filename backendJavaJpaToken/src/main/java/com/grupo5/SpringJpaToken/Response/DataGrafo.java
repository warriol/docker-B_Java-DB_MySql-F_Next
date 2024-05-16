package com.grupo5.SpringJpaToken.Response;

import com.grupo5.SpringJpaToken.Dto.PreviaDataDireccion;
import com.grupo5.SpringJpaToken.Dto.PreviasDataAsignatura;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Data
public class DataGrafo {
    private String message;
    private String evento;
    private List<PreviasDataAsignatura> dataAsignatura;
    private List<PreviaDataDireccion> dataPuntero;

    public DataGrafo(String message, String evento, List<PreviasDataAsignatura> dataAsignatura, List<PreviaDataDireccion> dataPuntero) {
        this.message = message;
        this.evento = evento;
        this.dataAsignatura = dataAsignatura;
        this.dataPuntero = dataPuntero;
    }

    public String getMessage() {
        return message;
    }

    public String getEvento() {
        return evento;
    }

    public List<PreviasDataAsignatura> getDataAsignatura() {
        return dataAsignatura;
    }

    public List<PreviaDataDireccion> getDataPuntero() {
        return dataPuntero;
    }


}
