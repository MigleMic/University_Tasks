//Echo klientas

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <unistd.h>

#define SMS_LENGTH 1024

struct addrinfo* addressInfo (char *host, char *port)
{
    //hint'ai
    struct addrinfo addrInfo; 
    memset(&addrInfo, 0, sizeof (addrInfo)); 
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

int createSocket (struct addrinfo *Info)
{
    //Sukuriam socket'a
    int socketInfo = socket(Info->ai_family, Info->ai_socktype, Info->ai_protocol); 
    if(socketInfo < 0)
    {
        perror("Nepavyko sukurti socket.");
        exit(1);
    }
    printf("Sukurtas socket\n");

    return socketInfo;
}

void connectSocket (int socketInfo, struct addrinfo *Info)
{
    //Prisijungiam prie socket'o
    int c = connect(socketInfo, Info->ai_addr, Info->ai_addrlen);
    if(c == -1)
    {
        perror("Nepavyko prisijungti prie socket");
        exit(1);
    }
}

void getFile (int socketInfo)
{
    //Gauname failo turini
    FILE *fp;
    fp = fopen("1.txt", "r");
    if (fp == NULL)
    {
        perror("Nepavyko atidaryti failo");
    }

    char *line = NULL;
    size_t len = 0;
    ssize_t read;
    int byteArray = 0;

    while ((read = getline(&line, &len, fp)) != -1)
    {
        //Cia reiks prideti siuntima

        byteArray = send(socketInfo, line, strlen(line),0);
        if (byteArray == -1)
        {   
            perror("Žinutė neišsiųsta");
            exit(1); 
        }
    }
    fclose(fp);
    if(line)
    {
        free(line);
    }
}
int main(int arg, char *args[])
{
    //Naudotojo portas
    char *host = args[1]; 
    char *port = args[2]; 

    struct addrinfo *Info = addressInfo(host,port);

    int socketInfo = createSocket(Info);
    printf("Prisijungta prie socket'o\n");
    connectSocket(socketInfo,Info);

    printf("Siuntimas prasideda\n");
    getFile(socketInfo);
    printf("Siuntimas baigesi\n");

    freeaddrinfo(Info);
    close(socketInfo);

    return 0;
}