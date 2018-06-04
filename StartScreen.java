import javax.swing.*;
import java.awt.*;

public class StartScreen extends JComponent {

    private JLabel middleText;
    private JLabel bottomText;

    public StartScreen()
    {
        middleText = new JLabel("Logi is the best", SwingConstants.CENTER);
        bottomText= new JLabel("");
        setLayout(new BorderLayout());
        add(middleText,BorderLayout.CENTER);
        add(bottomText,BorderLayout.SOUTH);


    }

    public void setErrorCheck(String x)
    {
        bottomText.setText(x);
    }

}
