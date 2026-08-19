package ru.mealcard.utils.config;

public final class PropertyKeys {
    private PropertyKeys() {}

    public static final String FILE_PROPERTY = "/app.properties";

    // server
    public static final String PORT_KEY = "server.port";
    public static final String POOL_SIZE_KEY = "server.pool-size";

    // timezone
    public static final String ZONE_KEY = "timezone.zone";

    // generator
    public static final String CHARSET_KEY = "GeneratorFile.charset";
    public static final String OUTPUT_DIR_KEY = "GeneratorFile.output-dir";

    // send
    public static final String SEND_URL_KEY = "send.url";
    public static final String SEND_CHUNK_SIZE_KEY = "send.size";

    // grpc
    public static final String GRPC_HOST_KEY = "grpc.host";
    public static final String GRPC_PORT_KEY = "grpc.port";
}