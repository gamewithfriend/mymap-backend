package com.sillimfive.mymap.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aws.s3")
@Getter @Setter
public class AwsProperties {
    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String url;
}
