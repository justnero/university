using System;
using System.Drawing;
using System.IO;
using System.Windows.Forms;

namespace lab4
{
    public partial class EditForm : Form
    {
        public Museum Result;

        public EditForm()
        {
            InitializeComponent();
        }

        public EditForm(Museum t)
        {
            InitializeComponent();

            nameField.Text = t.Name;
            ownerField.Text = t.Owner;
            addressField.Text = t.Address;
            visitsLabel.Text = t.Visits.ToString();
            visitsField.Value = t.Visits;
        }

        private void trackBar1_Scroll(object sender, EventArgs e)
        {
       
        
            visitsLabel.Text = $"{visitsField.Value}";
        
    }
        
        private void button1_Click(object sender, EventArgs e)
        {
            string name = nameField.Text ?? "Музей смерти";
            string owner = ownerField.Text ?? "Сатана";
            string address = addressField.Text ?? "Ад";
            int visits = visitsField.Value;
            Result = new Museum(name, owner, address, visits);

            DialogResult = DialogResult.OK;
            Close();
        }

        private void EditForm_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar == 13)
            {
                button1_Click(sender, e);
            }
        }
    }
    }

