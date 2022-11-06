#!/bin/bash
sbt ++"$TRAVIS_SCALA_VERSION" publishAll
sbt ++"$TRAVIS_SCALA_VERSION" test
docker-compose up -d && sbt ++"$TRAVIS_SCALA_VERSION" it:test