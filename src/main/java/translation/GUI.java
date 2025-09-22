package translation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Load translator
            JSONTranslator translator = new JSONTranslator();



            // -------------------- Country Scrollable List --------------------
            JPanel countryPanel = new JPanel();
            DefaultListModel<String> countryModel = new DefaultListModel<>();
            for (String countryCode : translator.getCountryCodes()) {
                countryModel.addElement(countryCode);
            }

            JList<String> countryList = new JList<>(countryModel);
            countryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            JScrollPane countryScrollPane = new JScrollPane(countryList);
            countryScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            countryScrollPane.setPreferredSize(new Dimension(120, 150));

            countryPanel.add(new JLabel("Country:"));
            countryPanel.add(countryScrollPane);

            // -------------------- Language Dropdown --------------------
            JPanel languagePanel = new JPanel();
            JComboBox<String> languageDropdown = new JComboBox<>(
                    translator.getLanguageCodes().toArray(new String[0])
            );
            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(languageDropdown);

            // -------------------- Translation Display --------------------
            JPanel resultPanel = new JPanel();
            JLabel resultLabel = new JLabel("Select a language and country");
            resultPanel.add(new JLabel("Translation:"));
            resultPanel.add(resultLabel);

            // -------------------- Update translation when selection changes --------------------
            Runnable updateTranslation = () -> {
                String selectedCountry = countryList.getSelectedValue();
                String selectedLanguage = (String) languageDropdown.getSelectedItem();
                if (selectedCountry != null && selectedLanguage != null) {
                    String translation = translator.translate(selectedCountry, selectedLanguage);
                    if (translation == null) {
                        translation = "No translation found!";
                    }
                    resultLabel.setText(translation);
                }
            };

            // Listeners
            countryList.addListSelectionListener(e -> updateTranslation.run());
            languageDropdown.addActionListener(e -> updateTranslation.run());

            // -------------------- Main Panel --------------------
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(countryPanel);
            mainPanel.add(languagePanel);

            mainPanel.add(resultPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
