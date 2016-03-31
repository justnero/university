using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace Lab._01
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("\t\tSevSU semestr.04 .NET Lab.01");
            task_1();
            task_2();
            task_3();
            Console.WriteLine("\tPress any key to exit...");
            Console.ReadKey();
        }

        static void task_1()
        {
            Console.WriteLine("\tTask 01");
            int[,] a = new int[12,12];
            int k = 1;
            for(int i=0;i<12;i++) {
                a[i, 0] = a[0, i] = a[11, i] = a[i, 11] = -1;
            }
            int x = 1, y = 1;
            int dir = 0;
            while (k <= 100)
            {
                a[x, y] = k++;
                switch (dir)
                {
                    case 0:
                        if (a[x, y + 1] != 0)
                        {
                            dir = 1;
                            x += 1;
                        }
                        else
                        {
                            y += 1;
                        }
                        break;
                    case 1:
                        if (a[x + 1, y] != 0)
                        {
                            dir = 2;
                            y -= 1;
                        }
                        else
                        {
                            x += 1;
                        }
                        break;
                    case 2:
                        if (a[x, y - 1] != 0)
                        {
                            dir = 3;
                            x -= 1;
                        }
                        else
                        {
                            y -= 1;
                        }
                        break;
                    case 3:
                        if (a[x - 1, y] != 0)
                        {
                            dir = 0;
                            y += 1;
                        }
                        else
                        {
                            x -= 1;
                        }
                        break;
                }
            }
            for (int i = 1; i <= 10; i++)
            {
                for (int j = 1; j <= 10; j++)
                {
                    Console.Write("{0,3} ", a[i, j]);
                    
                }
                Console.WriteLine();
            }
            Console.WriteLine();
        }

        static void task_2()
        {
            Console.WriteLine("\tTask 02");
            Console.WriteLine("Write text line");
            string str = Console.ReadLine();
            Console.WriteLine("Write word to be replaced");
            string from = Console.ReadLine();
            Console.WriteLine("Write word to be the replacement");
            string to = Console.ReadLine();
            str = str.Replace(from, to);
            Console.WriteLine("New line:");
            Console.WriteLine(str);
            Console.WriteLine();
        }

        static void task_3()
        {
            Console.WriteLine("\tTask 03");
            Console.WriteLine("Write text line");
            string str = Console.ReadLine();
            char[] sep = { ' ', ',', '!', '.', '?' };
            string regexpr = @"([^\s.,!?]+)";
            Regex pat = new Regex(regexpr);
            Match match = pat.Match(str);
            Console.WriteLine("Words:");
            while (match.Success)
            {
                Console.WriteLine(match.Value);
                match = match.NextMatch();
            }
        }
    }
}
