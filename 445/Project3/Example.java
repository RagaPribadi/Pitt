
/**
* This is code for a very bare bones application using swing 
* A simple file chooser to see what it takes to make one of these work.
* Add a button to reverse the song file...store the notes in a stack...reverse them
**/


import java.io.*;
import java.util.Scanner;
import javax.swing.*;
import java.util.regex.*;
import java.util.*;

public class Example extends JFrame {
    
  public static void main(String args[]) throws FileNotFoundException, IOException {
      
      String filename = JOptionPane.showInputDialog("Type in File Name");
      BufferedReader br = new BufferedReader(new FileReader(filename));
      String line;
      String[] tokens = null;
      String[] dateToken = null;
      TreeMap<String,ArrayList<ArrayList<String>>> dataset = new TreeMap<>();
      TreeMap<String,ArrayList<String>> dates = new TreeMap<>();
      
      while ((line = br.readLine()) != null) 
      {
          tokens = line.split("\t");
          
        ArrayList <String> relVal = new ArrayList<>();
        ArrayList <ArrayList<String>> outerVal = new ArrayList<>();
        ArrayList <String> dateVal = new ArrayList<>();
       // process the line.
        /*venueID = 0
        TimeStamp = 1
        Lat = 3
        Lon = 4
        VenueCity = 5 
        checkinsCount = 11
        HereNowCount = 15
          */

        
        relVal.add(tokens[5]); //adds venueCity to ArrayList index[0]
        relVal.add(tokens[1]); //adds TimeStamp to ArrayList index[1]
        relVal.add(tokens[3]); //adds Latitude  to ArrayList index[2]
        relVal.add(tokens[4]); //adds Longitude  to ArrayList index[3]
        relVal.add(tokens[11]); //adds checkinsCount to ArrayList index[4]
        relVal.add(tokens[15]); //adds HereNowCount to ArrauList index[5]
        dateToken = tokens[1].split("\\s+");
        
        if(dataset.containsKey(tokens[0]) == false)
          {
              outerVal.add(relVal);
              dataset.put(tokens[0], outerVal); //puts a VenueID as key and relVal arraylist as data
          }  
        else
        {
            dataset.get(tokens[0]).add(relVal);
        }
        ///////////////////////dates////////////////
        if(dates.containsKey(dateToken[0]) == false)
          {
              dateVal.add(tokens[0]);
              dates.put(dateToken[0], dateVal); //puts date as Key a VenueID as value
          }  
        else
        {
            dates.get(dateToken[0]).add(tokens[0]);
        }
        
                
      }
      br.close();
      String puzzle = JOptionPane.showInputDialog("Type in the number of the Question you would like Answered \n"
                                                    + "1) How many times has ____________ (a venue ID) trended?\n"
                                                    + "2) When was the last time ___________ (a venue ID) trended?\n"
                                                    + "3) How many times has _____________ (a venue ID) trended in comparison to not trended?\n"
                                                    + "4) What other venues have trended within ____ Miles of ___________ (a venue ID)\n"
                                                    + "5) On ____________ (date) this list of venues was trending\n"
                                                    + "6) Given ____ (lat) ____(long).  Here is a list of venues that have trended within a 1 mile radius of that point\n");
      int Q = Integer.parseInt(puzzle);
      if(Q == 1)
      {
        String venueID = JOptionPane.showInputDialog("Type the VenueID");
          int counter = 0;
          ArrayList <ArrayList<String>> tempSet ;
          tempSet = dataset.get(venueID);
          for(int i = 0; i<=tempSet.size()-1;i++)
          {
              ArrayList<String> tempArray;
              tempArray = tempSet.get(i);
              if(Integer.parseInt(tempArray.get(5)) >= 6)
              {
                  counter++;
              }
              
          }
          JOptionPane.showMessageDialog(null,venueID+" has trended "+counter+" times.");
      }
      if(Q == 2)
      {
          
        String venueID = JOptionPane.showInputDialog("Type the VenueID");
        TreeMap<String,String> question2 = new TreeMap<>();
        ArrayList <ArrayList<String>> tempSet ;
        tempSet = dataset.get(venueID);
        
          for(int i = 0; i<=tempSet.size()-1;i++)
          {
              ArrayList<String> tempArray;
              tempArray = tempSet.get(i);
              
              question2.put(tempArray.get(1), venueID);
              
              
          }
          JOptionPane.showMessageDialog(null,"The VenueID "+ venueID+ " last trended on "+question2.lastKey());
          //System.out.println("The VenueID "+question2.lastEntry()+ " last trended on " +question2.lastKey());
          
      }
      if(Q == 3)
      {
        String venueID = JOptionPane.showInputDialog("Type the VenueID");
          int counter = 0;
          ArrayList <ArrayList<String>> tempSet ;
          tempSet = dataset.get(venueID);
          for(int i = 0; i<=tempSet.size()-1;i++)
          {
              ArrayList<String> tempArray;
              tempArray = tempSet.get(i);
              if(Integer.parseInt(tempArray.get(5)) >= 6)
              {
                  counter++;
              }
              
          }
          JOptionPane.showMessageDialog(null,venueID+" is in the dataset"+ dataset.get(venueID).size() +" times "+" but has trended "+counter+" times.");
      }
      if(Q==4)
      {
          String venueID = JOptionPane.showInputDialog("Type the VenueID");
          String rad = JOptionPane.showInputDialog("Type the radius");
          int radius = Integer.parseInt(rad);
          double Lat = Double.parseDouble(dataset.get(venueID).get(0).get(2));
          double Lon = Double.parseDouble(dataset.get(venueID).get(0).get(3));
          ArrayList<ArrayList<String>> tempList = new ArrayList<>();
          ArrayList<String> tempArr = new ArrayList<>();
          TreeMap<Double,String> sortedLoc = new TreeMap<>();
          
          for(Map.Entry<String,ArrayList<ArrayList<String>>> entry : dataset.entrySet())
          {
            String key = entry.getKey(); 
            tempList = dataset.get(key);
            for(int i = 0; i<tempList.size();i++)
            {
                tempArr = tempList.get(i);
                double lat2 = Double.parseDouble(tempArr.get(2));
                double lon2 = Double.parseDouble(tempArr.get(3));
                double miles = distance(Lat, Lon, lat2, lon2, 'M');
                if(miles<= radius)
                {
                    sortedLoc.put(miles, key);
                }
            }
          }
          JOptionPane.showMessageDialog(null,"From closest to furthest, these are the venues within the radius "+ sortedLoc );
          
      }
      if(Q==5)
      {
          String dateID = JOptionPane.showInputDialog("Type the Time Stamp in the EXACT format yyyy-mm-dd Ex:2012-08-01");
          MergeSort test = new MergeSort(dates.get(dateID));
          test.sort();
          String showOrder = JOptionPane.showInputDialog("Type A for ascending B for descending");
          if(showOrder.equals("A") || showOrder.equals("a") )
          {
              JOptionPane.showMessageDialog(null,"On this date, these venues were trending "+ test.show() );              
          }
          else
          {
              JOptionPane.showMessageDialog(null,"On this date, these venues were trending "+ test.showDesc() );              
          }
          
      }
      
      if(Q==6)
      {
          String Lt = JOptionPane.showInputDialog("Type in the Latitude");
          double Lat = Double.parseDouble(Lt);
          String Ln = JOptionPane.showInputDialog("Type in the Longitude");
          double Lon = Double.parseDouble(Ln);
          ArrayList<ArrayList<String>> tempList = new ArrayList<>();
          ArrayList<String> tempArr = new ArrayList<>();
          TreeMap<Double,String> sortedLoc = new TreeMap<>();
          
          for(Map.Entry<String,ArrayList<ArrayList<String>>> entry : dataset.entrySet())
          {
            String key = entry.getKey(); 
            tempList = dataset.get(key);
            for(int i = 0; i<tempList.size();i++)
            {
                tempArr = tempList.get(i);
                double lat2 = Double.parseDouble(tempArr.get(2));
                double lon2 = Double.parseDouble(tempArr.get(3));
                double miles = distance(Lat, Lon, lat2, lon2, 'M');
                if(miles<= 1)
                {
                    sortedLoc.put(miles, key);
                }
            }
          }
          /*
          String showOrder = JOptionPane.showInputDialog("Type A for ascending B for descending");
          if(showOrder.equals("A") || showOrder.equals("a") )
          {
              String name = JOptionPane.showInputDialog("Type D for by distance N for by name");
              if(name.equals("D") || name.equals("d") )
                {
          */
                    JOptionPane.showMessageDialog(null,"These locations are within a mile" +sortedLoc );              
/*
                }
                else
                {
                   JOptionPane.showMessageDialog(null,"These locations are within a mile" +sortedLoc );
                }
                         
          }
          else
          {
             System.out.println(test.showDesc());
          }
          */
      }
      
      
      
      //System.out.println(dataset.keySet());
      //System.out.println(distance(Double.parseDouble(dataset.get("4a5b7f80f964a52054bb1fe3").get(2)),Double.parseDouble(dataset.get("4a5b7f80f964a52054bb1fe3").get(3)),
             // Double.parseDouble(dataset.get("4b54a9c3f964a520a8c527e3").get(2)),Double.parseDouble(dataset.get("4b54a9c3f964a520a8c527e3").get(3)),'M'));
      System.out.println("Done");
      System.exit(0);
      
  }
  
