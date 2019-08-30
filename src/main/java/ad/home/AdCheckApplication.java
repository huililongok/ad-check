package ad.home;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan // 扫描filter servlet
public class AdCheckApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdCheckApplication.class, args);
    }



}

