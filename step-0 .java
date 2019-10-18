﻿using System;
using System.IO;
using System.Linq;

namespace dev275x.studentlist
{
    class Program
    {
        static void Main(string[] args)
        {
            /* Check argument count */
            if (args == null || args.Length != 1)
            {
                ShowUsage();
                return; // Exit early.
            }

            // Every operation requires us to load the student list.
            var studentList = LoadStudentList();

            // TODO: Handle case when studentList is empty
            if (args[0] == Constants.ShowAll) 
            {
                // TODO: This action occurs multiple times. Consider refactoring 
                // into a method.
                var students = studentList.Split(Constants.StudentEntryDelimiter);
                foreach(var student in students) 
                {
                    Console.WriteLine(student);
                }
            }
            else if (args[0]== Constants.ShowRandom)
            {
                var students = studentList.Split(Constants.StudentEntryDelimiter);

                // TODO: The sequence of random numbers generated by a single 
                // Random instance is supposed to be uniformly distributed. 
                // By creating a new Random instance every time this operation 
                // is invoked, we run a risk of generating identical random 
                // numbers. Either reseed Random or instantiate it 
                // when the program starts.
                var rand = new Random();
                var randomIndex = rand.Next(0,students.Length);
                Console.WriteLine(students[randomIndex]);
            }
            else if (args[0].StartsWith(Constants.AddEntry)) 
            {
                var newEntry = args[0].Substring(1);

                // TODO: Handle duplicate student names.
                UpdateStudentList(studentList + Constants.StudentEntryDelimiter 
                                  + newEntry);
            }
            else if (args[0].StartsWith(Constants.FindEntry))
            {
                var students = studentList.Split(Constants.StudentEntryDelimiter);
                var searchTerm = args[0].Substring(1);

                // Using the 'Any'  LINQ method to return whether or not 
                // any item  matches the given predicate.
                if (students.Any(s => s.Trim() == searchTerm))
                {
                     Console.WriteLine($"Entry '{searchTerm}' found.");
                }
                else
                {
                     Console.WriteLine($"Entry '{searchTerm}' does not exist.");
                }
            }
            else if (args[0] == Constants.ShowCount)
            {
                var students = studentList.Split(Constants.StudentEntryDelimiter);
                Console.WriteLine(String.Format("{0} words found", students.Length));
            }
            else
            {
                // Arguments were supplied, but they are invalid. We'll handle
                // this case gracefully by listing valid arguments to the user.
                ShowUsage();
            }
        }

        // Reads data from the given file. 
        static string LoadStudentList()
        {
            // The 'using' construct does the heavy lifting of flushing a stream
            // and releasing system resources the stream was using.
            using (var fileStream = new FileStream(Constants.StudentList,FileMode.Open))
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
        static void UpdateStudentList(string content)
        {
            var timestamp = String.Format("List last updated on {0}", DateTime.Now);

            // The 'using' construct does the heavy lifting of flushing a stream
            // and releasing system resources the stream was using.
            using (var fileStream = new FileStream(Constants.StudentList,FileMode.Open))
            using (var writer = new StreamWriter(fileStream))
            {
                writer.WriteLine(content);
                writer.WriteLine(timestamp);
            }
        }

        static void ShowUsage()
        {
            Console.WriteLine($"Usage: dotnet dev275x.rollcall.dll (-a | -r | -c | +WORD | ?WORD)");
        }
    }
}