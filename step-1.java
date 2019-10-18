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
            if (args[0] == "a") 
            {
                Console.WriteLine("Loading data ...");
                var s = new FileStream("students.txt",FileMode.Open);
                var r = new StreamReader(s);
                var D = r.ReadToEnd(); 
                var i = D.Split(',');
                foreach(var j in i) 
                {
                    Console.WriteLine(j);
                }
                Console.WriteLine("Data loaded");
            }
            else if (args[0]== "r")
            {
                Console.WriteLine("Loading data ...");

                // We are loading data
                var s = new FileStream("students.txt",FileMode.Open);
                var r = new StreamReader(s);
                var d = r.ReadToEnd();
                var i = d.Split(',');
                var x = new Random();
                var y = x.Next(0,i.Length);
                Console.WriteLine(i[y]);
                Console.WriteLine("Data loaded");
            }
            else if (args[0].Contains("+"))
            {
                // read
                Console.WriteLine("Loading data ...");
                var s = new FileStream("students.txt",FileMode.Open);
                var r = new StreamWriter(s);
                var t = args[0].Substring(1);
                var g = new StreamReader(s);
                var d = g.ReadToEnd();
                s.Seek(0,SeekOrigin.Begin);

                // Write
                // But we're in trouble if there are ever duplicates entered
                r.WriteLine(d.Replace('\n',' ') + "," + t);
                var now = DateTime.Now;
                r.WriteLine(String.Format("List last updated on {0}", now));
                r.Flush();
                Console.WriteLine("Data loaded");
            }
            else if (args[0].Contains("?"))
            {
                Console.WriteLine("Loading data ...");
                var s = new FileStream("students.txt",FileMode.Open);
                var r = new StreamReader(s);
                var D = r.ReadToEnd(); 
                var i = D.Split(',');
                bool done = false;
                var t = args[0].Substring(1);
                for (int idx = 0; idx < i.Length && !done; idx++)
                {
                    if (i[idx] == t)
                        Console.WriteLine("We found it!");
                        done = true;
                }
            }
            else if (args[0].Contains("c"))
            {
                Console.WriteLine("Loading data ...");
                var s = new FileStream("students.txt",FileMode.Open);
                var r = new StreamReader(s);
                var D = r.ReadToEnd();
                var a = D.ToCharArray();
                var in_word = false;
                var count = 0;
                foreach(var c in a)
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