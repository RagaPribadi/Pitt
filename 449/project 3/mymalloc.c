#include "mymalloc.h"

node *alc[26] = {NULL};
static void *base = NULL;

int twos(int num){ //twos finds what index is large enough
	int i;
	for(i=5; i<=30; i++)
	{
		if((1<<i) >num)
			{
				return i;
			}
	}
}
 
void *halves(int index, int baseCase) {			//HALVES divides large nodes to the appropriate size rucrsively
		
	node *temphalves = alc[index + 1]; 
	int size = ((1 << (index + 6)) / 2);
	node *hold =(char *)temphalves + size; 
        

	if(temphalves->next != NULL) 
	{
		alc[index+1] = temphalves->next;
		temphalves->next= NULL;
		alc[index+1]->previous = NULL;
	}
	else
	{
		alc[index+1]=NULL;
	}	

	temphalves->next = hold;
	hold->previous = temphalves;

	if(alc[index] != NULL)
	{
		hold->next = alc[index];
		alc[index]->previous=hold;
	}
	else
	{
		hold->next = NULL;
	}

	alc[index] = temphalves;
	temphalves->previous=NULL;
	temphalves->header = index+5;
	hold->header = index+5;

	if(alc[index]->header == baseCase+5)
	{
		alc[index]->header |=128;
		void *tmp = alc[index];
                alc[index]=alc[index]->next;
		if(alc[index]!=NULL)
		{
			alc[index]->previous=NULL;	
		}
		return tmp;
	}
	else{
		halves(index-1,baseCase);
	}
}


void *freeSpace(int size){// goes through array to find space that fits best. Calls halves if not found
	int i;
	for(i=size-5; i<26; i++)
		{
			if(alc[i] != NULL)
			{	
				if(alc[i]->header==size)
				{
					alc[i]->header |=128;
					void *temporaryPnt= alc[i];
					alc[i]=alc[i]->next;
					if(alc[i]!=NULL)
					{
						alc[i]->previous=NULL;
					}
					return temporaryPnt;
				} 
				else
				{ 
					void *pointer = halves(i-1, size-5);
					return pointer;
				}
			}
		}
		printf("Out of Memory");
}

void *my_buddy_malloc(int size){
    if(base == NULL)
    	{
            base = mmap(NULL, MAX_MEM, PROT_READ|PROT_WRITE, MAP_PRIVATE|MAP_ANON, 0, 0);
       		alc[25]=base;
			alc[25] -> header = 30;
			alc[25]->next=NULL;
			alc[25]->previous = NULL;
 		}

    assert(base!=NULL);

	int tp = twos(size);
	void *space = freeSpace(tp);       
	return space;
}


void my_free(void *ptr){
	node *free = (struct Node*)ptr;

	if(free->header ==30 )
	{
		return;
	}

	free->header &=127;
	
        void *val = ((ptr-base) ^ (1 << free->header)) + base;
        node *myBuddy = (struct Node*)val;
	unsigned char myBits= myBuddy->header;
	myBits=myBits>>7;

	if(myBits == 0 ) 
	{
		if(myBuddy->next==NULL && myBuddy->previous==NULL)
		{
				alc[myBuddy->header-5]=NULL;
		}
		else if(myBuddy->next == NULL && myBuddy->previous != NULL)
		{
				node *budTemp = myBuddy->previous;
				budTemp->next = NULL;
				alc[myBuddy->header-5]=NULL;
		}
		else if(myBuddy->next!= NULL && myBuddy->previous != NULL)
		{
				node *budTempPrev = myBuddy->previous;
				node *budTempNext = myBuddy->next;		
				budTempPrev->next = budTempNext;
				budTempNext->previous = budTempPrev;
				alc[myBuddy->header-5]=NULL;
		}
		else if(myBuddy->next!=NULL && myBuddy->previous == NULL)
		{
				
				alc[myBuddy->header-5] = myBuddy->next;
				alc[myBuddy->header-5]->previous = NULL;
				myBuddy->next = NULL;
		}
		
		int greater = val-ptr;
		if(greater>0)
		{
			//ptr
			free->header +=1;	
			if(alc[free->header-5]!=NULL)
			{
				alc[free->header-5]->previous=free;
				free->next=alc[free->header-5];
				alc[free->header-5]=free;
				free->previous=NULL;
				my_free(ptr);		
			}
			else
			{
				alc[free->header-5]=free;	
				free->previous= NULL;
				free->next = NULL;
			}
		}
		else
		{
			myBuddy->header +=1;
			if(alc[myBuddy->header-5]!=NULL)
			{
				alc[myBuddy->header-5]->previous=myBuddy;
				myBuddy->next=alc[myBuddy->header-5];
				alc[myBuddy->header-5]=myBuddy;
				myBuddy->previous= NULL;
				my_free(val);

			}
			else
			{
				alc[myBuddy->header-5]=myBuddy;	
				myBuddy->previous= NULL;
				myBuddy->next= NULL;
			}
		}	
	}
	else
	{
		if(alc[free->header-5]!=NULL)
		{
			free->next=alc[free->header-5];
			alc[free->header-5]->previous = free;
			alc[free->header-5]=free;
			free->previous = NULL;
		}
		else
		{
			free->next=NULL;
			free->previous = NULL;
			alc[free->header-5]=free;
		}
	}

}

