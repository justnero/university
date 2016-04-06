using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Runtime.Serialization.Formatters.Binary;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Lab._03
{
    public partial class MainForm : Form
    {
        private BindingList<Museum> list;

        public MainForm()
        {
            InitializeComponent();

            list = new BindingList<Museum>();

            grid.DataSource = list;
            grid.Columns["Name"].DisplayIndex = 0;
            grid.Columns["Address"].DisplayIndex = 1;
            grid.Columns["Owner"].DisplayIndex = 2;
            grid.Columns["Visits"].DisplayIndex = 3;
            grid.EditMode = DataGridViewEditMode.EditProgrammatically;
        }

        private void MainForm_Load(object sender, EventArgs e)
        {
            DialogResult dr = MessageBox.Show("Не желаете ли загрузить данные из файла?", "Загрузка", MessageBoxButtons.YesNo);
            if (dr == DialogResult.Yes)
            {
                openFile.ShowDialog();
            }
        }

        private void openFile_FileOk(object sender, CancelEventArgs e)
        {
            readFromFile(openFile.FileName);
        }

        private void readFromFile(string filename)
        {
            list.Clear();
            Stream stream = new FileStream(filename, FileMode.Open, FileAccess.Read, FileShare.None);
            IFormatter formatter = new BinaryFormatter();
            Console.WriteLine(stream.Length);
            while(stream.Position != stream.Length)
            {
                Museum m = (Museum)formatter.Deserialize(stream);
                list.Add(m);
            }
            stream.Close();
        }

        private void MainForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            DialogResult dr;
            if (list.Count > 0)
            {
                dr = MessageBox.Show("Сохранить данные в файл перед закрытием?", "Закрытие", MessageBoxButtons.YesNoCancel);
                if (dr == DialogResult.Yes)
                {
                    saveFile.ShowDialog();
                    e.Cancel = false;
                }
                else if (dr == DialogResult.No)
                {
                    e.Cancel = false;
                }
                else
                {
                    e.Cancel = true;
                }
            }
            else
            {
                dr = MessageBox.Show("Вы действительно хотите закрыть?", "Закрытие", MessageBoxButtons.YesNo);
                if (dr == DialogResult.Yes)
                {
                    e.Cancel = false;
                }
                else
                {
                    e.Cancel = true;
                }
            }
        }

        private void saveFile_FileOk(object sender, CancelEventArgs e)
        {
            saveToFile(saveFile.FileName);
        }

        private void saveToFile(string filename)
        {
            Stream stream = new FileStream(filename, FileMode.Create, FileAccess.Write, FileShare.None);
            IFormatter formatter = new BinaryFormatter();
            foreach(Museum m in list)
            {
                formatter.Serialize(stream, m);
            }
            stream.Close();
        }

        private void открытьToolStripMenuItem_Click(object sender, EventArgs e)
        {
            openFile.ShowDialog();
        }

        private void сохранитьКакToolStripMenuItem_Click(object sender, EventArgs e)
        {
            saveFile.ShowDialog();
        }

        private void удалитьToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (grid.CurrentRow == null)
            {
                return;
            }
            DialogResult dr = MessageBox.Show("Вы действительно хотите удалить выбранный музей?", "Удаление", MessageBoxButtons.YesNo);
            if (dr == DialogResult.Yes)
            {
                int index = grid.CurrentRow.Index;
                list.RemoveAt(index);
                grid.Refresh();
            }
        }

        private void добавитьToolStripMenuItem_Click(object sender, EventArgs e)
        {
            DataForm df = new DataForm(this, -1, null);
            df.ShowDialog();
        }

        public void addOrEdit(int id, Museum m)
        {
            if(id >= 0)
            {
                list[id] = m;
            }
            else
            {
                list.Add(m);
            }
        }

        private void изменитьToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if(grid.CurrentRow != null)
            {
                DataForm df = new DataForm(this, grid.CurrentRow.Index, list[grid.CurrentRow.Index]);
                df.ShowDialog();
            }
            else
            {
                // ERROR
            }
        }
    }
}
