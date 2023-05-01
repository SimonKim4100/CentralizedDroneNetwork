# CentralizedDroneNetwork
Android Studio files for centralized drone network \n
Centralized networks are vulnerable to hacking compared to decentralized nerworks. \n
This app is a simulation for a hacking example of a centralized drone network with one ground station and four drones. \n

# How to Use
Prepare five mobile devices supporting android. \n
One phone is the ground station(Server app) and four phones are the drones(Client app) \n
The first phone(client) to connect to the server(socket1) becomes the "hacker". We are assuming that the hacker is skilled enough to have already hacked into the server's network. \n
Pressing "send" from server causes the integers shown on the clients to go up by 1. This simulates that the server is continuously sending signals to the drones. \n
Pressing "send" on 1st client(hacker) sends a hack signal to server, and creates null. The server's integer count becomes 1000 and the signals sent to drones become "null". This simulates that the drones have now become hijacked due to the ground station being hacked. \n
