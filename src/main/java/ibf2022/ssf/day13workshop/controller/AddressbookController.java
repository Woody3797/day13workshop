package ibf2022.ssf.day13workshop.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ibf2022.ssf.day13workshop.model.Contact;
import ibf2022.ssf.day13workshop.util.Contacts;
import jakarta.validation.Valid;

@Controller
@RequestMapping(path = "/contact")
public class AddressbookController {

    @Autowired
    private Contacts contacts;

    @Autowired
    private ApplicationArguments appArgs;

    @Value("${day13workshop.default.data.dir}")
    private String defaultDataDir;

    @GetMapping
    public String showAddressBookForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "addressbook";
    }

    @PostMapping
    public String saveContact(@Valid Contact contact, BindingResult binding, Model model) throws IOException {
        if (binding.hasErrors()) {
            return "addressbook";
        }
        contacts.saveContact(contact, model, appArgs, defaultDataDir);
        return "showcontact";
    }

    @GetMapping(path = "{contactID}")
    public String getContactID(Model model, @PathVariable String contactID) throws IOException {
        contacts.getContactByID(model, contactID, appArgs, defaultDataDir);
        return "showcontact";
    }

    @GetMapping(path = "/list")
    public String getAllContacts(Model model) {
        contacts.getAllContacts(model, appArgs, defaultDataDir);
        return "contacts";
    }
}
