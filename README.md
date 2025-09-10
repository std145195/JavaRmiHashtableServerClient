# Java RMI Hashtable Server-Client – Remote Method Invocation Project

## Project Description
This project implements a distributed key-value store using Java RMI (Remote Method Invocation) technology. The server maintains a hashtable and exposes remote methods for clients to insert, delete, search, and close connections. The use of an RMI interface decouples the client from the implementation details of the server, enabling flexibility and easier maintenance.

## Features
- Defines a remote interface extending `java.rmi.Remote` with methods exposing server capabilities.
- Server implements the remote interface and manages a thread-safe hashtable for key-value storage.
- Client communicates with the server via RMI lookup and invokes remote methods.
- Supports commands analogous to insert (1), delete (2), search (3), and close connection (0).
- Exception handling for remote communication issues.
- Interface-based design allowing the server implementation to change without recompiling client code.

## How to Run
1. Compile the remote interface, server implementation, and client classes.
2. Start the RMI registry with `rmiregistry`.
3. Run the server program which registers the remote object in the RMI registry.
4. Run the client program that looks up the remote server object.
5. The client sends commands of the form `A,B,C` to the server and receives responses.
6. Enter `0,0` to terminate the client-server session.

## Requirements
- Java 8 or later.
- RMI registry running on default port 1099.
- Localhost environment (or modify hostnames for remote use).

## File Structure
- `RemoteInterface.java` – Definition of remote methods.
- `Server.java` – Server implementation of the remote interface.
- `Client.java` – Client program invoking remote methods via RMI.
- `README.md` – This documentation file.
