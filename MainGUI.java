import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EtchedBorder;


public class MainGUI {
    private JFrame frame;

    private Container container;

    private JPanel topPanel;
    private JPanel bottomPanel;

    private StartScreen screen = new StartScreen();
    //private MapScreen map = new MapScreen();

    private ArrayList<JComponent> screens= new ArrayList<>();
    private DataFilter filter  = new DataFilter();

    private JButton left;
    private JButton right;

    private String[] fromValues = {"0","20","30","40","50", "100", "200", "300", "400", "500", "1000", "1500"};
    private String[] toValues = {"0","10","30","40","50", "100", "200", "300", "400", "500", "1000", "1500", "2000", "2000+"};

    private JComboBox<String> toComBox;
    private JComboBox<String> fromComBox;

    int toValue;
    int fromValue;

    private  int pageCounter;




    public MainGUI(){
        filter.load();
        makeFrame();
    }

    private void makeFrame() {
        frame = new JFrame("AirBnbMapData");
        container = frame.getContentPane();
        container.setLayout(new BorderLayout(6,6));


        makeTopPanel();
        container.add(topPanel, BorderLayout.NORTH);
        screens.add(screen);
        //screens.add(map);




        makeBottomPanel();
        container.add(bottomPanel, BorderLayout.SOUTH);
        pageCounter =0;
        container.add(screens.get(pageCounter), BorderLayout.CENTER);
        frame.setSize(842,730);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
    }

    private void makeTopPanel(){
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout(6,6));

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout());

        fromComBox = new JComboBox<>(fromValues);
        rightPanel.add(new JLabel("From :"));
        rightPanel.add(fromComBox);

        toComBox = new JComboBox<>(toValues);
        rightPanel.add(new JLabel("To :"));
        rightPanel.add(toComBox);

        topPanel.add(rightPanel, BorderLayout.EAST);

        toComBox.addActionListener((ActionEvent e)->{
            int toValue1 = Integer.parseInt(toValues[toComBox.getSelectedIndex()]);
            assignToValue(toValue1);
            topSelectionValidation();
            System.out.println(toValue1);

        });

        fromComBox.addActionListener((ActionEvent e) ->{
            int fromValue1 = Integer.parseInt(fromValues[fromComBox.getSelectedIndex()]);
            assignFromValue(fromValue1);
            topSelectionValidation();
            System.out.println(fromValue1);
        });


    }

    private void makeBottomPanel(){
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout(6,0));

        left = new JButton("<");
        left.setEnabled(false);


        right = new JButton(">");
        right.setEnabled(false);



        bottomPanel.add(left, BorderLayout.WEST);
        bottomPanel.add(right, BorderLayout.EAST);

        left.addActionListener((ActionEvent e)->{
            container.remove(screens.get(pageCounter));
            changePageCounter(1);
            System.out.println(pageCounter);
            generateScreens();
            container.add(screens.get(pageCounter), BorderLayout.CENTER);
            container.validate();
            container.repaint();
        });

        right.addActionListener((ActionEvent e)->{
            container.remove(screens.get(pageCounter));
            changePageCounter(0);
            System.out.println(pageCounter);
            generateScreens();
            container.add(screens.get(pageCounter), BorderLayout.CENTER);
            container.validate();
            container.repaint();
        });
    }

    private void topSelectionValidation()
    {
        if(fromValue>toValue || fromValue==toValue)
        {

            left.setEnabled(false);
            right.setEnabled(false);
            screen.setErrorCheck("Incorrect");
        }
        else
        {
            screen.setErrorCheck("");
            left.setEnabled(true);
            right.setEnabled(true);

        }
    }

    private void assignToValue(int x)
    {
         toValue=x;
         System.out.println(toValue);

    }

    private void assignFromValue(int x)
    {
        fromValue=x;
        System.out.println(fromValue);

    }

    private void changePageCounter(int i)
    {
        if (i == 0) {
            pageCounter = (pageCounter + 1) % screens.size();
        }else {
            pageCounter = (pageCounter + screens.size() -1)%screens.size();
        }

    }

    private void generateScreens()
    {
        if (pageCounter == screens.size()-1 || pageCounter==1)
        {
        screens.clear();
        ArrayList<AirbnbListing> listings = filter.filterByPrice(fromValue,toValue);
        MapScreen map1 = new MapScreen(listings);
        StatisticsScreen statisticsScreen =  new StatisticsScreen(listings);
        screens.add(screen);
        screens.add(map1);
        screens.add(statisticsScreen);
        }

    }


}
