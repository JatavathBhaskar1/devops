package com.techie.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.mongodb.ReadPreference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.cloud-name}")
    private String cloudName;
    @Value("${cloudinary.api-key}")
    private String apikey;
    @Value("${cloudinary.api-secret}")
    private String apiSecret;


    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap("cloud_name", cloudName, "api_key", apikey, "api_secret", apiSecret));

    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDbFactory) {
        MongoTemplate template = new MongoTemplate(mongoDbFactory);

        // Configure to avoid locale/collation issues
        template.setReadPreference(ReadPreference.primary());

        return template;
    }
}
