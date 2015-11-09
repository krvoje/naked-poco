package org.nakedpojo.configuration;

import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final String PROPS_FILENAME="/nakedpojo.properties";

    private static final String TARGET_FILENAME_KEY="target.filename";
    private static final String TARGET_FILENAME_DEFAULT="naked_all.js";

    public enum GenerationStrategy {
        MULTIPLE_FILES("multiple"),
        SINGLE_FILE("single");

        public static final String KEY = "generation.strategy";
        public static final GenerationStrategy DEFAULT = MULTIPLE_FILES;

        private final String VALUE;

        private GenerationStrategy(String generationStrategyValue) {
            this.VALUE = generationStrategyValue;
        }

        public static GenerationStrategy parse(String generationStrategyValue) {
            if(generationStrategyValue == null)
                return GenerationStrategy.DEFAULT;

            if(generationStrategyValue.equals(MULTIPLE_FILES.VALUE))
                return MULTIPLE_FILES;
            else if(generationStrategyValue.equals(SINGLE_FILE.VALUE))
                return SINGLE_FILE;
            else
                // TODO: warning or exception
                return DEFAULT;
        }
    }

    public final GenerationStrategy generationStrategy;
    public final String targetFilename;

    public Config() {
        Properties props = new Properties();
        try {
            InputStream is = Config.class.getResourceAsStream(PROPS_FILENAME);
            props.load(is);
        } catch (Exception e) {
            // TODO: Emit warning
            System.out.println("Did not load nakedpojo.properties, falling back to defaults.");
            this.generationStrategy = GenerationStrategy.DEFAULT;
            this.targetFilename = TARGET_FILENAME_DEFAULT;
            return;
        }

        this.generationStrategy = GenerationStrategy.parse(props.getProperty(GenerationStrategy.KEY, GenerationStrategy.DEFAULT.VALUE));
        this.targetFilename = props.getProperty(TARGET_FILENAME_KEY, TARGET_FILENAME_DEFAULT);
    }
}
