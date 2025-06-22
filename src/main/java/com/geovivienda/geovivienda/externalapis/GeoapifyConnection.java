package com.geovivienda.geovivienda.externalapis;

import com.geovivienda.geovivienda.dtos.DireccionDTO;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

@Getter
@Setter
public class GeoapifyConnection {
    private static final String API_KEY = "a638798ea1c142ef85837a2036970f91";
    private static final String SEARCH_PARAMS = "&lang=es&limit=1&type=street&format=json";
    private static final String URL_GEOCODE = "https://api.geoapify.com/v1/geocode/search?";
    private static final String URL_REVERSE_GEOCODE = "https://api.geoapify.com/v1/geocode/reverse?";
    private String address;

    private DireccionDTO dto;

    public GeoapifyConnection() {
        this.address = "Avenida Primavera, Santiago de Surco, Lima Metropolitana 15023, Peru";
    }

    public GeoapifyConnection(String address) {
        this.address = address;
    }

    public GeoapifyConnection(DireccionDTO dto) {
        this.address = dto.getDireccion();
        this.dto = dto;
    }

    private JSONObject getResponseFromAPI(String urlQuery) throws Exception {
        URL url = new URL(urlQuery);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestProperty("Accept", "application/json");
        if (http.getResponseCode() == HttpURLConnection.HTTP_OK) { // Check for HTTP 200
            BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
            StringBuilder responseBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBody.append(line);
            }
            reader.close();
            JSONObject json = new JSONObject(responseBody.toString());
            return json.getJSONArray("results").getJSONObject(0);
        } else {
            System.out.println("Error: " + http.getResponseCode() + " " + http.getResponseMessage());
        }
        http.disconnect();
        return null;
    }

    public DireccionDTO getDireccionDTOAsociada() throws Exception {
        String urlQuery = address != null ? URL_GEOCODE + "text=" + address.replaceAll(" ", "%20")
                + SEARCH_PARAMS + "&apiKey=" + API_KEY
                : URL_REVERSE_GEOCODE + "lat=" + dto.getLatitud() + "&lon=" + dto.getLongitud()
                + SEARCH_PARAMS + "&apiKey=" + API_KEY;
        JSONObject result = this.getResponseFromAPI(urlQuery);
        String formattedAddress = result.get("formatted").toString();
        BigDecimal latitud = new BigDecimal(result.get("lat").toString());
        BigDecimal longitud = new BigDecimal(result.get("lon").toString());

        return new DireccionDTO(formattedAddress, latitud, longitud);
    }

}
