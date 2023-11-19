#!/bin/sh

# Turn off all the things
curl -X POST http://192.168.178.100:8080/on
curl -X POST http://192.168.178.100:8080/off

# Light mode sample
# See `turnLightMode` for more supported ids
curl -X POST http://192.168.178.100:8080/lightmode -d "blue"

# Morse word
curl -X POST http://192.168.178.100:8080/morse -d "hello"

# System shutdown
curl -X POST http://192.168.178.100:8080/shutdown
