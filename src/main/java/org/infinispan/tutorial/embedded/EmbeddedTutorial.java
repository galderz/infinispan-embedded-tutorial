package org.infinispan.tutorial.embedded;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

public class EmbeddedTutorial {

   public static void main(String[] args) {
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

      // Stop the cache manager and release all resources
      cacheManager.stop();
   }
}
