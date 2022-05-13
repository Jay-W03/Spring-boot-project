#!/bin/bash
export $(cat .env | xargs)
java -jar build/libs/Sample_Spring_Project-0.0.1-SNAPSHOT.jar