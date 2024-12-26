package common;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static common.ConstantUtils.BASE_URL;
import static common.ConstantUtils.PORT;

public class EnvConfig {
    private static final Log log = LogFactory.getLog(EnvConfig.class);
    private static Dotenv dotenv;

    public static Dotenv getDotEnv(){
        String env = System.getenv("TEST_ENV");
        if(env==null){
            env = "local.env";
        }
        dotenv = Dotenv
                .configure()
                .directory("/env")
                .filename(env)
                .load();
        return dotenv;
    }

    ///Test getDotEnv()
//    public static void main(String[] args) {
//        System.out.println(BASE_URL + ":" + PORT);
//    }
}