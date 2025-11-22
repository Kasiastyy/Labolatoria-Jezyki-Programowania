package org.client.service;

import org.client.api.TranstatApiClient;
import org.client.api.TranstatEndpoints;
import org.client.model.ShipTrafficEntry;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.IOException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TrafficStatsService {

    private final TranstatApiClient apiClient;

    // cache żeby nie robić zbyt wielu zapytań /api/v1/C007MInd117p
    private List<ShipTrafficEntry> cachedAll;

    public TrafficStatsService(TranstatApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public synchronized List<ShipTrafficEntry> getAll() {
        if (cachedAll == null) {
            cachedAll = fetchAndParse(TranstatEndpoints.base());
        }
        return cachedAll;
    }

    public List<ShipTrafficEntry> getByPort(String portName) {
        String url = TranstatEndpoints.forMapParam(portName);
        return fetchAndParse(url);
    }

    public List<ShipTrafficEntry> getByDirectionPl(String directionPl) {
        String url = TranstatEndpoints.forMultiParamPl(directionPl);
        return fetchAndParse(url);
    }

    public List<String> getAvailablePorts() {
        return getAll().stream()
                .map(ShipTrafficEntry::getPortName)
                .filter(Objects::nonNull)
                .distinct()
                .sorted(String::compareToIgnoreCase)
                .collect(Collectors.toList());
    }

    public List<String> getAvailableDirectionsPl() {
        return getAll().stream()
                .map(ShipTrafficEntry::getDirectionNamePl)
                .filter(Objects::nonNull)
                .distinct()
                .sorted(String::compareToIgnoreCase)
                .collect(Collectors.toList());
    }

    private List<ShipTrafficEntry> fetchAndParse(String url) {
        try {
            String json = apiClient.get(url, Collections.emptyMap());
            JSONArray array = new JSONArray(json);
            List<ShipTrafficEntry> result = new ArrayList<>(array.length());
            System.out.println("URL = " + url);
            System.out.println("API RESPONSE = " + json);

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                long id = obj.optLong("id");
                String dateStr = obj.optString("date", null);
                String mapParam = obj.optString("mapParam", null);
                String multiParam = obj.optString("multiParam", null);
                String multiParamPl = obj.optString("multiParamPl", null);
                double value = obj.optDouble("value", Double.NaN);

                LocalDate date = null;
                if (dateStr != null && !dateStr.isEmpty()) {
                    date = java.time.LocalDateTime.parse(dateStr).toLocalDate();
                }


                ShipTrafficEntry entry = new ShipTrafficEntry(
                        id,
                        date,
                        mapParam,
                        multiParam,
                        multiParamPl,
                        value
                );
                result.add(entry);
            }

            return result;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error fetching data from TranStat API", e);
        }
    }
}
