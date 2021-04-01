#include <cstdlib>
#include <string.h>
#define BUFSIZE 256

int main(int argc, char **argv) {
char *buf;
buf = (char *)malloc(sizeof(char)*BUFSIZE);
strcpy(buf, argv[1]);
}
