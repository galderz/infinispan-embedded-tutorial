package org.infinispan.tutorial.embedded;

import org.infinispan.manager.DefaultCacheManager;

public class EmbeddedTutorial {

   public static void main(String[] args) {
      // Create a default local cache manager
      DefaultCacheManager cacheManager = new DefaultCacheManager();

      // Stop the cache manager and release all resources
      cacheManager.stop();
   }
}
