using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab._03
{
    [Serializable]
    public class Museum : AMuseum, IVisitable, INotifyPropertyChanged
    {

        private string owner;
        public string Owner
        {
            get { return owner; }
            set { if (owner != "") owner = value; }
        }

        private int visits = 0;
        public int Visits
        {
            get { return visits; }
            set { if (visits == 0) visits = value; }
        }

        public Museum(string name, string address, string owner)
            : base(name, address)
        {
            this.owner = owner;
        }

        public Museum(string name, string address, string owner, int visits)
            : base(name, address)
        {
            this.owner = owner;
            this.visits = visits;
        }

        public Museum(string name, string address, string owner, string visits)
            : base(name, address)
        {
            this.owner = owner;
            this.visits = int.Parse(visits);
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

        public event PropertyChangedEventHandler PropertyChanged;
        private void NotifyPropertyChanged(string name)
        {
            if (PropertyChanged != null)
                PropertyChanged(this, new PropertyChangedEventArgs(name));
        }

    }
}
