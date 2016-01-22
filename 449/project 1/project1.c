 #include<stdio.h>
 #include<math.h>
 #include<stdlib.h>
 #include<time.h>
 #define CODELENGTH 4
 #define NUMSYMBOLS 6

char MasterCode[4];/*randomly generated answer*/
char guess[5];/*holds user entered char array*/

 void genCode ()
 {
	char all[6] = {'r','o','y','g','b','p'};
	int i;
	//chooses four random colors and its order
	MasterCode[0] = all[rand() % (5 - 0 + 1) + 0];
	MasterCode[1] = all[rand() % (5 - 0 + 1) + 0];
	MasterCode[2] = all[rand() % (5 - 0 + 1) + 0];
	MasterCode[3] = all[rand() % (5 - 0 + 1) + 0];
	
	
	
 }
 
 void getGuess ()
 {

	printf( "Please enter your 4 colors: " );
	int k;
	scanf( "%s", guess );
	
	printf( "Your array has these values: " );

	for ( k = 0 ; k < 4; k++ ) {
		printf( "%c ", guess[ k ] );
	}

	printf( "\n" );
	
 }



 



 int main (int argc, char **argv)
 {
	int answer = 0;
	char ans[3];
	printf("Welcome to Mastermind!\n");
	printf("");
	while(answer == 0)
	{
		printf("Would you like to play?:");
		scanf("%s",ans);
		printf("");
		if(ans[0] == 'n')
		{
			break;
			answer = 1;
		}
			int tries=0;
			srand((unsigned int)time(NULL));	
			genCode();
			while(tries < 11)
			{
				int both=0;
				int pos = 0;
				int x;
				int y;
				
				getGuess();
				
				for(x=0; x<4; x++)
				{
					 if (MasterCode[x]==guess[x])
					 {
						both++;
						guess[x] = 'X'; // Remove peg color so it's not looked at again
					 } 
				}
				/************************************************/
				/************************************************/
				
				for(x=0;x<4;x++)
				{
					 for(y=0;y<4;y++)
					 {
						if(MasterCode[x]==guess[y])
						{
						 pos++;
						 guess[y] = 'X'; // Remove peg color so it's only picked once
						}
					 }
				}
				
				if(both == 4)
				{
					printf("YOU GOT IT RIGHT!\n");
					tries=11;
				}
				
				printf("Colors in the correct place:%d\n", both);
				printf("Colors correct but in wrong position:%d\n", pos);
				printf("\n");
		
			}
			
	}
	
	 
	printf("BYE");


}