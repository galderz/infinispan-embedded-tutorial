# Infinispan Embedded Tutorial

## Overview

This is a tutorial which explains how to use Infinispan embedded in your own
application.

Each tagged commit is a separate lesson teaching a single aspect of Infinispan.

## Prerequisites

### Git

- A good place to learn about setting up git is [here][git-github]
- Git [home][git-home] (download, documentation)

### JDK

- Get a [JDK][jdk-download]. You will need version 1.7 or higher

### Maven

- Get [Maven][maven-download]. You will need version 3.2 or higher

## Commits / Tutorial Outline

You can check out any point of the tutorial using
    git checkout step-?

To see the changes between any two lessons use the git diff command.
    git diff step-?..step-?

### step-0/cache-manager

- Setup a basic Maven project with dependencies
- Create a simple class which starts and stops a local Cache Manager

### step-1/cache-reading-writing

- Obtain the default cache
- Store an entry into the cache
- Retrieve an entry from the cache

### step-2/expiration

- Storing entries which expire

### step-3/listeners

- Receive events when entries in the cache change

### step-4/clustered

- Making the cache clustered

## Building and running the tutorial

- Run `mvn clean package` to rebuild the application
- Run `mvn exec:java` to execute the application. In case you're running a clustered step, run this from
  multiple terminals, where each instance will represent a node

## Application Directory Layout

    src/                -->
      main/             -->
        java/           -->
        resources/      -->

## Contact

For more information on Infinispan please check out http://infinispan.org/

[jdk-download]: http://www.oracle.com/technetwork/articles/javase/index-jsp-138363.html
[git-home]: http://git-scm.com
[git-github]: http://help.github.com/set-up-git-redirect
[maven-download]: http://maven.apache.org/download.html

