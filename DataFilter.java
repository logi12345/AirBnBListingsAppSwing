import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

public class DataFilter {

    private ArrayList<AirbnbListing> listings;
    private Map<String, Integer> housesInNbrHood;


    public void load() {
        listings = new AirbnbDataLoader().load();
    }

    public ArrayList<AirbnbListing> filterByPrice(int from, int to) {
        List<AirbnbListing> priceRangeList = listings.stream()
                .filter(p -> p.getPrice() >= from && p.getPrice() <= to)
                .collect(Collectors.toList());
        return (ArrayList<AirbnbListing>) priceRangeList;
    }

    public HashMap<String, Integer> housesPerNbrHood(ArrayList<AirbnbListing> listings) {
        housesInNbrHood = listings.stream()
                .collect(groupingBy(AirbnbListing::getNeighbourhood, summingInt(e -> 1)));

        return (HashMap<String, Integer>) housesInNbrHood;
    }

    public ArrayList<AirbnbListing> filterByNbrhdName(String neighbourhoodName, ArrayList<AirbnbListing> listings) {
        List<AirbnbListing> nbrhoodList = listings.stream()
                .filter(p -> p.getNeighbourhood().equals(neighbourhoodName))
                .collect(Collectors.toList());
        return (ArrayList<AirbnbListing>) nbrhoodList;

    }

    public String getAverageReviewScore(ArrayList<AirbnbListing> listings)
    {
        int sum =0;
        for(AirbnbListing listing: listings){
            sum+=listing.getNumberOfReviews();
        }
        return "<HTML>Average review Score: <br> <center>"+Integer.toString(sum/listings.size())+"</center></HTML>";
    }

    public String getTotalNumberOfAvailableProperties(ArrayList<AirbnbListing> listings)
    {
        int sum=0;
        for (AirbnbListing listing: listings){
            if(listing.getAvailability365()>0)
            {
                sum++;
            }
        }
        return "<HTML>Total number of available properties: <br><center>" +Integer.toString(sum)+"</center></HTML>";
    }

    public String getTotalNumberOfNonPrivateRoom(ArrayList<AirbnbListing> listings)
    {
        int sum=0;
        for (AirbnbListing listing: listings)
        {
            if(!listing.getRoom_type().equals("Private room"))
            {
                sum++;
            }
        }
        return "<HTML>Total Number of non-private rooms: <br> <center>"+Integer.toString(sum)+"</center></HTML>";
    }

    public String getMostExpensiveNeighbourhood(ArrayList<AirbnbListing> listings) {
        HashMap<String, Integer> totalEachNeighbourhood = new HashMap<>();
        for (AirbnbListing listing : listings)
        {
            if (totalEachNeighbourhood.containsKey(listing.getNeighbourhood()))
            {
                String key = listing.getNeighbourhood();
                int oldValue = totalEachNeighbourhood.get(key);
                int newValue = oldValue + (listing.getMinimumNights()*listing.getPrice());
                totalEachNeighbourhood.replace(key,oldValue, newValue);
            }
            else if (totalEachNeighbourhood.isEmpty() || !totalEachNeighbourhood.containsKey(listing.getNeighbourhood())){
                totalEachNeighbourhood.put(listing.getNeighbourhood(),listing.getPrice()*listing.getMinimumNights());
            }
        }

        String neighourHood="";
        int sum =0;
        for (Map.Entry<String, Integer> entry : totalEachNeighbourhood.entrySet() )
        {
            if (sum < entry.getValue())
            {
                neighourHood = "<HTML>Most Expensive NeighbourHood: <br><center>"+entry.getKey()+"</center></HTML>";
                sum = entry.getValue();
            }
        }

        return neighourHood;
    }


}
