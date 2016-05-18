namespace lab4
{
    partial class EditForm
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
            this.label1 = new System.Windows.Forms.Label();
            this.nameField = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.visitsField = new System.Windows.Forms.TrackBar();
            this.visitsLabel = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.button1 = new System.Windows.Forms.Button();
            this.ownerField = new System.Windows.Forms.TextBox();
            this.addressField = new System.Windows.Forms.TextBox();
            ((System.ComponentModel.ISupportInitialize)(this.visitsField)).BeginInit();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(17, 31);
            this.label1.Margin = new System.Windows.Forms.Padding(8, 0, 8, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(152, 32);
            this.label1.TabIndex = 0;
            this.label1.Text = "Название:";
            // 
            // nameField
            // 
            this.nameField.Location = new System.Drawing.Point(268, 28);
            this.nameField.Margin = new System.Windows.Forms.Padding(8, 7, 8, 7);
            this.nameField.Name = "nameField";
            this.nameField.Size = new System.Drawing.Size(522, 38);
            this.nameField.TabIndex = 1;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(17, 83);
            this.label2.Margin = new System.Windows.Forms.Padding(8, 0, 8, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(154, 32);
            this.label2.TabIndex = 2;
            this.label2.Text = "Владелец:";
            // 
            // visitsField
            // 
            this.visitsField.Location = new System.Drawing.Point(268, 194);
            this.visitsField.Margin = new System.Windows.Forms.Padding(8, 7, 8, 7);
            this.visitsField.Maximum = 1000;
            this.visitsField.Name = "visitsField";
            this.visitsField.Size = new System.Drawing.Size(522, 114);
            this.visitsField.TabIndex = 3;
            this.visitsField.Scroll += new System.EventHandler(this.trackBar1_Scroll);
            // 
            // visitsLabel
            // 
            this.visitsLabel.AutoSize = true;
            this.visitsLabel.Location = new System.Drawing.Point(282, 276);
            this.visitsLabel.Margin = new System.Windows.Forms.Padding(8, 0, 8, 0);
            this.visitsLabel.Name = "visitsLabel";
            this.visitsLabel.Size = new System.Drawing.Size(31, 32);
            this.visitsLabel.TabIndex = 4;
            this.visitsLabel.Text = "0";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(17, 135);
            this.label4.Margin = new System.Windows.Forms.Padding(8, 0, 8, 0);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(104, 32);
            this.label4.TabIndex = 5;
            this.label4.Text = "Адрес:";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(17, 194);
            this.label5.Margin = new System.Windows.Forms.Padding(8, 0, 8, 0);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(175, 32);
            this.label5.TabIndex = 8;
            this.label5.Text = "Посещения:";
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(23, 336);
            this.button1.Margin = new System.Windows.Forms.Padding(8, 7, 8, 7);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(767, 55);
            this.button1.TabIndex = 10;
            this.button1.Text = "OK";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // ownerField
            // 
            this.ownerField.Location = new System.Drawing.Point(268, 80);
            this.ownerField.Margin = new System.Windows.Forms.Padding(8, 7, 8, 7);
            this.ownerField.Name = "ownerField";
            this.ownerField.Size = new System.Drawing.Size(522, 38);
            this.ownerField.TabIndex = 11;
            // 
            // addressField
            // 
            this.addressField.Location = new System.Drawing.Point(268, 132);
            this.addressField.Margin = new System.Windows.Forms.Padding(8, 7, 8, 7);
            this.addressField.Name = "addressField";
            this.addressField.Size = new System.Drawing.Size(522, 38);
            this.addressField.TabIndex = 12;
            // 
            // EditForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(16F, 31F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.Window;
            this.ClientSize = new System.Drawing.Size(821, 407);
            this.Controls.Add(this.addressField);
            this.Controls.Add(this.ownerField);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.visitsLabel);
            this.Controls.Add(this.visitsField);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.nameField);
            this.Controls.Add(this.label1);
            this.Margin = new System.Windows.Forms.Padding(8, 7, 8, 7);
            this.Name = "EditForm";
            this.Text = "EditForm";
            this.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.EditForm_KeyPress);
            ((System.ComponentModel.ISupportInitialize)(this.visitsField)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox nameField;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TrackBar visitsField;
        private System.Windows.Forms.Label visitsLabel;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.TextBox ownerField;
        private System.Windows.Forms.TextBox addressField;
    }
}