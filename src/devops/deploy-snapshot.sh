#!/bin/bash

cp src/devops/.travis.settings.xml $HOME/.m2/settings.xml

mvn deploy -q