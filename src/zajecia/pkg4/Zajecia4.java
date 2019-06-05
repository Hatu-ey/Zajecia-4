/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zajecia.pkg4;
import java.io.*;
import java.nio.file.Files;

/**
 *
 * @author Acer
 */
public class Zajecia4 {

    /**
     * @param args the command line arguments
     */
    
    
    public static void CountWords(String fileName) throws IOException 
    {
    /*1. Napisać funkcję liczZnakiSlowa, która zlicza:
    • liczbę znaków w pliku,
    • liczbę białych znaków w pliku (białe znaki to spacja, tabulator, znacznik
    końca wiersza),
    • liczbę słów w pliku.
    Wynikiem funkcji jest tablica złożona z 3 liczb całkowitych po jednej dla wymienionych podpunktów. */
        BufferedReader file = null;
        int countChars = 0;
        int countNL = 0;
        int countWords = 0;
        
        try
        {
            file = new BufferedReader(new FileReader(fileName)); //
            String line;
            while((line = file.readLine()) != null){
             countChars += line.replaceAll("\\s+","").length();
             countWords += line.split(" ").length;
             countNL++;
            }
            
        }
        catch(FileNotFoundException ex)
        {
            System.out.println("Brak pliku");
        }
        finally{
            if (file != null)
            {
            file.close();
            }
        }
        System.out.printf("Znaki: %d Linie: %d Słowa: %d", countChars, countNL, countWords);
        System.out.println("");
        
    }  // zadanie1

    public static void Find(String fileNameIn, String fileNameOut,String word) throws IOException
    {
    /*2. 
    Napisać funkcję:
    public static void Find(String fileNameIn, String fileNameOut,
    String word)
    której zadaniem jest znalezienie wszystkich wierszy w pliku, które zawierają szukane słowo. Wszystkie wiersze, które zawierają słowo powinny zostać zapisane w
    pliku wynikowym wraz z nr wiersza (z pierwszego pliku). Nazwa pierwszego pliku
    zapamiętana jest w parametrze fileNameIn, nazwa pliku wynikowego podana
    jest w parametrze fileNameOut, natomiast szukane słowo w parametrze word. 
    */
        BufferedReader fileIn = null;
        PrintWriter fileOut = null;
        try
        {
            fileIn = new BufferedReader(new FileReader(fileNameIn));
            fileOut = new PrintWriter(fileNameOut);
            String line;
            int lineNr = 1;
            while((line = fileIn.readLine()) != null)
            {
                if(line.contains(word))
                {
                    fileOut.println(lineNr + ": " + line);
                }
                lineNr++;
            }
        }
        catch( FileNotFoundException ex)
        {
            System.out.println("BrakPliku");
        }
        finally
        {
            if(fileIn != null && fileOut != null)
            {
                fileIn.close();
                fileOut.close();
            }
        }
        
    } // zadanie 2

    public static void SumAndSave(String fileName) throws IOException
    {   
        /*3. Napisać funkcję public static void SumAndSave(String fileName) , która odczytuje file o podanej nazwie zawierający liczby całkowite (po jednej w
        wierszu). Funkcja ma za zadanie odczytać i zsumować wszystkie liczby z pliku,
        a następnie dopisać na końcu pliku wyznaczoną sumę powiększoną o 1. Ponowne uruchomienia funkcji będą skutkowały dopisywaniem kolejnych wierszy. Jeżeli
        file nie istnieje to ma zostać utworzony – sum dla pustego pliku wyniesie 0, a
        więc należy dopisać wiersz zawierający 1.*/
        int sum = 1;
        BufferedReader file = null;
        PrintWriter fileOut = null; 
        
        if(fileExists(fileName))
        {
            file = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = file.readLine()) != null)
            {
                try
                {
                    sum += Integer.parseInt(line);
                } 
                catch (NumberFormatException ex)
                {
                    System.out.println("To nie jest wartosc liczbowa");                  
                }
                
            }
            
