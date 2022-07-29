import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.awt.Dimension;

public class FindAGame {
    private static CriteriaEntry criteriaEntry;
    private static JPanel userInformationView = null;
    // JFrame main window fields
    private static JFrame mainWindow = null;
    private static final String appName = "Greek Geeks Game Finder";
    private static final String allGamesFilePath = "./allGames_v2.txt";
    private static AllGames allGames = null;
    private static final String iconFilePath = "./icon.png";
    private static ImageIcon icon = null;

    // Search View fields
    private static JPanel searchView = null;

    /**
     * main method -> initializes and creates main JFrame
     * 
     * @param args NA
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        allGames = loadGames();
        mainWindow = new JFrame(appName);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        icon = new ImageIcon(iconFilePath);
        mainWindow.setIconImage(icon.getImage());
        mainWindow.setMinimumSize(new Dimension(300, 300));
        // first screen is the search view
        searchView = generateSearchView();
        mainWindow.setContentPane(searchView);
        mainWindow.pack();
        mainWindow.setVisible(true);

    }

    /**
     * Method to generate the search window so a user can enter in their search
     * criteria
     * 
     * @return a Jpanel that performs the above
     */
    public static JPanel generateSearchView() {
        // JPanel to contain search fields and button
        JPanel searchWindow = new JPanel();
        searchWindow.setLayout(new BorderLayout());
        criteriaEntry = new CriteriaEntry();
        // initialize the criteriaEntry object with a new search view

        JPanel criteria = new JPanel();
        // set layout to box layout y axis so that all components are vertically stacked
        criteria.setLayout(new BoxLayout(criteria, BoxLayout.Y_AXIS));

        criteria.add(criteriaEntry.getUserInputGenre(allGames.getGenreToSubgenreMapping()));
        criteria.add(criteriaEntry.getUserInputRating());
        criteria.add(criteriaEntry.getUserInputPlatform());
        criteria.add(criteriaEntry.getUserInputPriceRange());
        criteria.add(Box.createRigidArea(new Dimension(0, 0)));

        searchWindow.add(criteria, BorderLayout.CENTER);

        // add a button at the bottom of the search panel, when users click it, the
        // conduct search method will be called
        JButton submitInfo = new JButton("Submit");
        ActionListener actionListener = e -> conductSearch(criteriaEntry);
        submitInfo.addActionListener(actionListener);
        // add the button to the bottom-most part of the search panel
        submitInfo.setBackground(Color.decode("#0080FF"));
        searchWindow.add(submitInfo, BorderLayout.SOUTH);
        // set the field value to the search panel (view 1)
        return searchWindow;
    }

    /**
     * This method is called once the user has selected all the criteria they wish
     * to search by to find a game, creates a uses
     * 
     * @param criteriaEntry an object of the CriteriaEntry class, which has fields
     *                      set via user selections/entries
     */
    public static void conductSearch(CriteriaEntry criteriaEntry) {
        Map<Specs, Object> gameSpecs = new HashMap<>();

        Genre genre = criteriaEntry.getSelectedGenre();
        String subGenre = criteriaEntry.getSubGenre();
        double minPrice = criteriaEntry.getMinPrice();
        double maxPrice = criteriaEntry.getMaxPrice();
        Ratings rating = criteriaEntry.getSelectedRatings();
        Platform platform = criteriaEntry.getSelectedPlatform();
        // genre
        gameSpecs.put(Specs.GENRE, genre);

        // subgenre dont forget to check for NA
        if (!subGenre.equals("NA")) {
            gameSpecs.put(Specs.SUBGENRE, subGenre);

        }

        // Ratings
        if (!rating.equals(Ratings.NA)) {
            gameSpecs.put(Specs.RATING, rating);
        }

        if (!platform.equals(Platform.NA)) {
            gameSpecs.put(Specs.PLATFORM, platform);
        }

        // create user search GameSpecs object
        GameSpecs userSearch = new GameSpecs(gameSpecs, minPrice, maxPrice);
        // search for games
        ArrayList<Game> foundGames = allGames.findGames(userSearch);

        showResults(foundGames);
    }

