public class TowerOfHanoi
{
private int discs;
private int[] zero;
private int[] one;
private int[] two;

/* Construct the Towers of Hanoi (3 towers) with aNumDisc
* on the first tower. Each tower can be identified by an
* integer number (0 for the first tower, 1 for the second
* tower, and 2 for the third tower). Each disc can be identified
* by an integer number starting from 0 (for the smallest disc)
* and (aNumDisc - 1) for the largest disc.
*/
public TowerOfHanoi(int aNumDiscs)
{
    discs = aNumDiscs;
    zero = new int[aNumDiscs];
    one = new int[0];
    two = new int[0];
    for (int i = aNumDiscs; i > 0; i--)
    {
        zero[aNumDiscs-i] = i-1;
    }
}
/* Returns an array of integer representing the order of
* discs on the tower (from bottom up). The bottom disc should
* be the first element in the array and the top disc should be
* the last element of the array. The size of the array MUST
* be the number of discs on the tower. For example, suppose
* the tower 0 contains the following discs 0,1,4,6,7,8 (from top
* to bottom). This method should return the array [8,7,6,4,1,0]
* (from first to last). 
* @param tower the integer identify the tower number.
* @return an array of integer representing the order of discs.
*/
public int[] getArrayOfDiscs(int tower)
{
    if (tower == 0)
    {
        return zero;
    }
    else if (tower == 1)
    {
        return one;
    }
    else
    {
        return two;
    }
// TO DO same as get num of digits?
}
/* Gets the total number of discs in this Towers of Hanoi
* @return the total number of discs in this Towers of Hanoi
*/
public int getNumberOfDiscs()
{
    return discs;
}
/* Gets the number of discs on a tower.
* @param tower the tower identifier (0, 1, or 2)
* @return the number of discs on the tower.
*/
public int getNumberOfDiscs(int tower)
{
    if (tower == 0)
    {
        return zero.length;
    }
    if (tower == 1)
    {
        return one.length;
    }
    else
    {
        return two.length;
    }
}
/* Moves the top disc from fromTower to toTower. Note that
* this operation has to follow the rule of the Tower of Hanoi
* puzzle. First fromTower must have at least one disc and second
* the top disc of toTower must not be smaller than the top disc
* of the fromTower.
* @param fromTower the source tower
* @param toTower the destination tower
* @return ture if successfully move the top disc from
*         fromTower to toTower.
*/
public boolean moveTopDisc(int fromTower, int toTower)
{
    int[] from = getArrayOfDiscs(fromTower);
    int[] to = getArrayOfDiscs(toTower);
    //int disk = 0;
    //int mover = 0;
    if (getNumberOfDiscs(fromTower) == 0)
    {
        return false;
    }
    if (getNumberOfDiscs(toTower) != 0)
    {
        if (from[getNumberOfDiscs(fromTower)-1] > to[getNumberOfDiscs(toTower)-1])
        {
            return false;
        }
    }
    
    int tempOne[] = new int[0];
    int tempTwo[] = new int[0];
    int mover = 0;
    
    if (fromTower == 0 && toTower == 1)
    {
        tempOne = new int[zero.length-1]; 
        tempTwo = new int[one.length+1];
        mover = zero[zero.length-1];
        
        
        for (int i = 0; i < zero.length-1; i++)
        {
            tempOne[i] = zero[i];
        }
        for (int j = 0; j < one.length; j++)
        {
            tempTwo[j] = one[j];
        }
        
        tempTwo[one.length] = mover;
        zero = new int[tempOne.length];
        one = new int[tempTwo.length];
        
        
        for (int k = 0; k < zero.length; k++)
        {
            zero[k] = tempOne[k];
        }
        for (int a = 0; a < one.length; a++)
        {
            one[a] = tempTwo[a];
        }
        return true;
    }
    else if (fromTower == 0 && toTower == 2)
    {
        tempOne = new int[zero.length-1];
        tempTwo = new int[two.length+1];
        mover = zero[zero.length-1];
        
        for (int i = 0; i < zero.length-1; i++)
        {
            tempOne[i] = zero[i];
        }
        for (int j = 0; j < two.length; j++)
        {
            tempTwo[j] = two[j];
        }
        
        tempTwo[two.length] = mover;
        zero = new int[tempOne.length];
        two = new int[tempTwo.length];
        
        for (int k = 0; k < zero.length; k++)
        {
            zero[k] = tempOne[k];
        }
        for (int a = 0; a < two.length; a++)
        {
            two[a] = tempTwo[a];
        }
        return true;
    }
    
    else if (fromTower == 1 && toTower == 0)
    {
        tempOne = new int[one.length-1];
        tempTwo = new int[zero.length+1];
        mover = one[one.length-1];
        
        for (int i = 0; i < one.length-1; i++)
        {
            tempOne[i] = one[i];
        }
        for (int j = 0; j < zero.length; j++)
        {
            tempTwo[j] = zero[j];
        }
        
        tempTwo[zero.length] = mover;
        one = new int[tempOne.length];
        zero = new int[tempTwo.length];
        
        for (int k = 0; k < one.length; k++)
        {
            one[k] = tempOne[k];
        }
        for (int a = 0; a < zero.length; a++)
        {
            zero[a] = tempTwo[a];
        }
        return true;
    }
    
    else if (fromTower == 1 && toTower == 2)
    {
        tempOne = new int[one.length-1];
        tempTwo = new int[two.length+1];
        mover = one[one.length-1];
        
        for (int i = 0; i < one.length-1; i++)
        {
            tempOne[i] = one[i];
        }
        for (int j = 0; j < two.length; j++)
        {
            tempTwo[j] = two[j];
        }
        
        tempTwo[two.length] = mover;
        one = new int[tempOne.length];
        two = new int[tempTwo.length];
        
        for (int k = 0; k < one.length; k++)
        {
            one[k] = tempOne[k];
        }
        for (int a = 0; a < two.length; a++)
        {
            two[a] = tempTwo[a];
        }
        return true;
    }
    
    else if (fromTower == 2 && toTower == 0)
    {
        tempOne = new int[two.length-1];
        tempTwo = new int[zero.length+1];
        mover = two[two.length-1];
        
        for (int i = 0; i < two.length-1; i++)
        {
            tempOne[i] = two[i];
        }
        for (int j = 0; j < zero.length; j++)
        {
            tempTwo[j] = zero[j];
        }
        
        tempTwo[zero.length] = mover;
        two = new int[tempOne.length];
        zero = new int[tempTwo.length];
        
        for (int k = 0; k < two.length; k++)
        {
            two[k] = tempOne[k];
        }
        for (int a = 0; a < zero.length; a++)
        {
            zero[a] = tempTwo[a];
        }
        return true;
    }
    
    else if (fromTower == 2 && toTower == 1)
    {
        tempOne = new int[two.length-1];
        tempTwo = new int[one.length+1];
        mover = two[two.length-1];
        
        for (int i = 0; i < two.length-1; i++)
        {
            tempOne[i] = two[i];
        }
        for (int j = 0; j < one.length; j++)
        {
            tempTwo[j] = one[j];
        }
        
        tempTwo[one.length] = mover;
        two = new int[tempOne.length];
        one = new int[tempTwo.length];
        
        for (int k = 0; k < two.length; k++)
        {
            two[k] = tempOne[k];
        }
        for (int a = 0; a < one.length; a++)
        {
            one[a] = tempTwo[a];
        }
        return true;
    }
    
    else 
    {
        return false;
    }
    
    }
}