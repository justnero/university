using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Windows.Forms;

namespace lab4
{
    public partial class Table : Form
    {
        public BindingList<Museum> Museums = new BindingList<Museum>();
        public DataGridView DataGridView { get { return dataGridView1;} }
        public Table()
        {
            InitializeComponent();
            dataGridView1.DataSource = Museums;
            dataGridView1.AutoSizeColumnsMode=DataGridViewAutoSizeColumnsMode.Fill;
            dataGridView1.AutoSizeRowsMode=DataGridViewAutoSizeRowsMode.AllCells;
            Closing += (sender,e) =>
            {
                Container c = (Container) MdiParent;
                c.ChildrenOpen--;
            };
        }



        public Table(IEnumerable<Museum> museumsCollection): this()
        {
            foreach (Museum tiger in museumsCollection)
            {
                Museums.Add(tiger);
            }
        }

        
    }
}
