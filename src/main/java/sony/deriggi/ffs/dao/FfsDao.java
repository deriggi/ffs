package sony.deriggi.ffs.dao;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Repository;

import sony.deriggi.ffs.dto.ApiDatum;
import sony.deriggi.ffs.dto.ApiMessage;
import sony.deriggi.ffs.dto.ErrorMessage;
import sony.deriggi.ffs.dto.FeatureStatus;
import sony.deriggi.ffs.json.JsonBodyHandler;

@Repository
public class FfsDao {

    private static final String URL = "http://localhost:12300/";
    private static final String PATH = "featureflags";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";

    public ApiMessage createOrUpdate(final FeatureStatus fs) {

        final StringBuilder sb = new StringBuilder();
        sb.append(URL).append(PATH);
        final HttpClient client = HttpClient.newBuilder().version(Version.HTTP_2).build();
        final ObjectMapper objectMapper = new ObjectMapper();
        String requestBody;
        final ApiMessage message = new ApiMessage();

        try {

            requestBody = objectMapper.writeValueAsString(fs);
            final HttpRequest request = HttpRequest.newBuilder().header(CONTENT_TYPE, APPLICATION_JSON)
                    .uri(URI.create(sb.toString()))
                    .POST(BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response;
            String responseBody = null;

            response = client.send(request, BodyHandlers.ofString());
            responseBody = response.body();
            message.setStatusCode(response.statusCode());

            if (response.statusCode() >= 400) {
                final List<ErrorMessage> dataItems = new ArrayList<>(1);
                final ErrorMessage error = handlePostException(responseBody);
                dataItems.add(error);
                message.setDataItems(dataItems);
            }else if(response.statusCode() >= 200){
                final List<FeatureStatus> dataItems = Arrays.asList(handleOkResponse(responseBody));
                message.setDataItems(dataItems);
            }else{
                message.setDataItems(new ArrayList<>(0));;
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            message.setStatusCode(500);
            message.setDataItems(new ArrayList<>(0));;
        }

        return message;
    }

    private ErrorMessage handlePostException(final String responseBody) {
        final TypeReference<ErrorMessage> typeRef = new TypeReference<ErrorMessage>() {};
        final ObjectMapper mapper = new ObjectMapper();
        ErrorMessage map;
        try {
            map = mapper.readValue(responseBody, typeRef);
            return map;
            
        } catch (final JsonMappingException e) {
            e.printStackTrace();
        } catch (final JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private FeatureStatus[] handleOkResponse(final String responseBody) {
        final TypeReference<FeatureStatus[]> typeRef = new TypeReference<FeatureStatus[]>() {
        };
        final ObjectMapper mapper = new ObjectMapper();

        FeatureStatus[] features;
        try {
            features = mapper.readValue(responseBody, typeRef);
            return features;
            
        } catch (final JsonMappingException e) {
            e.printStackTrace();
        } catch (final JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ApiMessage fetchStatus() {

        final StringBuilder sb = new StringBuilder();
        sb.append(URL).append(PATH);
        final HttpClient client = HttpClient.newBuilder().version(Version.HTTP_2).build();
        FeatureStatus[] allFs;

        final HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(sb.toString()))
            .header(CONTENT_TYPE, APPLICATION_JSON).build();
        final ApiMessage message = new ApiMessage();

        try {
            HttpResponse<Supplier<FeatureStatus[]>> response = client.send(request, new JsonBodyHandler<>(FeatureStatus[].class));
            allFs = response.body().get();
            message.setDataItems(Arrays.asList(allFs));
            message.setStatusCode(response.statusCode());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            message.setStatusCode(500);
            message.setDataItems(new ArrayList<>(0));
        }
        
        return message;

    }
    
}
