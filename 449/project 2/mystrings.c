//Adhyaksa Pribadi
//The purpose of this program is to print 
//strings that are found within a given file
//cs449


#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char **argv) {

	char in;					// takes byte in
	int flag;		// byte read check
	short str;			// see if at least 4 consecutive bytes to make a string

	//missing filename
	if (argc != 2){
	    printf("missing filename\n");
	    return 1;
	}

	// open file
	FILE *f = fopen(argv[1], "rb");	
	
	printf("Strings %s:\n\n", argv[1]);

	// while the EOF still has content
	while (!feof(f)) {
		
		str = 0;	
		
		do {
			flag = fread(&in, 1, 1, f);	// read in a byte
			str++;														// increment str to make sure you get at least four char
		} while (in >= 32 && in <= 126 && flag != 0);	//reads in as long as char is within ascii value
		// print char
		if (str >= 4) {
			char *ptr = malloc(str);		
			fseek(f, -str, SEEK_CUR);		
			fread(ptr, 1, str, f);			
			printf("%s", ptr);							
		}
	}
	fclose(f);		
	printf("\n");	
	return 0;
}