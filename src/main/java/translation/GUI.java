package translation;

import javax.swing.*;
import java.awt.event.*;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    private static Translator translator = new JSONTranslator();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JPanel countryPanel = new JPanel();
            JList<String> countryField = new JList<>(translator.getCountryCodes().toArray(new String[0]));
            countryField.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            countryField.setVisibleRowCount(-1);
            JScrollPane scrollPane = new JScrollPane(countryField);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setPreferredSize(new java.awt.Dimension(20,100));

            countryPanel.add(new JLabel("Country:"));
            countryPanel.add(countryField);

            JPanel languagePanel = new JPanel();
            JComboBox<String> languageField = new JComboBox<>(translator.getLanguageCodes().toArray(new String[0]));
            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(languageField);

            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);


            // adding listener for when the user clicks the submit button
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String language = languageField.getSelectedItem().toString().toLowerCase();
                    String country = countryField.getSelectedValue().toString().toLowerCase();

                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later.
                    Translator translator = new JSONTranslator();

                    String result = translator.translate(country, language);
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);

                }

            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(countryPanel);
            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
