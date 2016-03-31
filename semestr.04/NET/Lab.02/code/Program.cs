using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace Lab._02
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("\t\tSevSU semestr.04 .NET Lab.02");
            Museum m = new Museum("Центральный", "ул. Пушкина", "Город");
            m[0] = "Холл";
            m[1] = "Зал A";
            m[2] = "Зал Б";
            m[3] = "Зал В";
            m[4] = "Зал Г";

            Random rnd = new Random();
            for (int i = 0; i < rnd.Next(100); i++)
            {
                m.visit();
            }
            try
            {
                m.Name = "Областной";
            }
            catch (InvalidValueException ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
            m.print();
            Console.WriteLine("\tPress any key to exit...");
            Console.ReadKey();
        }
    }
}
