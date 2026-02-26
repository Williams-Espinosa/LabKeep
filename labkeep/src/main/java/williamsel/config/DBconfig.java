package williamsel.config;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariConfig;
import javax.sql.DataSource;
import io.github.cdimascio.dotenv.Dotenv;

public class DBconfig {

    private static HikariDataSource dataSource;

    public static DataSource getDataSource() {

        if (dataSource == null) {

            Dotenv dotenv = Dotenv.load();

            String host = dotenv.get("DB_HOST");
            String dbName = dotenv.get("DB_NAME");
            String user = dotenv.get("DB_USER");
            String pass = dotenv.get("DB_PASS");
            String port = dotenv.get("DB_PORT");

            String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbName;

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(url);
            config.setUsername(user);
            config.setPassword(pass);
            config.setDriverClassName("org.postgresql.Driver");

            dataSource = new HikariDataSource(config);
        }
        return dataSource;
    }
}