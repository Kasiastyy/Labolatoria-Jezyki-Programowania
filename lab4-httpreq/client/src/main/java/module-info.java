module client {
    requires java.net.http;
    requires org.json;

    exports org.client.model;
    exports org.client.service;
    exports org.client.api;
}
