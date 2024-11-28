package itmo.tuchin.nikitin.ejb.client;

import itmo.tuchin.nikitin.ejb.dto.EnumDTO;
import itmo.tuchin.nikitin.ejb.dto.TotalDTO;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;

import java.io.Serializable;
import java.util.List;

@RequestScoped
public class PeopleClient implements Serializable {
    private static final String REST_URI
            = "https://172.20.0.5:8089/api/v1";

    private Client client;

    @PostConstruct
    private void getClient() {
        System.out.println("created client");
        client = ClientBuilder.newBuilder()
                .hostnameVerifier((s, session) -> true)
                .build();
    }

    @PreDestroy
    private void closeClient() {
        System.out.println("closed client");
        client.close();
    }

    public int getTotal() {
        Response responseTotal = client.target(REST_URI)
                .path("people")
                .queryParam("limit", 1)
                .request()
                .get();
        return responseTotal.readEntity(TotalDTO.class).getTotal();
    }

    public int getCountByHairColor(String color) {
        Response responseTotalByColor = client.target(REST_URI)
                .path("people")
                .queryParam("hairColor", color)
                .request()
                .get();
        return responseTotalByColor.readEntity(TotalDTO.class).getTotal();
    }

    public int getCountByNationalityAndHairColor(String nationality, String color) {
        Response responseCount = client.target(REST_URI)
                .path("people")
                .queryParam("limit", 1)
                .queryParam("nationality", nationality)
                .queryParam("hairColor", color)
                .request()
                .get();
        return responseCount.readEntity(TotalDTO.class).getTotal();
    }

    public List<String> getColors() {
        Response responseCount = client.target(REST_URI)
                .path("color")
                .request()
                .get();
        return responseCount.readEntity(EnumDTO.class).getData();
    }

    public List<String> getCountries() {
        Response responseCount = client.target(REST_URI)
                .path("country")
                .request()
                .get();
        return responseCount.readEntity(EnumDTO.class).getData();
    }
}
