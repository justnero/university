namespace Lab._03
{
    partial class DataForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(DataForm));
            this.tableLayoutPanel1 = new System.Windows.Forms.TableLayoutPanel();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.nameField = new System.Windows.Forms.TextBox();
            this.addressField = new System.Windows.Forms.TextBox();
            this.ownerField = new System.Windows.Forms.TextBox();
            this.visitsField = new System.Windows.Forms.TextBox();
            this.actBtn = new System.Windows.Forms.Button();
            this.tableLayoutPanel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // tableLayoutPanel1
            // 
            this.tableLayoutPanel1.ColumnCount = 2;
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel1.Controls.Add(this.actBtn, 1, 4);
            this.tableLayoutPanel1.Controls.Add(this.label1, 0, 0);
            this.tableLayoutPanel1.Controls.Add(this.label2, 0, 1);
            this.tableLayoutPanel1.Controls.Add(this.label3, 0, 2);
            this.tableLayoutPanel1.Controls.Add(this.label4, 0, 3);
            this.tableLayoutPanel1.Controls.Add(this.nameField, 1, 0);
            this.tableLayoutPanel1.Controls.Add(this.addressField, 1, 1);
            this.tableLayoutPanel1.Controls.Add(this.ownerField, 1, 2);
            this.tableLayoutPanel1.Controls.Add(this.visitsField, 1, 3);
            this.tableLayoutPanel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel1.Location = new System.Drawing.Point(0, 0);
            this.tableLayoutPanel1.Name = "tableLayoutPanel1";
            this.tableLayoutPanel1.RowCount = 5;
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 20F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 20F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 20F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 20F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 20F));
            this.tableLayoutPanel1.Size = new System.Drawing.Size(539, 247);
            this.tableLayoutPanel1.TabIndex = 0;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label1.Location = new System.Drawing.Point(3, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(263, 49);
            this.label1.TabIndex = 0;
            this.label1.Text = "Name";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label2.Location = new System.Drawing.Point(3, 49);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(263, 49);
            this.label2.TabIndex = 1;
            this.label2.Text = "Address";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label3.Location = new System.Drawing.Point(3, 98);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(263, 49);
            this.label3.TabIndex = 2;
            this.label3.Text = "Owner";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label4.Location = new System.Drawing.Point(3, 147);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(263, 49);
            this.label4.TabIndex = 3;
            this.label4.Text = "Visits";
            // 
            // nameField
            // 
            this.nameField.Dock = System.Windows.Forms.DockStyle.Fill;
            this.nameField.Location = new System.Drawing.Point(272, 3);
            this.nameField.Name = "nameField";
            this.nameField.Size = new System.Drawing.Size(264, 31);
            this.nameField.TabIndex = 4;
            // 
            // addressField
            // 
            this.addressField.Dock = System.Windows.Forms.DockStyle.Fill;
            this.addressField.Location = new System.Drawing.Point(272, 52);
            this.addressField.Name = "addressField";
            this.addressField.Size = new System.Drawing.Size(264, 31);
            this.addressField.TabIndex = 5;
            // 
            // ownerField
            // 
            this.ownerField.Dock = System.Windows.Forms.DockStyle.Fill;
            this.ownerField.Location = new System.Drawing.Point(272, 101);
            this.ownerField.Name = "ownerField";
            this.ownerField.Size = new System.Drawing.Size(264, 31);
            this.ownerField.TabIndex = 6;
            // 
            // visitsField
            // 
            this.visitsField.Dock = System.Windows.Forms.DockStyle.Fill;
            this.visitsField.Location = new System.Drawing.Point(272, 150);
            this.visitsField.Name = "visitsField";
            this.visitsField.Size = new System.Drawing.Size(264, 31);
            this.visitsField.TabIndex = 7;
            // 
            // actBtn
            // 
            this.actBtn.Dock = System.Windows.Forms.DockStyle.Fill;
            this.actBtn.Location = new System.Drawing.Point(272, 199);
            this.actBtn.Name = "actBtn";
            this.actBtn.Size = new System.Drawing.Size(264, 45);
            this.actBtn.TabIndex = 1;
            this.actBtn.Text = "Save";
            this.actBtn.UseVisualStyleBackColor = true;
            this.actBtn.Click += new System.EventHandler(this.actBtn_Click);
            // 
            // DataForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(12F, 25F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoSize = true;
            this.ClientSize = new System.Drawing.Size(539, 247);
            this.Controls.Add(this.tableLayoutPanel1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Margin = new System.Windows.Forms.Padding(6, 6, 6, 6);
            this.Name = "DataForm";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
            this.Text = "DataForm";
            this.tableLayoutPanel1.ResumeLayout(false);
            this.tableLayoutPanel1.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel1;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox nameField;
        private System.Windows.Forms.TextBox addressField;
        private System.Windows.Forms.TextBox ownerField;
        private System.Windows.Forms.TextBox visitsField;
        private System.Windows.Forms.Button actBtn;
    }
}