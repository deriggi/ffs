package sony.deriggi.ffs.dao;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Repository;

import sony.deriggi.ffs.dto.ApiMessage;
import sony.deriggi.ffs.dto.FeatureStatus;
import sony.deriggi.ffs.json.JsonBodyHandler;

@Repository
public class FfsDao {

    private static final String URL = "http://localhost:12300/";
    private static final String PATH = "featureflags";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";

    public FeatureStatus[] createOrUpdate(FeatureStatus fs) {

        StringBuilder sb = new StringBuilder();
        sb.append(URL).append(PATH);
        HttpClient client = HttpClient.newBuilder().version(Version.HTTP_2).build();
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody;
        FeatureStatus[] responseStatus = null;
        try {

            requestBody = objectMapper.writeValueAsString(fs);
            HttpRequest request = HttpRequest.newBuilder().header(CONTENT_TYPE, APPLICATION_JSON)
                    .uri(URI.create(sb.toString()))
                    .POST(BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response;
            String responseBody = null;

            response = client.send(request, BodyHandlers.ofString());
            responseBody = response.body();

            if (response.statusCode() >= 400) {
                handlePostException(responseBody);
                responseStatus= null;
            }else if(response.statusCode() >= 200){
                responseStatus= handleOkResponse(responseBody);
            }else{
                responseStatus= null;
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            responseStatus = null;
        }

        return responseStatus;

    }

    private ApiMessage handlePostException(String responseBody) {
        TypeReference<ApiMessage> typeRef = new TypeReference<ApiMessage>() {};
        ObjectMapper mapper = new ObjectMapper();
        ApiMessage map;
        try {
            map = mapper.readValue(responseBody, typeRef);
            return map;
            
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private FeatureStatus[] handleOkResponse(String responseBody) {
        TypeReference<FeatureStatus[]> typeRef = new TypeReference<FeatureStatus[]>() {
        };
        ObjectMapper mapper = new ObjectMapper();

        FeatureStatus[] features;
        try {
            features = mapper.readValue(responseBody, typeRef);
            return features;
            
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public FeatureStatus[] fetchStatus() {

        StringBuilder sb = new StringBuilder();
        sb.append(URL).append(PATH);
        HttpClient client = HttpClient.newBuilder().version(Version.HTTP_2).build();
        FeatureStatus[] allFs;

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(sb.toString()))
            .header(CONTENT_TYPE, APPLICATION_JSON).build();

        try {
            allFs = client.send(request, new JsonBodyHandler<>(FeatureStatus[].class)).body().get();
            

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            allFs = new FeatureStatus[0];
        }
        
        return allFs;


    }

    public static void main(String[] agrs){
        // new FfsDao().fetchStatus();
        
        // new FfsDao().createOrUpdate(new FeatureStatus("Identity_Information", 8));
        new FfsDao().createOrUpdate(new FeatureStatus(null, 8));

    }
    
}
