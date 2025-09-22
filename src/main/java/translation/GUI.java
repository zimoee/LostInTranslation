package translation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {
    public static LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();
    public static CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Load translator
            JSONTranslator translator = new JSONTranslator();

            // -------------------- Country Scrollable List --------------------
            JPanel countryPanel = new JPanel();
            DefaultListModel<String> countryModel = new DefaultListModel<>();
            java.util.HashMap<String, String> countryNameToCode = new java.util.HashMap<>();
            for (String countryCode : translator.getCountryCodes()) {
                String country = countryCodeConverter.fromCountryCode(countryCode);
                countryModel.addElement(country);
                countryNameToCode.put(country, countryCode);
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
            DefaultComboBoxModel<String> languageModel = new DefaultComboBoxModel<>();
            java.util.HashMap<String, String> languageNameToCode = new java.util.HashMap<>();
            for (String languageCode : translator.getLanguageCodes()) {
                String language = languageCodeConverter.fromLanguageCode(languageCode);
                languageModel.addElement(language);
                languageNameToCode.put(language, languageCode);
            }
            JComboBox<String> languageDropdown = new JComboBox<>(languageModel);
            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(languageDropdown);

            // -------------------- Translation Display --------------------
            JPanel resultPanel = new JPanel();
            JLabel resultLabel = new JLabel("Select a language and country");
            resultPanel.add(new JLabel("Translation:"));
            resultPanel.add(resultLabel);

            // -------------------- Update translation when selection changes --------------------
            Runnable updateTranslation = () -> {
                String selectedCountryName = countryList.getSelectedValue();
                String selectedLanguageName = (String) languageDropdown.getSelectedItem();
                if (selectedCountryName != null && selectedLanguageName != null) {
                    String countryCode = countryNameToCode.get(selectedCountryName);
                    String languageCode = languageNameToCode.get(selectedLanguageName);
                    String translation = translator.translate(countryCode, languageCode);
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
            mainPanel.add(languagePanel);
            mainPanel.add(resultPanel);
            mainPanel.add(countryPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
