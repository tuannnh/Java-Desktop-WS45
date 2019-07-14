
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author tnnh
 */
public class DepartmentManager extends JFrame {

    JPanel treePanel;
    JPanel depPanel;
    JPanel emPanel;
    JPanel detailPanel;

    JLabel depCodeLabel;
    JLabel depNameLabel;

    JLabel empCodeLabel;
    JLabel empNameLabel;
    JLabel salaryLabel;

    JScrollPane treeScrollPane;
    JTree tree;

    JTextField depCodeTextField;
    JTextField depNameTextField;
    JTextField empCodeTextField;
    JTextField empNameTextField;
    JTextField salaryTextField;

    JButton depNewButton;
    JButton depRemoveButton;
    JButton depSaveButton;
    JButton empNewButton;
    JButton empRemoveButton;
    JButton empSaveButton;
    JButton saveToFileButton;

    //Tree
    String fileName = "employee.txt";
    DefaultMutableTreeNode root = null;
    DefaultMutableTreeNode curDepNode = null;
    DefaultMutableTreeNode curEmpNode = null;
    boolean addNewDep = false;
    boolean addNewEmp = false;

    public DepartmentManager() {
        initComponents();
    }

    void initComponents() {
        depPanel = new JPanel();
        emPanel = new JPanel();
        treePanel = new JPanel();
        treeScrollPane = new JScrollPane();
        detailPanel = new JPanel();
        Container rootContainer = getRootPane();
        Container detailPanelContainer = detailPanel;
        Container treePanelContainer = treePanel;
        Container treeScrollPaneContainer = treeScrollPane;
        Container depPanelContainer = depPanel;
        Container emPanelContainer = emPanel;

        rootContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        detailPanelContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        depPanelContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 15));
        emPanelContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        treePanelContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        treePanelContainer.add(treeScrollPane);

        tree = new JTree();
        treeScrollPaneContainer.setLayout(new ScrollPaneLayout());
        treeScrollPane.setPreferredSize(new Dimension(200, 272));

        TitledBorder border = new TitledBorder("");
        border.setTitleJustification(TitledBorder.LEFT);
        border.setTitlePosition(TitledBorder.TOP);
        treePanel.setBorder(border);
        treePanel.setPreferredSize(new Dimension(200, 300));

        detailPanel.setPreferredSize(new Dimension(345, 320));

        border = new TitledBorder("Department Details");
        depPanel.setPreferredSize(new Dimension(345, 140));
        depPanel.setBorder(border);

        border = new TitledBorder("Employee Details");
        emPanel.setPreferredSize(new Dimension(345, 150));
        emPanel.setBorder(border);

        rootContainer.add(treePanel);
        rootContainer.add(detailPanel);
        detailPanelContainer.add(depPanel);
        detailPanelContainer.add(emPanel);

        depCodeLabel = new JLabel("Dept.Code:");
        depCodeLabel.setPreferredSize(new Dimension(120, 20));
        depNameLabel = new JLabel("Dept.Name:");
        depNameLabel.setPreferredSize(new Dimension(120, 20));
        depCodeTextField = new JTextField();
        depCodeTextField.setPreferredSize(new Dimension(100, 20));
        depNameTextField = new JTextField();
        depNameTextField.setPreferredSize(new Dimension(180, 20));
        depNewButton = new JButton("New");
        depNewButton.setPreferredSize(new Dimension(100, 22));
        depSaveButton = new JButton("Save");
        depSaveButton.setPreferredSize(new Dimension(100, 22));
        depRemoveButton = new JButton("Remove");
        depRemoveButton.setPreferredSize(new Dimension(100, 22));

        depPanelContainer.add(depCodeLabel);
        depPanelContainer.add(depCodeTextField);
        depPanelContainer.add(depNameLabel);
        depPanelContainer.add(depNameTextField);
        depPanelContainer.add(depNewButton);
        depPanelContainer.add(depSaveButton);
        depPanelContainer.add(depRemoveButton);

        empCodeLabel = new JLabel("Emp.Code:");
        empCodeLabel.setPreferredSize(new Dimension(120, 20));
        empNameLabel = new JLabel("Emp.Name:");
        empNameLabel.setPreferredSize(new Dimension(120, 20));
        salaryLabel = new JLabel("Salary:");
        salaryLabel.setPreferredSize(new Dimension(120, 20));
        empCodeTextField = new JTextField();
        empCodeTextField.setPreferredSize(new Dimension(100, 20));
        empNameTextField = new JTextField();
        empNameTextField.setPreferredSize(new Dimension(180, 20));
        salaryTextField = new JTextField();
        salaryTextField.setPreferredSize(new Dimension(100, 20));
        empNewButton = new JButton("New");
        empNewButton.setPreferredSize(new Dimension(100, 22));
        empSaveButton = new JButton("Save");
        empSaveButton.setPreferredSize(new Dimension(100, 22));
        empRemoveButton = new JButton("Remove");
        empRemoveButton.setPreferredSize(new Dimension(100, 22));

        emPanelContainer.add(empCodeLabel);
        emPanelContainer.add(empCodeTextField);
        emPanelContainer.add(empNameLabel);
        emPanelContainer.add(empNameTextField);
        emPanelContainer.add(salaryLabel);
        emPanelContainer.add(salaryTextField);
        emPanelContainer.add(empNewButton);
        emPanelContainer.add(empSaveButton);
        emPanelContainer.add(empRemoveButton);

        //TreeModel
        saveToFileButton = new JButton("Save To File");
        saveToFileButton.setPreferredSize(new Dimension(200, 22));
        treePanelContainer.add(treeScrollPane);
        treePanelContainer.add(saveToFileButton);
        treeScrollPane.setViewportView(tree);
        DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Departments");
        tree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));

        root = (DefaultMutableTreeNode) (this.tree.getModel().getRoot());
        loadData();
        TreePath path = new TreePath(root);
        tree.expandPath(path);

        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                treeMouseClicked(evt);
            }

        });

        depNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                depNewButtonActionPerformed(e);
            }
        });

        depRemoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                depRemoveButtonActionPerformed(e);
            }
        });

        depSaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                depSaveButtonActionPerformed(e);
            }
        });

        empNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empNewButtonActionPerformed(e);
            }
        });

        empRemoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empRemoveButtonActionPerformed(e);
            }
        });

        empSaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empSaveButtonActionPerformed(e);
            }
        });

        saveToFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToFile();
            }
        });

    }

    private void loadData() {
        this.depCodeTextField.setEditable(false);
        String S = "";
        StringTokenizer stk;
        try {
            FileReader f = new FileReader(fileName);
            BufferedReader bf = new BufferedReader(f);
            while ((S = bf.readLine()) != null) {
                S = S.trim();
                boolean isDep = (S.charAt(S.length() - 1) == ':');
                stk = new StringTokenizer(S, "-:,");
                String code = stk.nextToken().trim();
                String name = stk.nextToken().trim();
                if (isDep) {
                    curDepNode = new DefaultMutableTreeNode(new Department(code, name));
                    root.add(curDepNode);
                } else {
                    int salary = Integer.parseInt(stk.nextToken().trim());
                    curEmpNode = new DefaultMutableTreeNode(new Employee(code, name, salary));
                    curDepNode.add(curEmpNode);
                }

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    private void viewDepAndEmp() {
        Department curDep = null;
        Employee curEmp = null;
        if (curDepNode != null) {
            curDep = (Department) (curDepNode.getUserObject());
        }
        if (curEmpNode != null) {
            curEmp = (Employee) (curEmpNode.getUserObject());
        }
        this.depCodeTextField.setText(curDep != null ? curDep.getDepCode() : "");
        this.depNameTextField.setText(curDep != null ? curDep.getDepName() : "");
        this.empCodeTextField.setText(curEmp != null ? curEmp.getEmpCode() : "");
        this.empNameTextField.setText(curEmp != null ? curEmp.getEmpName() : "");
        this.salaryTextField.setText("" + (curEmp != null ? curEmp.getSalary() : ""));
        this.depCodeTextField.setEditable(false);
        this.empCodeTextField.setEditable(false);
    }

    private boolean validDep() {
        String code = this.depCodeTextField.getText().trim();
        if (!code.matches("(D)\\d{3}")) {
            JOptionPane.showMessageDialog(this, "Invalid code [Dxxx]");
            this.depCodeTextField.requestFocus();
            return false;
        }
        for (int i = 0; i < root.getChildCount(); i++) {
            DefaultMutableTreeNode depNode = (DefaultMutableTreeNode) root.getChildAt(i);
            Department dep = (Department) depNode.getUserObject();
            if (code.equalsIgnoreCase(dep.getDepCode())) {
                JOptionPane.showMessageDialog(this, "This code is existed!");
                this.depCodeTextField.requestFocus();
                return false;
            }
        }
        return true;
    }

    private boolean validEmp() {
        String code = this.empCodeTextField.getText().trim();
        if (!code.matches("(E)\\d{3}")) {
            JOptionPane.showMessageDialog(this, "Invalid code [Exxx]");
            this.empCodeTextField.requestFocus();
            return false;
        }
        for (int i = 0; i < root.getChildCount(); i++) {
            DefaultMutableTreeNode depNode = (DefaultMutableTreeNode) root.getChildAt(i);
            for (int j = 0; j < depNode.getChildCount(); j++) {
                DefaultMutableTreeNode empNode = (DefaultMutableTreeNode) depNode.getChildAt(j);
                Employee emp = (Employee) empNode.getUserObject();
                if (code.equalsIgnoreCase(emp.getEmpCode())) {
                    JOptionPane.showMessageDialog(this, "This code is existed!");
                    this.empCodeTextField.requestFocus();
                    return false;
                }
            }

        }
        return Validator.checkNumber(this.salaryTextField.getText());
    }

    public static void print(DefaultMutableTreeNode aNode) {
        String name = aNode.toString();
        int maxLevel = aNode.getLevel();
        int level = 0;
        String placement = "";
        while (level < maxLevel) {
            placement += ">";
            level++;
        }
        if (aNode.isLeaf()) {
            System.out.println(placement + name);
            return;
        }

        System.out.println(placement + "--- " + name + " ---");
        for (int i = 0; i < aNode.getChildCount(); i++) {
            print((DefaultMutableTreeNode) aNode.getChildAt(i));
        }
    }

    private void treeMouseClicked(java.awt.event.MouseEvent evt) {
        tree.cancelEditing();
        TreePath path = tree.getSelectionPath();
        if (path == null) {
            return;
        }
        DefaultMutableTreeNode selectedNode = null;
        selectedNode = (DefaultMutableTreeNode) (path.getLastPathComponent());
        Object selectedObject = selectedNode.getUserObject();
        if (selectedNode == root) {
            this.curDepNode = this.curEmpNode = null;
        } else {
            if (selectedObject instanceof Department) {
                this.curDepNode = selectedNode;
                this.curEmpNode = null;
            } else if (selectedObject instanceof Employee) {
                curEmpNode = selectedNode;
                curDepNode = (DefaultMutableTreeNode) (selectedNode.getParent());
            }
        }
        viewDepAndEmp();
        addNewDep = addNewEmp = false;
    }

    private void depNewButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.addNewDep = true;
        this.depCodeTextField.setText("");
        this.depNameTextField.setText("");
        this.empCodeTextField.setText("");
        this.empNameTextField.setText("");
        this.salaryTextField.setText("");
        this.depCodeTextField.setEditable(true);
        this.depCodeTextField.requestFocus();
    }

    private void depRemoveButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (this.curDepNode == null) {
            return;
        }
        if (this.curDepNode.getChildCount() > 0) {
            String msg = "Remove all employess before deleting a department.";
            JOptionPane.showMessageDialog(this, msg);
        } else {
            int response = JOptionPane.showConfirmDialog(this, "Delete this department- OK?");
            if (response == JOptionPane.OK_OPTION) {
                root.remove(this.curDepNode);
                curDepNode = curEmpNode = null;
                tree.updateUI();
            }
        }
    }

    private void depSaveButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String code = this.depCodeTextField.getText().trim().toUpperCase();
        depCodeTextField.setText(code);
        String name = this.depNameTextField.getText().trim();
        depNameTextField.setText(name);
        if (!validDep()) {
            return;
        }
        if (addNewDep == true) {
            Department newDep = new Department(code, name);
            root.add(new DefaultMutableTreeNode(newDep));
        } else {
            if (curDepNode != null) {
                ((Department) curDepNode.getUserObject()).setDepName(name);
            }
        }
        this.tree.updateUI();
        this.addNewDep = false;
        curDepNode = curEmpNode = null;
        print(root);
    }

    private void empNewButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.addNewEmp = true;
        this.empCodeTextField.setText("");
        this.empNameTextField.setText("");
        this.salaryTextField.setText("");
        this.empCodeTextField.setEditable(true);
        this.empCodeTextField.requestFocus();
    }

    private void empRemoveButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (this.curEmpNode != null) {
            int response = JOptionPane.showConfirmDialog(this, "Delete this employee- OK?");
            if (response == JOptionPane.OK_OPTION) {
                curDepNode.remove(this.curEmpNode);
                tree.updateUI();
            }
        }
    }

    private void empSaveButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (curDepNode == null) {
            empNewButtonActionPerformed(null);
            return;
        }
        String code = this.empCodeTextField.getText().trim().toUpperCase();
        empCodeTextField.setText(code);
        String name = this.empNameTextField.getText().trim();
        empNameTextField.setText(name);
        String salaryStr = this.salaryTextField.getText().trim();
        salaryTextField.setText(salaryStr);
        try {
              int sal = Integer.parseInt(salaryStr);
        
        if (!validEmp()) {
            return;
        }
        if (addNewEmp == true) {
            Employee newEmp = new Employee(code, name, sal);
            curDepNode.add(new DefaultMutableTreeNode(newEmp));
        } else {
            if (curEmpNode != null && curDepNode != null) {
                Employee emp = (Employee) (curEmpNode.getUserObject());
                emp.setEmpName(name);
                emp.setSalary(sal);
            }
        }
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Salary must be a number!");
        }
        this.tree.updateUI();
        this.addNewEmp = false;
        print(root);
    }

    private void saveToFile() {
        if (root.getChildCount() == 0) {
            return;
        }
        String s;
        try {
            FileWriter fw = new FileWriter(fileName);
            PrintWriter pw = new PrintWriter(fw);
            Enumeration depts = root.children();
            while (depts.hasMoreElements()) {
                DefaultMutableTreeNode depNode = (DefaultMutableTreeNode) depts.nextElement();
                Department dept = (Department) depNode.getUserObject();
                s = dept.getDepCode() + "-" + dept.getDepName() + ":";
                pw.println(s);
                Enumeration emps = depNode.children();
                while (emps.hasMoreElements()) {
                    DefaultMutableTreeNode empNode = (DefaultMutableTreeNode) emps.nextElement();
                    Employee emp = (Employee) empNode.getUserObject();
                    s = emp.getEmpCode() + "," + emp.getEmpName() + "," + emp.getSalary();
                    pw.println(s);
                }
            }
            fw.close();
            pw.close();
            JOptionPane.showMessageDialog(this, "Data had saved to file " + fileName);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public static void main(String[] args) {
        DepartmentManager depManager = new DepartmentManager();
        depManager.setSize(600, 380);
        depManager.setDefaultCloseOperation(EXIT_ON_CLOSE);
        depManager.setTitle("Manager");
        depManager.setResizable(false);
        depManager.setVisible(true);
    }

}
