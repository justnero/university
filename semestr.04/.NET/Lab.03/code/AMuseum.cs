using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab._03
{
    abstract class AMuseum
    {

        private string name;
        public string Name
        {
            get { return name; }
            set { if (name == null) { name = value; } else { throw new InvalidValueException(FieldType.Name); } }
        }

        private string address;
        public string Address
        {
            get { return address; }
            set { if (address != "") { address = value; } else { throw new InvalidValueException(FieldType.Address); } }
        }

        private int hall_count = 0;
        protected int Hall_count
        {
            get { return hall_count; }
        }

        const int hall_max_count = 100;

        private string[] halls = new string[hall_max_count];
        public string this[int i]
        {
            get 
            {
                if (i >= 0 && i <= hall_count)
                {
                    return halls[i];
                }
                else
                {
                    throw new InvalidValueException(FieldType.Index);
                }
            }
            set 
            {
                if (i == hall_count)
                {
                    halls[i] = value;
                    hall_count++;
                }
                else if (i >= 0 && i <= hall_count)
                {
                    halls[i] = value;
                }
                else
                {
                    throw new InvalidValueException(FieldType.Index);
                }
            }
        }

        public AMuseum(string name, string address)
        {
            this.name = name;
            this.address = address;
        }

        public virtual void print()
        {
            Console.WriteLine("Museum \"{0}\", {1}", name, address);
        }

        public void printHalls()
        {
            for (int i = 0; i < hall_count;i++)
            {
                Console.WriteLine("Hall #{0}: {1}", i+1, this[i]);
            }
        }

        public abstract void printInfo();
    
    }
}
