using System;
using System.IO;

namespace dev275x.studentlist
{
    class Program
    {
        // The Main method 
        static void Main(string[] args)
        {
            /* Check arguments */
            if (args == null || args.Length != 1)
            {
                Console.WriteLine("Usage: dotnet dev275x.rollcall.dll (a | r | c | +WORD | ?WORD)");
                return; // Exit early.
            }

            if (args[0] == "a") 
            {
                Console.WriteLine("Loading data ...");
                var fileStream = new FileStream("students.txt",FileMode.Open);
                var reader = new StreamReader(fileStream);
                var fileContents = reader.ReadToEnd(); 
                var words = fileContents.Split(',');
                foreach(var word in words) 
                {
                    Console.WriteLine(word);
                }
                Console.WriteLine("Data loaded");
            }
            else if (args[0]== "r")
            {
                Console.WriteLine("Loading data ...");

                // We are loading data
                var fileStream = new FileStream("students.txt",FileMode.Open);
                var reader = new StreamReader(fileStream);
                var fileContents = reader.ReadToEnd();
                var words = fileContents.Split(',');
                var rand = new Random();
                var randomIndex = rand.Next(0,words.Length);
                Console.WriteLine(words[randomIndex]);
                Console.WriteLine("Data loaded");
            }
            else if (args[0].Contains("+"))
            {
                // read
                Console.WriteLine("Loading data ...");
                var fileStream = new FileStream("students.txt",FileMode.Open);
                var writer = new StreamWriter(fileStream);
                var argValue = args[0].Substring(1);
                var reader = new StreamReader(fileStream);
                var fileContents = reader.ReadToEnd();
                fileStream.Seek(0,SeekOrigin.Begin);

                // Write
                // But we're in trouble if there are ever duplicates entered
                writer.WriteLine(fileContents.Replace('\n',' ') + "," + argValue);
                var now = DateTime.Now;
                writer.WriteLine(String.Format("List last updated on {0}", now));
                writer.Flush();
                Console.WriteLine("Data loaded");
            }
            else if (args[0].Contains("?"))
            {
                Console.WriteLine("Loading data ...");
                var fileStream = new FileStream("students.txt",FileMode.Open);
                var reader = new StreamReader(fileStream);
                var fileContents = reader.ReadToEnd(); 
                var words = fileContents.Split(',');
                bool done = false;
                var argValue = args[0].Substring(1);
                for (int idx = 0; idx < words.Length && !done; idx++)
                {
                    if (words[idx] == argValue)
                        Console.WriteLine("We found it!");
                        done = true;
                }
            }
            else if (args[0].Contains("c"))
            {
                Console.WriteLine("Loading data ...");
                var fileStream = new FileStream("students.txt",FileMode.Open);
                var reader = new StreamReader(fileStream);
                var fileContents = reader.ReadToEnd();
                var characters = fileContents.ToCharArray();
                var in_word = false;
                var count = 0;
                foreach(var c in characters)
                {
                    if (c > ' ' && c < 0177)
                    {
                        if (!in_word) 
                        {
                            count = count + 1;
                            in_word = true;
                        }
                    }
                    else 
                    {
                        in_word = false;
                    }
                }
                Console.WriteLine(String.Format("{0} words found", count));
            }
            
        }
    }
}