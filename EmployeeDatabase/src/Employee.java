import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class Employee {
    private JPanel Main;
    private JTextField txtName;
    private JTextField txtSalary;
    private JTextField txtMobile;
    private JButton saveButton;
    private JTable table1;
    private JButton upldateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JTextField searchBox;
    private JScrollPane table_1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Employee");
        frame.setContentPane(new Employee().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    Connection con;
    PreparedStatement pst;

    public void connect()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/rbcompany", "root","");
            System.out.println("Successs");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    void table_load()
    {
        try
        {
            pst = con.prepareStatement("select * from employee");
            ResultSet rs = pst.executeQuery();
            DbUtils DbUtils = null;
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public Employee() {
        connect();
        table_load();
    saveButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String empname,salary,mobile;
            empname = txtName.getText();
            salary = txtSalary.getText();
            mobile = txtMobile.getText();
            try {
                pst = con.prepareStatement("insert into employee(empName,salary,mobile)values(?,?,?)");
                pst.setString(1, empname);
                pst.setString(2, salary);
                pst.setString(3, mobile);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "New Record Added!!!!!");
                //table_load();
                txtName.setText("");
                txtSalary.setText("");
                txtMobile.setText("");
                txtName.requestFocus();
            }
            catch (SQLException e1)
            {
                e1.printStackTrace();
            }
        }
    });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String empid = searchBox.getText();
                    pst = con.prepareStatement("select empName,salary,mobile from employee where id = ?");
                    pst.setString(1, empid);
                    ResultSet rs = pst.executeQuery();
                    if(rs.next()==true)
                    {
                        String empname = rs.getString(1);
                        String emsalary = rs.getString(2);
                        String emmobile = rs.getString(3);

                        txtName.setText(empname);
                        txtSalary.setText(emsalary);
                        txtMobile.setText(emmobile);
                    }
                    else
                    {
                        txtName.setText("");
                        txtSalary.setText("");
                        txtMobile.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Employee No");
                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        upldateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String empid,empname,salary,mobile;
                empname = txtName.getText();
                salary = txtSalary.getText();
                mobile = txtMobile.getText();
                empid =  searchBox.getText();
                try {
                    pst = con.prepareStatement("update employee set empName = ?,salary = ?,mobile = ? where id = ?");
                    pst.setString(1, empname);
                    pst.setString(2, salary);
                    pst.setString(3, mobile);
                    pst.setString(4, empid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "New Record Updated!!!!!");
                    table_load();
                    txtName.setText("");
                    txtSalary.setText("");
                    txtMobile.setText("");
                    txtName.requestFocus();
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String empid;
                empid = searchBox.getText();
                try {
                    pst = con.prepareStatement("delete from employee where id = ?");
                    pst.setString(1, empid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleted!!!!!");
                    table_load();
                    txtName.setText("");
                    txtSalary.setText("");
                    txtMobile.setText("");
                    txtName.requestFocus();
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
    }
}
