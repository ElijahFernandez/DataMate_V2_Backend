package com.capstone.datamate.Config;

import javax.sql.DataSource;

import com.capstone.datamate.GeminiAPI.GeminiInterface;
import com.capstone.datamate.OpenAI.OpenAIInterface;
import com.capstone.datamate.OpenAI.OpenAIRecords;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestClient;
//import org.springframework.web.service.RestClientBuilder;
import org.springframework.web.client.support.RestClientAdapter;
//import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@ComponentScan("com.capstone.datamate")
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/datamate"); // was datamate initially
        dataSource.setUsername("root");
        dataSource.setPassword(""); // was root initially

        return dataSource;
    }

    @Bean
    public JdbcTemplate getjdbctemplate() {
    return new JdbcTemplate(dataSource());
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
