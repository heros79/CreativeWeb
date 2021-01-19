package am.creative.example.config;

import am.creative.example.dao.CommentRepository;
import am.creative.example.dao.NotificationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;
import java.util.concurrent.Executor;

/**
 * @author David Karchikyan
 * Класс настроект программы
 * */

@Configuration
public class AppConfiguration {

    @Bean
    RepositoriesBeen repoBeans (CommentRepository commentRepository, NotificationRepository notificationRepository) {
        return new RepositoriesBeen(commentRepository, notificationRepository);
    }
    //TODO Конфигурация для асинхронной работы
    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(2000);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Notification-");
        executor.initialize();
        return executor;
    }
    //TODO Конфигурация для H2 базы, в продакшн можно удалить
    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder
                .setType(EmbeddedDatabaseType.H2)
                .addScript("data.sql")
                .build();
        return db;
    }
}