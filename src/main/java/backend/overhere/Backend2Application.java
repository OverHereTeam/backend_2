package backend.overhere;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Paths;
import java.util.Objects;

@SpringBootApplication
public class Backend2Application {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		settingEnv(dotenv);

		SpringApplication.run(Backend2Application.class, args);
	}

	private static void settingEnv(Dotenv dotenv) {
		// 환경 변수를 시스템 속성에 설정
		System.setProperty("KAKAO_CLIENT_ID", Objects.requireNonNull(dotenv.get("KAKAO_CLIENT_ID")));
		System.setProperty("KAKAO_CLIENT_SECRET", Objects.requireNonNull(dotenv.get("KAKAO_CLIENT_SECRET")));
		System.setProperty("KAKAO_REDIRECT_URI", Objects.requireNonNull(dotenv.get("KAKAO_REDIRECT_URI")));

		System.setProperty("NAVER_CLIENT_ID", Objects.requireNonNull(dotenv.get("NAVER_CLIENT_ID")));
		System.setProperty("NAVER_CLIENT_SECRET", Objects.requireNonNull(dotenv.get("NAVER_CLIENT_SECRET")));
		System.setProperty("NAVER_REDIRECT_URI", Objects.requireNonNull(dotenv.get("NAVER_REDIRECT_URI")));

		System.setProperty("GOOGLE_CLIENT_ID", Objects.requireNonNull(dotenv.get("GOOGLE_CLIENT_ID")));
		System.setProperty("GOOGLE_CLIENT_SECRET", Objects.requireNonNull(dotenv.get("GOOGLE_CLIENT_SECRET")));
		System.setProperty("GOOGLE_REDIRECT_URI", Objects.requireNonNull(dotenv.get("GOOGLE_REDIRECT_URI")));

		System.setProperty("JWT_SECRET", Objects.requireNonNull(dotenv.get("JWT_SECRET")));


		System.setProperty("DATABASE_NAME", Objects.requireNonNull(dotenv.get("DATABASE_NAME")));
		System.setProperty("DATABASE_USERNAME", Objects.requireNonNull(dotenv.get("DATABASE_USERNAME")));
		System.setProperty("DATABASE_PASSWORD", Objects.requireNonNull(dotenv.get("DATABASE_PASSWORD")));

		System.setProperty("LOCAL_DDL_TYPE", Objects.requireNonNull(dotenv.get("LOCAL_DDL_TYPE")));

		System.setProperty("API_KEY", Objects.requireNonNull(dotenv.get("API_KEY")));
		System.setProperty("GPT_KEY", Objects.requireNonNull(dotenv.get("GPT_KEY")));
	}

}
