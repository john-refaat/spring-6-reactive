package guru.springframework.spring6reactive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.HttpLogFormatter;
import org.zalando.logbook.Sink;
import org.zalando.logbook.json.JsonHttpLogFormatter;
import org.zalando.logbook.logstash.LogstashLogbackSink;

/**
 * Author:john
 * Date:07/02/2025
 * Time:03:23
 */
@Configuration
public class LogbookConfig {

    @Bean
    public Sink LogbookLogStash() {
        HttpLogFormatter formatter = new JsonHttpLogFormatter();
        return new LogstashLogbackSink(formatter);
    }
}
