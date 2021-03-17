package Main;

import FileReader.ReadCSV;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
//        ReadCSV.parseCSV("resources/DataShed_Technical_Test.csv");
//        ReadCSV.splitLines("resources/DataShed_Technical_Test.csv");
//        ReadCSV.getDateandGender("resources/DataShed_Technical_Test.csv");
        ReadCSV.findDuplicates("resources/DataShed_Technical_Test.csv", 70);
    	
//        rCSV.getChars("resources/DataShed_Technical_Test2.csv", 4);
//        ReadCSV.sortList("resources/DataShed_Technical_Test.csv");
    }
}
