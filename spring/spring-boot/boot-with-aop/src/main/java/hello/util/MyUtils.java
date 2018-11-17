package hello.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyUtils {
    public static final LoadingCache<String, String> cache = CacheBuilder
            .newBuilder()
            .recordStats()
            .maximumSize(10)
            .expireAfterAccess(2, TimeUnit.MINUTES)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String key) {
                    return key.toUpperCase();
                }
            });

    public static void testBasicCache() {
        try {
            cache.get("first");
            cache.get("second");
            cache.get("third");
            cache.get("forth");
        } catch (ExecutionException e) {
            log.info("Retrieving failed!");
        }
        getMapMemorySize(cache.asMap());
    }

    public static int getMapMemorySize(Map map) {
        int byteSize = 0;
        try {
            List<Map.Entry> entries = ImmutableList.copyOf(map.entrySet());
            String cacheContent = Arrays.deepToString(entries.toArray());
            log.info("Cache content: {}", cacheContent);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(cacheContent);
            oos.close();
            byteSize = baos.size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteSize;
    }
}
