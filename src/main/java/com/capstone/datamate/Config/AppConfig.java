package com.capstone.datamate.Config;

import javax.sql.DataSource;
import com.capstone.datamate.GeminiAPI.GeminiInterface;
import com.capstone.datamate.OpenAI.OpenAIInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@ComponentScan("com.capstone.datamate")
public class AppConfig {

    @Bean
    public DataSource dataSource(
            @Value("${DATABASE_HOST}") String host,
            @Value("${DATABASE_PORT}") String port,
            @Value("${DATABASE_NAME}") String database,
            @Value("${DATABASE_USER}") String username,
            @Value("${DATABASE_PASSWORD}") String password)
    {

//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://localhost:3306/datamate"); // was datamate initially
//        dataSource.setUsername("root");
//        dataSource.setPassword(""); // was root initially
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(String.format("jdbc:mysql://%s:%s/%s?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf8", host, port, database));
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public JdbcTemplate getjdbctemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
    }

//     DataMate V2 for Increment
    @Bean
    public RestClient geminiRestClient(@Value("${gemini.baseurl}") String baseUrl,
                                       @Value("${googleai.api.key}") String apiKey) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("x-goog-api-key", apiKey)
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Accept", "application/json")
                .build();
    }

    @Bean
    public GeminiInterface geminiInterface(@Qualifier("geminiRestClient") RestClient client) {
        RestClientAdapter adapter = RestClientAdapter.create(client);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(GeminiInterface.class);
    }

    @Bean
    public RestClient openAIRestClient(@Value("${openai.api.url}") String baseUrl,
                                       @Value("${openai.api.key}") String apiKey) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Accept", "application/json")
                .build();
    }

    @Bean
    public OpenAIInterface openAIInterface(@Qualifier("openAIRestClient") RestClient client) {
        RestClientAdapter adapter = RestClientAdapter.create(client);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(OpenAIInterface.class);
    }
}
