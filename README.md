# Exploring different protocols for an API

Using Spring Boot and the same code and config (exception is Rsocket with multiple transports),
try to verify that an application can expose the same operations. With:

* H2 Clear-Text and HTTP 1.1
* REST (reactive / non reactive)
* GraphQL REST
* Graphql over Websocket
* Graphql over RSocket over TCP
* Graphql over RSocket over Websocket (different profile, conflicts only with RSocket over TCP)
* Rsocket over TCP
* Rsocket over Websocket

## HTTP 2 support test

    $ curl -si --http2 http://127.0.0.1:8888/greeting/mono
    HTTP/1.1 101 Switching Protocols
    connection: upgrade
    upgrade: h2c
    
    HTTP/2 200
    content-type: text/plain;charset=UTF-8
    content-length: 6
    
    Hello!