    /**
     * this method displays the results of their search and populates a drop down
     * list that the user can select from
     * 
     * @param foundGamesthe Arraylist of game that match their crieria
     */
    public static void showResults(ArrayList<Game> foundGames) {
        if (foundGames.size() > 0) {
            Map<String, Game> options = new HashMap<>();

            // ensure user can choose None if they wish
            options.put("None", null);
            // create a panel to conatin the descriptions of games
            JPanel gameDescriptions = new JPanel();

            gameDescriptions.setBorder(
                    BorderFactory.createTitledBorder("Matches found!! The following games meet your criteria: "));
            gameDescriptions.setLayout(new BoxLayout(gameDescriptions, BoxLayout.Y_AXIS));
            gameDescriptions.add(Box.createRigidArea(new Dimension(0, 10)));
            gameDescriptions.setBackground(Color.decode("#0080FF"));
            // create a new no editable text area for each game, calling getGameDescription
            // to get description for each game
            for (Game game : foundGames) {
                gameDescriptions.add(describeGame(game));

                // add each games title and game object to options map
                options.put(game.getTitle() + " (" + game.getProductCode() + ")", game);
            }

            // add a scroll pane to the results window so if there are many results users
            // can scroll as needed
            JScrollPane verticalScrollBar = new JScrollPane(gameDescriptions);
            // set default sixe of window containg game descriptions
            verticalScrollBar.setPreferredSize(new Dimension(300, 450));
            // only show the scroll bar if it is needed
            verticalScrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            verticalScrollBar.setBackground(Color.decode("#0080FF"));
            // this positions the scrollbar at the top (without it, the scrollbar loads part
            // way through
            // adding of the text areas to the JPanel, resulting in the scrollbar being
            // halfway down the results
            SwingUtilities.invokeLater(() -> verticalScrollBar.getViewport().setViewPosition(new Point(0, 0)));

            // next, initialise the combo box with the games titles (key set)
            JComboBox<String> optionsCombo = new JComboBox<>(options.keySet().toArray(new String[0]));
            // if the user selects a game from the dropdown list, this action listener will
            // pick up on it
            ActionListener actionListener = e -> {
                checkUserSelection(options, optionsCombo);
            };
            optionsCombo.addActionListener(actionListener);

            // this panel is for the dropdown list
            JPanel selectionPanel = new JPanel();
            selectionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            selectionPanel.setBorder(
                    BorderFactory.createTitledBorder("Please select which (if any) game you'd like to purchase:"));
            selectionPanel.add(optionsCombo);
            selectionPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            selectionPanel.setBackground(Color.decode("#0080FF"));

            // this is the overall panel (view 2) used to display the compatible games and
            // the dropdown list
            JPanel results = new JPanel();
            results.setLayout(new BorderLayout());
            // add padding to the top of the panel
            results.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.NORTH);
            // add the scrollable game descriptions to the center
            results.add(verticalScrollBar, BorderLayout.CENTER);
            // add the dropdown list to the bottom
            results.add(selectionPanel, BorderLayout.SOUTH);
            // set main window to the results panel (view 2)
            results.setBackground(Color.decode("#0080FF"));
            mainWindow.setContentPane(results);
            mainWindow.setBackground(Color.decode("#0080FF"));
            mainWindow.revalidate();
        } else {
            // if there are no compatible games, let the user know using a popup window
            JOptionPane.showMessageDialog(searchView, "Unfortunately none of our games meet your criteria.\n",
                    "No Matching Games", JOptionPane.INFORMATION_MESSAGE, icon);
            // now pretend the user selected 'none' and give the user the option to search
            // this will switch to view 4
            userSelectedNone();
        }
    }

    /**
     * Method to check if user has selected None or a game
     * 
     * @param options      Map of Game titles to game objects
     * @param optionsCombo object holding the users selected item
     * 
     */
    public static void checkUserSelection(Map<String, Game> options, JComboBox<String> optionsCombo) {
        String gameTitle = (String) optionsCombo.getSelectedItem();
        if (options.get(gameTitle) == null) {
            userSelectedNone();
        }
        // if user has selected a game see which game it is and move to submit pane
        else {
            Game chosenGame = options.get(gameTitle);
            // this will switch the contents of main frame to the user contact field
            placeOrder(chosenGame);
        }
    }

    /**
     * method to handle the case that the user selects none and lets them return
     * back to the search window
     */
    public static void userSelectedNone() {
        // if the user has selected none (or there are no results) create a new view
        JPanel mainFramePanel = new JPanel();
        mainFramePanel.setLayout(new BorderLayout());
        mainFramePanel.setBackground(Color.decode("#0080FF"));
        JLabel searchAgain = new JLabel(new ImageIcon("searchAgain.gif"));
        mainFramePanel.add(searchAgain, BorderLayout.NORTH);
        mainFramePanel.setBackground(Color.decode("#0080FF"));
        // give the user the option to return to view 1 (to search again)
        JButton goBackToSearch = new JButton("Go back to search");
        goBackToSearch.addActionListener(e -> {
            mainWindow.setContentPane(searchView);
            mainWindow.revalidate();
        });
        goBackToSearch.setBackground(Color.decode("#0080FF"));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(goBackToSearch);
        buttonPanel.setBackground(Color.decode("#0080FF"));

        // once the buttons are on the button panel, add it to the main frame
        mainFramePanel.add(buttonPanel, BorderLayout.SOUTH);
        mainFramePanel.setBackground(Color.decode("#0080FF"));
        mainWindow.setContentPane(mainFramePanel);// view 4
        mainWindow.revalidate();
    }

    /**
     * method to describe an individual game, within a non-editable JTextArea
     * 
     * @param game the game to describe
     * @return a JTextArea
     */
    public static JTextArea describeGame(Game game) {
        // create a text area to contain the game description
        JTextArea gameDescription = new JTextArea(game.getDescription(game.getGameSpecs().getAllSpecs()));
        gameDescription.setEditable(false);
        // this will ensure that if the description is long, it 'overflows'
        gameDescription.setLineWrap(true);
        gameDescription.setWrapStyleWord(true);
        return gameDescription;
    }

    /**
     * @param lineToWrite the String to be written to the file
     *                    a method to write a user's order message to
     *                    a file
     */
    public static void writeMessageToFile(String lineToWrite) {
        String filePath = criteriaEntry.getName().replace(" ", "_") + "_Order.txt";
        Path path = Path.of(filePath);
        try {
            Files.writeString(path, lineToWrite);
            JOptionPane.showMessageDialog(mainWindow,
                    "Thank you for your message. \nOne of our friendly staff will be in touch shortly. \nClose this dialog to terminate.",
                    "Message Sent", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        } catch (IOException io) {
            JOptionPane.showMessageDialog(mainWindow, "Error: Message could not be sent! Please try again!", null,
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * method to retrieve user details
     * 
     * @param chosenGame
     */
    public static void placeOrder(Game chosenGame) {
        // Create label asking user to fil out order form
        JLabel gameMessage = new JLabel("To place an Order for " + chosenGame.getTitle() + "fill out the form below");
        // Creatte Jscroll pane to hold the details of the chosen game
        JScrollPane jScrollPane = new JScrollPane(describeGame(chosenGame));
        jScrollPane.setBackground(Color.decode("#0080FF"));
        jScrollPane.getViewport().setPreferredSize(new Dimension(300, 150));
        // Combine jscrollPane and game message to get a Panel that will display these
        JPanel gameDescriptionPanel = new JPanel();
        gameDescriptionPanel.add(gameMessage);
        gameDescriptionPanel.add(jScrollPane);
        gameDescriptionPanel.setBackground(Color.decode("#0080FF"));
        JPanel userInputPanel = criteriaEntry.contactForm();
        userInputPanel.setBackground(Color.decode("#0080FF"));
        JButton submit = new JButton("Submit");
        ActionListener actionListener = e -> {
            String lineToWrite = "Name: " + criteriaEntry.getName() + " \nEmail: " + criteriaEntry.getEmail()
                    + "\nPhone number: " + criteriaEntry.getPhoneNumber() + "\n\nMessage: " + criteriaEntry.getMessage()
                    +
                    "\n\n" + criteriaEntry.getName().split(" ")[0] + " has ordered " + chosenGame.getTitle() + " ("
                    + chosenGame.getProductCode() + ")";
            writeMessageToFile(lineToWrite);
        };
        submit.addActionListener(actionListener);
        submit.setBackground(Color.decode("#0080FF"));
        // add the game description panel, contact form panel and button to a new frame,
        // and assign it to view 3
        JPanel mainFramePanel = new JPanel();
        mainFramePanel.setLayout(new BorderLayout());
        mainFramePanel.add(gameDescriptionPanel, BorderLayout.NORTH);
        mainFramePanel.add(userInputPanel, BorderLayout.CENTER);
        mainFramePanel.add(submit, BorderLayout.SOUTH);
        mainFramePanel.setBackground(Color.decode("#0080FF"));
        userInformationView = mainFramePanel;
        mainWindow.setContentPane(userInformationView);
        mainWindow.revalidate();

    }

    /**
     * Method to load all the games in allGames_V2.txt and create an AllGames object
     * 
     * @return the AllGames object which contains all avaliable games
     * @throws IOException throws exception in case of unable to open allGames.txt
     *                     file
     */
    public static AllGames loadGames() throws IOException {
        AllGames allGames = new AllGames();

        Path path = Path.of(allGamesFilePath);
        List<String> fileContents = null;
        try {
            fileContents = Files.readAllLines(path);
        } catch (Exception e) {
            System.out.println("cannot read allGames_V2.txt");
            e.printStackTrace();
            System.exit(1);

        }
        for (int i = 1; i < fileContents.size(); i++) {
            String[] elements = fileContents.get(i).split(",");

            String title = elements[0];
            long productCode = Long.parseLong(elements[1]);
            double price = Double.parseDouble(elements[2]);

            Map<Specs, Object> specs = new HashMap<>();

            Genre genre = Genre.valueOf(elements[3].toUpperCase().replace(" ", "_"));
            Platform platform = Platform.valueOf(elements[4].toUpperCase().replace(" ", "_"));
            String subgenre = elements[5];
            specs.put(Specs.SUBGENRE, subgenre);
            Ratings rating = Ratings.valueOf(elements[6]);
            specs.put(Specs.RATING, rating);
            specs.put(Specs.GENRE, genre);
            specs.put(Specs.PLATFORM, platform);

            GameSpecs gameSpecs = new GameSpecs(specs);
            Game game = new Game(title, productCode, price, gameSpecs);
            allGames.addGame(game);
        }
        return allGames;
    }
}
