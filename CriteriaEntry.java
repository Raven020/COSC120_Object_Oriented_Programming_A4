import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.*;

public class CriteriaEntry {

    // fields
    private JComboBox<Genre> genreSelection;
    private Genre selectedGenre;
    private JComboBox<String> subGenreSelection;
    private Set<String> relevantSubGenres = new HashSet<>();
    private String selectedSubGenre;
    private JComboBox<Ratings> ratingSelection;
    private Ratings selectedRating;
    private JComboBox<Platform> platformSelection;
    private Platform selectedPlatform;

    private double minPrice;
    private double maxPrice;
    private JTextField min;
    private JTextField max;

    private JTextField name;
    private JTextField email;
    private JTextField phoneNumber;
    private JTextArea message;

    /**
     * 
     * @param genreToSubgenre
     * @return a jpanel that allows user to get genre and subgenre
     */
    public JPanel getUserInputGenre(Map<Genre, Set<String>> genreToSubgenre) {
        JLabel instructionGenre = new JLabel("Please select your preferred Genre & Subgenre");
        // populate array
        genreSelection = new JComboBox<>(Genre.values());
        genreSelection.requestFocusInWindow();

        genreSelection.setSelectedItem(Genre.SELECT_GENRE);

        genreSelection.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                // set selectedGenre to users choice
                selectedGenre = (Genre) genreSelection.getSelectedItem();
                // if the user hasn't selected the dummy value, get all relevent sub-genres
                assert selectedGenre != null;
                if (!selectedGenre.equals(Genre.SELECT_GENRE))
                    relevantSubGenres = genreToSubgenre.get(selectedGenre);
                // if the user has selected the dummy value set subgenres to empty set
                else
                    relevantSubGenres = Collections.emptySet();
                // Once subgenres field has been initialised populate subgenre dropdown list
                subGenreSelection.setModel(new DefaultComboBoxModel<>(relevantSubGenres.toArray(new String[0])));

                selectedSubGenre = subGenreSelection.getItemAt(0);
                subGenreSelection.requestFocusInWindow();
            }
        });
        // request that user select their subGenre
        // JLabel instructionSubGenre = new JLabel("Please select your preferred
        // subgenre");

        subGenreSelection = new JComboBox<>();
        // dummy value
        subGenreSelection.addItem("Select Subgenre");
        // if the user selects or changes their selected subgenre update subGenre field
        subGenreSelection.addItemListener(f -> {
            if (f.getStateChange() == ItemEvent.SELECTED)
                selectedSubGenre = (String) subGenreSelection.getSelectedItem();
        });

        // create panel to store insutrciotns for the 2 dropdown lists
        JPanel subGenreGenreSelectionPanel = new JPanel();
        subGenreGenreSelectionPanel.setBorder(BorderFactory.createTitledBorder("Genre and SubGenre"));
        // add padding to make it look nicer
        subGenreGenreSelectionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        subGenreGenreSelectionPanel.setLayout(new BoxLayout(subGenreGenreSelectionPanel, BoxLayout.Y_AXIS));
        subGenreGenreSelectionPanel.add(instructionGenre);
        subGenreGenreSelectionPanel.add(genreSelection);
        subGenreGenreSelectionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        // subGenreGenreSelectionPanel.add(instructionSubGenre);
        subGenreGenreSelectionPanel.add(subGenreSelection);
        subGenreGenreSelectionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        subGenreGenreSelectionPanel.setBackground(Color.decode("#0080FF"));

        return subGenreGenreSelectionPanel;
    }

    /**
     * method to get and validate users price range
     * 
     * @return a JPanel containg instructions and textfields to enter price range
     */
    public JPanel getUserInputPriceRange() {
        JLabel minLabel = new JLabel("Min Price");
        JLabel maxLabel = new JLabel("Max Price");

        min = new JTextField(3);
        min.setText("20");
        max = new JTextField(3);
        max.setText("80");
        // set feedback label to blank
        JLabel feedback = new JLabel("   ");

        feedback.setFont(new Font("", Font.ITALIC, 12));

        min.requestFocusInWindow();

        min.addActionListener(e -> {
            try {
                // attempt to parse user input as integer
                this.minPrice = Integer.parseInt(min.getText());
                if (minPrice < 0) {
                    // let user know and change colour of feedback to red
                    feedback.setText("Min price must be positive");
                    feedback.setForeground(Color.red);

                    min.selectAll();
                    // keep user cursor in min
                    min.requestFocus();
                } else {
                    feedback.setText("");
                    max.requestFocusInWindow();
                }
            } catch (NumberFormatException n) {
                feedback.setText("Please enter a valid Number");
                feedback.setForeground(Color.RED);
                min.selectAll();
                min.requestFocusInWindow();
            }
        });

        max.addActionListener(e -> {
            try {
                this.maxPrice = Integer.parseInt(max.getText());
                if (maxPrice < minPrice) {
                    // if max price is less than min price alter feedback
                    feedback.setText("Max price must be greater than min price");
                    feedback.setForeground(Color.RED);
                    max.selectAll();
                    max.requestFocusInWindow();

                } else {
                    feedback.setText("");
                }
            } catch (NumberFormatException n) {
                feedback.setText("Please enter a valid number");
                feedback.setForeground(Color.RED);
                max.selectAll();
                // keep user cursor in max window
                max.requestFocusInWindow();
            }
        });
        JPanel inputPanel = new JPanel();
        inputPanel.add(minLabel);
        inputPanel.add(min);
        inputPanel.add(maxLabel);
        inputPanel.add(max);
        inputPanel.setBackground(Color.decode("#0080FF"));
        JPanel finalPanel = new JPanel();
        finalPanel.setBorder(BorderFactory.createTitledBorder("Price Range"));
        finalPanel.setLayout(new BoxLayout(finalPanel, BoxLayout.Y_AXIS));
        finalPanel.add(inputPanel);
        // add padding between text fields and feeback
        finalPanel.add(feedback);
        finalPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        finalPanel.setBackground(Color.decode("#0080FF"));
        return finalPanel;
    }

    /**
     * Method that produces panel so that users can enter in desired ratings
     * 
     * @param
     * @return a jpanel that allows user to select preferred Rating
     */
    public JPanel getUserInputRating() {
        JLabel instructionRating = new JLabel("Please select your preferred Ratings");
        // populate array
        ratingSelection = new JComboBox<>(Ratings.values());
        ratingSelection.requestFocusInWindow();

        ratingSelection.setSelectedItem(Ratings.SELECT_RATING);
        ratingSelection.addItemListener(e -> {
            selectedRating = (Ratings) ratingSelection.getSelectedItem();
        });
        // create panel to store insutrciotns for the dropdown list
        JPanel ratingsSelectionPanel = new JPanel();
        ratingsSelectionPanel.setBorder(BorderFactory.createTitledBorder("Ratings"));
        // add padding to make it look nicer
        ratingsSelectionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        ratingsSelectionPanel.setLayout(new BoxLayout(ratingsSelectionPanel, BoxLayout.Y_AXIS));
        ratingsSelectionPanel.add(instructionRating);
        ratingsSelectionPanel.add(ratingSelection);
        ratingsSelectionPanel.setBackground(Color.decode("#0080FF"));
        return ratingsSelectionPanel;
    }

    /**
     * Method to produce panel so that users can enter desired platform
     * 
     * @param
     * @return a jpanel that allows user to select preferred Platform
     */
    public JPanel getUserInputPlatform() {
        JLabel instructionPlatform = new JLabel("Please select your preferred Platform");
        // populate array
        platformSelection = new JComboBox<>(Platform.values());
        platformSelection.requestFocusInWindow();

        platformSelection.setSelectedItem(Platform.SELECT_PLATFORM);
        platformSelection.addItemListener(e -> {
            selectedPlatform = (Platform) platformSelection.getSelectedItem();
        });
        // create panel to store insutrciotns for the dropdown list
        JPanel platformSelectionPanel = new JPanel();
        platformSelectionPanel.setBorder(BorderFactory.createTitledBorder("Platform"));
        // add padding to make it look nicer
        platformSelectionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        platformSelectionPanel.setLayout(new BoxLayout(platformSelectionPanel, BoxLayout.Y_AXIS));
        platformSelectionPanel.add(instructionPlatform);
        platformSelectionPanel.add(platformSelection);
        platformSelectionPanel.setBackground(Color.decode("#0080FF"));
        return platformSelectionPanel;
    }

    /**
     * A method to generate JPanel containing a name, email, ph num and message
     * 
     * @return a JPanel as described
     */
    public JPanel contactForm() {
        // create labels and text fields for users to enter contact info and message
        JLabel enterName = new JLabel("Enter your full name");
        name = new JTextField(12);
        JLabel enterEmail = new JLabel("Enter your email address");
        email = new JTextField(12);
        JLabel enterPhoneNumber = new JLabel("Enter your phone number");
        phoneNumber = new JTextField(12);
        JLabel enterMessage = new JLabel("Type your query below");
        message = new JTextArea(6, 12);

        JScrollPane jScrollPane = new JScrollPane(message);
        jScrollPane.getViewport().setPreferredSize(new Dimension(200, 200));

        // create a new panel, add padding and user entry boxes/messages to the panel
        JPanel userInputPanel = new JPanel();
        userInputPanel.setLayout(new BoxLayout(userInputPanel, BoxLayout.Y_AXIS));
        userInputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userInputPanel.add(enterName);
        userInputPanel.add(name);
        userInputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userInputPanel.add(enterEmail);
        userInputPanel.add(email);
        userInputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userInputPanel.add(enterPhoneNumber);
        userInputPanel.add(phoneNumber);
        userInputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userInputPanel.add(enterMessage);
        userInputPanel.add(jScrollPane);
        userInputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        // return the panel to the calling method
        return userInputPanel;
    }

    /**
     * Getter for users min price
     * 
     * @return users minPrice
     */
    public double getMinPrice() {
        return Double.parseDouble(min.getText());
    }

    /**
     * Getter for users max price
     * 
     * @return users maxPrice
     */
    public double getMaxPrice() {
        return Double.parseDouble(max.getText());
    }

    /**
     * Getter for users selected genre
     * 
     * @return users selected genre
     */

    public Genre getSelectedGenre() {
        return selectedGenre;
    }

    /**
     * Getter for users desired subGenre
     * 
     * @return users subGenre
     */
    public String getSubGenre() {
        return selectedSubGenre;
    }

    /**
     * Getter for users desired Rating
     * 
     * @return users desired ratings
     */
    public Ratings getSelectedRatings() {
        return selectedRating;
    }

    /**
     * Getter for users desired platform
     * 
     * @return users desired platform
     */
    public Platform getSelectedPlatform() {
        return selectedPlatform;
    }

    /**
     * Getter for users name
     * 
     * @return users Name
     */
    public String getName() {
        return name.getText();
    }

    /**
     * Getter for users email
     * 
     * @return users email
     */
    public String getEmail() {
        return email.getText();
    }

    /**
     * Getter for users Phone number
     * 
     * @return userse phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber.getText();
    }

    /**
     * Getter for users Message
     * 
     * @return users message
     */
    public String getMessage() {
        return message.getText();
    }
}