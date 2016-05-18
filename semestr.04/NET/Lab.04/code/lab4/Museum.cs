using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab4
{
    [Serializable]
    public class Museum
    {
        public string Name { get; set; }
        public string Owner { get; set; }
        public string Address { get; set; }
        public int Visits { get; set; }

        public Museum(string name, string owner, string address, int visits)
        {
            Name = name;
            Owner = owner;
            Address = address;
            Visits = visits;
        }
    }
}