  /////////////////////////////////////////////////////
  /////////////////METHODS GO DOWN HERE/////////////////
  /////////////////////////////////////////////////////
  
  /*
  These Methods are used to calculate distance between to points
  using Longitude and Latitude coordinates
  */
  private static double distance(double lat1, double lon1, double lat2, double lon2, char unit) 
  {
    double theta = lon1 - lon2;
    double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
    dist = Math.acos(dist);
    dist = rad2deg(dist);
    dist = dist * 60 * 1.1515;
    if (unit == 'K') {
      dist = dist * 1.609344;
    } 
    else if (unit == 'N') {
      dist = dist * 0.8684;
      }
    return (dist);
   }
 

/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
/*::  This function converts decimal degrees to radians             :*/
/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
private static double deg2rad(double deg) {
  return (deg * Math.PI / 180.0);
}
 
/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
/*::  This function converts radians to decimal degrees             :*/
/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
private static double rad2deg(double rad) {
  return (rad * 180 / Math.PI);
}

///////////////////////////MergeSort////////////
public static class MergeSort 
{
 
    private ArrayList<String> StringList;
 
 
    public MergeSort(ArrayList<String> input)
    {
        StringList = new ArrayList<String>();
        for(int i=0; i<input.size(); i++)
        {
            StringList.add(input.get(i));
        }
    }
 
