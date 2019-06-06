package com.yhml.bd.bd.es;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;

/**
 * @author: Jfeng
 * @date: 2018/7/5
 */
public class ESClientFactory {

    private TransportClient client;

    private ESConfig config;

    private ESClientFactory(ESConfig config) {
        this.config = config;
    }

    public static ESClientFactory build(ESConfig config) {
        if (StringUtils.isBlank(config.getAddressHostPorts())) {
            throw new RuntimeException("addressHostPorts can not be empty.");
        }

        if (StringUtils.isBlank(config.getClusterName())) {
            throw new RuntimeException("clusterName is empty.");
        }

        return new ESClientFactory(config);
    }

    // @formatter:off
    public synchronized TransportClient getClient() {
        if (client != null){
            return client;
        }

        String xpackSecurityUserValue = config.getUserName() + ":" + config.getPassword();

        Settings settings = Settings.builder()
                .put("cluster.name", config.getClusterName())
                .put("xpack.security.user", xpackSecurityUserValue)
                .put("client.transport.sniff", Boolean.FALSE)
                .build();

        client = new PreBuiltXPackTransportClient(settings);

        String[] addressArray = config.getAddressHostPorts().split(",");

        for (String esAddressHostPort : addressArray) {
            String[] a = esAddressHostPort.split(":");
            try {
                client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(a[0]), NumberUtils.toInt(a[1], 9300)));
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        }

        return client;
    }


}
