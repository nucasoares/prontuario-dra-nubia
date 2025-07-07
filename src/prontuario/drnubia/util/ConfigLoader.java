package prontuario.drnubia.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    private final static String ARQUIVO = "lib/config.properties";
    private final static Properties PROP = new Properties();

    static {
        load(ARQUIVO);
    }

    public static void load(String arquivo) {
        try (FileInputStream fis = new FileInputStream(arquivo)) {
            PROP.load(fis);
        } catch (IOException e) {
            System.out.println("Erro ao carregar arquivo de configuração:");
            e.printStackTrace();
        }
    }

    public static String getValor(String chave) {
        return PROP.getProperty(chave);
    }
}