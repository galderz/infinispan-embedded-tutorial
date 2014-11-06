package org.infinispan.tutorial.embedded;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryCreated;
import org.infinispan.notifications.cachelistener.event.CacheEntryCreatedEvent;
import org.infinispan.notifications.cachemanagerlistener.annotation.ViewChanged;
import org.infinispan.notifications.cachemanagerlistener.event.ViewChangedEvent;

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

   @Listener
   public static class ClusterListener {
      CountDownLatch latch = new CountDownLatch(1);

      @ViewChanged
      public void viewChanged(ViewChangedEvent event) {
         System.out.printf("** View changed: %s\n", event.getNewMembers());
         // Once we have two members we open release the latch
         if (event.getCacheManager().getMembers().size() == 2) {
            latch.countDown();
         }
      }
   }

   public static void main(String[] args) throws InterruptedException {
      // We want a clustered configuration
      GlobalConfigurationBuilder global = GlobalConfigurationBuilder.defaultClusteredBuilder();

      // Redefine the default cache to be a distributed, synchronous cache
      ConfigurationBuilder builder = new ConfigurationBuilder();
      builder.clustering().cacheMode(CacheMode.DIST_SYNC);

      // Create a default clustered cache manager
      DefaultCacheManager cacheManager = new DefaultCacheManager(global.build(), builder.build());

      // Register a cluster listener
      ClusterListener clusterListener = new ClusterListener();
      cacheManager.addListener(clusterListener);

      // Obtain the default cache
      Cache<String, String> cache = cacheManager.getCache();

      // Wait for all nodes to join
      clusterListener.latch.await();

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
