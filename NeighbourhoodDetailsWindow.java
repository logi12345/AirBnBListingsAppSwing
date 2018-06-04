import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class NeighbourhoodDetailsWindow {

    private JFrame frame;
    DataFilter filter = new DataFilter();
    ArrayList<AirbnbListing> neighbourhoodList;
    Container container;
    JPanel grid;

    public NeighbourhoodDetailsWindow(String neighbourhoodName, ArrayList<AirbnbListing> lisitings) {
        neighbourhoodList = filter.filterByNbrhdName(neighbourhoodName, lisitings);
        makeFrame(neighbourhoodName);
    }


    public void makeFrame(String nbrhoodName) {
        frame = new JFrame(nbrhoodName);
        container = frame.getContentPane();
        container.setLayout(new BorderLayout());
        sortingMenu(frame);
        makeMiddlePanel();


        frame.pack();
        frame.setVisible(true);
    }

    public void makeMiddlePanel() {
        container.removeAll();
        grid = new JPanel(new GridLayout(0, 4));
        for (AirbnbListing listing : neighbourhoodList) {
            grid.add(new JLabel("Host Name: " + listing.getHost_name()));
            grid.add(new JLabel("Price: " + listing.getPrice()));
            grid.add(new JLabel("Review Score: " + listing.getNumberOfReviews()));
            grid.add(new JLabel("Minimum Night Stay: " + listing.getMinimumNights()));

        }
        container.add(new JScrollPane(grid), BorderLayout.CENTER);
        container.validate();
        container.repaint();
    }

    public void sortingMenu(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu menu = new JMenu("Sort");
        menuBar.add(menu);

        JMenuItem item;

        item = new JMenuItem("Alphabetical");
        item.addActionListener((ActionEvent e) -> {
            sortAlphabetical();
            makeMiddlePanel();
        });
        menu.add(item);

        item = new JMenuItem("Price (Increasing)");
        item.addActionListener((ActionEvent e)->{
            sortPrice();
            makeMiddlePanel();
        });
        menu.add(item);

        item = new JMenuItem("Review Score (Decreasing)");
        item.addActionListener((ActionEvent e)->{
            sortReviewScore();
            makeMiddlePanel();
        });
        menu.add(item);

    }

    private void sortPrice() {
        Collections.sort(neighbourhoodList, new Comparator<AirbnbListing>() {
            @Override
            public int compare(AirbnbListing o1, AirbnbListing o2) {
                if(o1.getPrice()>o2.getPrice()) {
                    return 1;
                } else if (o1.getPrice()==o2.getPrice()) {
                    return 0;
                }
                else {
                    return -1;
                }
            }
        });
    }

    private void sortReviewScore() {
        Collections.sort(neighbourhoodList, new Comparator<AirbnbListing>() {
            @Override
            public int compare(AirbnbListing o1, AirbnbListing o2) {
                if(o1.getNumberOfReviews()<o2.getNumberOfReviews()) {
                    return 1; }
                else if (o1.getNumberOfReviews()==o2.getNumberOfReviews()) {
                    return 0; }
                else {
                    return -1;
                }
            }
        });

    }

    private void sortAlphabetical() {
        Collections.sort(neighbourhoodList, new Comparator<AirbnbListing>() {
            @Override
            public int compare(AirbnbListing o1, AirbnbListing o2) {
                if (o1.getHost_name().compareToIgnoreCase(o2.getHost_name()) > 0) {
                    return 1; }
                else {
                    return -1;
                }
            }
        });
    }
}