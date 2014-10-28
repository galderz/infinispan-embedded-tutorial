package org.infinispan.tutorial.embedded;

import java.util.concurrent.TimeUnit;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

public class EmbeddedTutorial {

   public static void main(String[] args) throws InterruptedException {
      // Create a default local cache manager
      DefaultCacheManager cacheManager = new DefaultCacheManager();

      // Obtain the default cache
      Cache<String, String> cache = cacheManager.getCache();

      // Insert an entry into the cache
      cache.put("key", "value");

      // Read an entry from the cache
      String value = cache.get("key");

      // Print it out
      System.out.printf("key ==> %s\n", value);

      // Insert a mortal entry, i.e. an entry which will expire
      cache.put("mortal", "I am going to die", 1, TimeUnit.SECONDS);

      // Print it out
      System.out.printf("mortal ==> %s\n", cache.get("mortal"));

      // Let's wait a couple of seconds
      System.out.println("Sleeping for 2 seconds...");
      Thread.sleep(TimeUnit.SECONDS.toMillis(2));

      // Print it out again. It will have died
      System.out.printf("mortal ==> %s\n", cache.get("mortal"));

      // Stop the cache manager and release all resources
      cacheManager.stop();
   }
}
