using System;
using System.IO;
using System.Linq;
using System.Runtime.Serialization.Formatters.Binary;
using System.Windows.Forms;


namespace lab4
{
    public partial class Container : Form
    {
        public int ChildrenOpen { get; set; }

        public Container()
        {
            ChildrenOpen = 0;
            InitializeComponent();
            Text = "Museums";
            IsMdiContainer = true;
        }

        public sealed override string Text
        {
            get { return base.Text; }
            set { base.Text = value; }
        }

        private void newToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Table table=new Table();
            ChildrenOpen++;
            table.MdiParent = this;
            table.Text= "Table " + ChildrenOpen;
            table.Show();
        }

        private void addToolStripMenuItem_Click(object sender, EventArgs e)
        {
            EditForm form=new EditForm();
            DialogResult result = form.ShowDialog();
            if (result == DialogResult.OK && form.Result != null)
            {
               
                    if (ActiveMdiChild == null)
                    {
                        newToolStripMenuItem_Click(sender,e);
                    }

                var table = (Table) ActiveMdiChild;
                table?.Museums.Add(form.Result);
            }
        }

        private void deleteToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (ActiveMdiChild == null)
            {
                MessageBox.Show("Не открыто ни одной формы", "Ошибка", MessageBoxButtons.OK, MessageBoxIcon.Hand);
                return;
            }
            Table table = (Table) ActiveMdiChild;
            var currentRow = table.DataGridView.CurrentRow;
            Museum t = (Museum) currentRow?.DataBoundItem;
            if (t != null)
            {
                table.Museums.Remove(t);
            }
        }

        private void editATigerToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (ActiveMdiChild == null)
            {
                MessageBox.Show("Не открыто ни одной формы", "Ошибка", MessageBoxButtons.OK, MessageBoxIcon.Hand);
                return;
            }
            Table table = (Table)ActiveMdiChild;
            Museum t = (Museum)table.DataGridView.CurrentRow?.DataBoundItem;
            if (t != null)
            {
                int index = table.Museums.IndexOf(t);
                using (EditForm editForm=new EditForm(t))
                {
                    var result = editForm.ShowDialog();
                    if (result == DialogResult.OK)
                    {
                        table.Museums[index] = editForm.Result;
                    }
                }
            }
        }

        private void saveToolStripMenuItem_Click(object sender, EventArgs e)
        {

            if (ActiveMdiChild == null)
            {
                MessageBox.Show("Не открыто ни одной таблицы!", "Ошибка", MessageBoxButtons.OK, MessageBoxIcon.Asterisk);
                return;
            }

            SaveFileDialog sfd = new SaveFileDialog();
            Table table = (Table) ActiveMdiChild;
            sfd.Filter = "dat-файл(*.dat)|*.dat";
            if (sfd.ShowDialog() == DialogResult.OK)
            {
                BinaryFormatter formatter = new BinaryFormatter();
                FileStream fs = File.Open(sfd.FileName, FileMode.OpenOrCreate);

                formatter.Serialize(fs, table.Museums.ToArray());
                fs.Close();
            }

           
        }

        private void openToolStripMenuItem_Click(object sender, EventArgs e)
        {
            OpenFileDialog ofd = new OpenFileDialog();
            if (ofd.ShowDialog() == DialogResult.OK)
            {
                Museum[] tigers;
                try
                {
                    BinaryFormatter formatter = new BinaryFormatter();
                    FileStream fs = File.Open(ofd.FileName, FileMode.OpenOrCreate);

                    tigers = (Museum[])formatter.Deserialize(fs);
                    
                }
                catch (IOException)
                {
                    return;
                }
                catch (NullReferenceException)
                {
                    return;
                }
                catch (InvalidCastException)
                {
                    MessageBox.Show("Неверный тип файла", "Ошибка!", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return;
                }

                Table table=new Table(tigers);
                ChildrenOpen++;
                table.MdiParent = this;
                table.Text = "Table " + ChildrenOpen;
                table.Show();
            }

        }
    }
    }

