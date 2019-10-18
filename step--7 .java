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
                Console.WriteLine("Usage: dotnet dev275x.rollcall.dll (-a | -r | -c | +WORD | ?WORD)");
                return; // Exit early.
            }

            // Every operation requires us to load the student list.
            var fileContents = LoadData(Constants.StudentList);

            if (args[0] == Constants.ShowAll) 
            {
                var words = fileContents.Split(Constants.StudentEntryDelimiter);
                foreach(var word in words) 
                {
                    Console.WriteLine(word);
                }
            }
            else if (args[0]== Constants.ShowRandom)
            {
                var words = fileContents.Split(Constants.StudentEntryDelimiter);
                var rand = new Random();
                var randomIndex = rand.Next(0,words.Length);
                Console.WriteLine(words[randomIndex]);
            }
            else if (args[0].Contains(Constants.AddEntry))
            {
                var argValue = args[0].Substring(1);

                // Write
                // But we're in trouble if there are ever duplicates entered
                UpdateContent(fileContents + Constants.StudentEntryDelimiter + argValue, Constants.StudentList);
            }
            else if (args[0].Contains(Constants.FindEntry))
            {
                var words = fileContents.Split(Constants.StudentEntryDelimiter);
                var argValue = args[0].Substring(1);
                var indexLocation = -1;
                for (int idx = 0; idx < words.Length; idx++)
                {
                    if (words[idx].Trim() == argValue)
                    {
                        indexLocation = idx;
                        break;
                    }
                }

                if (indexLocation >= 0)
                {
                    Console.WriteLine($"Entry '{argValue}' found at index {indexLocation}");
                }
                else
                {
                    Console.WriteLine($"Entry '{argValue}' does not exist");
                }
            }
            else if (args[0].Contains(Constants.ShowCount))
            {
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

        // Reads data from the given file. 
        static string LoadData(string fileName)
        {
            // The 'using' construct does the heavy lifting of flushing a stream
            // and releasing system resources the stream was using.
            using (var fileStream = new FileStream(fileName,FileMode.Open))
            using (var reader = new StreamReader(fileStream))
            {

                // The format of our student list is that it is two lines.
                // The first line is a comma-separated list of student names. 
                // The second line is a timestamp. 
                // Let's just retrieve the first line, which is the student names. 
                return reader.ReadLine();
            }
        }

        // Writes the given string of data to the file with the given file name.
        //This method also adds a timestamp to the end of the file. 
        static void UpdateContent(string content, string fileName)
        {
            var timestamp = String.Format("List last updated on {0}", DateTime.Now);

            // The 'using' construct does the heavy lifting of flushing a stream
            // and releasing system resources the stream was using.
            using (var fileStream = new FileStream(fileName,FileMode.Open))
            using (var writer = new StreamWriter(fileStream))
            {
                writer.WriteLine(content);
                writer.WriteLine(timestamp);
            }
        }
    }
}