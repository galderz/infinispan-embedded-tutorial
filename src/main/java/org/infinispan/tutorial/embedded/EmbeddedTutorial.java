package org.infinispan.tutorial.embedded;

import java.util.concurrent.TimeUnit;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryCreated;
import org.infinispan.notifications.cachelistener.event.CacheEntryCreatedEvent;

public class EmbeddedTutorial {

   @Listener
   public static class SimpleListener {
      @CacheEntryCreated
      public void modifiedEntry(CacheEntryCreatedEvent<String, String> event) {
         if (!event.isPre()) {
            System.out.printf("** Key '%s' was created\n", event.getKey());
         }
      }
   }

   public static void main(String[] args) throws InterruptedException {
      // Create a default local cache manager
      DefaultCacheManager cacheManager = new DefaultCacheManager();

      // Obtain the default cache
      Cache<String, String> cache = cacheManager.getCache();

      // Add the listener to the cache
      cache.addListener(new SimpleListener());

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
