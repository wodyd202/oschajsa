package com.ljy.oschajsa.core.infrastructure;

import com.ljy.oschajsa.core.application.AddressHelper;
import com.ljy.oschajsa.core.object.AddressInfo;
import com.ljy.oschajsa.core.object.Coordinate;
import com.ljy.oschajsa.core.object.InvalidAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

@Profile("!test")
@Component
public class KakaoMapAddressHelper implements AddressHelper {
    @Value("${kakao.openAPI.service-key}")
    private String serviceKey;

    @Value("${kakao.openAPI.path}")
    private String path;

    @Value("${kakao.openAPI.protocol}")
    private String protocol;

    @Value("${kakao.openAPI.host}")
    private String host;

    @Autowired
    private RestTemplate restTpl;

    private final String META = "meta";
    private final String DOCUMENTS = "documents";
    private final String TOTAL_COUNT = "total_count";
    private final String ADDRESS = "address";
    private final String REGION_1_DEPTH_NAME = "region_1depth_name";
    private final String REGION_2_DEPTH_NAME = "region_2depth_name";
    private final String REGION_3_DEPTH_NAME = "region_3depth_name";
    private final String INVALID_COORDINATE = "invalid coordinate";
    private final String REST_CLIENT_EXCEPTION = "rest client exception";

    @Override
    public AddressInfo getAddressInfoFrom(Coordinate coordinate) throws InvalidAddressException {
        HttpHeaders header = createHeader();
        MultiValueMap<String,String> params = createParams(coordinate);

        URI uri = UriComponentsBuilder.newInstance()
                .scheme(protocol)
                .host(host)
                .path(path)
                .queryParams(params)
                .build()
                .encode().toUri();
        try{
            HashMap<?,?> response = restTpl.exchange(uri, HttpMethod.GET, new HttpEntity<>(header), HashMap.class)
                    .getBody();

            HashMap<?, ?> meta = (HashMap<?, ?>) response.get(META);
            List<HashMap<?, ?>> documents = (List<HashMap<?, ?>>) response.get(DOCUMENTS);
            int totalCount = Integer.parseInt(meta.get(TOTAL_COUNT).toString());
            if(existResult(totalCount)){
                HashMap<?, ?> address = (HashMap<?, ?>) documents.get(0).get(ADDRESS);
                String region1DepthName = address.get(REGION_1_DEPTH_NAME).toString();
                String region2DepthName = address.get(REGION_2_DEPTH_NAME).toString();
                String region3DepthName = address.get(REGION_3_DEPTH_NAME).toString();
                return AddressInfo.withCityProvinceDong(region1DepthName, region2DepthName, region3DepthName);
            }
            throw new InvalidAddressException(INVALID_COORDINATE);
        }catch (RestClientException e){
            throw new InvalidAddressException(REST_CLIENT_EXCEPTION);
        }
    }

    private boolean existResult(int totalCount) {
        return totalCount == 1;
    }

    private HttpHeaders createHeader(){
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.AUTHORIZATION, serviceKey);
        return header;
    }

    private final String X = "x";
    private final String Y = "y";
    private MultiValueMap<String, String> createParams(Coordinate coordinate){
        MultiValueMap<String, String> params = new HttpHeaders();
        params.add(X, Double.toString(coordinate.getLongtitude()));
        params.add(Y, Double.toString(coordinate.getLettitude()));
        return params;
    }
}
