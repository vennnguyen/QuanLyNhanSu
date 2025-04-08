package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class HomeForm extends JPanel{
    private homeForm1 home1;
    private homeForm2 home2;
    private homeForm3 home3;
    
    public HomeForm(){
        init();
    }
    
    public void init() {
        setLayout(null);
        // form thống kê biểu đồ cột
        home1 = new homeForm1();
        home1.setBounds(10,10,535,320);
        this.add(home1);
        // form thống kê biểu đồ tròn                  																		
        home2 = new homeForm2();
        home2.setBounds(555,10,535,320);
        this.add(home2);
        
        home3 = new homeForm3();
        home3.setBounds(10,340,1080,363);
        this.add(home3);
    }
    
    public homeForm2 getHomeForm2() {
        return this.home2;
    }
    
    public homeForm1 getHomeForm1() {
        return this.home1;
    }
    
    public homeForm3 getHomeForm3() {
        return this.home3;
    }

    // Main method to run the GUI demo
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("HomeForm Demo");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                HomeForm homeForm = new HomeForm();
                frame.getContentPane().add(homeForm);
                frame.setSize(1100, 750);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}