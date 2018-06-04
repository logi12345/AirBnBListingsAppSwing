import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class StatisticsScreen extends JComponent {

    DataFilter filter = new DataFilter();
    ArrayList<AirbnbListing> listings;
    String[] stats;
    ArrayList<Integer> counters;


    public StatisticsScreen(ArrayList<AirbnbListing> listings) {
        createCounters();
        this.listings = listings;
        createArrayOfStats();
        setLayout(new GridLayout(0, 2));
        for (int i = 0; i < 4; i++) {
            JPanel statPane = new JPanel(new BorderLayout());
            statPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            ArrayList<String> listOfStats = new ArrayList<>();
            listOfStats.add(stats[i]);
            listOfStats.add(stats[i + 4]);
            JLabel factLabel = new JLabel(stats[i]);
            Button leftButton = new Button("<");
            String panel = Integer.toString(i);
            leftButton.addActionListener((ActionEvent e) -> {
                int counterToCahnge = counters.get(Integer.parseInt(panel));
                counterToCahnge = (counterToCahnge +1)% listOfStats.size();
                factLabel.setText(listOfStats.get(counterToCahnge));
                modifyCounters(Integer.parseInt(panel), counterToCahnge);
            });

            Button rightButton = new Button(">");
            rightButton.addActionListener((ActionEvent e) -> {
                int counterToCahnge = counters.get(Integer.parseInt(panel));
                counterToCahnge = (counterToCahnge +1)% listOfStats.size();
                factLabel.setText(listOfStats.get(counterToCahnge));
                modifyCounters(Integer.parseInt(panel), counterToCahnge);
            });

            factLabel.setHorizontalAlignment(SwingConstants.CENTER);
            statPane.add(leftButton, BorderLayout.WEST);
            statPane.add(rightButton, BorderLayout.EAST);
            statPane.add(factLabel, BorderLayout.CENTER);
            add(statPane);
        }
    }


    public void createArrayOfStats() {
        stats = new String[8];
        stats[0] = filter.getAverageReviewScore(listings);
        stats[1] = filter.getTotalNumberOfAvailableProperties(listings);
        stats[2] = filter.getTotalNumberOfNonPrivateRoom(listings);
        stats[3] = filter.getMostExpensiveNeighbourhood(listings);
        stats[4] = "1";
        stats[5] = "2";
        stats[6] = "3";
        stats[7] = "4";


    }

    public void createCounters()
    {
        counters = new ArrayList<>();
        counters.add(0);
        counters.add(0);
        counters.add(0);
        counters.add(0);
    }

    public void modifyCounters(int statNumeber, int counterValue)
    {
        counters.remove(statNumeber);
        counters.add(statNumeber,counterValue);

    }

}
