using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
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
            string filename = openFile.SafeFileName;
            readFromFile(filename);
        }

        private void readFromFile(string filename)
        {
            if (!File.Exists(filename))
            {
                return;
            }
            StreamReader sr = File.OpenText(filename);
            if (sr == null)
            {
                return;
            }
            for (int i = 0; i < 3; i++, sr.ReadLine());
            list.Clear();
            while (!sr.EndOfStream)
            {
                string line = sr.ReadLine();
                string[] data = line.Split('│');
                if (data.Length == 6)
                {
                    for (int i = 0; i < data.Length; i++)
                    {
                        data[i] = data[i].Trim();
                    }
                    Museum m = new Museum(data[1], data[2], data[3], data[4]);
                    list.Add(m);
                }
                sr.ReadLine();
            }
            sr.Close();
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
            StreamWriter sw = File.CreateText(filename);
            if (sw == null)
            {
                return;
            }
            sw.WriteLine("");
            sw.WriteLine("");
            foreach (Museum m in list)
            {
                sw.WriteLine();
                sw.WriteLine(String.Format("│{0}│{1}│{2}|{3}│",
                    m.Name,
                    m.Address,
                    m.Owner,
                    m.getVisits()));
            }
            sw.Close();
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
            DataForm df = new DataForm();
            df.ShowDialog();
        }
    }
}
