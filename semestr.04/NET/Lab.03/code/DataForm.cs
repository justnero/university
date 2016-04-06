using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Lab._03
{
    public partial class DataForm : Form
    {
        private bool IsEdit = false;
        private int ElementID = 0;
        private Museum CurMuseum = null;
        private MainForm Parent = null;

        public DataForm(MainForm parent, int elID, Museum m)
        {
            InitializeComponent();
            Parent = parent;
            IsEdit = elID >= 0 && m != null;
            ElementID = elID;
            CurMuseum = m;

            if(IsEdit)
            {
                actBtn.Text = "Save";
                nameField.Text = m.Name;
                addressField.Text = m.Address;
                ownerField.Text = m.Owner;
                visitsField.Text = Convert.ToString(m.Visits);
            } else
            {
                actBtn.Text = "Add";
            }
        }

        private void actBtn_Click(object sender, EventArgs e)
        {
            Parent.addOrEdit(ElementID, new Museum(nameField.Text, addressField.Text, ownerField.Text, visitsField.Text));
            Close();
        }
    }
}