            try
            {
                fileOut = new PrintWriter(new FileWriter(fileName, true));
                fileOut.println(sum);
            }
            finally 
            {         
                if(fileOut != null)
                {             
                    fileOut.close(); 
                }
            }
                
        } else
            {
                
                try
                {           
                    fileOut = new PrintWriter(new FileWriter(fileName, true));                        
                    fileOut.println(sum);
                } 
                finally 
                {         
                    if(fileOut != null)
                    {             
                        fileOut.close(); 
                    }
                }
            }
        
    } //zadanie 3
    
    public static boolean fileExists(String fileName) {
        File f = new File(fileName);
        return f.exists() && f.isFile();
    }
    
    static void Encrypt(String fileName, int push) throws IOException
    {
        /*Funkcja Encrypt dokonuje szyfrowania pliku, którego nazwa podana została jako
        pierwszy parametr. Szyfrowanie polega na zamianie każdej litery na character przesunięty o wartość podaną drugim parametrem np. dla przesunięcia równego 2 literka
        ’a’ powinna zostać zastąpiona literką ’c’, literka ’z’ literką ’b’ itp.
        Wynikiem działania funkcji ma być file o nazwie utworzonej na podstawie nazwy
        pliku wejściowego poprzez dołączenie znaku ’ ’ np. dla pliku dane.txt zaszyfrowana
        postać powinna mieć nazwę dane.txt. Funkcja Decrypt powinna deszyfrować file
        (niekoniecznie ten sam) zaszyfrowany przez funkcję Encrypt.*/
        
        FileReader file = null;
        FileWriter newFile = null;
        int character;
        int tmp;
        
        try
        {
            file = new FileReader(fileName);
            newFile = new FileWriter("_" + fileName,false);
            
            
            while((character = file.read())!=-1)
            {   
                tmp = character + push;
                if( character >= 'a' && character <= 'z')
                {
                    if (tmp > 'z')
                    {
                        tmp = tmp - 26;
                    }                   
                } else if ( character >= 'A' && character <= 'Z')
                    {
                        if(tmp > 'Z')
                        {
                            tmp = tmp - 26;
                        }
                    }
                else 
                {
                    tmp = character;
                }
                newFile.write((char)tmp);
                
            }
            
            file.close();
            newFile.close();
        } 
        catch(FileNotFoundException ex)
        {
            System.out.println("Brak Pliku");
        }
        finally
        {
            if(file != null)
            {
                file.close();
            }
        }         
    } // zadanie 4 a)
    
    static void Decrypt(String fileNameIn, int push) throws IOException
    {
        FileReader file = null;
        int character;
        int tmp;
        try
        {
            file = new FileReader(fileNameIn);
        
        while((character = file.read()) != -1)
        {
            tmp = character - push;
                if( character >= 'a' && character <= 'z')
                {
                    if (tmp < 'a')
                    {
                        tmp = tmp + 26;
                    }                   
                } else if ( character >= 'A' && character <= 'Z')
                    {
                        if(tmp < 'A')
                        {
                            tmp = tmp + 26;
                        }
                    }
                else 
                {
                    tmp = character;
                }
            System.out.print((char)tmp);
        }
        } catch(FileNotFoundException ex)
        {
            System.out.println(ex);
        }
        finally
        {
            if(file != null)
            {
                file.close();
            }
        }
    } // zadanie 4 b)
    
    static void pension(String fileName) throws IOException
    {   
        /*Napisać funkcję pension(String fileName), która wczyta z pliku o podanej
        nazwie dane pracowników zapisane w kolejnych wierszach w następujący sposób:
        Imię Nazwisko Płeć Wiek
        Przykład:
        Tomasz Nowak M 45
        Marta Ziobro K 42
        Jan Kowalski M 27
        Ewelina Tusk K 59
        Następnie funkcja dla każdego pracownika powinna wyznaczyć count lat pozostało
        do jego emerytury. Wyniki należy zapisać w następujący sposób:
        Nazwisko Imię ”Lata do emerytury”
        Przykład:
        Nowak Tomasz 20
        Kowalski Jan 38
        Wyniki dla kobiet należy zapisać w pliku o nazwie ”kobiety.txt”, natomiast wyniki
        dla mężczyzn należy zapisać w pliku ”mezczyzni.txt”.*/
        BufferedReader file = null;
        FileWriter fileWomen = null;
        FileWriter fileMen = null;
        try
        {
            file = new BufferedReader(new FileReader(fileName));
            fileWomen = new FileWriter("kobiety.txt");
            fileMen = new FileWriter("mezczyzni.txt");
            
            String line;
            while((line = file.readLine()) != null)
            {
                if(line.isEmpty())
                {
                    break;
                }
                String[] tab = line.split(" ");
                String tmp;
                int retirementAge;
                if(tab[2].equals("K"))
                {
                    retirementAge = 60 - Integer.parseInt(tab[3]);
                    if(retirementAge > 0)
                    {
                        tmp = Integer.toString(retirementAge);
                    } else tmp = "Emerytura";
                    fileWomen.write(tab[1] + " " + tab[0] + " " + tmp + "\n");  
                } else if (tab[2].equals("M"))
                {
                  retirementAge = 65 - Integer.parseInt(tab[3]);
                    if(retirementAge > 0)
                    {
                        tmp = Integer.toString(retirementAge);
                    } else tmp = "Emerytura";
                    fileMen.write(tab[1] + " " + tab[0] + " " + tmp + "\n");   
                }
            }
        }
        catch(FileNotFoundException ex)
        {
            System.out.println("brak plikow");
        }
        finally
        {
            if (file != null && fileWomen != null && fileMen != null)
            {
                file.close();
                fileWomen.close();
                fileMen.close();
            }
        }
    } // zadanie 5
    
    static void toHTML (String fileName) throws IOException
    {
        /*6. Napisać funkcję, której zadaniem jest odczytanie danych tabelarycznych z pliku
        tekstowego, a następnie zapisanie ich do nowego pliku w postaci kodu HTML.
        Przykład:
        Wejście:
        "Waga" "Wzrost" "BMI" "Nadwaga"
        70 1,8 21,6 "NIE"
        67 1,77 21,39 "NIE"
        85 1,7 29,41 "TAK"
        100 1,92 27,13 "TAK"
        Wynik:
        <html><body>
        <table>
        <tr><td>"Waga"</td><td>"Wzrost"</td><td>"BMI"</td><td>"Nadwaga"
        </td></tr>
        <tr><td>70</td><td>1,8</td><td>21,6</td><td>"NIE"
        </td></tr>
        <tr><td>67</td><td>1,77</td><td>21,39</td><td>"NIE"
        </td></tr>
        <tr><td>85</td><td>1,7</td><td>29,41</td><td>"TAK"
        </td></tr>
        <tr><td>100</td><td>1,92</td><td>27,13</td><td>"TAK"</td></tr>
        </table>
        </body></html>*/
        BufferedReader fileRead = null;
        FileWriter file = null;
        try
        {
            file = new FileWriter(fileName);
            fileRead = new BufferedReader(new FileReader("Wejscie.txt"));
            file.write("<html>\n<body>\n<table>\n");
            String linia;
            while ((linia = fileRead.readLine()) != null)
            {
                if(linia.isEmpty())
                {
                    break;
                }
                
                String[] tab = linia.split(" ");
                file.write("<tr>");
                for(String x : tab)
                {
                    file.write("<td>"+ x +"</td>");
                } 
                file.write("\n</tr>\n");
            }
            file.write("</table>\n</body>\n</html>");
        }
        catch(FileNotFoundException ex)
        {
            System.out.println(ex);
        }
        finally
        {
            if( file != null)
            {
                file.close();
            }
        }
    } // zadanie 6
    
    static void Chart (String fileName) throws IOException
    {
        /*7. Napisać program, który dla pliku tekstowego o podanej nazwie wyznaczy „Chart” częstości wystąpień małych liter alfabetu angielskiego. Słupki wykresu mają
        zostać utworzone ze znaków gwiazdki ’*’, przy czym długość słupka dla najczęściej
        występującej litery powinna wynosić 50. Dodatkowo dla każdego znaku należy dodatkowo wyświetlić liczbę jego wystąpień.
        Poniżej umieszczono przykładowy Chart wygenerowany dla tekstu „Adventures
        of Huckleberry Finn” M. Twaina dostępnego pod adresem:
        http://www.gutenberg.org/dirs/7/76/76.txt*/
        BufferedReader plik = null;
        try
        {
            int max = 0;
            int count = 0;
            char character = 'a';
            for (int i = 1; i <= 26; i++)
            {
                plik = new BufferedReader(new FileReader(fileName));
                String line;
                while ((line = plik.readLine()) != null)
                {
                    count += line.length() -  line.replaceAll(String.valueOf(character), "").length();
                }
                
                if( count > max )
                {
                    max = count;
                }
                int a = (int) ((double)count/max * 50);
                System.out.print(character + " ");
                for (int j = 1; j <= a; j++)
                {
                    System.out.print("*");
                }
                System.out.println(" " + count); 
                character++;               
                count = 0;   
            }
        }
        catch(FileNotFoundException ex)
        {
            System.out.println("Cos nie tak z plikiem");
        }
        finally
        {
            if(plik != null)
            {
                plik.close();
            }
        }
    } // zadanie 7 Wziąłem wersje plain UTF-8
    
    public static void main(String[] args) throws IOException{
        // TODO code application logic here
        //CountWords("src\\zajecia\\pkg4\\abc.txt");
        
        /*Find("src\\zajecia\\pkg4\\egzamin.txt", 
                "src\\zajecia\\pkg4\\poegzaminie.txt",
                "egzamin");
        */
        
        //SumAndSave("src\\zajecia\\pkg4\\sum.txt");
        
       //encrypt("szyfr.txt",1);
       //decrypt("_szyfr.txt",1);
       
       //pension("pension.txt");
       //toHTML("index.txt");
       Chart("gutenberg.txt");
       
   
    }
    
}
