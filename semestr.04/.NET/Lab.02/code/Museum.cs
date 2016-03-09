using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab._02
{
    class Museum: AMuseum, IVisitable
    {

        private string owner;
        public string Owner
        {
            get { return owner; }
            set { if (owner != "") owner = value; }
        }

        private int visits = 0;
        
        public Museum(string name, string address, string owner): base(name, address) {
            this.owner = owner;
        }

        new public void print()
        {
            Console.WriteLine("Museum \"{0}\", {1}; Owner: {2}, Visits: {3}", Name, Address, owner, visits);
            base.printHalls();
        }

        override public void printInfo()
        {
            Console.WriteLine("Owner: {0}, Visits: {1}", owner, visits);
        }


        public void visit()
        {
            visits++;
        }

        public int getVisits()
        {
            return visits;
        }
    }
}
