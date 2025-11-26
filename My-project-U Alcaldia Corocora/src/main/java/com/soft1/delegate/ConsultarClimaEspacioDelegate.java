package com.soft1.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component("consultarClimaEspacioDelegate")
public class ConsultarClimaEspacioDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = new URI("https://api.open-meteo.com/v1/forecast?latitude=4.15&longitude=-73.63&hourly=temperature_2m");
        HttpRequest req = HttpRequest.newBuilder(uri).GET().build();
        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        execution.setVariable("climaJson", resp.body());
        System.out.println("Clima consultado para el espacio: " + resp.statusCode());
    }
}

