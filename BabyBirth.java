import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.File;
public class BabyBirths {
    public void printNames() {
        FileResource fr = new FileResource();
        for(CSVRecord rec : fr.getCSVParser(false)) {
            System.out.println("Name " + rec.get(0) +
                                " Gender " + rec.get(1) +
                                " Total Born " + rec.get(2));
       
        }
    }
    
    //total births count girls and boys
    public void totalBirths(FileResource fr) {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        for(CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            if(rec.get(1).equals("M")){
                totalBoys += numBorn;
            }
            else {
                totalGirls += numBorn;
            }
            }
            System.out.println("Total Births = " + totalBirths);
            System.out.println("Total Boys = " + totalBoys);
            System.out.println("Total Girls= " + totalGirls);
    }
    
    
    
     //get Rank by giving name
    public int GetRank(int year, String name, String gender){
        String Filename = "yob" + year + "shorts.csv";
          FileResource fr = new FileResource();
        boolean found = false;
        int Rank = 0;
        for(CSVRecord rec : fr.getCSVParser(false)){
            if(rec.get(1).equals(gender)){
               
                if(rec.get(0).equals(name)){
                    Rank += 1;
                    found = true;
                    break;
                }
                else 
                {
                    Rank += 1;
                }
            }
        }
        if(found == true){
          return Rank;
        }
        else return -1;
    }
    
    //get name by giving rank
    public String GetName(int year, int rank, String gender)
    {
         int CheckRank = 0;
         String Name = null;
         FileResource fr = new FileResource();
         boolean found = false;
        for(CSVRecord rec : fr.getCSVParser(false)){
            if(rec.get(1).equals(gender)){
                    CheckRank += 1;
                     if(CheckRank == rank){
                        Name = rec.get(0);
                         found = true;
                          break;
                    } 
            }
        }
        if(found == true){
          return Name;
        }
        else return "NO NAME";
    }
    
    
    
    //What would your name be if you were born in a different year? 
    public void whatIsNameInYear(String name, int year, int newYear, String gender){
       int rank = GetRank(year,name ,gender);
       String newName =  GetName(newYear, rank, gender);
       System.out.println(name + " born in " + year + " would be " + newName + " in " + newYear);
    }
    
    //yearOfHighestRank
    public int yearOfHighestRank(String name, String gender){
         int highestYear = -1;
         int highestRank = 0;
         DirectoryResource dr = new DirectoryResource();
         for (File f : dr.selectedFiles()) {
            String year = f.getName().substring(3, 7);
            int rank =  GetRank(Integer.parseInt(year), name, gender);
            if (rank != -1 &&  rank <= highestRank){
                highestRank = rank;
                highestYear = Integer.parseInt(year);
            }
        }
        return highestYear;
    }
    
    //method to get Average Rank
    public double getAverageRank(String name, String gender){
         DirectoryResource dr = new DirectoryResource();
         double AvgRank = -1;
         int SumRank = 0;
         int count = 0;
         boolean found = false;
         for(File f : dr.selectedFiles()){
            String year = f.getName().substring(3, 7);
            int rank =  GetRank(Integer.parseInt(year), name, gender);
            if(rank != -1){
                found = true;
                count++;
                SumRank += rank;
            }
        }
        if (found)
                AvgRank = 1.0 * SumRank / count;
        else
                AvgRank = -1;
        return AvgRank;
   
    }
    
    //method to get total Births Ranked Higher
    public int getTotalBithsRankedHigher(int year, String name, String gender){
         int rank =  GetRank(year, name, gender);
         int ifRankHigh = 0;
         int totalBirths = 0;
         FileResource fr = new FileResource();
        for (CSVRecord rec : fr.getCSVParser(false)){
             if(rec.get(1).equals(gender)){
                 int numBorn = Integer.parseInt(rec.get(2));
                 ifRankHigh = GetRank(year, rec.get(0), gender);
                 if(ifRankHigh < rank)
                    totalBirths = totalBirths + numBorn;
              }
         }
        if(totalBirths > 0)
            return totalBirths;
           else
            return -1;
    }
  
  
  
  //testing methods
   
   public void getTotalBithsRankedHigher(){
       int total = getTotalBithsRankedHigher(2012, "Ethan", "M");
       System.out.println("Total Births Ranked Higher = " + total);
    }
   
    
   public void testGetAverageRank(){
        double avgRank = getAverageRank("Mason", "M");
        System.out.println("Average Rank is " + avgRank);
        double avgRank2 = getAverageRank("Jacob", "M");
	    System.out.println("Average  Rank is " + avgRank2);
	   }
    
    public void testYearOfHighestRank(){
        int highestRankedYear = yearOfHighestRank("Mich", "M");
        System.out.println("Highest Rank is " + highestRankedYear);
    }
    
    public void testWhatIsNameINyear(){
        whatIsNameInYear("Owen", 1974, 2014, "M");
      
    }
   
    public void testGetName(){
        String NAME = GetName(1982, 450 , "M");
        System.out.println("Found Name is: " + NAME);
    }
    
    public void testGetRank()
    {
        int rank = GetRank(1971, "Frank", "M");
        System.out.println("Rank = " + rank);
    }
    
    public void testtotalbirths(){
        FileResource fr = new FileResource("C:/Java/us_babynames_by_year/yob1905.csv");
        totalBirths(fr);
    }
    
   
}
