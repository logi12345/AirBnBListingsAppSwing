import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import static javax.swing.SwingConstants.TOP;

public class MapScreen extends JComponent {

    JLabel imageLabel;
    ImagePanel imageResizer;
    ImageIcon mapImage;
    JLabel testLabel;
    JLayeredPane lp = new JLayeredPane();
    DataFilter filter = new DataFilter();

    HashMap<String, Pair> nbrHoodCd = new HashMap<>();

    public MapScreen(ArrayList<AirbnbListing> listings){
        setMapCoordinates();
        makeBackground();
        createMapIcons(listings);
    }

    private void makeBackground() {
        imageResizer = new ImagePanel("Images/LondonMap.png", 842, 626);
        mapImage = new ImageIcon(imageResizer.getImage());
        imageLabel= new JLabel("");
        imageLabel.setIcon(mapImage);
    }

    private void setMapCoordinates()
    {

        nbrHoodCd.put("Hillingdon",new Pair<>(75,180));
        nbrHoodCd.put("Harrow", new Pair<>(174,141));
        nbrHoodCd.put("Barnet", new Pair<>(308,111));
        nbrHoodCd.put("Enfield", new Pair<>(412,54));
        nbrHoodCd.put("Waltham Forest", new Pair<>(470,117));
        nbrHoodCd.put("Haringey",new Pair<>(395,140));
        nbrHoodCd.put("Redbridge", new Pair<>(568,136));
        nbrHoodCd.put("Havering", new Pair<>(721,157));
        nbrHoodCd.put("Ealing", new Pair<>(178,236));
        nbrHoodCd.put("Brent", new Pair<>(243,191));
        nbrHoodCd.put("Camden", new Pair<>(342, 199));
        nbrHoodCd.put("Islington", new Pair<>(390, 200));
        nbrHoodCd.put("Hackney", new Pair<>(437,194));
        nbrHoodCd.put("Newham", new Pair<>(531,242));
        nbrHoodCd.put("Barking and Dagenham", new Pair<>(632,190));
        nbrHoodCd.put("Hounslow", new Pair<>(152, 319));
        nbrHoodCd.put("Hammersmith and Fulham", new Pair<>(281, 274));
        nbrHoodCd.put("Kensington and Chelsea", new Pair<>(301,263));
        nbrHoodCd.put("Westminster", new Pair<>(335,248));
        nbrHoodCd.put("City of London", new Pair<>(414, 252));
        nbrHoodCd.put("Tower Hamlets", new Pair<>(470, 241));
        nbrHoodCd.put("Richmond upon Thames", new Pair<>(205, 356));
        nbrHoodCd.put("Wandsworth", new Pair<>(323,354));
        nbrHoodCd.put("Lambeth", new Pair<>(390, 330));
        nbrHoodCd.put("Southwark", new Pair<>(431, 298));
        nbrHoodCd.put("Lewisham", new Pair<>(488, 347));
        nbrHoodCd.put("Greenwich", new Pair<>(565,301));
        nbrHoodCd.put("Bexley", new Pair<>(636, 332));
        nbrHoodCd.put("Kingston upon Thames", new Pair<>(244, 428));
        nbrHoodCd.put("Merton", new Pair<>(315, 414));
        nbrHoodCd.put("Sutton", new Pair<>(337, 487));
        nbrHoodCd.put("Croydon", new Pair<>(431, 473));
        nbrHoodCd.put("Bromley", new Pair<>(565,454));



    }

    public void createMapIcons(ArrayList<AirbnbListing> listings)
    {
        setMapCoordinates();
        HashMap<String, Integer> nbrhoodMap = filter.housesPerNbrHood(listings);
        int i =listings.size()+1;
        for(Map.Entry<String, Pair> neighbourHoodCoordinate: nbrHoodCd.entrySet())
        {
            if(nbrhoodMap.containsKey(neighbourHoodCoordinate.getKey())) {
                ImagePanel x = new ImagePanel("Images/HouseIcon.png", 15, 24);
                ImageIcon m = new ImageIcon(x.getImage());
                JLabel a = new JLabel("");
                a.setIcon(m);
                a.setBounds(0, 0, 15, 24);
                lp.add(a, --i);
                a.setBounds((Integer) neighbourHoodCoordinate.getValue().getFirst(), (Integer) neighbourHoodCoordinate.getValue().getSecond(), 15, 24);
                a.setVerticalAlignment(TOP);
                a.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        NeighbourhoodDetailsWindow aWindow = new NeighbourhoodDetailsWindow(neighbourHoodCoordinate.getKey(),listings);
                    }
                });
            }

        }
        lp.add(imageLabel,nbrHoodCd.size()+1);
        imageLabel.setBounds(0,0,1100,800);
        imageLabel.setVerticalAlignment(TOP);
        setLayout(new BorderLayout());
        add(lp, BorderLayout.CENTER);


    }

}
