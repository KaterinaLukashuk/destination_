package com;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.sap.ea.eacp.okhttp.destinationclient.OkHttpDestination;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.InternalServerErrorException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class DestinationFactory {

    private static final Logger LOG = LoggerFactory.getLogger(DestinationFactory.class);
    private static final int CONNECT_TIMEOUT = 5;
    private static final int READ_TIMEOUT = 2;
    private static final int WRITE_TIMEOUT = 2;
    private static final int EXPIRE_AFTER_WRITE_TIMEOUT = 5;
    private final LoadingCache<String, OkHttpDestination> cache;

    private final OkHttpClient okHttpClient;

    public DestinationFactory() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(LOG::info);
        logging.setLevel(Level.BODY);

        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MINUTES)
                .readTimeout(READ_TIMEOUT, TimeUnit.MINUTES)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.MINUTES)
                //               .cookieJar(new OkHttpCookieJar())
                .build();

        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_AFTER_WRITE_TIMEOUT, TimeUnit.MINUTES)
                .build(new CacheLoader<String, OkHttpDestination>() {
                    @Override
                    public OkHttpDestination load(final String destination) throws Exception {
                        LOG.trace("Loading destination for {}", destination);
                        return OkHttpDestination.create(destination, okHttpClient);
                    }
                });
    }

    public OkHttpDestination provide(String destinationName) {
        try {

            return cache.get(destinationName);

        } catch (ExecutionException e) {
            throw new InternalServerErrorException("Error creating destination client", e);
        }
    }
}
