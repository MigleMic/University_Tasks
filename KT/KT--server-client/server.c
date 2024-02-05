//Echo serveris

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <unistd.h>

#define SMS_ILGIS 1024


void upper (char *sms)
{
    int ilgis = strlen(sms);
    for(int i = 0; i < ilgis; i++)
    {
        sms[i] = toupper(sms[i]);
    }
}

int main (int arg, char *args[])
{
    //Serverio portas
    if (arg < 2)
    {
        printf("Įveskite porto numerį!\n");
        return 1;
    }

    char* portas = args[1]; 

    //hint'ai
    struct addrinfo addrInfo; 
    memset(&addrInfo, 0, sizeof addrInfo); 
    addrInfo.ai_family = AF_UNSPEC;     // tinka arba IPv4, arba IPv6
    addrInfo.ai_socktype = SOCK_STREAM; // TCP soketas
    addrInfo.ai_flags = AI_PASSIVE;     // uzpildyt IP

    //Adreso info
    int status;
    struct addrinfo *servinfo;
    status = getaddrinfo(NULL, portas, &addrInfo, &servinfo);
    if (status != 0)
    {
        printf("Nepavyko gauti adreso: %s\n", gai_strerror(status));
        exit(1);
    }

    //Sukuriam socket'a
    int socketInfo = socket(servinfo->ai_family, servinfo->ai_socktype, servinfo->ai_protocol); 
    if(socketInfo == -1)
    {
        perror("Nepavyko sukurti socket.");
        exit(1);
    }

    //Nustatom socket'a
    int prijungtas = 1;
    if (setsockopt(socketInfo,SOL_SOCKET, SO_REUSEADDR, &prijungtas, sizeof prijungtas) == -1)
    {
        perror("Nepavyko nustatyti socket'o");
        exit(1);
    } 

    //Bandom prisijungt prie to pacio kaip klientas
    int jungtis = connect(socketInfo, servinfo->ai_addr, servinfo->ai_addrlen);
    if(jungtis == -1)
    {
        perror("Nepavyko prisijungti prie socket");
        exit(1);
    }

    //Priimam rysi tarp kliento ir serverio
    struct sockaddr_storage k_add;
    socklen_t addr_dydis = sizeof k_add;
    int a = accept(socketInfo, (struct sockadrr *)&k_add, &addr_dydis);
    if (a == -1)
    {
        perror("Nepavyko sujungti kliento su serveriu");
        exit(1);
    }

    //"Klausome" socket'o
    int klausomes = listen(socketInfo, 10);
    if (klausomes == -1)
    {
        perror("Nesujungem");
        exit(1);
    }
    printf("KLausomes %s porte, %s socket'e", portas, socketInfo);
    
    //Gavom sms
    char *gavome = (char*) malloc(SMS_ILGIS);  
    int byteArray2 = recv(socketInfo, gavome, SMS_ILGIS, 0);
    if (byteArray2 == -1)
    {
        perror("Žinutė negauta");
        exit(1);
    }
    printf("Gavome %s\n", gavome);

    //Konvertuojam i didziasias raides
    upper(gavome);


    //Siunciam sms
    int byteArray = send(socketInfo, gavome, strlen(gavome), 0);
    if (byteArray == -1)
    {
        perror("Žinutė neišsiųsta");
        exit(1); 
    }
    printf("Žinutė išsiųsta %s\n", gavome);

    close(socketInfo);

}
