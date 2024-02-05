//Echo serveris

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <unistd.h>

#define SMS_LENGTH 1024

struct addrinfo* addressInfo (char *host, char *port)
{
    //hint'ai
    struct addrinfo addrInfo; 
    memset(&addrInfo, 0, sizeof(addrInfo)); 
    addrInfo.ai_family = AF_UNSPEC;     // tinka arba IPv4, arba IPv6
    addrInfo.ai_socktype = SOCK_STREAM; // TCP soketas
    addrInfo.ai_flags = AI_PASSIVE;     // uzpildyt IP

    //Adreso info
    int status;
    struct addrinfo *servinfo;
    status = getaddrinfo(host, port, &addrInfo, &servinfo);
    if (status != 0)
    {
        printf("Nepavyko gauti adreso: %s\n", gai_strerror(status));
        exit(1);
    }
    return servinfo;
}

int createSocket (struct addrinfo *addrInfo)
{
    //Sukuriam socket'a
    int socketInfo = socket(addrInfo->ai_family, addrInfo->ai_socktype, addrInfo->ai_protocol); 
    if(socketInfo < 0)
    {
        perror("Nepavyko sukurti socket.");
        exit(1);
    }
    printf("Sukurem socket\n");
    return socketInfo;
}

int bindingSockets(int socketInfo, struct addrinfo *addrInfo)
{
    //Bandom prisijungt prie to pacio kaip klientas
    int binding = bind(socketInfo, addrInfo->ai_addr, addrInfo->ai_addrlen);
    if(binding == -1)
    {
        perror("Nepavyko prisijungti prie socket");
        exit(1);
    }
    printf("Prisijungem socket\n");

    //"Klausome" socket'o
    int klausomes = listen(socketInfo, 10);
    if (klausomes == -1)
    {
        perror("Nesujungem");
        exit(1);
    }
    printf("Sujungem socket\n");

    //Priimam rysi tarp kliento ir serverio
    struct sockaddr_storage their_addr;
    socklen_t addr_size;
    addr_size = sizeof their_addr;
    int acceptedConnection = accept(socketInfo, (struct sockaddr *)&their_addr, &addr_size);
    if (acceptedConnection == -1)
    {
        perror("Nepavyko sujungti kliento su serveriu");
        exit(1);
    }
    //printf("Sujungem klienta su serveriu\n");

    return acceptedConnection;
}

void receiveSMS(int socketInfo)
{
    //Gauname failo turini
    FILE *fp;
    fp = fopen("server3.txt", "w");
    if (fp == NULL)
    {
        perror("Nepavyko atidaryti failo");
    }
    
    //Gavom sms
    char *received = (char*) malloc(SMS_LENGTH);  
    int byteArray2 = recv(socketInfo, received, SMS_LENGTH, 0);

    if (byteArray2 == -1)
    {
        perror("Žinutė negauta");
        exit(1);
    }

    fputs(received, fp);
    fclose(fp);
}

int main (int arg, char *args[])
{
    //Serverio portas
    char *host = args[1]; 
    char *port = args[2];

    struct addrinfo *addrInfo = addressInfo(host, port);

    int socketInfo = createSocket(addrInfo);
    int connection = bindingSockets(socketInfo, addrInfo);

    printf("Gavimas prasideda\n");
    receiveSMS(connection); 
    printf("Gavimas baigiasi\n");

    freeaddrinfo(addrInfo);
    close(socketInfo);
    close(connection);

    return 0;
}