    public void sort()
    {
 
        StringList=mergeSort(StringList);
 
    }
 
    public ArrayList<String> mergeSort(ArrayList<String> whole)
    {
        ArrayList<String> left = new ArrayList<String>();
        ArrayList<String> right = new ArrayList<String>();
        int center;
 
        if(whole.size()==1)    
            return whole;
        else
        {
            center = whole.size()/2;
            // copy the left half of whole into the left.
            for(int i=0; i<center; i++)
            {
                    left.add(whole.get(i));
            }
 
            //copy the right half of whole into the new arraylist.
            for(int i=center; i<whole.size(); i++)
            {
                    right.add(whole.get(i));
            }
 
            // Sort the left and right halves of the arraylist.
            left  = mergeSort(left);
            right = mergeSort(right);
 
 
            // Merge the results back together.
            merge(left,right,whole);
 
        }
        return whole;
    }
 
    private void merge(ArrayList<String> left, ArrayList<String> right, 
            ArrayList<String> whole) {
 
        int leftIndex = 0;
        int rightIndex = 0;
        int wholeIndex = 0;
 
 
        // As long as neither the left nor the right arraylist has
        // been used up, keep taking the smaller of left.get(leftIndex)
        // or right.get(rightIndex) and adding it at both.get(bothIndex).
        while (leftIndex < left.size() && rightIndex < right.size())
        {
            if ((left.get(leftIndex).compareTo(right.get(rightIndex)))<0) 
            {
                whole.set(wholeIndex,left.get(leftIndex));
                leftIndex++;
            }
            else
            {
                whole.set(wholeIndex, right.get(rightIndex));
                rightIndex++;
            }
            wholeIndex++;
        }
 
        ArrayList<String>rest;
        int restIndex;
        if (leftIndex >= left.size()) {
            // The left arraylist has been use up...
            rest = right;
            restIndex = rightIndex;
        }
        else {
            // The right arraylist has been used up...
            rest = left;
            restIndex = leftIndex;
        }
 
        // Copy the rest of whichever arraylist (left or right) was
        // not used up.
        for (int i=restIndex; i<rest.size(); i++) {
            whole.set(wholeIndex, rest.get(i));
            wholeIndex++;
        }
    }
 
    public String show()
    {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< StringList.size();i++)
        {
            sb.append(StringList.get(i));
            sb.append(" ");
        }
        String showString = sb.toString();
        return showString;
 
    }
 
    public String showDesc()
    {
        StringBuilder sb = new StringBuilder();
        for(int i=StringList.size()-1; i>=0;i--)
        {
            sb.append(StringList.get(i));
            sb.append(" ");
        }
        String showString = sb.toString();
        return showString;
 
    }
 
}

}
