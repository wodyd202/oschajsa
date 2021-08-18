package com.ljy.oschajsa.oschajsa.user.command.infrastructure;

import com.ljy.oschajsa.oschajsa.user.command.domain.AddressHelper;
import com.ljy.oschajsa.oschajsa.user.command.domain.AddressInfo;
import com.ljy.oschajsa.oschajsa.user.command.domain.Coordinate;
import com.ljy.oschajsa.oschajsa.user.command.domain.InvalidAddressException;
import org.springframework.beans.factory.annotation.Value;
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

    @Override
    public AddressInfo getAddressInfoFrom(Coordinate coordinate) throws InvalidAddressException {
        RestTemplate restTpl = new RestTemplate();
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

            HashMap<?, ?> meta = (HashMap<?, ?>) response.get("meta");
            List<HashMap<?, ?>> documents = (List<HashMap<?, ?>>) response.get("documents");
            int totalCount = Integer.parseInt(meta.get("total_count").toString());
            if(existResult(totalCount)){
                HashMap<?, ?> address = (HashMap<?, ?>) documents.get(0).get("address");
                String addressName = address.get("address_name").toString();
                String region1DepthName = address.get("region_1depth_name").toString();
                String region2DepthName = address.get("region_2depth_name").toString();
                String region3DepthName = address.get("region_3depth_name").toString();
                return AddressInfo.withCityProvinceDong(region1DepthName, region2DepthName, region3DepthName);
            }
            throw new InvalidAddressException("invalid coordinate");
        }catch (RestClientException e){
            throw new InvalidAddressException("rest client exception");
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

    private MultiValueMap<String, String> createParams(Coordinate coordinate){
        MultiValueMap<String, String> params = new HttpHeaders();
        params.add("x", Double.toString(coordinate.getLongtitude()));
        params.add("y", Double.toString(coordinate.getLettitude()));
        return params;
    }
}
