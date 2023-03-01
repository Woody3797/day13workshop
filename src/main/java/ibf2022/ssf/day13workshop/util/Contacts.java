package ibf2022.ssf.day13workshop.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import ibf2022.ssf.day13workshop.model.Contact;

@Component("contacts")
public class Contacts {
    
    public void saveContact(Contact contact, Model model, ApplicationArguments appArgs, String defaultDataDir) throws IOException {
        String dataFilename = contact.getId();
        PrintWriter pw = null;
        FileWriter fw = new FileWriter(getDataDir(appArgs, defaultDataDir) + "/" + dataFilename);

        pw = new PrintWriter(fw);
        pw.println(contact.getName());
        pw.println(contact.getEmail());
        pw.println(contact.getPhoneNumber());
        pw.println(contact.getDateOfBirth());
        pw.close();

        model.addAttribute("contact", contact);
    }

    public void getContactByID(Model model, String contactID, ApplicationArguments appArgs, String defaultDataDir) throws IOException {
        Contact contact = new Contact();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Path filePath = new File(getDataDir(appArgs, defaultDataDir) + "/" + contactID).toPath();
        Charset charset = Charset.forName("UTF-8");
        List<String> stringValues = Files.readAllLines(filePath, charset);
        contact.setId(contactID);
        contact.setName(stringValues.get(0));
        contact.setEmail(stringValues.get(1));
        contact.setPhoneNumber(stringValues.get(2));
        LocalDate dob = LocalDate.parse(stringValues.get(3), dtf);
        contact.setDateOfBirth(dob);

        model.addAttribute("contact", contact);
    }

    private String getDataDir(ApplicationArguments appArgs, String defaultDataDir) {
        String dataDirResult = "";
        List<String> optValues = null;
        String[] optValuesArr = null;
        Set<String> optNames = appArgs.getOptionNames();
        String[] optNamesArr = optNames.toArray(new String[optNames.size()]);

        if (optNamesArr.length > 0) {
            optValues = appArgs.getOptionValues(optNamesArr[0]);
            optValuesArr = optValues.toArray(new String[optValues.size()]);
            dataDirResult = optValuesArr[0];
        } else {
            dataDirResult = defaultDataDir;
        }

        return dataDirResult;
    }

    private Set<String> listFiles(String dir) {
        return Stream.of(new File(dir).listFiles()).filter(file -> !file.isDirectory()).map(File::getName).collect(Collectors.toSet());
    }

    public void getAllContacts(Model model, ApplicationArguments appArgs, String defaultDataDir) {
        Set<String> dataFiles = listFiles(getDataDir(appArgs, defaultDataDir));
        model.addAttribute("contact", dataFiles);
    }

}
